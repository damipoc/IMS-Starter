Coverage: 71%
# IMS Project

Simple java database system using CRUD and different methods to have functionality.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them


[Git Bash](https://git-scm.com/downloads)

[MySQL Workbench](https://dev.mysql.com/downloads/workbench/) 

[Java 1.8](https://www.oracle.com/uk/java/technologies/javase/javase8-archive-downloads.html) 

[Maven](https://maven.apache.org/download.cgi) 



### Installing

A step by step series of examples that tell you how to get a development env running

To build a version of this project, you must first git clone this repo and enter the target folder. After that open cmd and type ```mvn clean package```, once done type ```cd target``` and finally ```java -jar ims-0.0.1-jar-with-dependencies```

## Running the tests

Using your IDE you can access the test files in src/test/java. 
Those test files can be tested with JUnit. 

### Unit Tests 

Unit tests test different aspects of the program, specifically the domain, dao and controllers. 
The test run with set results expected and only pass when expected result is achieved with each test.
Mockito is used for tests that require inputs.


## Built With

* Version Control System - [Git](https://git-scm.com/)
* Source Code Management - [GitHub](https://github.com/)
* Kanban Board - [Jira](https://www.atlassian.com/software/jira)
* Database - [MySQL Server](https://dev.mysql.com/downloads/mysql/)
* Back-end Programming Language - [Java](https://www.java.com/en/)
* Build Tool - [Maven](https://maven.apache.org/)
* Unit Testing - [JUnit](https://junit.org/junit5/)

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Damian Poclitar** - *Updated project* - [damipoc](https://github.com/damipoc)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Thank you to Jordan and different other trainers at QA for in depth teaching of the different tools and languages used in the project.
* Stackoverflow is a life saver.
