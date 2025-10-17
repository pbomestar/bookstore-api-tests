# Bookstore API Automation Tests

This project automates testing of the Bookstore API using Java, TestNG, and RestAssured. It verifies CRUD operations for the Books endpoints and generates detailed test reports using Allure. The framework is containerized with Docker and ready to integrate into a CI/CD pipeline.

## Setup Instructions

### Prerequisites
Ensure you have the following installed:
- Java 17 or higher
- Maven 3.9+
- Docker (for containerized execution)
- Git

### Clone the Repository
git clone https://github.com/pbomestar/bookstore-api-tests.git
cd bookstore-api-tests

## Running the Tests

### Run Locally (without Docker)
1. Install dependencies:
   mvn clean install
2. Run the tests:
   mvn clean test
3. Generate and view the Allure report:
   mvn allure:serve

### Run Tests in Docker
1. Build the Docker image:
   docker build -t bookstore-api-tests .
2. Run the container with tests:
   docker run --rm -e BASE_URL=https://fakerestapi.azurewebsites.net bookstore-api-tests

The container will automatically execute the test suite and print a summary of results in the console.

## Test Reports
After running the tests, Allure results will be generated under:
target/allure-results/

To generate and open the full HTML report:
mvn allure:serve

This report includes detailed results for passed, failed, and skipped tests with request and response details.

## CI/CD Integration
This project can be easily integrated into CI/CD pipelines using tools like GitHub Actions, Jenkins, or GitLab CI. A typical pipeline will:
1. Build the Docker image
2. Run the tests inside the container
3. Generate and publish the Allure report as an artifact

## Project Structure
bookstore-api-tests/
├── pom.xml                # Maven dependencies and configuration
├── Dockerfile             # Container setup for automated tests
├── README.md              # Setup and usage instructions
└── src/
├── main/java/         # Core framework code (helpers, models, config)
└── test/java/         # Test classes and API test cases
