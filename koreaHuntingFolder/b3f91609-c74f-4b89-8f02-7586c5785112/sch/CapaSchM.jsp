<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- ▒▒▒startHtml▒▒▒ -->
<div id="CapaRqstWrt1" class="subCon1">
			<h2><i class="fi-rr-apps"></i>${pgName}</h2>
			<div class="btnWrap">
				<c:if test = "${updtAndExtnCount == 'updtAndExtnCount'}">
					<c:if test = "${CapaInqrM != 'CapaInqrM'}">
						<button id='btn_rqstMdfcAndExtn' class="etcBtn">수정 및 연장 요청 조회</button>
						<input type='hidden' id='rqstMdfcAndExtn' value="">
					</c:if>
				</c:if>
				<c:if test="${ptc_btn1 == 'ptc_btn1' || ptc_btn2 == 'ptc_btn2' || ptc_btn3 == 'ptc_btn3' || ptc_btn4 == 'ptc_btn4'}">
					<button id='btn_search' class="search">조회</button>
				</c:if>
				<c:if test="${ptc_btn2_Conf1 == 'ptc_btn2_Conf1' || ptc_btn3_Conf1 == 'ptc_btn3_Conf1' || ptc_btn4_Conf1 == 'ptc_btn4_Conf1'}">
					<button id='btn_Line' class="etcBtn">결재 라인 확인</button>
					<button id='btn_search' class="search">조회</button>
				</c:if>
				<c:if test="${ptc_btn5 == 'ptc_btn5'}">
					<div class="ctsInnerAreaTopRit">
						<button id='btn_report' class="etcBtn">레포트</button>
						<button id='btn_rqstMdfcAndExtn' class="etcBtn">수정 및 연장 요청 조회</button>
						<input type='hidden' id='rqstMdfcAndExtn' value="">
						<input type="hidden" id='elctSgntHstr' name="elctSgntHstr" value="">
						<button id='btn_elctSgntHisty' class="etcBtn">전자 서명 이력 조회</button>
						<button id='btn_aprvHstr' class="etcBtn">승인이력조회</button>
						<button id='btn_search' class="search">조회</button>
					</div>
				</c:if>
			</div>
		<!-- Main content -->
					<form action="javascript:;" id="CapaFrmSch" name="CapaFrmSch" class="mgT10">
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
						
						
							<c:if test="${ptc_sch1 == 'ptc_sch1'}">
								<tr>
									<th>관련 QMS 모듈</th>
									<td>
										<select id="rltdSysCodeSch" name="rltdSysCodeSch" class="wd100p schClass"></select>
									</td>
									<th>관련 QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="issueManageNoSch" name="issueManageNoSch">
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:if>

							<c:if test="${ptc_sch2 == 'ptc_sch2'}">
								<tr>
									<th>관련 QMS 모듈</th>
									<td>
										<select id="rltdSysCodeSch" name="rltdSysCodeSch" class="wd100p schClass"></select>
									</td>
									<th>관련 QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="issueManageNoSch" name="issueManageNoSch">
									</td>
									<th>제제유형</th>
									<td>
										<select id="mdcTyCodeSch" name="mdcTyCodeSch" class="wd100p schClass"></select>
									</td>
									<td></td>
									<td></td>
								</tr>
							</c:if>

							<c:if test="${ptc_sch3 == 'ptc_sch3'}">
								<tr>
									<th>관련 QMS 모듈</th>
									<td>
										<select id="rltdSysCodeSch" name="rltdSysCodeSch" class="wd100p schClass"></select>
									</td>
									<th>관련 QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="issueManageNoSch" name="issueManageNoSch">
									</td>
									<th>완료 기한</th>
									<td>
										<input type="checkbox" class="wd5p schClass" id="chkComptPrarnde" name="chkComptPrarnde">
										<label><input type="text" class="wd40p schClass" id="comptPrarndeStart" name="comptPrarndeStart" style="min-width:0; width: calc( 50% - 15px )"></label>
										 ~
										<label><input type="text" class="wd40p schClass" id="comptPrarndeFinish" name="comptPrarndeFinish" style="min-width:0; width: calc( 50% - 15px )"></label>
									</td>
									<th>QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="manageNoSch" name="manageNoSch">
									</td>
								</tr>
								<tr>
									<th>제제유형</th>
									<td>
										<select id="mdcTyCodeSch" name="mdcTyCodeSch" class="wd100p schClass"></select>
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:if>

							<c:if test="${ptc_sch4 == 'ptc_sch4'}">
								<tr>
									<th>관련 QMS 모듈</th>
									<td>
										<select id="rltdSysCodeSch" name="rltdSysCodeSch" class="wd100p schClass"></select>
									</td>
									<th>관련 QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="issueManageNoSch" name="issueManageNoSch">
									</td>
									<th>완료 기한</th>
									<td>
										<input type="checkbox" class="wd5p schClass" id="chkComptPrarnde" name="chkComptPrarnde" >
										<label><input type="text" class="wd40p schClass disabledClass" id="comptPrarndeStart" name="comptPrarndeStart" style="min-width:0; width: calc( 50% - 15px )" ></label>
										 ~
										<label><input type="text" class="wd40p schClass disabledClass" id="comptPrarndeFinish" name="comptPrarndeFinish" style="min-width:0; width: calc( 50% - 15px )"></label>
									</td>
									<th>QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="manageNoSch" name="manageNoSch">
									</td>
								</tr>
								<tr>
									<th>제제유형</th>
									<td>
										<select id="mdcTyCodeSch" name="mdcTyCodeSch" class="wd100p schClass"></select>
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:if>
							<c:if test="${ptc_sch5 == 'ptc_sch5'}">
								<tr>
									<th>관련 QMS 모듈</th>
									<td>
										<select id="rltdSysCodeSch" name="rltdSysCodeSch" class="wd100p schClass"></select>
									</td>
									<th>품목 코드</th>
									<td>
										<input type="text" class="wd100p schClass" id="prdlstCodeSch" name="prdlstCodeSch">
									</td>
									<th>진행상황</th>
									<td>
										<select id="capaProgrssittnCode" name="capaProgrssittnCode" class="wd100p schClass"></select>
									</td>
									<th>관련 QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="issueManageNoSch" name="issueManageNoSch">
									</td>
								</tr>
								<tr>
									<th>품목명</th>
									<td>
										<input type="text" class="wd100p schClass" id="prdlstNmSch" name="prdlstNmSch">
									</td>
									<th>완료 기한</th>
									<td>
										<input type="checkbox" class="wd5p schClass" id="chkComptPrarnde" name="chkComptPrarnde" >
										<label><input type="text" class="wd40p schClass disabledClass" id="comptPrarndeStart" name="comptPrarndeStart" style="min-width:0; width: calc( 50% - 15px )"></label>
										 ~
										<label><input type="text" class="wd40p schClass disabledClass" id="comptPrarndeFinish" name="comptPrarndeFinish" style="min-width:0; width: calc( 50% - 15px )"></label>
									</td>
									<th>QMS 관리번호</th>
									<td>
										<input type="text" class="wd100p schClass" id="manageNoSch" name="manageNoSch">
									</td>
									<th>제제유형</th>
									<td>
										<select id="mdcTyCodeSch" name="mdcTyCodeSch" class="wd100p schClass"></select>
									</td>
								</tr>
								<tr>
									<th>최종 승인일</th>
									<td>
										<input type="checkbox" class="wd5p schClass" id="chkDate" name="chkDate" >
										<label><input type="text" class="wd40p schClass disabledClass" id="lastConfmdeStart" name="lastConfmdeStart" style="min-width:0; width: calc( 50% - 15px )"></label>
										 ~
										<label><input type="text" class="wd40p schClass disabledClass" id="lastConfmdeFinish" name="lastConfmdeFinish" style="min-width:0; width: calc( 50% - 15px )"></label>
									</td>
									<th>담당팀</th>
									<td>
										<input type="text" class="wd100p schClass" id="deptNmCapaSch" name="deptNmCapaSch">
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:if>
						</table>
					</form>
					
					<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
					<div id="ptcGrid_Master" style="width:100%; height:300px; margin:0 auto;"></div>
					
					<div class="mapkey" style="width: 100%;">
						<c:if test="${updtAndExtnCount == 'updtAndExtnCount'}">
							<label class="bg-timeout">수정 및 연장</label>
						</c:if>
						<c:if test="${CapaInqrM != 'CapaInqrM'}">
							<label class="bg-aprvRjct">반려건</label>
						</c:if>
						<c:if test="${CapaInqrM == 'CapaInqrM'}">
							<label class="bg-waitAprv">완료기한임박</label>
						</c:if>
					</div>
</div>
	<script>
		var data = JSON.parse('${data}');
	</script>
	<script src="/viewScript/ptc/sch/CapaSch.js"></script>
	<script src="/viewScript/ptc/CapaGrid.js"></script>