# Co-Dev-Assignment2

## **Context**

### _Opening Statement & Summary_

Create a responsive web application with the following functionalities:
1. Upload a CSV file with appropriate feedback to the user on the upload progress.
2. List the data uploaded with pagination.
3. Search data from the uploaded file.

Technologies to use: 
* Front-End: React - Typescript
* Back-End: Springboot - Kotlin


## Demo
No live demo for this project.

___
## **Content: Implemented Functionalities**

Web-App preview:

![0-1](/README-Assets/0-1.jpg)


### 1. Upload a CSV file with appropriate feedback to the user on the upload progress.
 - To the left of the Web-App, is where the upload button and progress bar is placed.
![1-1](/README-Assets/1-1.jpg)

- When clicked on "Browse...", you are able to pick which CSV file to upload.
![1-2]


### 2. List the data uploaded with pagination.

### 3. Search data from the uploaded file.






___
## **Local Build**
### Spring Boot
1. Build Project
```
./gradlew build
```
2. Run Project
```
./gradlew bootrun
```
___
### mySQL
1. Make sure port is to default 3306

```
http://localhost:3306/employees
```
2. Create Database
```
CREATE DATABASE employees
    DEFAULT CHARACTER SET = 'utf8mb4';
```
3. Create Table
```
CREATE TABLE `employees`.`employee` (
  `id` VARCHAR(255) NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `salary` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE);
```
___
### Angular
1. General start for Angular.

```
ng serve
```
___
## Technologies Used
- Springboot
  - starter
  - data-keyvalue
  - web
  - data
  - jdbc
- Mysql 
- Commons CSV
- JSONPatch
- lombok
- assertJ