
# na minha aplicacao uso Maven e JDK 21 para compilar o projeto
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# meu repositorio do gitHub
RUN git clone https://github.com/LuizaCrumenauer/CRUD-Adocao-Docker.git .

RUN mvn clean package -DskipTests

# imagem usada: WildFly 36 com JDK 21 - busquei essa imagem nas imagens oficiais
FROM quay.io/wildfly/wildfly:36.0.1.Final-jdk21

COPY --from=build /app/target/sistemaAdocao.war /opt/jboss/wildfly/standalone/deployments/

# a imagem base do WildFly já tem o ENTRYPOINT correto para iniciar o servidor na porta 8080.

