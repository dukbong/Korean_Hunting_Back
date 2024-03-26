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

---

# 소스 코드 분석 서비스

이 웹사이트는 소스 코드에서 특정 부분(예: 한글 문자, HTML 태그 내의 텍스트)을 찾아 사용자에게 알려주는 서비스를 제공합니다. 또한, Gradle 및 Maven 빌드를 사용자 정의하여 빌드 전 한글 문자의 존재 여부를 확인할 수 있는 서비스를 제공합니다.

## 기능

- **소스 코드 분석**: 사용자는 소스 코드 내에서 검색할 전략을 선택할 수 있습니다. 한글 문자, HTML 태그 내의 텍스트 등을 지정하면, 서비스가 소스 코드를 분석하여 기준을 충족하는 결과를 체계적으로 정리된 파일로 제공합니다.
  
- **커스텀 빌드 프로세스**: 이 서비스는 Gradle 및 Maven 빌드 시스템의 사용자 정의입니다. 사용자는 표준 빌드 프로세스 대신 커스텀 빌드 프로세스를 시작하고 실제 빌드가 진행되기 전에 한글 문자가 있는지 확인해야 합니다. 이를 통해 사용자는 배포 전에 소스 코드의 다국어 준비 상태를 보장할 수 있습니다.

## 사용 방법

### 소스 코드 분석

1. 소스 코드의 관련 부분을 ZIP 파일로 압축합니다.
2. 분석 기준을 선택하고 분석 프로세스를 시작합니다.
3. 소스 코드에서 지정된 기준의 존재 여부를 확인합니다. 팝업 창을 통해 결과를 TXT 파일로 다운로드할 수 있습니다.

### 커스텀 빌드 프로세스

1. 사용자 정의 Gradle 또는 Maven 빌드 스크립트를 사용하여 프로젝트를 구성합니다.
2. 빌드 프로세스를 시작하기 전에 소스 코드에 한글 문자가 없는지 확인합니다. 기본 빌드 프로세스 대신 커스텀 빌드 프로세스를 사용합니다.
3. 소스 코드에서 한글 문자가 발견되면 사용자에게 알립니다. 발생 횟수와 해당 파일 이름을 알려줍니다.
4. 소스 코드에서 한글 문자가 감지되면 사용자가 빌드 프로세스를 계속할지 여부를 결정합니다.

커스텀 빌드 프로세스 예시 (Gradle 프로젝트):
build.gradle에 다음을 추가합니다:
```groovy
task koreanCheck {
    doLast {
        exec {
            commandLine 'sh', './koreanCheck.sh'
            standardInput = System.in
        }
    }
}
