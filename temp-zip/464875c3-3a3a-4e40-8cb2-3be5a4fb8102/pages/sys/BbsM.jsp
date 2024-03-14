<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="body">

<div class="subContent">
	<div class="subCon1">
		<h2>${msg.C000001030}</h2> <!-- 게시판 목록 -->
		<div class="btnWrap">
			<button id="addRowBtn" class="etcBtn"><i class="fi-rs-plus"></i></button> <!-- 행추가 -->
			<button id="removeRowBtn" class="etcBtn"><i class="fi-rs-minuss"></i></button> <!-- 행삭제 -->
			<button id="deleteBtn" class="delete">${msg.C000000964}</button> <!-- 삭제 -->
			<button id="saveBtn" class="save">${msg.C000000984}</button> <!-- 저장 -->
			<button id="searchBtn" class="search">${msg.C000000989}</button> <!-- 조회 -->
		</div>

		<!-- Main content -->
		<form action="javascript:;" id="bbsForm" name="bbsForm">
			<table cellpadding="0" cellspacing="0" width="100%" class="subTable1">
				<colgroup>
					<col style="width:10%"></col>
					<col style="width:15%"></col>
					<col style="width:10%"></col>
					<col style="width:65%"></col>
				</colgroup>
				<tr>
					<th>${msg.C000000082}</th> <!-- 게시판 명 -->
					<td><input type="text"  id="bbsNm" name="bbsNm"></td>
					<th>${msg.C000000249}</th> <!-- 사용 여부 -->
					<td style="text-align:left;">
						<input name="useYn" value="all" type="radio">${msg.C000000508} <!-- 전체 -->
						<input name="useYn" value="Y" type="radio" checked="checked">${msg.C000000238} <!-- 사용 -->
				        <input name="useYn" value="N" type="radio">${msg.C000000248} <!-- 사용 안함 -->
				    </td>
				</tr>
			</table>
		</form>
	</div>
	<div class="subCon2">
		<div class="mgT15">
			<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
			<div id="bbsGrid" style="width:100%; height:400px; margin:0 auto;"></div>
		</div>
	</div>
</div>
    </tiles:putAttribute>
    <tiles:putAttribute name="head">
		<script src="/viewScript/sys/BbsM.js?ver=20231013"></script>
<!--  script 끝 -->
	</tiles:putAttribute>
</tiles:insertDefinition>
