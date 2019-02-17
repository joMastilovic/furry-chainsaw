FROM openjdk:11
ADD target/recipes.jar app.jar
ENTRYPOINT exec java -jar /app.jar
