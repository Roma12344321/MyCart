package com.martynov.spring.service;

import com.martynov.spring.entity.Category;
import com.martynov.spring.entity.Good;
import com.martynov.spring.repositories.GoodRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;
    private final CategoryService categoryService;
    private final EntityManager entityManager;

    @Value("${image_dir}")
    private String IMAGE_DIR;
    private static final int INITIAL_ID = 0;

    @Transactional(readOnly = true)
    @Cacheable(value = "goods", key = "#page + '_' + #goodPerPage")
    public List<Good> findAllGoodWithCommentAndLikeCount(int page, int goodPerPage) {
        Session session = entityManager.unwrap(Session.class);
        List<Object[]> resultList = session.createQuery(
                        "SELECT g, " +
                                "(SELECT COUNT(l) FROM Like l WHERE l.good.id = g.id), " +
                                "(SELECT COUNT(c) FROM Comment c WHERE c.good.id = g.id) " +
                                "FROM Good g left join fetch g.category",
                        Object[].class
                )
                .setFirstResult(page * goodPerPage)
                .setMaxResults(goodPerPage)
                .getResultList();

        List<Good> goodList = new ArrayList<>();
        for (Object[] result : resultList) {
            Good good = (Good) result[0];
            good.setLikeCount(((Long) result[1]).intValue());
            good.setCommentCount(((Long) result[2]).intValue());
            goodList.add(good);
        }
        return goodList;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "good_by_id_with_comment", key = "#id")
    public Good findByIdWithComments(int id) {
        Good good = findById(id);
        Hibernate.initialize(good.getComments());
        return good;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "good_by_id", key = "#id")
    public Good findByIdWithOutComments(int id) throws RuntimeException {
        return findById(id);
    }

    private Good findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Object[] result = session.createQuery(
                        "SELECT g, " +
                                "(SELECT COUNT(l) FROM Like l WHERE l.good.id =:goodId), " +
                                "(SELECT COUNT(c) FROM Comment c WHERE c.good.id =:goodId) " +
                                "FROM Good g left join fetch g.category where g.id=:goodId",
                        Object[].class
                ).setParameter("goodId", id)
                .getSingleResultOrNull();

        if (result == null) {
            throw new RuntimeException();
        }
        Good good = (Good) result[0];
        good.setLikeCount(((Long) result[1]).intValue());
        good.setCommentCount(((Long) result[2]).intValue());
        return good;
    }

    @Transactional
    public void save(Good good, Category category) {
        good.setCategory(category);
        category.getGoodList().add(good);
        goodRepository.save(good);
    }

    @Transactional
    @CacheEvict(value = {"goods", "good_by_id", "good_by_id_with_comment"}, allEntries = true)
    public void deleteById(int id) {
        Optional<Good> goodOptional = goodRepository.findById(id);
        if (goodOptional.isPresent()) {
            Good good = goodOptional.get();
            String imagePath = good.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    Files.deleteIfExists(Paths.get(imagePath));
                } catch (IOException e) {
                    System.err.println("Failed to delete image file: " + e.getMessage());
                }
            }
            goodRepository.deleteById(id);
        } else {
            System.err.println("Good with ID " + id + " not found");
        }
    }

    @Transactional(readOnly = true)
    public Resource getGoodImage(@PathVariable int id) {
        Good good = findById(id);
        if (good != null && good.getImagePath() != null) {
            try {
                Path filePath = Paths.get(good.getImagePath());
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Transactional
    @CacheEvict(value = {"goods", "good_by_id", "good_by_id_with_comment"}, allEntries = true)
    public void create(Good good, int id, MultipartFile imageFile) {
        good.setId(INITIAL_ID);
        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = IMAGE_DIR;
                String originalFilename = imageFile.getOriginalFilename();
                String fileName = UUID.randomUUID() + "-" + (originalFilename != null ? originalFilename : "file");
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                good.setImagePath(path.toString());
            } catch (IOException e) {
                good.setImagePath(null);
            }
        }
        Category categoryFromDb = categoryService.findById(id);
        save(good, categoryFromDb);
    }

    @Transactional
    public void patchGood(int id, Good good, int catId) {
        Good goodFromDb = findById(id);
        Category category = categoryService.findById(catId);
        goodFromDb.setName(good.getName());
        goodFromDb.setPrice(good.getPrice());
        goodFromDb.setDescription(good.getDescription());
        goodFromDb.setCategory(good.getCategory());
        goodFromDb.setImagePath(good.getImagePath());
        goodFromDb.setCategory(category);
    }
}