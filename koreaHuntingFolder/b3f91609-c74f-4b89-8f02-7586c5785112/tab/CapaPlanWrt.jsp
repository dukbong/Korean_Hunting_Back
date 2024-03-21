<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

				<div id="ptc_pg3" class="subCon1">
					<h2><i class="fi-rr-apps"></i>CAPA 계획 일반 사항</h2>
						<div class="btnWrap">
								<c:if test="${ptc_btn3 == 'ptc_btn3'}">
									<div class="ctsInnerAreaTopRit">
										<button id=${ptc_pg3}btn_upd_rqst class="etcBtn">수정요청</button>
										<button id=${ptc_pg3}btn_save class="save">저장</button>
										<button id=${ptc_pg3}btn_conf_rqst class="etcBtn">승인 요청</button>
										<button id=${ptc_pg3}btn_rtnResn class="etcBtn">반려 사유</button>
										<button id=${ptc_pg3}btn_new class="reset">초기화</button>
									</div>
								</c:if>
								<c:if test="${ptc_btn3_Conf1 == 'ptc_btn3_Conf1'}">
									<div class="ctsInnerAreaTopRit">
										<button id=${ptc_pg3}btn_rtnResn class="etcBtn">반려 사유</button>
										<button id=${ptc_pg3}btn_conf class="save">승인</button>
										<button id=${ptc_pg3}btn_rtn class="etcBtn">반려</button>
									</div>
								</c:if>
						</div>
						
						<form id=${ptc_pg3}Frm name=${ptc_pg3}Frm class="mgT15" onsubmit="return false">
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
									<th>CAPA 제목</th>
									<td colspan="7"><input type="text" id=${ptc_pg3}sj name="sj" class="wd100p readonlyClass" readonly></td>
								</tr>
								<tr>
									<th>CAPA 계획</th>
									<td colspan="7"><input type="text" id=${ptc_pg3}capaPlan name="capaPlan" class="wd100p readonlyClass" readonly></td>
								</tr>
								<tr>
									<th>문제점 및 지적사항</th>
									<td colspan="7">
										<textarea cols="100" rows="8" id=${ptc_pg3}cntrvsNdLgstrmatter name="cntrvsNdLgstrmatter" class="wd100p readonlyClass" readonly></textarea>
									</td>
								</tr>
								<tr>
									<th>QMS 관리번호</th>
									<td colspan="3">
										<input type="text" id=${ptc_pg3}manageNo name="manageNo" class="wd100p readonlyClass" readonly>
									</td>
									<th>CAPA 계획서 기한</th>
									<td colspan="3">
										<div class="btnWrap">
											<input type="text" id=${ptc_pg3}actplnTmlmt name="actplnTmlmt" class="wd85p readonlyClass" readonly>
											<c:if test="${ptc_btn3 == 'ptc_btn3'}">
												<button id=${ptc_pg3}btn_upd_rqstActplnTmlmt class="etcBtn" style="margin-left: 5px">연장요청</button>
											</c:if>
										</div>	
									</td>
								</tr>
								<tr>
									<th>원인 조사 수행 여부</th>
									<td colspan="3">
										<label><input type="radio" class="disabledClass" id=${ptc_pg3}causeExaminExcAtY name="causeExaminExcAt" value="Y">&nbsp;YES</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="radio" class="disabledClass" id=${ptc_pg3}causeExaminExcAtN name="causeExaminExcAt" value="N">&nbsp;NO</label>
									</td>
									<th>원인 조사 QMS 관리번호</th>
									<td colspan="3">
										<div class="btnWrap">
											<input type="text" id=${ptc_pg3}causeExaminManageNo name="causeExaminManageNo" class="wd85p readonlyClass" readonly>
											<!-- <button id=${ptc_pg3}btn_rltd_module class="etcBtn" style="display: none; margin-left: 5px">보기</button> -->
											<button type="button" id=${ptc_pg3}btn_rltd_module style="display: none; margin-left: 5px;" class="inTableBtn inputBtn">
										  		<img src="/assets/image/btnSearch.png" />
									      	</button>
										</div>
									</td>
								</tr>
								<tr>
									<th>CAPA 요청팀</th>
									<td colspan="3">
										<input type="text" id=${ptc_pg3}deptNm name="deptNm" class="wd100p readonlyClass" readonly>
									</td>
									<th>CAPA 요청자</th>
									<td colspan="3">
										<input type="text" id=${ptc_pg3}chargerNm name="chargerNm" class="wd100p readonlyClass" readonly>
									</td>
								</tr>
								<tr>
									<th>CAPA 담당팀</th>
									<td colspan="3">
										<input type="text" id=${ptc_pg3}deptNmCapa name="deptNmCapa" class="wd100p readonlyClass" readonly>
									</td>
									<th>CAPA 담당자</th>
									<td colspan="3">
										<input type="text" id=${ptc_pg3}chargerNmCapa name="chargerNmCapa" class="wd100p readonlyClass" readonly>
									</td>
								</tr>
							</table>
							
							<div class="ctsInnerAreaTop mgT15" id=${ptc_pg3}CAPA >
								<h2><i class="fi-rr-apps"></i>CAPA 계획 세부 사항 </h2>
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
										<th class="necessary">CAPA 계획(상세)</th>
										<td colspan="7">
											<textarea cols="100" rows="8" id=${ptc_pg3}capaPlanDetail name="capaPlanDetail" class="wd100p readonlyClass"></textarea>
										</td>
									</tr>
									<tr>
										<th class="necessary">CAPA 완료 기한</th>
										<c:if test="${ptc_btn4 != 'ptc_btn4'}">
											<td colspan="3"><input type="text" id=${ptc_pg3}comptPrarnde name="comptPrarnde"  class="wd100p disabledClass"></td>
										</c:if>
										<c:if test="${ptc_btn4 == 'ptc_btn4'}">
											<td colspan="3">
												<div class="btnWrap">
													<input type="text" id=${ptc_pg3}comptPrarnde name="comptPrarnde"  class="wd85p disabledClass">
													<button id=${ptc_pg3}btn_upd_rqstCompt class="etcBtn" style="margin-left:5px;">연장요청</button>
												</div>
											</td>
										</c:if>
										<th class="necessary">변경 관리</th>
										<td colspan="3">
											<label><input type="radio" id=${ptc_pg3}changeManageNeedAtY name="changeManageNeedAt" value="Y" >&nbsp;필요</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<label><input type="radio" id=${ptc_pg3}changeManageNeedAtN name="changeManageNeedAt" checked value="N" >&nbsp;불필요</label>
										</td>
									</tr>
									<tr>
										<th>비고</th>
										<td colspan="7">
											<textarea cols="100" rows="8" id=${ptc_pg3}planRm name="planRm" class="wd100p readonlyClass"></textarea>
										</td>
									</tr>
									<tr>
										<th>첨부 파일</th>
										<td colspan="7">
											<div id=${ptc_pg3}dropzoneArea></div>
											<input type="hidden" id=${ptc_pg3}atchmnflSeqno3 name="atchmnflSeqno3" class="wd25p disabledClass">
										</td>
									</tr>
									<c:if test="${pageNum == 3 }">
										<c:if test="${ptc_btn3_Conf1 == 'ptc_btn3_Conf1'}">
											<jsp:include page="../../com/imp/AprvConfirmForm.jsp" flush="false" >
												<jsp:param name="colspan" value="7"/>
											</jsp:include>
										</c:if>
										<c:if test="${ptc_btn3_Conf1 != 'ptc_btn3_Conf1'}">
											<jsp:include page="../../com/imp/AprvForm.jsp"  flush="false">
					                            <jsp:param name="colspan" value="7"/>
					                        </jsp:include>
										</c:if>
									</c:if>
									<c:if test="${pageNum > 3 }">
										<jsp:include page="../../com/imp/AprvConfirmForm.jsp" flush="false" >
												<jsp:param name="colspan" value="7"/>
											</jsp:include>
									</c:if>
								</table>

								<input type="hidden" id=${ptc_pg3}causeExaminNeedAt name="causeExaminNeedAt" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}capaRequstSeqno name="capaRequstSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}capaRceptSeqno name="capaRceptSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}dvtnOccrrncSeqno name="dvtnOccrrncSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}dvtnRceptSeqno name="dvtnRceptSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}dscnttOccrrncReportSeqno name="dscnttOccrrncReportSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}dscnttRceptSeqno name="dscnttRceptSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}rtrvlOccrrncSeqno name="rtrvlOccrrncSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}rtrvlRceptSeqno name="rtrvlRceptSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}eventOccrrncSeqno name="eventOccrrncSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}eventRceptSeqno name="eventRceptSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}capaPlanSeqno name="capaPlanSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}fileSave name="fileSave" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}modiResn name="modiResn" value=''/>
								<input type="hidden" id=${ptc_pg3}deptCode name="deptCode" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}chargerId name="chargerId" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}apprLine name="apprLine" value="">
								<input type="hidden" id=${ptc_pg3}sanctnSeqno name="sanctnSeqno" value="">
								<input type="hidden" id=${ptc_pg3}sanctnLineSeqno name="sanctnLineSeqno" value="">
								<input type="hidden" id=${ptc_pg3}rtnSeqno name="rtnSeqno" value="">
								<input type="hidden" id=${ptc_pg3}rtnResn name="rtnResn" value="">
								<input type="hidden" id=${ptc_pg3}btnDialConfirm name="btnDialConfirm" >
								<input type="hidden" id=${ptc_pg3}sanctnerId name="sanctnerId" value="">
								<input type="hidden" id=${ptc_pg3}apprLineBtn name="apprLineBtn" value="">
								<input type="hidden" id=${ptc_pg3}extnRqstBtn name="extnRqstBtn" value="">
								<input type='hidden' id=${ptc_pg3}updRqstBtn name="updRqstBtn" value=''/>
								<input type="hidden" id=${ptc_pg3}causeExaminSeqno name="causeExaminSeqno" class="wd25p" style="min-width:10em;">
								<input type="hidden" id=${ptc_pg3}procCode name="procCode" >
							</div>

						</form>
				</div>
				
				<c:if test="${ptc_btn3_Conf1 == 'ptc_btn3_Conf1'}">
					<script src="/viewScript/ptc/tab/CapaPlanConfirmM.js"></script>
				</c:if>
				<c:if test="${ptc_btn3_Conf1 != 'ptc_btn3_Conf1'}">
					<script src="/viewScript/ptc/tab/CapaPlan.js"></script>
				</c:if>
