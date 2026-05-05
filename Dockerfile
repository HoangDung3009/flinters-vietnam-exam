# Stage 1: Build stage
FROM maven:3.8.1-openjdk-11 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src/ ./src/

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/csv-analytics.jar .

# Copy sample data
COPY data/ ./data/

# Create output directory
RUN mkdir -p output

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Default command - can be overridden
ENTRYPOINT ["java", "-jar", "csv-analytics.jar"]
CMD ["data/input.csv", "output/"]
