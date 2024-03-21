<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

				<div id="ptc_pg4" class="subCon1">
					<h2><i class="fi-rr-apps"></i>CAPA 결과</h2>
						<div class="btnWrap">
							<c:if test="${ptc_btn4 == 'ptc_btn4'}">
									<div class="ctsInnerAreaTopRit">
										<button id=${ptc_pg4}btn_upd_rqst class="etcBtn">수정요청</button>
										<button id=${ptc_pg4}btn_save class="save">저장</button>
										<button id=${ptc_pg4}btn_conf_rqst class="etcBtn">승인 요청</button>
										<button id=${ptc_pg4}btn_rtnResn class="etcBtn">반려 사유</button>
										<button id=${ptc_pg4}btn_new class="reset">초기화</button>
									</div>
								</c:if>
								<c:if test="${ptc_btn4_Conf1 == 'ptc_btn4_Conf1'}">
									<div class="ctsInnerAreaTopRit">
										<button id=${ptc_pg4}btn_rtnResn class="etcBtn">반려 사유</button>
										<button id=${ptc_pg4}btn_conf class="etcBtn">승인</button>
										<button id=${ptc_pg4}btn_rtn class="etcBtn">반려</button>
									</div>
								</c:if>
						</div>
						
						<form id=${ptc_pg4}Frm name=${ptc_pg4}Frm class="mgT15" onsubmit="return false">
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
									<th class="necessary">CAPA 계획(상세)</th>
									<td colspan="7s">
										<textarea cols="100" rows="8" id=${ptc_pg4}comptCapaPlan name="comptCapaPlan" class="wd100p readonlyClass"></textarea>
									</td>
								</tr>
								<tr>
									<th class="taCt vaMd necessary">CAPA 결과</th>
									<td colspan="7">
										<textarea cols="100" rows="8" id=${ptc_pg4}capaResult name="capaResult" class="wd100p readonlyClass"></textarea>
									</td>
								</tr>
								<tr>
									<th class="necessary">근거 문서 정보</th>
									<td colspan="7">
										<textarea cols="100" rows="8" id=${ptc_pg4}basisDocInfo name="basisDocInfo" class="wd100p readonlyClass"></textarea>
									</td>
								</tr>
								<tr>
									<th>CAPA 완료 기한</th>
									<td colspan="3">
										<input type="text" id=${ptc_pg4}comptPrarnde4 name="comptPrarnde4"  class="wd100p readonlyClass" readonly>
									</td>
									<th class="necessary">완료일</th>
									<td colspan="3">
										<input type="text" id=${ptc_pg4}comptde name="comptde"  class="wd100p disabledClass">
									</td>
								</tr>
								<tr>
									<th>변경 관리 수행 여부</th>
									<td colspan="3">
										<label><input type="radio" id=${ptc_pg4}changeManageExcAtY name="changeManageExcAt" value="Y" checked>&nbsp;YES</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="radio" id=${ptc_pg4}changeManageExcAtN name="changeManageExcAt" value="N" >&nbsp;NO</label>
									</td>
									<th>변경 QMS 관리번호</th>
									<td colspan="3">
										<div class="btnWrap">
											<input type="text" id=${ptc_pg4}changeManageNo name="changeManageNo"  class="wd85p readonlyClass" readonly>
											<!-- <button id=${ptc_pg4}btn_change class="etcBtn" style="display:none; margin-left: 5px;">보기</button> -->
											<button type="button" id=${ptc_pg4}btn_change style="display: none; margin-left: 5px;" class="inTableBtn inputBtn">
										  		<img src="/assets/image/btnSearch.png" />
									      	</button>
										</div>
									</td>
								</tr>
								<tr>
									<th>비고</th>
									<td colspan="7">
										<textarea cols="100" rows="8" id=${ptc_pg4}comptRm name="comptRm" class="wd100p readonlyClass"></textarea>
									</td>
								</tr>
								<tr>
									<th>첨부 파일</th>
									<td colspan="7">
										<div id=${ptc_pg4}dropzoneArea></div>
										<input type="hidden" id=${ptc_pg4}atchmnflSeqno4 name="atchmnflSeqno4" class="wd100p">
									</td>
								</tr>
							</table>

							<div class="ctsInnerAreaTop mgT15">
								<h2><i class="fi-rr-apps"></i>CAPA 유효성 평가</h2>
							</div>
							<table cellpadding="0" cellspacing="0" width="100%" class="subTable1 mgT15">
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
									<th>유효성 평가 필요 여부</th>
									<td colspan="7">
										<label><input type="radio" id=${ptc_pg4}validfmnmEvlNeedAtY name="validfmnmEvlNeedAt"  class="disabledClass" value="Y" checked>&nbsp;필요</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="radio" id=${ptc_pg4}validfmnmEvlNeedAtN name="validfmnmEvlNeedAt"  class="disabledClass" value="N" >&nbsp;불필요</label>
									</td>
								</tr>
								<tr>
									<th>담당자</th>
									<td colspan="7">
										<div class="btnWrap">
											<input type="text" id=${ptc_pg4}efctEvlChargerNm name="efctEvlChargerNm"  class="wd85p readonlyClass" readonly>
											<c:if test="${ptc_btn4_Conf1 == 'ptc_btn4_Conf1'}">
												<!-- <button id=${ptc_pg4}findEfctEvlChargerId class="search" style="margin-left:5px;">찾기</button> -->
												<button type="button" id=${ptc_pg4}findEfctEvlChargerId style="margin-left: 5px;" class="inTableBtn inputBtn">
										  			<img src="/assets/image/btnSearch.png" />
									      		</button>
											</c:if>
										</div>
									</td>
								</tr>
								<tr>
									<th>QMS 관리번호</th>
									<td colspan="7">
										<input type="text" id=${ptc_pg4}validfmnmEvlManageNo name="validfmnmEvlManageNo" class="wd100p readonlyClass" placeholder='자동생성' readonly>
									</td>
								</tr>
								<tr>
									<th>유효성 평가 비고</th>
									<td colspan="7">
										<textarea id=${ptc_pg4}efctEvlRm name="efctEvlRm" class="wd100p readonlyClass" rows= "8"></textarea>
									</td>
								</tr>
									<c:if test="${pageNum == 4 }">
										<c:if test="${ptc_btn4_Conf1 == 'ptc_btn4_Conf1'}">
											<jsp:include page="../../com/imp/AprvConfirmForm.jsp" flush="false" >
												<jsp:param name="colspan" value="7"/>
											</jsp:include>
										</c:if>
										<c:if test="${ptc_btn4_Conf1 != 'ptc_btn4_Conf1'}">
											<jsp:include page="../../com/imp/AprvForm.jsp"  flush="false">
					                            <jsp:param name="colspan" value="7"/>
					                        </jsp:include>
										</c:if>
									</c:if>
							</table>
							<input type="hidden" id=${ptc_pg4}causeExaminNeedAt name="causeExaminNeedAt" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}capaRequstSeqno name="capaRequstSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}capaRceptSeqno name="capaRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}manageNo name="manageNo" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}capaPlanSeqno name="capaPlanSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}comptReportSeqno name="comptReportSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}fileSave name="fileSave" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}modiResn name="modiResn" value=''/>
							<input type="hidden" id=${ptc_pg4}deptCode name="deptCode" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}chargerId name="chargerId" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}apprLine name="apprLine" value="">
							<input type="hidden" id=${ptc_pg4}sanctnSeqno name="sanctnSeqno" value="">
							<input type="hidden" id=${ptc_pg4}sanctnLineSeqno name="sanctnLineSeqno" value="">
							<input type="hidden" id=${ptc_pg4}rtnSeqno name="rtnSeqno" value="">
							<input type="hidden" id=${ptc_pg4}rtnResn name="rtnResn" value="">
							<input type="hidden" id=${ptc_pg4}btnDialConfirm name="btnDialConfirm" >
							<input type="hidden" id=${ptc_pg4}sanctnerId name="sanctnerId" value="">
							<input type="hidden" id=${ptc_pg4}apprLineBtn name="apprLineBtn" value="">
							<input type="hidden" id=${ptc_pg4}efctEvlChargerId name="efctEvlChargerId" value="">
							<input type="hidden" id=${ptc_pg4}efctEvlChrgDeptCode name="efctEvlChrgDeptCode" value="">
							<input type='hidden' id=${ptc_pg4}updRqstBtn name="updRqstBtn" value=''/>
							<input type="hidden" id=${ptc_pg4}changeRceptSeqno name="changeRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}findEfctEvlChargerIdPopup name="findEfctEvlChargerIdPopup" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}emailAddress name="emailAddress" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg4}procCode name="procCode" >

						</form>
				</div>
				
				<c:if test="${pageNum == 4}">
					<c:if test="${ptc_btn4_Conf1 == 'ptc_btn4_Conf1'}">
						<script src="/viewScript/ptc/tab/CapaRrptConfirmM.js"></script>
					</c:if>
					<c:if test="${ptc_btn4_Conf1 != 'ptc_btn4_Conf1'}">
						<script src="/viewScript/ptc/tab/CapaRrptWrt.js"></script>
					</c:if>
				</c:if>
				
				<c:if test="${pageNum == 5}">
					<script src="/viewScript/ptc/tab/CapaInqr.js"></script>
				</c:if>