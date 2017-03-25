import org.eclipse.jetty.load.generator.*

node('master') {

  def version = '0.0.1-SNAPSHOT'

  //stage 'Checkout'
  git url: 'https://github.com/olamy/jetty-load-generator-app.git'

  //stage 'Maven'
  // System Dependent Locations
  //def mvntool = tool name: 'maven3', type: 'hudson.tasks.Maven$MavenInstallation'
  //def jdktool = tool name: 'jdk8', type: 'hudson.model.JDK'

  // Environment
  //List mvnEnv = ["PATH+MVN=${mvntool}/bin", "PATH+JDK=${jdktool}/bin", "JAVA_HOME=${jdktool}/", "MAVEN_HOME=${mvntool}"]
  List mvnEnv = []
  mvnEnv.add("MAVEN_OPTS=-Xms256m -Xmx1024m -Djava.awt.headless=true")
  withEnv(mvnEnv) {
    timeout(15) {
      sh "mvn -B clean package"
    }
  }

  archive 'target/app-${version}.jar'

  dir ('target') {
    def jettyPort = 9090
    parallel firstBranch: {
      sh "java -jar app-${version}.jar --port=${jettyPort}"
    }, secondBranch: {
      //def port = readFile 'jetty.local.port'
      sleep 5
      def profile = new Resource( "index.html",
                                  new Resource( "/css/bootstrap.css",
                                                new Resource( "/css/bootstrap-theme.css" ),
                                                new Resource( "/js/jquery-3.1.1.min.js"),
                                                new Resource( "/js/jquery-3.1.1.min.js"),
                                                new Resource( "/js/jquery-3.1.1.min.js"),
                                                new Resource( "/js/jquery-3.1.1.min.js")
                                  ),
                                  new Resource( "/js/bootstrap.js" ,
                                                new Resource( "/js/bootstrap.js" ),
                                                new Resource( "/js/bootstrap.js" ),
                                                new Resource( "/js/bootstrap.js" )
                                  ),
                                  new Resource( "/hello" ),
                                  new Resource( "/dump.jsp?wine=foo&foo=bar" ),
                                  new Resource( "/not_here.html" ),
                                  new Resource( "/hello?name=foo" ),
                                  new Resource( "/hello?name=foo" ),
                                  new Resource( "/upload" ).method("PUT").requestLength(8192),

                                  );
      def transport = org.mortbay.jetty.load.generator.starter.LoadGeneratorStarterArgs.Transport.HTTP;

      def timeUnit = java.util.concurrent.TimeUnit.SECONDS;

      loadgenerator host: 'localhost', port: jettyPort, resourceProfile: profile, users: 1, transactionRate: 1, transport: transport, runningTime: 20, runningTimeUnit: timeUnit

      sh "curl http://localhost:${jettyPort}/stop"

    },
             failFast: true


  }







}
