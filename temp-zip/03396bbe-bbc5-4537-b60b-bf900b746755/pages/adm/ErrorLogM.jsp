<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="title">Tiles 3 Test</tiles:putAttribute>
    <tiles:putAttribute name="body">
<!--  body 시작 -->
<div class="subContent">
	<div class="subCon1">
		<h2><i class="fi-rr-apps"></i>${msg.C000000792}</h2> <!-- 예외로그조회 -->
		<div class="btnWrap">
			<button id="btnSearch" class="search btn3" >${msg.C000000002}</button> <!-- 조회 -->
		</div>
		<!-- Main content -->
		<form id="searchFrm" name="searchFrm" onsubmit="return false" >
			<table cellpadding="0" cellspacing="0" width="100%" class="subTable1">
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
					<th>${msg.C000001305}</th> <!-- 에러 번호 -->
					<td>
						<input type="text" id="excpLogSeqnoSch" name="excpLogSeqnoSch"> 
					</td>
					<th>${msg.C000000793}</th> <!-- 발생일자 -->
					<td>
						<input type="text" id="changeDteSch" name="changeDteSch" class="wd6p schClass" style="min-width: 6em;"> 
						~ 
						<input type="text" id="changeDteFinish" name="changeDteFinish" class="wd6p schClass" style="min-width: 6em;">
					</td>
					<td colspan="4"></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="subCon2">
		<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
		<div id="errorLogGrid" style="width: 100%; height: 450px; margin: 0 auto;"></div>
	</div> 
</div>

<!--  body 끝 -->
</tiles:putAttribute>
<tiles:putAttribute name="script">

<!--  script 시작 -->
<script>
var errorLogGrid = 'errorLogGrid';

// 그리드 데이터
$(function() {

	// grid rendering
	seterrorLogGrid();

	// button event
	setButtonEvent();

	// grid event
	seterrorLogGridEvent();

	// grid resize
	gridResize([errorLogGrid]);  /* 그리드가 여러 개인 경우 콤마로 구분 */

	// selectbox bind
	setCombo();
	
	// Calendar 세팅
	datePickerCalendar(["changeDteSch"], true, ["DD",-1]);
	datePickerCalendar(["changeDteFinish"], true,["DD",0]);

});

// selectbox bind
function setCombo(){
	// url,obj,param,flag, msg, selectVal, auth, callbackfnc 
}

// grid rendering
function seterrorLogGrid() {

	// grid column array
	var errorCol = [];
	auigridCol(errorCol);

	// column property define
	var erroraaColPros = {
			wordWrap : false, 
			rowHeight : 80,  // 고정할 행 높이
			enableCellMerge : true,
			editable : true
	};

	errorCol.addColumnCustom("excpLogSeqno", "${msg.C000000794}",null, true, true);                   /* 예외번호 */
	errorCol.addColumnCustom("url", "${msg.C000000407}",200, true, true);                             /* URL */
	errorCol.addColumnCustom("dvlprDc", "${msg.C000001818}",80, true, true);                         /* 개발자 설명 */
	errorCol.addColumnCustom("excpNm", "${msg.C000000795}",500, true, true, {wrapText : true});       /* 예외명 */
	errorCol.addColumnCustom("excpCn", "${msg.C000000796}",500, true, true, {wrapText : true});       /* 예외내용 */
	errorCol.addColumnCustom("excpTraceCn", "${msg.C000000797}",500, true, true, {wrapText : true});  /* 예외추적내용 */
	errorCol.addColumnCustom("vriablCn", "${msg.C000001819}",80, true, true);                        /* 변수 내용 */
	errorCol.addColumnCustom("lastChangerNm", "${msg.C000000231}", null, true, false);                /* 최종변경자  */
	errorCol.addColumnCustom("lastChangeDt", "${msg.C000000793}", null, true, false);                 /* 발생일자 */
	
	// create AUIgrid
	errorLogGrid = createAUIGrid(errorCol, errorLogGrid,erroraaColPros);
	
}; 

// grid event
function seterrorLogGridEvent() {

}

// button event
function setButtonEvent() {

	// 조회
	$('#btnSearch').click(function() {
		searchErrorLog();
	})

	// enter key event
	$("input").keypress(function (e) {
		if (e.which == 13){
			searchErrorLog()
		}
	});
	
	$("#btnTest").click(function(e){
		ajaxJsonParam3("<c:url value='/adm/searchTesttt.lims'/>", null, function(data) {
			var arr = new Array();
			var newObj  = new Object();
			searchTesto();
			
			if(data.length != 0){
				var expriemSeqnoArr;
				var expriemNmArr;
				var resultValueArr;	 
				
				for(var i = 0 ; i < data.length; i++){
					
					if(Array.isArray(data[i].expriemSeqnoArr)){
						expriemSeqnoArr = data[i].expriemSeqnoArr;
					} else {
						expriemSeqnoArr = new Array();
						expriemSeqnoArr.push(data[i].expriemSeqnoArr);
					}
					
					if(Array.isArray(data[i].expriemNmArr)){
						expriemNmArr = data[i].expriemNmArr;
					} else {
						expriemNmArr = new Array();
						expriemNmArr.push(data[i].expriemNmArr);
					}
					
					if(Array.isArray(data[i].resultValueArr)){
						resultValueArr = data[i].resultValueArr;
					} else {
						resultValueArr = new Array();
						resultValueArr.push(data[i].resultValueArr);
					}
					
					if(!!expriemNmArr){
						var obj = new Object();
						for(var j=0; j<expriemNmArr.length; j++){
							obj[expriemNmArr[j]] = resultValueArr[j];
						}
						arr.push(Object.assign(data[i], obj));
					}
				}

				AUIGrid.setGridData(testvGrid,arr);
			}
		}); 
	
	});
}

// 조회 함수
function searchErrorLog() {
	getGridDataForm("<c:url value='/adm/getErrorLog.lims'/>", "searchFrm", errorLogGrid);
}
		
</script>
<!--  script 끝 -->
</tiles:putAttribute>    
</tiles:insertDefinition>
