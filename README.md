# N26 Coding test
Source code for the service that can be used to save transactions based on timestamp(i.e save only those transaction within 60 seconds) and fetch stats for transactions which occured during Last 60 seconds.

This service is built using Spring boot and have been developed using STS.

### Design used

1. For storing data i have used ConcurentHashMap, This is will provide thread safety

2. Java 8 provides the option on Map object to compute, which means you can append and store new values in map

3. Idea here is to pre append and keep the computed values during saving transaction

4. The precomputed StatsData stored in Map, Has key as seconds and StatsData as values, Hence the storage will take O(1)

5. During fetching statistic , we have to just have to ignore timestamp > 60 , So the data fetched will be in O(1)


### Steps to run application

This project is spring boot project so its fairly simple to run. 

* ###### Either import project in STS and run as Spring boot

* ###### You can also run the application using command line by executing

Build the project

```
mvn clean install
```

Run the project

```
mvn spring-boot:run
```

You can access endpoints at `http://localhost:8080/` or `http://127.0.0.1:8080/`

### Unit test cases

In this project for Unit Testing `JUnint` is used and for mocking of calls and variables `Mockito` and some places reflection classes have been used 

###### To run the application execute

```java
mvn test
```
