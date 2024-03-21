<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

				<div id="ptc_pg2" class="subCon1">
					<h2><i class="fi-rr-apps"></i>CAPA 접수</h2>
						<div class="btnWrap">
							<c:if test="${ptc_btn2 == 'ptc_btn2'}">
								<div class="ctsInnerAreaTopRit">
									<button id=${ptc_pg2}btn_save class="save">저장</button>
									<button id=${ptc_pg2}btn_conf_rqst class="etcBtn">승인 요청</button>
									<button id=${ptc_pg2}btn_rtnResn class="etcBtn">반려 사유</button>
									<button id=${ptc_pg2}btn_new class="reset">초기화</button>
								</div>
							</c:if>
							<c:if test="${ptc_btn2_Conf1 == 'ptc_btn2_Conf1'}">
								<div class="ctsInnerAreaTopRit">
									<button id=${ptc_pg2}btn_rtnResn class="etcBtn">반려 사유</button>
									<button id=${ptc_pg2}btn_conf class="etcBtn">승인</button>
									<button id=${ptc_pg2}btn_rtn class="etcBtn rjct">반려</button>
								</div>
							</c:if>
						</div>
						
						<form id=${ptc_pg2}Frm name=${ptc_pg2}Frm class="mgT15" onsubmit="return false">
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
									<th class="necessary">CAPA 제목</th>
									<td colspan="7">
										<input type="text" id=${ptc_pg2}sj name="sj"  class="wd100p readonlyClass">
									</td>
								</tr>

								<tr>
									<th class="necessary">제제유형</th>
									<td colspan="7">
										<select id=${ptc_pg2}mdcTyCode name="mdcTyCode" class="wd100p disabledClass"></select>
									</td>
								</tr>
								<tr>
									<th>비고</th>
									<td colspan="7">
										<textarea cols="100" rows="8" id=${ptc_pg2}rm name="rm" class="wd100p readonlyClass"></textarea>
									</td>
								</tr>
								<tr>
									<th>첨부 파일</th>
									<td colspan="7">
										<div id=${ptc_pg2}dropzoneArea></div>
										<input type="hidden" id=${ptc_pg2}atchmnflSeqno2 name="atchmnflSeqno2" class="wd25p disabledClass">
									</td>
								</tr>
								<c:if test="${pageNum == 2 }">
										<c:if test="${ptc_btn2_Conf1 == 'ptc_btn2_Conf1'}">
											<jsp:include page="../../com/imp/AprvConfirmForm.jsp" flush="false" >
												<jsp:param name="colspan" value="7"/>
											</jsp:include>
										</c:if>
										<c:if test="${ptc_btn2_Conf1 != 'ptc_btn2_Conf1'}">
											<jsp:include page="../../com/imp/AprvForm.jsp"  flush="false">
					                            <jsp:param name="colspan" value="7"/>
					                        </jsp:include>
										</c:if>
									</c:if>
									<c:if test="${pageNum > 2 }">
										<jsp:include page="../../com/imp/AprvConfirmForm.jsp" flush="false" >
												<jsp:param name="colspan" value="7"/>
											</jsp:include>
									</c:if>
		                        
							</table>
							
							<div class="subCon2 wd100p fL mgT15" id=${ptc_pg2}btn_DetailGrid2_1 >
								<h3>CAPA 담당팀 </h3>
									<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
								<div id=${ptc_pg2}ptcGrid_Detail2_1 class="mgT15" style="width:100%; height:300px; margin:0 auto;"></div>
							</div>
							
							<input type="hidden" id=${ptc_pg2}bfSj name="bfSj" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}bfRm name="bfRm" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}capaRequstSeqno name="capaRequstSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}capaRceptSeqno name="capaRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}dvtnRceptSeqno name="dvtnRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}dvtnOccrrncSeqno name="dvtnOccrrncSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}dscnttOccrrncReportSeqno name="dscnttOccrrncReportSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}dscnttRceptSeqno name="dscnttRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}rtrvlOccrrncSeqno name="rtrvlOccrrncSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}rtrvlRceptSeqno name="rtrvlRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}eventOccrrncSeqno name="eventOccrrncSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}eventRceptSeqno name="eventRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}fileSave name="fileSave" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}modiResn name="modiResn" value=''/>
							<input type="hidden" id=${ptc_pg2}userId name="userId" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg2}causeexaminSeqno name="causeexaminSeqno" class="wd25p" style="min-width:10em;">

							<input type="hidden" id=${ptc_pg2}apprLine name="apprLine" value="">
							<input type="hidden" id=${ptc_pg2}sanctnSeqno name="sanctnSeqno" value="">
							<input type="hidden" id=${ptc_pg2}sanctnLineSeqno name="sanctnLineSeqno" value="">
							<input type="hidden" id=${ptc_pg2}rtnSeqno name="rtnSeqno" value="">
							<input type="hidden" id=${ptc_pg2}rtnResn name="rtnResn" value="">
							<input type="hidden" id=${ptc_pg2}btnDialConfirm name="btnDialConfirm" >
							<input type="hidden" id=${ptc_pg2}sanctnerId name="sanctnerId" value="">
							<input type="hidden" id=${ptc_pg2}apprLineBtn name="apprLineBtn" value="">
							<input type="hidden" id=${ptc_pg2}procCode name="procCode" >

						</form>
				</div>
				
				<c:if test="${ptc_btn2_Conf1 == 'ptc_btn2_Conf1'}">
					<script src="/viewScript/ptc/tab/CapaRcptConfirmM.js"></script>
				</c:if>
				<c:if test="${ptc_btn2_Conf1 != 'ptc_btn2_Conf1'}">
					<script src="/viewScript/ptc/tab/CapaRcpt.js"></script>
				</c:if>
