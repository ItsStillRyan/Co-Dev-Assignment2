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

___

## Demo
No live demo for this project.

___
## **Content: Implemented Functionalities**

Web-App preview:

![0-1](/README-Assets/0-1.jpg)


### 1. Upload a CSV file with appropriate feedback to the user on the upload progress.
 - To the left of the Web-App, is where the upload button and progress bar is placed. The "Upload" button will be disabled as no files are selected

![1-1](/README-Assets/1-1.jpg)

- When clicked on "Browse...", you are able to pick which CSV file to upload.

![1-2](/README-Assets/1-2.jpg)

- After selecting your CSV file, the file name should be previewed and the "Upload" button will be enabled for upload.

![1-3](/README-Assets/1-3.jpg)

- When "Upload" button is triggered,it will disable and a progress circle will replace it. This is to prevent multiple uploads of the same file. A progress text "Rows uploaded:" will appear as well, showing how many rows of data is uploaded into the database, this is a live update and will update every 1/2 second.

![1-4](/README-Assets/1-4.jpg)



### 2. List the data uploaded with pagination.
 - The table will be dynamically updated as the data uploads into the database. At this point, it is possible to interact with the table. 

 ![2-1](/README-Assets/2-1.jpg)

 - Below the table, there is an option to select 10, 25, 100 or 500 rows to be displayed at a page.

 ![2-2](/README-Assets/2-2.jpg)

 - To the right of that, there is pagination option to select which set of data you would like to see

 ![2-3](/README-Assets/2-3.jpg)

### 3. Search data from the uploaded file.

- At the top of the Table, there is a seach bar. This search bar will search through **all** data from all columns.

![3-1](/README-Assets/3-1.jpg)

- For example, if you wanted to search for the Country of France, input "France" and Only France data will be displayed

![3-2](/README-Assets/3-2.jpg)

___
## Back-End

### Big data File Upload:

For these big data file uploads, I used:
1. Kotlin's Coroutines
2. Batch Uploads


to make the upload as efficient as possible.
 

A known bottleneck in my application is that I am using mySQL as a database. For big data, perhaps it is better to use a NoSQL document-based database like Apache Hbase, but I chose to stick with mySQL for familiarity with the implementation.


___
## **Local Build**
### Spring Boot
- Using IntelliJ as it has native Kotlin Support
1. Right Click on your application.kt file, select Run
![4-1](/README-Assets/4-1.jpg)

___
### mySQL
1. Make sure port is to default 3306

```
http://localhost:3306/invoices
```
2. Create Database
```
CREATE DATABASE invoices
    DEFAULT CHARACTER SET = 'utf8mb4';
```
3. Create Table
```
CREATE TABLE `invoices`.`invoices` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `invoice_no` VARCHAR(255) NULL,
  `stock_code` VARCHAR(255) NULL,
  `description` VARCHAR(255) NULL,
  `quantity` INT NULL,
  `invoice_date` TIMESTAMP NULL,
  `unit_price` DOUBLE NULL,
  `customerID` VARCHAR(255) NULL,
  `country` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));
```
___
### React
1. General start for React.

```
npm install
```
```
npm start
```
___
## Technologies Used
- Springboot
  - Kotlin
  - starters
  - mySQL
  - junit-jupiter
  - OpenCSV
  - Kotlinx-Coroutines
  - mockK
- React
  - TypeScript
  - MaterialUI React
  - Bootstrap
  - Axios




		
		
		
		