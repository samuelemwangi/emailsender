FROM openjdk:17-alpine AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew /app/
COPY gradle /app/gradle
RUN ./gradlew dependencies
COPY . .
RUN ./gradlew build --no-daemon -x test

RUN jlink \
--compress 2 \
--strip-java-debug-attributes \
--no-header-files \
--no-man-pages \
--output /jre \
--add-modules ALL-MODULE-PATH


FROM alpine:3.16 AS runtime
WORKDIR /app
COPY --from=build /jre /jre
COPY --from=build /app/build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["/jre/bin/java", "-jar", "app.jar"]