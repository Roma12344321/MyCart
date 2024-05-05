package com.martynov.spring.api;

import com.martynov.spring.dto.AuthenticationDto;
import com.martynov.spring.dto.PersonDto;
import com.martynov.spring.entity.Person;
import com.martynov.spring.mapper.Mapper;
import com.martynov.spring.security.JWTUtil;
import com.martynov.spring.service.RegistrationService;
import com.martynov.spring.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final Mapper<Person,PersonDto> mapper;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDto personDto, BindingResult bindingResult) {
        Person person = mapper.mapDtoToEntity(personDto,Person.class);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return Map.of("message", "error");
        }
        registrationService.register(person);
        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt_token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDto.getUsername(), authenticationDto.getPassword()
        );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "incorrect credentials");
        }
        String token = jwtUtil.generateToken(authenticationDto.getUsername());
        return Map.of("jwt_token", token);
    }
}