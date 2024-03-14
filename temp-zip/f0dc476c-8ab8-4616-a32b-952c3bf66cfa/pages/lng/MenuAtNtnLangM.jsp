<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="title">${msg.C000001061}</tiles:putAttribute> <!-- 메뉴 별 국가 언어 관리 -->
    <tiles:putAttribute name="body">
	<!--  body 시작 -->
	<div class="subContent">
		<div class="subCon1">
			<h2><i class="fi-rr-apps"></i>${msg.C000001061}</h2> <!-- 메뉴 별 국가 언어 관리 -->
			<div class="btnWrap">
				<button id="btnSave" class="save">${msg.C000000984}</button> <!-- 저장 -->
				<button id="btnSearch" class="search">${msg.C000000989}</button> <!-- 조회 -->
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
						<th>${msg.C000000385}</th> <!-- 언어 -->
						<td><select id="ntnLangCdSch" name="ntnLangCdSch"></select></td>
						<th>${msg.C000000316}</th> <!-- 시스템 구분 -->
						<td><select id="sysSeCdSch" name="sysSeCdSch"></select></td>
						<th>${msg.C000000175}</th> <!-- 메뉴 명 -->
						<td><input type="text" id="menuNmSch" name="menuNmSch"></td>
						<td colspan="4"></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="subCon2">
		   <!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
		   <div id="menuGrid" style="width: 100%; height: 450px; margin: 0 auto;"></div>
		</div>
	</div>

	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script src="/viewScript/lng/MenuAtNtnLangM.js?ver=20230511"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
