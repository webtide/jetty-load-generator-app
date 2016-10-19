# jetty-load-generator-app

Embeded app for Jetty Load Testing

Build: `mvn clean package -DskipTests -Djetty.version=9.3.13.M0`

-Djetty.version= the version version you want to include in the uber jar

Start: `java -jar target/app-0.0.1-SNAPSHOT.jar --port=9090`

When using random port 0, the local port is write in a file called jetty.local.port

File upload: `curl -X PUT -d @pom.xml http://localhost:9090/upload`





