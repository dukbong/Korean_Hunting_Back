#!/bin/bash
file_path="./source_code_io/token.txt"
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
response=$(curl -X POST -H "Authorization: Bearer $token_value" -F "file=@$current_dir/$zip_file" http://localhost:8888/api/upload)
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
        exit 1 
    fi
fi