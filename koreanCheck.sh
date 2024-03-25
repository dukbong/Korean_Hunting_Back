#!/bin/bash
echo -e "\033[1;32mChecking whether Korean exists in this project ...\033[0m"

function build_with_maven {
    mvn clean install
}

function build_with_gradle {
    ./gradlew clean build
}

function has_gradle_folder {
    [ -d ".gradle" ]
}

function build_project {
    if has_gradle_folder; then
        build_with_gradle
    else
        build_with_maven
    fi
}

current_date=$(date +%Y%m%d) 

# 현재 스크립트의 디렉토리 경로를 얻습니다.
current_dir=$(pwd) # C:\Korean_Hunting_Back
project_name=$(basename "$current_dir") # Korean_Hunting_Back

# 압축할 파일의 이름을 설정합니다.
zip_file="${project_name}_${current_date}.zip"

# 프로젝트 디렉토리 경로를 출력합니다.
echo "--------------------------------------------------"
echo -e "\033[1;32m> Project Information\033[0m"
echo "--------------------------------------------------"
echo "Project Directory: $current_dir"
echo "Project Name     : $project_name"
echo "Current Date     : $current_date"
echo "--------------------------------------------------"
echo

# 프로젝트를 압축하는 중임을 출력합니다.
echo "--------------------------------------------------"
echo -e "\033[1;32m> Compressing project...\033[0m"
echo "--------------------------------------------------"
zip -r "$current_dir/$zip_file" "$current_dir/src"
echo "--------------------------------------------------"
echo -e "\033[1;32m> Completing project Compression!!!\033[0m"
echo "--------------------------------------------------"
echo

# API 서버로 POST 요청을 보냅니다.
#response=$(curl -X POST -F "file=@$current_dir/$zip_file" http://localhost:8888/api/upload)
response=0
# 압축 파일을 삭제합니다.
echo "--------------------------------------------------"
rm "$current_dir/$zip_file"
echo -e "\033[1;32mDeleted compressed files successfully!!!\033[0m"
echo "--------------------------------------------------"
echo

# 응답을 출력합니다.
echo "--------------------------------------------------"
echo -e "\033[1;33mKorean Check Result : \033[0m"
echo "--------------------------------------------------"
# echo "$response"
echo -e "\033[1;33mDo you want to continue building? (Y/N)\033[0m"
read -r result
echo "--------------------------------------------------"
echo

if [[ "$result" == "N" || "$result" == "n" ]]; then
    exit 0 
fi

build_project
