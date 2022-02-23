# build
FROM maven:3.8.4-openjdk-17 AS build
COPY notifications-server/src /home/app/src
COPY notifications-server/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# package
FROM maven:3.8.4-openjdk-17
COPY --from=build /home/app/target/notifications-server-1.0.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]