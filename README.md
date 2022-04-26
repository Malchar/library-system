# Library System
Paul Schmitz 2022 

## Description
Library System is a web app for libraries. It includes accessible controls, user self-service, and inventory management. Clients can freely browse available books, quickly create an account, checkout, and return items. Employees can  track and update inventory. Please contact me at p_schmitz@email.com for any questions or inquiries.

## Table of Contents
- [Technology](#technology)
- [Schema](#schema)
- [Screenshots](#screenshots)

## Technology

The app launches a Tomcat webserver which runs a Springboot JPA application. It connects to an SQL database which holds all of the library entities such as users and books.

The frontend consists of websites that allow users to create accounts and login. Then, they can search, view, and checkout books. After books are checked out, the users can return books and also view previous orders. Admin users can view all past orders, edit/delete books, and edit/delete inventory items. The frontend also includes javascript, jstl, bootstrap, and css.

The backend is in java using spring JPA. It makes spring queries, non-native HQL queries, and native SQL queries. The code also incorporates concepts such as streams, logging, lambda functions, and junit jupiter test cases.

* JSP/CSS/Javascript
* [JQuery](https://api.jquery.com/)
* [mySQL](https://dev.mysql.com/doc/)
* [Bootstrap](https://getbootstrap.com/)
* [Java](https://docs.oracle.com/en/java/)
* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Junit](https://junit.org/junit5/docs/current/user-guide/)
* [Maven](https://maven.apache.org/guides/index.html)
* [Lombok](https://projectlombok.org/features/all)
* [Bcrypt](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html)
* [Log4j](https://www.slf4j.org/docs.html)
* [Spring Security](https://docs.spring.io/spring-security/reference/index.html)

## Schema
![Schema](https://github.com/Malchar/library-system/blob/58c2cd7b20950c8c1a12827e006c2c1d001dfc04/schema%20v6.png)

## Screenshots

<img src="https://github.com/Malchar/library-system/blob/877f6439e5180c16f8f6f182fb1e3b977f62062c/screenshots/sc1.png">
<img src="https://github.com/Malchar/library-system/blob/877f6439e5180c16f8f6f182fb1e3b977f62062c/screenshots/sc2.png">
<img src="https://github.com/Malchar/library-system/blob/877f6439e5180c16f8f6f182fb1e3b977f62062c/screenshots/sc3.png">
<img src="https://github.com/Malchar/library-system/blob/877f6439e5180c16f8f6f182fb1e3b977f62062c/screenshots/sc4.png">
