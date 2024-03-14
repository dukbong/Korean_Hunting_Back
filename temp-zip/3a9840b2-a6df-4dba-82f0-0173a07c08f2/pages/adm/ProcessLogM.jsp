<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
<tiles:putAttribute name="title">시험이력조회</tiles:putAttribute>
<tiles:putAttribute name="body">
<!--  body 시작 -->
<div class="subContent">
	<div class="subCon1">
		<h2><i class="fi-rr-apps"></i>${msg.C100001112}</h2> <!-- 시험 이력 조회 -->
		<div class="btnWrap">
			<button id="btn_search" name="btn_search" class="btn1 search">${msg.C100000767}</button> <!-- 조회 -->
		</div>
		<!-- Main content -->
		<form id="frmSearch">
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
					<th >${msg.C100001113}</th> <!-- 이력 구분 -->
					<td>
						<label><select id="histSeCodeSch" name="histSeCodeSch"></select></label>
					</td>
					<th >${msg.C100001114}</th> <!-- 처리 명 -->
					<td>
						<label><input type="text" id="processNmSch" name="processNmSch" class="schClass"></label>
					</td>
					<th >${msg.C100000056}</th> <!-- Lot No. -->
					<td>
						<label><input type="text" id="lotIdSch" name="lotIdSch" class="schClass"></label>
					</td>
					<th >${msg.C100001115}</th> <!-- 이력 발생 일시 -->
					<td>
						<input type="text" class="wd6p schClass" name="histPblicteStartDt" id="histPblicteStartDt" style="min-width: 6em;">
						<span>~</span>
						<input type="text" class="wd6p schClass" name="histPblicteEndDt" id="histPblicteEndDt" style="min-width: 6em;">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
	<div class="subCon2 mgT15">
		<div style="width:79.5%;display:inline-block;">
			<div id="processLogMGridId" style="height:480px;"></div>
		</div>
		<div style="width:20%;display:inline-block;">
			<textarea id="processDetailTx" name="processDetailTx" rows="20" style="width: 100%;" readonly></textarea>
		</div>
	</div>
</div>
<!--  body 끝 -->
</tiles:putAttribute>

<!--  script 시작 -->
<tiles:putAttribute name="script">
<script>
	var processLogMGridId ="processLogMGridId";
	var processLogMGrid;

	var columnLayout = {
		processLogMGrid : []
	};

	$(function() {
		setCombo();

		setProcssLogGrid();

		setGridEvent();

		setButtonEvent();
	});

	//콤보 박스
	function setCombo() {
		ajaxJsonComboBox('/com/getCmmnCode.lims','histSeCodeSch',{"upperCmmnCode" : "CM03", "tmprField1Value" : "TEST"},true);
	}

	// 그리드 생성
	function setProcssLogGrid() {

		var erroraaColPros = {
			wordWrap : false,
			rowHeight : 80,  // 고정할 행 높이
			enableCellMerge : true,
			editable : true
		};

		auigridCol(columnLayout.processLogMGrid);
		columnLayout.processLogMGrid.addColumnCustom("histSeNm", "${msg.C100001113}","8%",true,false); /* 이력 구분 */
		columnLayout.processLogMGrid.addColumnCustom("processNm", "${msg.C100001114}","13%",true,false); /* 처리 명 */
		columnLayout.processLogMGrid.addColumnCustom("histPblicteDt", "${msg.C000000786}","12%",true,false); /* 이력 발생 일시 */
		columnLayout.processLogMGrid.addColumnCustom("lotNo", "${msg.C100000056}","10%",true,false); /* Lot No. */
		columnLayout.processLogMGrid.addColumnCustom("exprOdr", "${msg.C100001116}","7%",true,false); /* 시험 차수 */
		columnLayout.processLogMGrid.addColumnCustom("exprNumot", "${msg.C100001095}","7%",true,false); /* 시험 횟수 */
		columnLayout.processLogMGrid.addColumnCustom("changeAfterCn", "${msg.C100000384}","35%",true,true, {wrapText : true}); /* 변경 후 */
		columnLayout.processLogMGrid.addColumnCustom("userNm", "${msg.C100000451}","8%",true,false); /* 사용자 */

		processLogMGrid = createAUIGrid(columnLayout.processLogMGrid , processLogMGridId, erroraaColPros);

		gridResize([processLogMGrid]);

		/*AUIGrid.bind(processLogMGrid, "ready", function(event) {
			gridColResize(processLogMGrid,"1");
		});*/

		datePickerCalendar(["histPblicteStartDt"], true, ["DD",-7]);
		datePickerCalendar(["histPblicteEndDt"], true, ["DD",0]);
	}

	function setGridEvent(){
		AUIGrid.bind(processLogMGrid, "cellDoubleClick", function(event){
			$("#processDetailTx").val(event.item.changeAfterCn.replaceAll("^^", "\n"));
		});
	}

	function setButtonEvent() {

		$("#btn_search").click(function() {
			searchProcessLog();
		});

		$(".schClass").keyup(function(e) {
			if(e.which == 13){
				searchProcessLog();
			}
		})
	}

	//시험 이력 조회
	function searchProcessLog(keyCode) {
		$("#processDetailTx").val('');

		if(keyCode != undefined && keyCode == 13) {
			searchProcessLog();
		}else {
			if(keyCode == undefined) {
				ajaxJsonForm("/adm/getProcessLogListM.lims","frmSearch", function (data) {
					AUIGrid.setGridData(processLogMGrid, data);
				});
			}
		}
	}

</script>
<!--  script 끝 -->

</tiles:putAttribute>
</tiles:insertDefinition>
