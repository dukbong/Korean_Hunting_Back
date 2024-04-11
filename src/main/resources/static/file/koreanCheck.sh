#!/bin/bash
file_path="./source_code_io/token.txt"
task_name=$(grep -oP '(?<=task=).*' ./source_code_io/task.txt)
if [ -z "$task_name" ]; then
    echo "Task name is empty. Exiting."
    exit 1
fi

if [ -f "$file_path" ]; then
    token=$(grep -o 'token=[^ ]*' "$file_path" | cut -d'=' -f2)
    if [ -z "$token" ]; then
        echo -e "\e[91mError : Token value is empty!\e[0m"
        echo -e "\e[91mPlease check the token.txt file and manual.\e[0m"
        sleep 5
        exit 1
    else
        token_value="$token"
    fi
else
    echo -e "\e[91mError : There is no token value!\e[0m"
    echo -e "\e[91mPlease check the token.txt file and manual.\e[0m"
    sleep 5
    exit 1
fi
echo -e "\033[1;33mChecking whether Korean exists in this project ...\033[0m"
current_date=$(date +%Y%m%d) 
current_dir=$(pwd)
project_name=$(basename "$current_dir")
zip_file="${project_name}_${current_date}.zip"
echo "--------------------------------------------------"
echo -e "\033[1;33m> Project Information\033[0m"
echo "--------------------------------------------------"
echo "Project Directory: $current_dir"
echo "Project Name     : $project_name"
echo "Current Date     : $current_date"
echo "--------------------------------------------------"
echo
echo "--------------------------------------------------"
echo -e "\033[1;33m> Compressing project...\033[0m"
echo "--------------------------------------------------"
zip -r "$current_dir/$zip_file" "$current_dir/src"
echo "--------------------------------------------------"
echo -e "\033[1;32m> Completing project Compression!!!\033[0m"
echo "--------------------------------------------------"
echo
response=$(curl -X POST -H "Authorization: Bearer $token_value" -F "file=@$current_dir/$zip_file" -F "projectName=$project_name" http://localhost:8998/api/upload)
echo "test = " + $response
echo "--------------------------------------------------"
rm "$current_dir/$zip_file"
echo -e "\033[1;32mDeleted compressed files successfully!!!\033[0m"
echo "--------------------------------------------------"
echo
echo "--------------------------------------------------"
echo -e "\033[1;33m> Korean Check Result\033[0m"
echo "--------------------------------------------------"
count=$(echo "$response" | grep -o '"count":[^,}]*' | awk -F: '{print $2}' | tr -d '"')
errormsg=$(echo "$response" | jq -r '.errors[0]' | awk -F "'" '{print $2}')
# Token Error...
if [ -z "$count" ]; then
    if [ -n "$errormsg" ]; then
        echo -e "\e[91mWARNING MESSAGE : $errormsg\e[0m"
        exit 1
    else
        echo -e "\e[91mWARNING MESSAGE : Please contact the website administrator.\e[0m"
        exit 1
    fi
# Not include Korean
elif [ "$count" -eq 0 ]; then
    echo -e "\e[1;92mThe test results did not include Korean!!\e[0m"
# Include Korean
else
    echo -e "\e[91mWARNING!\e[0m"
    echo -e "\e[91mKOREAN COUNT : \e[0m$count"
    echo -e "\e[91mFILE LOCATION\e[0m"
    fileName=$(echo "$response" | grep -o '"fileName":\[.*\]' | sed 's/"fileName":\[\(.*\)\]/\1/')
    IFS=',' read -r -a content_array <<< "$fileName"
    for item in "${content_array[@]}"; do
        echo -e "\e[91m---> \e[0m$item"
    done

    echo -e "--------------------------------------------------\n\033[1;33mDo you want to continue building? (Y/N)\033[0m\n--------------------------------------------------\n"
    read -r input
    echo
    if [[ "$input" == "N" || "$input" == "n" ]]; then
        echo -e "\e[91mAborting a build task due to a user request.\e[0m"
        exit 0
    else
        echo -e "\033[1;33mKorean text was found in the source code, but I will proceed with the build.\033[0m"
        if [ "$task_name" == "build" ]; then
            echo "Executing build task..."
            ./gradlew build
        elif [ "$task_name" == "clean" ]; then
            echo "Executing clean task..."
            ./gradlew clean
        elif [ "$task_name" == "test" ]; then
            echo "Executing test task..."
            ./gradlew test
        elif [ "$task_name" == "integrationTest" ]; then
            echo "Executing integrationTest task..."
            ./gradlew integrationTest
        elif [ "$task_name" == "check" ]; then
            echo "Executing check task..."
            ./gradlew check
        elif [ "$task_name" == "javadoc" ]; then
            echo "Executing javadoc task..."
            ./gradlew javadoc
        elif [ "$task_name" == "dokka" ]; then
            echo "Executing dokka task..."
            ./gradlew dokka
        elif [ "$task_name" == "dependencies" ]; then
            echo "Executing dependencies task..."
            ./gradlew dependencies
        elif [ "$task_name" == "assemble" ]; then
            echo "Executing assemble task..."
            ./gradlew assemble
        elif [ "$task_name" == "publish" ]; then
            echo "Executing publish task..."
            ./gradlew publish
        else
            echo "Unknown option: $task_name"
        fi
    fi
fi