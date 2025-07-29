**Test Strategy — QA Item API Automation**

**Objective**
The purpose of this test strategy is to validate the functionality, reliability, and error handling of the backend APIs for user authentication and item service. The APIs under test include:

POST /login – Authenticate user and display success message

GET /items – Retrieve list of items

POST /items – Create a new item

PUT /items/:id – Update an existing item

DELET /items/:id - Delete item and return success message along with id


**Scope of Testing**
The testing will cover the following areas:

 Functional Testing – Verify that all API endpoints work as expected for valid requests.
 Negative Testing – Validate error handling for invalid inputs, unauthorized access, or missing parameters.
 Boundary Testing – Check behavior for edge cases (e.g., empty fields, large inputs).



**Test Approach and Tools**

- **Methodology**: Automated API testing using RestAssured and TestNG
- **Data Strategy**: Test data created dynamically; includes cleanup logic
- **Reporting**: ExtentReports provide detailed status with embedded request/response logs


| Tool            | Usage                                       |
|-----------------|---------------------------------------------|
| RestAssured     | HTTP request validation in Java             |
| TestNG          | Test orchestration and suite execution      |
| ExtentReports   | HTML reporting with visual cues             |
| Maven           | Dependency management and execution         |
| JsonPath        | JSON payload assertion    


**Test Coverage / Metrics** 

| Endpoint           		| Positive Scenarios               | Negative Scenarios              |
|---------------------------|----------------------------------|---------------------------------|
| `POST /items`      		| Valid item → 201                 | Missing fields → 400            |
| `GET /items`       		| Items exist → 200                | Empty list → 204                |
| `PUT /items/{id}` 		| Valid update → 200               | Invalid ID/body → 404/400       |
| `DELETE /items/{id}`	| Confirm deletion → 200           | Already deleted → 404           |
| `POST /login`      		| Auth → 200 + success message     | Invalid credentials → 401       |

**Test Execution**
Backend setup :
git clone https://github.com/ankita-churi/qa-api-app-nodejs.git
npm install && npm start

Automation suite setup:
git clone https://github.com/ankita-churi/restassured-api-automation-sample.git
cd restassured-api-tests
mvn clean test

Report available at test-output/ExtentReport.html


