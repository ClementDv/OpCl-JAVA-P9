#### BUILD

FROM maven:3.6.3-adoptopenjdk-11 as build

COPY --chown=app . .
RUN mvn package -DskipTests

#### RUNTIME

FROM adoptopenjdk/openjdk15:alpine-jre as runtime

# Create app user
RUN addgroup -g 1000 app && \
    adduser -h /var/lib/app -u 1000 -G app -D app && \
    mkdir -p /app && \
    chown app:app /app
USER app
WORKDIR /app

CMD ["java", "-jar", "mediscreen-service.jar"]

#### SERVICES

# Assessment
FROM runtime as assessment
COPY --chown=app --from=build /assessment/target/assessment.jar mediscreen-service.jar

# Note
FROM runtime as note
COPY --chown=app --from=build /note/target/note.jar mediscreen-service.jar

# Patient
FROM runtime as patient
COPY --chown=app --from=build /patient/target/patient.jar mediscreen-service.jar
