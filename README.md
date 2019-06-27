## Usage

##### To list all the occurrences/frequencies in descending order 
```bash
$ mvn test
```

##### Top N occurrences/frequencies from a given path, in silent-mode (change the command line arguments for different results)
```bash
$ java   -jar target/counter-0.0.1-jar-with-dependencies.jar 20 target/test-classes/files
```

##### Top N occurrences/frequencies from a given path, in verbose-mode (change the command line arguments for different results)   
```bash
$ java  -Dlog4j.configuration=file:target/classes/log4j_dev.properties -jar target/counter-0.0.1-jar-with-dependencies.jar 20 target/test-classes/files
```

## Build
```bash
$mvn clean  package
```
