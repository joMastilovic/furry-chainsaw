# rezdy lunch compiler

The application requires Java 11. Make sure that env variable JAVA_HOME is set.

To package into .jar archive and launch the application execute `mvn package && java -jar ./target/recipes.jar` 

Application is deployed to localhost:8080. Try GET localhost:8080/lunch.

If needed, change port in ./src/main/resources/application.properties and repeat the command

To start docker container execute
`mvn package && docker build -t rezdy . && docker run --rm -p 8080:8080 rezdy`
