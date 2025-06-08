# =================================================================
# ESTÁGIO 1: Build da Aplicação com Maven
# =================================================================
# Usamos uma imagem com Maven e JDK 21 para compilar o projeto
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Clona o seu repositório do GitHub
# ❗ ATENÇÃO: Substitua pela URL correta do seu repositório!
RUN git clone https://github.com/LuizaCrumenauer/CRUD-Adocao-Docker.git .

# Executa o build com Maven para gerar o arquivo .war
# A flag -DskipTests pula a execução de testes para agilizar o build
RUN mvn clean package -DskipTests

# =================================================================
# ESTÁGIO 2: Ambiente de Execução com WildFly
# =================================================================
# Usamos a imagem oficial do WildFly 36 com JDK 21
FROM jboss/wildfly:36.0.1.Final-jdk21

# Copia o arquivo .war gerado no estágio anterior para a pasta de deployments do WildFly
# ❗ ATENÇÃO: O nome do arquivo .war deve ser o mesmo gerado na pasta 'target' do seu projeto.
# O seu parece ser 'sistemaAdocao.war' pela imagem. Se for diferente, ajuste o nome abaixo.
COPY --from=build /app/target/sistemaAdocao.war /opt/jboss/wildfly/standalone/deployments/

# A imagem base do WildFly já tem o ENTRYPOINT correto para iniciar o servidor na porta 8080.
# O comando é: "/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0"
# Não precisamos definir um ENTRYPOINT novo.