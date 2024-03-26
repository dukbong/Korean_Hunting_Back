# Source Code Analysis Service

This website provides a service to find specific parts in the source code (e.g., Korean characters, text within HTML tags) and notify users. Additionally, it offers a service to customize Gradle and Maven builds to check for the presence of Korean characters before building.

## Features

- **Source Code Analysis**: Users can select the strategy they want to use for searching within the source code. By specifying criteria such as Korean characters, text within HTML tags, etc., the service analyzes the source code and provides results that meet the criteria in a neatly organized file.
  
- **Customized Build Process**: This service is a customization of the Gradle and Maven build systems. Users should initiate the custom build process instead of the standard one and verify whether Korean characters are present before the actual build occurs. This allows users to ensure the multilingual readiness of their source code before deployment.

## How to Use

### Source Code Analysis

1. Compress the relevant portion of your source code into a ZIP file.
2. Choose your analysis criteria and start the analysis process.
3. Check the presence of specified criteria in the source code through a popup window. Download the results as a TXT file.

### Custom Build Process

1. Configure your project using custom Gradle or Maven build scripts.
2. Before initiating the build process, ensure there are no Korean characters in the source code, using a custom build process instead of the default one.
3. If Korean characters are found in the source code, the user is notified about the number of occurrences and the respective file names.
4. If Korean characters are detected in the source code, the user decides whether to proceed with the build process.
Example of Custom Build Process (Gradle Project):
Add to build.gradle:
```groovy
task koreanCheck {
    doLast {
        exec {
            commandLine 'sh', './koreanCheck.sh'
            standardInput = System.in
        }
    }
}
```
## Contributions

Contributions to the project are welcome! If you have any suggestions for improvements or would like to report issues, please feel free to open an issue or submit a pull request on GitHub.

## License

This project is licensed under the [MIT License](LICENSE).
