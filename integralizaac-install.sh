#!/bin/bash

echo "["$(date +'%d/%m/%Y %X%Z')"] Instalando o docker" 
apt-get install docker-ce

echo "["$(date +'%d/%m/%Y %X%Z')"] Instalando o docker-compose" 
apt-get install docker-compose

echo "["$(date +'%d/%m/%Y %X%Z')"] Instalando o maven" 
apt-get install maven

echo "["$(date +'%d/%m/%Y %X%Z')"] Empacotando a aplicação" 
mvn install

echo "["$(date +'%d/%m/%Y %X%Z')"] Subindo os serviços necessários par a aplicação"
docker-compose up -d

echo "["$(date +'%d/%m/%Y %X%Z')"] Aplicando script seed!"
docker cp ./target/integralizaac/WEB-INF/classes/script_inicial_integralizaac.sql mypostgres:/home/dump
docker exec mypostgres psql -U postgres -d integralizaac -f /home/dump/script_inicial_integralizaac.sql

echo "["$(date +'%d/%m/%Y %X%Z')"] Feito!"

