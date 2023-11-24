# End Of Line

## EOL UML
![UML of END OF LINE](https://github.com/gii-is-DP1/DP1--2023-2024-l4-4/assets/91957427/dedf74c3-6aa0-439c-94c8-4c3737b0379e)

If UML doesn't view correctly, you can view it [here](https://ibb.co/0DMfSrC).




## Running EOL backend locally
EOL is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:

```
git clone https://github.com/gii-is-DP1/DP1--2023-2024-l4-4
cd DP1--2023-2024-l4-4
./mvnw package
java -jar target/*.jar
```

You can then access EOL backend here: [http://localhost:8080/](http://localhost:8080/swagger-ui/index.html)



Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```
## Database configuration

In its default configuration, EOL uses an in-memory database (H2) which
gets populated at startup with data. The INSERTs are specified in the file data.sql.

## Working with EOL in your IDE

### Prerequisites
The following items should be installed in your system:
* Java 17 or newer.
* Node.js 18 or newer.
* git command line tool (https://help.github.com/articles/set-up-git)
* Your preferred IDE 
  * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
  not there, just follow the install process here: https://www.eclipse.org/m2e/
  * [Spring Tools Suite](https://spring.io/tools) (STS)
  * IntelliJ IDEA
  * [VS Code](https://code.visualstudio.com)

### Steps:

1) On the command line
```
git clone https://github.com/gii-is-DP1/DP1--2023-2024-l4-4
```
2) Inside Eclipse or STS
```
File -> Import -> Maven -> Existing Maven project
```

Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA

In the main menu, choose `File -> Open` and select the Petclinic [pom.xml](pom.xml). Click on the `Open` button.

CSS files are generated from the Maven build. You can either build them on the command line `./mvnw generate-resources`
or right click on the `EndOfLine` project then `Maven -> Generates sources and Update Folders`.

A run configuration named `EndOfLineApplication` should have been created for you if you're using a recent Ultimate
version. Otherwise, run the application by right clicking on the `EndOfLineApplication` main class and choosing
`Run 'EndOfLineApplication'`.

4) Navigate to EOL
Visit [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) in your browser.

## Starting the frontend

The EOL is implemented with a React frontend in the folder named "frontend".
You can start the development server to see frontend using the command (maybe you should use the command npm insall prior to this):
```
npm start
```
You can then access the EOL frontend at [http://localhost:3000](http://localhost:3000)
