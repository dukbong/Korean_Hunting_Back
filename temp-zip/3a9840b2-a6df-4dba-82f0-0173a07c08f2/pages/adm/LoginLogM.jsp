<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="title">접속로그관리</tiles:putAttribute>
    <tiles:putAttribute name="body">
<!--  body 시작 -->

<div class="subContent">
	<div class="subCon1">
		<h2><i class="fi-rr-apps"></i>${msg.C000000775}</h2>	<!-- 접속이력조회 -->
		<div class="btnWrap">
			<button value="조회" id="srchBtn" class="search btn1">${msg.C000000002}</button> <!-- 조회 -->
		</div>
		<!-- Main content -->
		<form id="srchForm" action="javascript:;">
			<table class="subTable1">
				<colgroup>
					<col style="width:10%"></col> 
					<col style="width:15%"></col>
					<col style="width:10%"></col>
					<col style="width:15%"></col>
					<col style="width:10%"></col>			
					<col style="width:15%"></col>	
					<col style="width:10%"></col>			
					<col style="width:15%"></col>			
				</colgroup>
				<tr>
					<th>${msg.C000000106}</th> <!-- 부서명 -->
						<td>
							<input type="text" id="inspctInsttNm" name="inspctInsttNm">
						</td>
					<th>${msg.C000000100}</th> <!-- 사용자명 -->
						<td>
							<input type="text" id="userNm" name="userNm">
						</td>
					<th>${msg.C000000776}</th> <!-- 사용자아이디 -->
						<td>
							<input type="text" id="loginId" name="loginId">
						</td>
					<th>${msg.C000000777}</th> <!-- 로그일자 -->
					<td>
				 		<input type="text" id="rcpDt1" name="rcpDt1" class="wd6p" style="min-width: 6em;">
					 	<span>~</span>
				 		<input type="text" id="rcpDt2" name="rcpDt2" class="wd6p" style="min-width: 6em;">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="subCon2">
		<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
		<div class="mgT15">
			<div id="loginlogMGrid" style="height:450px; margin:0 auto;"></div>
		</div>
	</div>
</div>
<!--  body 끝 -->
    </tiles:putAttribute>

   <tiles:putAttribute name="script">
<!--  script 시작 -->
	<script>
	//각 그리드 영역의 Id값 저장
	var loginlogMGridId = $("#loginlogMGrid").attr("id");
	//그리드 객체를 담을 변수
	var loginlogMGrid;
	
	//최초 그리드 생성
	 $(function() {
		 loginlogMGrid = createAUIGrid(columnLayout.loginlogMGrid,loginlogMGridId,{selectionMode : "multipleCells"});
		 
		 //로그일자 15일 전 ~ 오늘
		 datePickerCalendar(["rcpDt1"], true, ["DD",-15]);
		 datePickerCalendar(["rcpDt2"], true, ["DD",0]);
		//그리드 사이즈 조절 
		gridResize([loginlogMGrid]);
		
		//버튼 이벤트
		setButtonEvent();
	 });  
	
	var gidID;
	var param;
	var url;
	
	//그리드 정의
	var columnLayout = { 
		loginlogMGrid : []
	}
	
	//접속로그관리 그리드
	auigridCol(columnLayout.loginlogMGrid);
	columnLayout.loginlogMGrid.addColumnCustom("inspctInsttNm","${msg.C000000106}","26%",true); /* 부서명 */
	columnLayout.loginlogMGrid.addColumnCustom("userNm","${msg.C000000100}","13%",true); /* 사용자명 */
	columnLayout.loginlogMGrid.addColumnCustom("loginId","${msg.C000000776}","13%",true); /* 사용자아이디 */
	columnLayout.loginlogMGrid.addColumnCustom("loginIp","${msg.C000000778}","16%",true); /* 사용자IP */
	columnLayout.loginlogMGrid.addColumnCustom("loginDt","${msg.C000000779}","16%",true); /* 로그인시간 */
	columnLayout.loginlogMGrid.addColumnCustom("logoutDt","${msg.C000000780}","16%",true); /* 로그아웃시간 */
	
	/*############ 조회, 저장, 삭제 함수 ############*/
	
	function setButtonEvent(){
		$("#srchBtn").click(function(){
			searchLogingLoigM(); 
		});
	}
	
	//접속로그관리 조회
	function searchLogingLoigM(){
		url="<c:url value='/adm/getLogingLoigMList.lims'/>";
		divID="loginlogMGrid";
		formId=$("#srchForm").attr('id');
		getGridDataForm(url, formId, divID);
	}
	
	$("#inspctInsttNm, #userNm, #loginId").on("keydown", function(key){
		if(key.keyCode==13){
			$("#srchBtn").click();
		}
	});
	</script>
<!--  script 끝 -->
	</tiles:putAttribute>    
</tiles:insertDefinition>
