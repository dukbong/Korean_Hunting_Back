<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
	<tiles:putAttribute name="body">
		<div class="subContent">
			<div class="subCon1">
				<h2><i class="fi-rr-apps"></i>${msg.C000001089}</h2> <!-- 사용자 목록 -->
				<div class="btnWrap">
					<c:if test = "${UserMVo.authrtCd == 'GRNT000002'}">
						<button type="button" id="btn_notUse" class="delete">${msg.C000000248}</button> <!-- 사용 안함 -->
						<button type="button" id="btn_reset_password" class="save">${msg.C000000961}</button> <!-- 비밀번호초기화 -->
						<button type="button" id="btn_notLoginSixMnth_select" class="search">${msg.C000000929}</button> <!-- 6개월 이상 미접속자 조회 -->
					</c:if>
					<button type="button" id="btn_select" value="조회" class="search">${msg.C000000989}</button> <!-- 조회 -->
				</div>
				<form id="searchFrm" onsubmit="return false;">
					<table class="subTable1" style="width:100%">
						<colgroup>
							<col style="width: 10%"></col>
							<col style="width: 15%"></col>
							<col style="width: 10%"></col>
							<col style="width: 15%"></col>
							<col style="width: 10%"></col>
							<col style="width: 15%"></col>
						</colgroup>
						<tr>
							<th>${msg.C000000215}</th> <!-- 부서명 -->
							<td><select id="deptSnSch" name="deptSnSch" ></select></td>
							<th>${msg.C000000257}</th> <!-- 사용자 명 -->
							<td><input type="text" id="userNmSch" name="userNmSch"></td>
							<input type="hidden" id="bplcCdSch" name="bplcCdSch" value="${UserMVo.bplcCd}">
							<th>${msg.C000000249}</th> <!-- 사용여부 -->
							<td style="text-align:left;">
								<label><input type="radio" id="use_allsch" name="useYnSch" value="all" >${msg.C000000508}</label> <!-- 전체 -->
								<label><input type="radio" id="use_ysch" name="useYnSch" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="use_nsch" name="useYnSch" value="N" >${msg.C000000248}</label> <!-- 사용안함 -->
							</td>
							<td colspan="2"></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="subCon2">
				<div id="userGrid"></div>
			</div>

			<div class="subCon1 mgT20">
				<h2><i class="fi-rr-apps"></i>${msg.C000001090}</h2> <!-- 사용자 상세 정보 -->
				<div class="btnWrap">
					<button id="btn_new" class="reset">${msg.C000000993}</button> <!-- 초기화 -->
					<c:if test = "${UserMVo.authrtCd == 'GRNT000002'}">
						<button id="btn_delete" class="delete" style="display:none">${msg.C000000964}</button> <!-- 삭제 -->
						<button type="button" id="btn_renewal" value="갱신" class="save">${msg.C000000940}</button> <!-- 갱신 -->
					</c:if>
					<button id="btn_save" class="save">${msg.C000000984}</button> <!-- 저장 -->
					<input type="hidden" id="btnSave_sign" value="서명파일">
				</div>
				<form id="userInfoFrm" autocomplete="off" onsubmit="return false;" data-match="userGrid">
					<table class="subTable1" style="width:100%">
						<colgroup>
							<col style="width: 10%"></col>
							<col style="width: 15%"></col>
							<col style="width: 10%"></col>
							<col style="width: 15%"></col>
							<col style="width: 10%"></col>
							<col style="width: 15%"></col>
							<col style="width: 10%"></col>
							<col style="width: 15%"></col>
						</colgroup>
						<tr>
							<th class="taCt vaMd necessary">${msg.C000000215}</th> <!-- 부서 명-->
							<td>
								<select id="deptSn" name="deptSn" required ></select>
							</td>

							<th class="taCt vaMd necessary">${msg.C000000257}</th> <!-- 사용자 명 -->
							<td>
								<input type="text" id="userNm" name="userNm" class="GuBunUser" autocomplete="off" onkeyup="fnChkByte(this,200)" required >
							</td>

							<th class="taCt vaMd necessary">${msg.C000000168}</th> <!-- 로그인ID -->
							<td>
								<input type="text" id="lgnId" name="lgnId" class="GuBunUser" autocomplete="off" onkeyup="fnChkByte(this,32)" required>
							</td>

							<th>${msg.C000000434}</th> <!-- 이동 전화번호 -->
							<td>
								<input type="text" id="mvmnTelno" name="mvmnTelno" oninput="autoHyphen(this)" class="GuBunUser" placeholder="${msg.C000000001}" onkeyup="fnChkByte(this,20)" data-validator="tel"/> <!-- 10, 11자리 숫자 -->
							</td>
						</tr>
						<tr>
							<th>${msg.C000000438}</th> <!-- 이메일 -->
							<td>
								<input type="text" id="eml" name="eml" class="GuBunUser" placeholder="sample@sample.com" onkeyup="fnChkByte(this,100)" data-validator="email" />
							</td>

							<th>${msg.C000000235}</th> <!-- 사번 -->
							<td>
								<input type="text" id="empno" name="empno" class="GuBunUser" onkeyup="fnChkByte(this,10)"/>
							</td>

							<th>${msg.C000000561}</th> <!-- 직위 명 -->
							<td>
								<input type="text" id="jbpsNm" name="jbpsNm" class="GuBunUser" onkeyup="fnChkByte(this,200)"/>
							</td>

							<th>${msg.C000000563}</th> <!-- 직책 명 -->
							<td>
								<input type="text" id="jbttlNm" name="jbttlNm" class="GuBunUser" onkeyup="fnChkByte(this,200)"/>
							</td>
						</tr>
						<tr>
							<th>${msg.C000000464}</th> <!-- 입사 일자 -->
							<td>
								<input type="text" id="jncmpYmd" name="jncmpYmd" class="GuBunUser dateChk" maxlength="10">
							</td>

							<th>${msg.C000000630}</th> <!-- 퇴사 일자 -->
							<td>
								<input type="text" id="rsgntnYmd" name="rsgntnYmd" class="GuBunUser dateChk" maxlength="10">
							</td>

							<th>${msg.C000000045}</th> <!-- 가입 승인 여부 -->
							<td style="text-align: left;">
								<label><input type="radio" id="joinAprvYnY" name="joinAprvYn" value="Y" checked>${msg.C000000298}</label> <!-- 승인 -->
								<label><input type="radio" id="joinAprvYnN" name="joinAprvYn" value="N">${msg.C000000189}</label> <!-- 미승인 -->
							</td>
							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td style="text-align:left;" >
								<label><input type="radio" id="useYnY" name="useYn" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="useYnN" name="useYn" value="N">${msg.C000000248}</label> <!-- 사용안함 -->
							</td>
						</tr>
						<tr>
							<th>${msg.C000000485}</th> <!-- 잠금 여부 -->
							<td style="text-align: left;">
								<label><input type="radio" id="lockYnN" name="lockYn" value="N" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="lockYnY" name="lockYn" value="Y">${msg.C000000484}</label></td> <!-- 잠금 -->
							<th id="authrtTh"  class="taCt ">${msg.C000000118}</th> <!-- 권한 -->
							<td id="authrtTd">
								<select id="authrtCd" name="authrtCd"></select>
								<input type="hidden" id="authorCode" name="authorCode" />
							</td>
							<th>${msg.C000000090}</th> <!-- 계정 잠금 사유 -->
								<td colspan="3">
								<input type="text" id="acntLockRsn" name="acntLockRsn" onkeyup="fnChkByte(this,4000)">
							</td>
							<input type="hidden" id="userSn" name="userSn" />
						</tr>
						<tr style="display:none;">
							<th>${msg.C000000374}</th> <!-- 시험자 여부 -->
							<td style="text-align:left;" >
								<label><input type="radio" id="testerYnY" name="testerYn" value="Y" checked disabled>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="testerYnN" name="testerYn" value="N" disabled>${msg.C000000248}</label> <!-- 사용안함 -->
							</td>

							<th>${msg.C000000576}</th> <!-- 채취자 여부 -->
							<td style="text-align:left;" >
								<label><input type="radio" id="colctorYnY" name="colctorYn" value="Y" checked disabled>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="colctorYnN" name="colctorYn" value="N" disabled>${msg.C000000248}</label> <!-- 사용안함 -->
							</td>

							<th>${msg.C000000030}</th> <!-- LIMS 사용자 여부 -->
							<td style="text-align: left;">
								<label><input type="radio" id="limsUserYnY" name="limsUserYn" value="Y" checked disabled>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="limsUserYnN" name="limsUserYn" value="N" disabled>${msg.C000000248}</label> <!-- 사용안함 -->
							</td>

							<th>${msg.C000000151}</th> <!-- 담당자 여부 -->
							<td style="text-align: left;">
								<label><input type="radio" id="picYnY" name="picYn" value="Y" checked disabled>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="picYnN" name="picYn" value="N" disabled>${msg.C000000248}</label> <!-- 사용안함 -->
							</td>

							<th>${msg.C000000279}</th> <!-- 서명이미지 -->
							<td style="text-align: left;" colspan="3">
								<input type="file" id="printingFile" disabled/>
								<input type="hidden" id="elecSignSn" name="elecSignSn" disabled/>
								<a id="userFileDownload" style="cursor:pointer; margin-left:20px;" disabled=""></a>
							</td>

							<th>${msg.C000000636}</th> <!-- 팝업 사용 여부 -->
							<td style="text-align: left;">
								<label><input type="radio" id="popupUseYnY" name="popupUseYn" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="popupUseYnN" name="popupUseYn" value="N">${msg.C000000248}</label> <!-- 사용안함 -->
							</td>
						</tr>
					</table>

					<input type="hidden" id="authorTrgterSeqno" name="authorTrgterSeqno">
				</form>
			</div>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script src="/viewScript/wrk/UserM.js?ver=20231121"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
