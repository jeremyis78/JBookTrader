#Developing with JBookTrader

JBookTrader is built with Java 8.  
  
Development cycle
NOTE: https://mvnrepository.com/artifact/com.interactivebrokers.tws/tws-api/9.72.05 
NOTE: The latest tws api version (9.72.05) fails to compile

```bash
mvn clean compile -Dtws.api.version=9.66
```
```bash
./run/JBookTrader.sh
```  
  
Packaged jar:
```bash
mvn clean package -Dtws.api.version=9.66
```  
```bash
java -jar target/JBookTrader-9.0.2.0-SNAPSHOT.jar .
```  

Release

```bash
BUILD_NUMBER=fromCIBuild
mvn deploy scm:tag -Drevision=${BUILD_NUMBER}
```

TODO: I ran 'mvn deploy scm:tag -Drevision=1' and it produced
a huge artifact (488M Apr 28 23:24 JBookTrader-JBookTrader-9.0.2.1.tar.gz)
which included historical_* directories which I want to exclude, but 
don't know how yet. The packaged/shaded jar appears the right 
size (472K Apr 28 23:19 original-JBookTrader-9.0.2.1.jar)
See https://github.com/jeremyis78/JBookTrader/releases