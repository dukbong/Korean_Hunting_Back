<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
<tiles:putAttribute name="title">${msg.C000001157}</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="subContent">
			<div class="subCon1">
				<h2><i class="fi-rr-apps"></i>${msg.C000001080}</h2> <!-- 부서 목록 -->
				<div class="btnWrap">
					<button id="btn_select" class="search">${msg.C000000989}</button> <!-- 조회 -->
				</div>
				<form id="searchFrm" onSubmit="return false;">
					<table class="subTable1" style="width:100%">
						<colgroup>
							<col style="width:10%"></col>
							<col style="width:15%"></col>
							<col style="width:10%"></col>
							<col style="width:15%"></col>
							<col style="width:10%"></col>
							<col style="width:15%"></col>
						</colgroup>
						<tr>
							<th>${msg.C000000267}</th> <!-- 상위 부서 명 -->
							<td>
								<select id="upDeptSnSch" name="upDeptSnSch"></select>
							</td>

							<th>${msg.C000000215}</th> <!-- 부서 명 -->
							<td>
								<input type="text" id="deptNmSch" name="deptNmSch" class="schClass"/>
							</td>

							<th>${msg.C000000216}</th> <!-- 부서 코드 -->
							<td>
								<input type="text" id="deptCdSch" name="deptCdSch" class="schClass"/>
							</td>

							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td style="text-align:left;">
								<label><input type="radio" name="useYnSch" value="all" />${msg.C000000508}</label> <!-- 전체 -->
								<label><input type="radio" name="useYnSch" value="Y" checked />${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" name="useYnSch" value="N" />${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>

						</tr>
					</table>
				</form>
			</div>
			<div class="subCon2">
				<div id="orgGrid" class="wd100p" style="height:350px; margin:0 auto;"></div>
			</div>
			<div class="subCon1 mgT20">
				<h2><i class="fi-rr-apps"></i>${msg.C000001081}</h2> <!-- 부서 정보 -->
				<div class="btnWrap">
					<button id="btn_new" type="button" class="reset">${msg.C000000993}</button> <!-- 초기화 -->
					<button id="btnDelete" type="button" class="delete" style="display: none;">${msg.C000000964}</button> <!-- 삭제 -->
					<button id="btn_save" type="button" class="save">${msg.C000000984}</button> <!-- 저장 -->
				</div>
				<form id="ognzForm" onSubmit="return false;" >
					<table class="subTable1" style="width:100%">
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
							<th class="necessary">${msg.C000000267}</th> <!-- 상위 부서 명 -->
							<td><select id="upDeptSn" name="upDeptSn" required></select></td>

							<th class="necessary">${msg.C000000215}</th> <!-- 부서 명 -->
							<td>
								<input type="text" id="deptNm" name="deptNm" maxlength="200" required/>
							</td>

							<th>부서 코드</th> <!-- 부서 코드 -->
							<td>
								<input type="text" id="deptCd" name="deptCd">
							</td>

							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td colspan="1" style="text-align:left;">
								<label><input type="radio" id="use_y" name="useYn" value="Y" checked />${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" id="use_n" name="useYn" value="N" />${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
							<!-- 부서 생성/삭제를 구분하기 위한 일련번호 -->
                            <input type="text" id="deptSn" name="deptSn" style="display:none;"/>
							<input type="text" id="hghrkDeptSn" name="hghrkDeptSn" style="display:none;"/>
							<input type="text" id="delYn" name="delYn" value="N" style="display:none;"/>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script src="/viewScript/wrk/OgnzM.js?ver=20230511"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
