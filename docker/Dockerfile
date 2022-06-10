FROM maven:3.5.4-jdk-8-alpine as builder

COPY ./legacy/pom.xml ./pom.xml
COPY ./legacy/src ./src
RUN mvn clean package

FROM tomcat:9.0-alpine
COPY --from=builder /target/EShop-1.0.0.war /usr/local/tomcat/webapps/
COPY ./legacy/conf/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

EXPOSE 8080
