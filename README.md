# jetty-load-generator-app

Jetty Spring boot type app for Jetty Load Testing

Build: `mvn clean package -DskipTests -Djetty.version=9.3.13.M0`

-Djetty.version= the version version you want to include in the uber jar

Start: `java -jar target/app-0.0.1-SNAPSHOT.jar --port=9090`

File upload: `curl -vvv -X PUT  -F file=@pom.xml http://localhost:9090/upload`




