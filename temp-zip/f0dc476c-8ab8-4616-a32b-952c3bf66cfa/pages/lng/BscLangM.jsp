<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="title">${msg.C000001052}</tiles:putAttribute> <!-- 기본 언어 관리 -->
    <tiles:putAttribute name="body">
<!--  body 시작 -->
<div class="subContent">
	<div class="subCon1">
		<h2><i class="fi-rr-apps"></i>${msg.C000001052}</h2> <!-- 기본 언어 관리 -->
		<div class="btnWrap">
			<button id="btn_add" class="etcBtn"><img src="/assets/image/plusBtn.png"></button>
			<button id="btn_remove" class="etcBtn"><img src="/assets/image/minusBtn.png"></button>
			<button id="btnSave" class="save">${msg.C000000984}</button> <!-- 저장 -->
			<button id="btnSearch" class="search" >${msg.C000000989}</button> <!-- 조회 -->
		</div>
		<!-- Main content -->
		<form id="searchFrm" name="searchFrm" onsubmit="return false" >
			<table cellpadding="0" cellspacing="0" width="100%" class="subTable1">
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
					<th>${msg.C000000392}</th> <!-- 언어 코드 -->
					<td><input type="text" id="langCoSch" name="langCoSch"></td>
					<th>${msg.C000000389}</th> <!-- 언어 명 -->
					<td><input type="text" id="langNmSch" name="langNmSch"></td>
					<th>${msg.C000000387}</th> <!-- 언어 구분 -->
					<td><select id="langSeCdSch" name="langSeCdSch" style="width: 100%;"></select></td>
					<th>${msg.C000000249}</th> <!-- 사용 여부 -->
					<td colspan="1" style="text-align:left;">
						<label><input type="radio" id="use_a" name="useAtSch" value="">${msg.C000000508}</label> <!-- 전체 -->
						<label><input type="radio" id="use_y" name="useAtSch" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
						<label><input type="radio" id="use_n" name="useAtSch" value="N" >${msg.C000000248}</label> <!-- 사용 안함 -->
					</td>
					<td colspan="2"></td>
				</tr>
			</table>
		</form> 
	</div>
		<div class="subCon2">
             <!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
           <div id="defaultGrid" style="width: 100%; height: 450px; margin: 0 auto;"></div>
        </div> 
	</div>
	<!--  body 끝 -->
    </tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script src="/viewScript/lng/BscLangM.js?ver=20230511"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
