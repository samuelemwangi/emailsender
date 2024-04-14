FROM gradle:8.7-jdk17-alpine AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew /app/
COPY gradle /app/gradle
RUN gradle dependencies
COPY . .
RUN gradle build --no-daemon -x test

RUN jlink \
--compress 2 \
--strip-java-debug-attributes \
--no-header-files \
--no-man-pages \
--output /jre \
--add-modules ALL-MODULE-PATH


FROM alpine:latest AS runtime
WORKDIR /app

RUN adduser --disabled-password \
  --home /app \
  --gecos '' appuser && chown -R appuser /app
RUN apk upgrade musl

USER appuser
COPY --from=build /jre /jre
COPY --from=build /app/build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["/jre/bin/java", "-jar", "app.jar"]
