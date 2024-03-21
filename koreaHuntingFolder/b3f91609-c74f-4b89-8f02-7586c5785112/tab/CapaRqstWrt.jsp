<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

				<div id="ptc_pg1" class="subCon1">
					<h2><i class="fi-rr-apps"></i>CAPA 요청</h2>
					<div class="btnWrap">
						<div class="ctsInnerAreaTop">
							<c:if test="${ptc_btn1 == 'ptc_btn1'}">
								<div class="ctsInnerAreaTopRit">
									<button id=${ptc_pg1}btn_save class="save">저장</button>
									<button id=${ptc_pg1}btn_del class="delete">삭제</button>
									<button id=${ptc_pg1}btn_conf_rqst class="etcBtn">작성완료</button>
									<button id=${ptc_pg1}btn_new class="reset">초기화</button>
								</div>
							</c:if>
						</div>
					</div>
						
						<form id=${ptc_pg1}Frm class="mgT15" name=${ptc_pg1}Frm onsubmit="return false">
							<table cellpadding="0" cellspacing="0" width="100%" class="subTable1 tab3">
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
									<th class="necessary">관련 QMS 모듈</th>
									<td class="disabledClass" id=${ptc_pg1}rltdSysCode colspan="7"></td>
								</tr>
								<tr>
									<th>기타내용</th>
									<td colspan="7">
										<input type="text" id= ${ptc_pg1}requstBasisEtc name="requstBasisEtc"  class="wd100p readonlyClass">
									</td>
								</tr>
								<tr>
									<th class="necessary">관련 QMS 관리번호</th>
									<td colspan="7">
										<div class="btnWrap" style="display: flex;">
											<input type="text" id=${ptc_pg1}issueManageNo name="issueManageNo" class="wd85p readonlyClass">
											<!-- <button id=${ptc_pg1}btn_rltd_module class="etcBtn" style="display: none; margin-left: 5px">보기</button> -->
											<button type="button" id=${ptc_pg1}btn_rltd_module style="display: none; margin-left: 5px;" class="inTableBtn inputBtn">
										  		<img src="/assets/image/btnSearch.png" />
									      	</button>
										</div>
									</td>
								</tr>
								<tr>
									<th>비고</th>
									<td colspan="7">
										<textarea cols="100" rows="8" id=${ptc_pg1}rmRqst name="rmRqst" class="wd100p readonlyClass"></textarea>
									</td>
								</tr>
								<tr>
									<th>첨부 파일</th>
									<td colspan="7">
										<div id=${ptc_pg1}dropzoneArea></div>
										<input type="hidden" id=${ptc_pg1}atchmnflSeqno name="atchmnflSeqno" class="wd25p disabledClass">
									</td>
								</tr>
							</table>
							
							<div class="SubCon2 wd100p fL mgT15" id=${ptc_pg1}btn_DetailGrid1 >
								<h3>CAPA 담당팀</h3>
								<div class="btnWrap">
									<c:if test="${ptc_btn1 == 'ptc_btn1'}">
										<div class="ctsInnerAreaTopRit mgT15" id=${ptc_pg1}btn_AddanDel style="display:block;">
											<button id=${ptc_pg1}btn_pot_deptSch class="etcBtn">찾기</button>
											<button id=${ptc_pg1}btn_removeRow class="etcBtn">행삭제</button>
										</div>
									</c:if>
								</div>
								<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
								<div id=${ptc_pg1}ptcGrid_Detail1 class="mgT15" style="width:100%; height:300px; margin:0 auto;"></div>
							</div>
							
							<input type="hidden" id=${ptc_pg1}capaRequstSeqno name="capaRequstSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}fileSave name="fileSave" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}modiResn name="modiResn" value=''/>
							<input type="hidden" id=${ptc_pg1}userId name="userId" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}issueSeqno name="issueSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}dvtnRceptSeqno name="dvtnRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}dscnttRceptSeqno name="dscnttRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}rtrvlRceptSeqno name="rtrvlRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}eventRceptSeqno name="eventRceptSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}slfctrlChckPlanCode name="slfctrlChckPlanCode" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}extrlAuditPlanSeqno name="extrlAuditPlanSeqno" class="wd25p" style="min-width:10em;">
							<input type="hidden" id=${ptc_pg1}userSch name="userSch" class="wd25p" style="min-width:10em;">

							<input type="hidden" id=${ptc_pg1}apprLine name="apprLine" value="">
							<input type="hidden" id=${ptc_pg1}sanctnSeqno name="sanctnSeqno" value="">
							<input type="hidden" id=${ptc_pg1}sanctnLineSeqno name="sanctnLineSeqno" value="">
							<input type="hidden" id=${ptc_pg1}rtnSeqno name="rtnSeqno" value="">
							<input type="hidden" id=${ptc_pg1}rtnResn name="rtnResn" value="">
							<input type="hidden" id=${ptc_pg1}btnDialConfirm name="btnDialConfirm" >
							<input type="hidden" id=${ptc_pg1}capaProgrssittnCode name="capaProgrssittnCode" >
							<input type="hidden" id=${ptc_pg1}procCode name="procCode" >
							<input type="hidden" id=${ptc_pg1}openRdBtnPopUp name="openRdBtnPopUp" value=''/>
							<input type="hidden" id=${ptc_pg1}apprLineHisBtn name="apprLineHisBtn" value=''/>
						</form>
				</div>
				
				<script src="/viewScript/ptc/tab/CapaRqstWrt.js"></script>