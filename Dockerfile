FROM maven:3.9.11-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

ENV BASE_URL=https://fakerestapi.azurewebsites.net

CMD mvn clean test allure:report -Dbase.url=${BASE_URL}
