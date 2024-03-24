#!/bin/bash

# Maven 빌드 함수 정의
function build_with_maven {
    echo "Maven을 사용하여 프로젝트 빌드 및 설치 중..."
    mvn clean install
}

# Gradle 빌드 함수 정의
function build_with_gradle {
    echo "Gradle을 사용하여 프로젝트 빌드 중..."
    ./gradlew clean build
}

function has_gradle_folder {
    [ -d ".gradle" ]
}

# Gradle이 있는지 확인하고 빌드를 실행합니다.
function build_project {
    if has_gradle_folder; then
        build_with_gradle
    else
        build_with_maven
    fi
}

current_date=$(date +%Y%m%d) 

# 현재 스크립트의 디렉토리 경로를 얻습니다.
current_dir=$(dirname "$0") # C:\Korean_Hunting_Back

project_name=$(basename "$current_dir")

# 압축할 파일의 이름을 설정합니다.
zip_file="${project_name}_${current_date}.zip"

# 프로젝트 디렉토리 경로를 출력합니다.
echo "프로젝트 디렉토리: $current_dir"

# 프로젝트를 압축하는 중임을 출력합니다.
echo "프로젝트를 압축하는 중..."

# src 폴더를 압축합니다.
zip -r "$current_dir/$zip_file" "$current_dir/src"

echo "프로젝트 압축 완료 후 API 서버에 전송 중 ..."


# API 서버로 POST 요청을 보냅니다.
response=$(curl -X POST -F "file=@$current_dir/$zip_file" http://localhost:8088/api/upload)

# 압축 파일을 삭제합니다.
rm "$current_dir/$zip_file"
echo "압축 파일 삭제 완료"

# 응답을 출력합니다.
echo "API 서버 응답: $response"
sleep 5

if [[ "$response" == "0" ]]; then
    echo "빌드 시작"
    build_project

elif [[ "$response" == "1" ]]; then
    echo "API 서버로부터 경고를 받았습니다. 빌드를 진행합니다."
    build_project
fi
