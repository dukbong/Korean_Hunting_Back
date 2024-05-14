#### 문제점 인식과 동기

이 프로젝트는 회사에서 배포 시 소스 코드에서 한글의 존재 여부를 확인하는 번거로운 작업에서 시작되었습니다.

동료 직원들이 작업을 수행하는 데 많은 시간이 소비되고 있음을 관찰하였고, 이를 직접 경험하여 너무 많은 시간이 소요된다는 것을 확인하였습니다.

이에 따라, 이러한 문제를 해결하기 위해 간단한 방법으로 소스 코드에서 원하는 문자열을 추출하는 프로세스를 자동화하고자 시작하게 되었습니다.

더 나아가, 빌드 시에 원하는 문자열을 확인하고 빌드를 진행할지 여부를 선택할 수 있는 기능을 추가하여 직원들이 시간을 절약하고 더 효율적으로 작업할 수 있도록 하였습니다.

이 프로젝트는 직원들의 업무 효율성을 향상시키고, 자동화된 프로세스를 통해 작업 속도를 높이는 것을 목표로 합니다.

#### 성능 테스트 기록
[링크](https://jangto.tistory.com/category/SourceCode.io%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8)에서 성능 테스트에 대한 자세한 기록을 확인할 수 있습니다.

#### 기술 스택
- 백엔드: Spring Boot, JPA
- 프론트엔드: [React](https://github.com/dukbong/Korean_Hunting_Front)
- 컨테이너화: Docker
- 웹 서버: Nginx
- 인증 및 보안: JWT
- 모니터링: Actuator, Prometheus
- 데이터베이스: MySQL
- 성능 분석: JMH
- 부하 테스트: JMeter

---

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
2. Before starting the build process, make sure there are no Korean characters in your source code.
3. If Korean characters are found in the source code, the user is notified about the number of occurrences and the respective file names.
4. If Korean characters are detected in the source code, the user decides whether to proceed with the build process.

## Contributions

Contributions to the project are welcome! If you have any suggestions for improvements or would like to report issues, please feel free to open an issue or submit a pull request on GitHub.

## License

This project is licensed under the [MIT License](LICENSE).

---

# 소스 코드 분석 서비스

이 웹사이트는 소스 코드에서 특정 부분(예: 한글 문자, HTML 태그 내의 텍스트)을 찾아 사용자에게 알려주는 서비스를 제공합니다. 또한, Gradle 및 Maven 빌드를 사용자 정의하여 빌드 전 한글 문자의 존재 여부를 확인할 수 있는 서비스를 제공합니다.

## 사용 기술
- SPring Boot, Java 17, Shell Script, JWT, Gradle

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
2. 빌드 프로세스를 시작하기 전에 소스 코드에 한글 문자가 없는지 확인합니다.
3. 소스 코드에서 한글 문자가 발견되면 사용자에게 알립니다. 발생 횟수와 해당 파일 이름을 알려줍니다.
4. 소스 코드에서 한글 문자가 감지되면 사용자가 빌드 프로세스를 계속할지 여부를 결정합니다.
