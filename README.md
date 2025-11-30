# API Testing with Cucumber

### **Test Execution Evidence** :
- https://docs.google.com/document/d/1HcXvmKsgwLjeMsqUTeoVnI7FNZcXj1suIsl09CMMaCg/edit?usp=sharing

### **Set Up Process of The Project** :
#### **1. Tools Required**
- Install Java Development Kit (JDK)
- IDE : Intellij IDEA 
- Install Gradle -> Mainly for dependency management
- Cucumber Plugin on Intellij -> recognize .feature file to be connected with steps.java through runner.java
- Rest-assured dependency -> library to get the response
- Cucumber + JUnit Platform Engine dependency -> execute behavior-driven tests on top of JUnit 5
- JSON Schema Validator dependency -> To verify that the API response matches the expected JSON schema

#### **2. Project Structure**
Leave the src/main empty because this is not a project to build an application but a project to test automation suite

- POST, GET, DELETE, AND PUT steps are centralized in UserApiSteps and user_api.feature because they're still related to User data
- POST and PUT schema have the same structure so I used the same schema instead
```
src
 └── test
     ├── java
     │    └── com.example
     │         ├── runners
     │         │    └── CucumberTestRunner.java
     │         └── steps
     │              ├── UserApiSteps.java
     │    
     │
     └── resources
          ├── features
          │      ├── user_api.feature
          │      
          │      
          └── schemas
                 ├── post-put_schema.json
                 ├── list_of_post_schema.json
                 └── delete_schema.json
```
#### **3. Cucumber Test Runner**
- ``@SelectClasspathResource("features")`` -> Where to find feature files
- ``@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.steps")`` -> To find step definitions
- ``@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, summary")`` -> To format the test output

#### **4. Executing the Tests**
On CucumberTestRunner.java click Run button 
or
``./gradlew test``