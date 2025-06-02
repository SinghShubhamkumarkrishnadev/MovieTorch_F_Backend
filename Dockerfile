FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

# Copy maven executable and configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies first (for better caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Runtime image
FROM eclipse-temurin:17-jre-alpine

# Add CA certificates and update them
RUN apk add --no-cache ca-certificates && update-ca-certificates

# Create a non-root user to run the application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Set volume for temporary files
VOLUME /tmp

# Copy application from build stage
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Set environment variables
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Run the application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.movieTorch.movieTorch.MovieTorchApplication"]

# Expose the application port
EXPOSE 8080
