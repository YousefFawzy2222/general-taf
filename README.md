# Test Automation Framework

A Java-based UI Test Automation Framework built with Selenium WebDriver, TestNG, Maven, Allure Reports, AspectJ, and Log4j2.

The framework is designed to support reusable browser actions, page objects, validations, screenshots, logs, video recording, and automatic Allure report generation.

## Features

- Selenium WebDriver browser automation
- TestNG test execution and lifecycle management
- Page Object Model structure
- Reusable browser, element, alert, and frame actions
- Configurable browser type from `.properties` files
- Centralized property reader
- TestNG listener auto-registration
- Allure report generation
- Single-file Allure HTML report generation
- Allure `@Step` support for test sub-steps
- Screenshot capture after test execution
- Optional screen recording
- Log4j2 logging
- Soft and hard assertion utilities
- Support for Chrome, Firefox, and Edge drivers
- Maven-based dependency and test execution

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Allure Reports
- AspectJ Weaver
- Log4j2
- Apache Commons IO

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.automationexercise
│   │       ├── drivers
│   │       ├── listeners
│   │       ├── media
│   │       ├── pages
│   │       ├── utils
│   │       └── validations
│   └── resources
│       ├── webapp.properties
│       ├── waits.properties
│       ├── video.properties
│       ├── allure.properties
│       └── META-INF/services/org.testng.ITestNGListener
└── test
    └── java
        └── com.automationexercise.tests
```

## Configuration

Main configuration files are located in:

```text
src/main/resources
```

Example `webapp.properties`:

```properties
browserType=Chrome
executionType=Local
baseUrlWeb=https://automationexercise.com/
```

Example `video.properties`:

```properties
recordTests=false
video.folder=test-output/recordings
```

Example `allure.properties`:

```properties
allure.results.directory=test-output/allure-results
OpenAllureReportAfterExecution=true
```

## TestNG Listener Registration

The listener should be registered using this file:

```text
src/main/resources/META-INF/services/org.testng.ITestNGListener
```

File content:

```text
com.automationexercise.listeners.TestNGListeners
```

## Running Tests

Run tests using Maven:

```bash
mvn clean test
```

Or run a TestNG test class directly from IntelliJ.

When running from IntelliJ, add AspectJ Weaver to VM options if Allure `@Step` annotations do not appear:

```text
-javaagent:"C:\Users\youse\.m2\repository\org\aspectj\aspectjweaver\1.9.25.1\aspectjweaver-1.9.25.1.jar"
```

## Reports and Artifacts

Generated files are stored under:

```text
test-output
├── allure-results
├── full-report
├── reports
├── screenshots
├── recordings
└── Logs
```

The framework can generate:

- Allure result files
- Full Allure report
- Single-file Allure report
- Screenshots
- Logs
- Optional video recordings

## Common Notes

- Properties must be loaded before using values such as `browserType` or `video.folder`.
- Screenshots should be taken after the test method finishes, when the test status is known.
- Allure `@Step` requires AspectJ Weaver at runtime.
- Avoid project paths containing special characters like `&` on Windows.
- For Windows `cmd start`, use an empty title argument:

```java
cmd /c start "" "path-to-report.html"
```

## Recommended Java Version

Java 21 LTS is recommended for better compatibility with Selenium, TestNG, Allure, and AspectJ.

## Framework Flow

1. TestNG execution starts.
2. Properties are loaded.
3. Output folders are cleaned and recreated.
4. Browser driver is initialized.
5. Test steps are executed.
6. Screenshots, logs, and recordings are attached.
7. Allure results are generated.
8. Full and single-file reports are created.
9. Report opens automatically if configured.
