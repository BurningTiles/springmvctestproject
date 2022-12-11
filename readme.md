
# Spring MVC Application
A project created using spring MVC architecture/framework with Create, Read, Update and Delete operations.

This project mainly focus on the way of implementation, logic and use of Spring to understand spring in better manner which can help to learn creating realworld big projects.

This project uses MongoDB as a database and storing data on MongoDB Atlas online database.

### Instructions to run
- Make sure to have Java IDE with Lombok plugin installed in it
- If lombok is not installed then [check here](https://www.geeksforgeeks.org/introduction-to-project-lombok-in-java-and-how-to-get-started/)
- Install dependencies (Usually it'll be automatically installed)
- Run project or run SpringMvcUsersApplication class
- Open any browser and visit "localhost"
- If port is not available or occupied then change port in application.properties

### Features/Implementations
- Sprint MVC Application
- Contains page with form
- Contains more than 3 fields
- Form is validated on server side
- Content get saved on database and can be viewed
- List of data can be viewed which uses Thymeleaf library
- Can filter data by searching by name, email, education or country
- An additional api to know each page hit count
- Page hit api called asynchronously every 3 sec and result displayed on every pages
- Spring dependencies used in multiple classes as per requirements
- Using lombok for model classes
- Simple and userfriendly view of frontend
- Includes required testcases to maintain/develop code without breaking old functionalities

### Libraries used
- **Spring boot starter web** : To use basic spring functionalities to build the project
- **Spring boot devtools** : To improve productivity/development by fast reload and other testing functionalities
- **Spring boot starter test** : To create unit tests and test the application while development
- **Spring boot starter validation** : To validate the form data in better manner and handle those validation and errors
- **Spring boot starter data mongodb** : To connect with MongoDB and store data in database in efficient manner
- **Spring boot starter thymeleaf** : To generate dynamic frontend webpages for user and make it more interactive
- **Lombok** : To make development faster by elemenating boilerplate codes with simple annotations 


---
#### Developer: Akshat