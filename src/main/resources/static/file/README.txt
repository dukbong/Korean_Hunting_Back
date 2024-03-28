This shell script is designed for assisting in building processes by checking for the presence of Korean characters in the source code.

Usage Instructions:

1. Place the koreanCheck.sh file in the root folder of the project you intend to use it with.
2. Refer to the manual and modify the code according to the requirements of your project.
3. Obtain a personal API authentication key.
4. Write your personal API authentication key in the token.txt file in the format "token=value", and place it in the same location as koreanCheck.sh.
5. Follow the manual to initiate the build process tailored to your project.

The Shell Script relies on a project management tool.

If there are no Korean characters detected, the script will immediately execute building.

However, if Korean characters are found, the script will prompt the user with a warning and the option to proceed with building.

If any issues arise during usage, please leave a message in the Issues section at https://github.com/dukbong/Korean_Hunting_Back.

Thank you.

========================================================================================================

해당 셸 스크립트는 소스 코드에서 한글 유무를 확인한 후 빌드를 도와주는 커스텀 빌드를 위한 스크립트입니다.

사용 방법 숙지:

1. koreanCheck.sh 파일을 사용할 프로젝트의 루트 폴더에 복사해주세요.
2. 메뉴얼을 참고하여 각 프로젝트에 맞게 코드를 수정해주세요.
3. 개인 API 인증키를 발급 받아주세요.
4. 발급받은 개인 API 인증키를 token.txt 파일에 "token=value" 형식으로 작성한 후, koreanCheck.sh 파일과 같은 위치에 넣어주세요.
5. 메뉴얼을 따라 각 프로젝트에 맞게 빌드를 시작해주세요.

해당 Shell Script는 프로젝트 관리 도구에 의존하고 있습니다.

만약 한글이 없는 경우, 즉시 build가 실행되며, 한글이 포함된 경우 경고 메시지와 함께 사용자가 build를 진행할지 여부를 선택할 수 있습니다.

문제가 발생할 경우, https://github.com/dukbong/Korean_Hunting_Back 에서 Issues에 문의해주시면 감사하겠습니다.
