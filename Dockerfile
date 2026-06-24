# Build mərhələsi: Gradle vasitəsilə jar faylını hazırlayırıq
FROM gradle:8.6-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle clean bootJar -x test

# Run mərhələsi: Yalnız JDK mühitində tətbiqi başladırıq
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Gradle jar faylını build/libs qovluğuna yığır
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]