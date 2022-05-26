docker build --tag=webshop-apache apache
docker tag webshop-apache fatorice/webshop-apache:latest
docker push fatorice/webshop-apache

docker build --tag=webshop-category-service -f docker/DockerfileCategory .
docker tag webshop-category-service fatorice/webshop-category-service:latest
docker push fatorice/webshop-category-service

docker build --tag=webshop-product-service -f docker/DockerfileProduct .
docker tag webshop-product-service fatorice/webshop-product-service:latest
docker push fatorice/webshop-product-service

docker build --tag=webshop-mysql-db-image -f docker/DockerfileMySQL .
docker tag webshop-mysql-db-image fatorice/webshop-mysql-db-image:latest
docker push fatorice/webshop-mysql-db-image