# Builder Image
FROM gradle:jdk14 as builder

COPY build.gradle .
COPY src ./src

RUN gradle clean && gradle shadowJar

# Prod Image
FROM openjdk:15-alpine

COPY --from=builder /home/gradle/build/libs/gradle-version_1-all.jar \
        /sp-twitter-api.jar

CMD ["java", "-jar", "/sp-twitter-api.jar"]