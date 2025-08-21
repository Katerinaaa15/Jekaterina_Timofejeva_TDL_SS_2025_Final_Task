# Jekaterina_Timofejeva_TDL_SS_2025_Final_Task

## Project Description
Automation testing framework built with:
- **Java**
- **Maven**
- **Selenium WebDriver**
- **TestNG**
- **ExtentReports**

The framework follows the Page Object Model (POM) design pattern for better test structure and maintainability.

---

### There are chromedriver versions for windows and mac.

### Chromedriver can be switched in src/main/java/common/DriverFactory (by replacing comments for chromedriver path)

- Chromedrivers are located in src/main/resources/drivers



##  How to Run Tests

### Run all tests
- mvn clean test

### Run only Smoke tests (TestNG groups)
- mvn clean test -Dgroups=smoke

### Run only Regression tests (TestNG groups)
- mvn clean test -Dgroups=regression

### Run a specific test class
- mvn -Dtest=Tests test

### Run a specific test method in a class
- mvn -Dtest=Tests#test3_addRemoveFleece test
- mvn -Dtest=Tests#test4_logoutLoginWithVisualUser test

### Skip all tests (compile only)
- mvn clean install -DskipTests

---

## Maven & TestNG Notes
### Groups (smoke, regression) are defined in the test methods with TestNG annotations:
## Smoke Tests
###  @Test(groups = {"smoke"})
- test1_cartCheckoutValidation
- test2_lockedOutUser 
## Regression Tests
### @Test(groups = {"regression"})
- test3_addRemoveFleece
- test4_logoutLoginWithVisualUser
## Both types of Tests (smoke and regression)
### groups = {"smoke", "regression"}
- test5_allProductsHaveSamePicture
  

## Excluding tests can be done in:
    - pom.xml → <excludes>
    - testng.xml → comment/remove <test> section
    - Maven CLI → -DskipTests

---

## Project Structure
- src/test/java → BaseTest and Tests with test cases
- testng.xml -> TestNG suite configuration
- src/main/java/pages→ page objects
- src/main/java/common -> config files
- src/main/java/utils → HTML reports
- README.md -> maven commands and project structure


### Additional files:
- pom.xml → Maven dependencies & plugins
- src/main/java/resources/config.properties → Project configuration (URL, credentials, etc.)
- target/ → generated screenshots

---

## Reports

After running the tests, an **Extent Report** is generated.

Open in browser:
src/main/java/utils -> extentReport.html

This report contains:
- Test execution status (pass/fail/skip)
- Detailed logs per test

Screenshots for failed tests and validation steps are stored in target/screenshots

---















  
