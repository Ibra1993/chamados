
# Estágio 1: Build (Compilação com Maven/Gradle)
FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app

# Copie os arquivos de construção (pom.xml ou build.gradle + código-fonte)
COPY pom.xml .
COPY src ./src

# Instale o Maven (se usar Gradle, substitua pelo comando adequado)
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# Estágio 2: Runtime (Imagem final otimizada)
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copie o JAR gerado no estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Otimizações para containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+AlwaysPreTouch"

# Porta exposta (ajuste para a porta do seu Spring Boot)
EXPOSE 8080

# Comando de execução (substitua por ENTRYPOINT se preferir)
CMD ["java", "-jar", "app.jar"]