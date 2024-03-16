#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/MyCart-0.0.1-SNAPSHOT.jar \
    root@78.24.217.212:/root/

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa root@78.24.217.212 << EOF

pgrep java | xargs kill -9
nohup java -jar MyCart-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'