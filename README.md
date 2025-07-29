CRUD on Items REST API Test Suite
Automated tests for the CreateItem endpoint using RestAssured, designed to validate item creation, cleanup, and response structure. Developed in Java with integrated reporting via ExtentReports.
🔧 Technologies Used
- Java (JDK 11+)
- RestAssured for API testing
- TestNG as the test framework
- ExtentReports for rich test reporting
- Maven for dependency management

├── src/test/java/com/qa/tests/CreateItemTests.java      # Core test class
├── src/test/java/com/qa/utils/Utils.java                # JSON parsing helpers
├── src/test/java/com/qa/payload/PayloadBuilder.java     # Request payload builder
├── src/test/java/com/qa/config/BaseConfig.java          # Base URL and config
├── testng.xml                                           # TestNG suite config
└── README.md                                            # You're here!

⚙️ Prerequisite: Local API Server Setup
To run these tests successfully, you must clone and run the backend API locally. This test suite interacts with a Node.js app that exposes the /items endpoint.
git clone https://github.com/ankita-churi/qa-api-app-nodejs.git
cd qa-api-app-nodejs
npm install
npm start
