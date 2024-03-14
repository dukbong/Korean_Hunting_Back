<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
	<tiles:putAttribute name="title">${msg.C000001084}</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<!--  body 시작 -->
		<div class="subContent" style="height: 900px;">
			<div class="subCon1">
				<h2><i class="fi-rr-apps"></i>${msg.C000001084}</h2><!-- 사업장 관리 -->
				<div class="btnWrap">
					<button id="btn_add" class="etcBtn" style="display: none;"><img src="/assets/image/plusBtn.png"></button>
					<button id="btn_remove" class="etcBtn" style="display: none;"><img src="/assets/image/minusBtn.png"></button>
					<button id="btn_save" class="save" data-divid="eSign1">${msg.C000000984}</button><!-- 저장 -->
					<button id="btn_select" class="search" data-divid="eSign2">${msg.C000000989}</button><!-- 조회 -->
				</div>
				<form id="searchForm" action="javascript:;">
					<table cellpadding="0" cellspacing="0" width="100%" class="subTable1">
						<colgroup>
							<col style="width:10%"></col>
							<col style="width:15%"></col>
							<col style="width:10%"></col>
							<col style="width:65%"></col>
						</colgroup>
						<tr>
							<th>${msg.C000000236}</th> <!-- 사업장 명 -->
							<td><input type="text" id ="deptNmSch" name ="deptNmSch" class="schClass" maxlength="100"></td>
							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td style="text-align:left;">
								<label><input type="radio" name="useYnSch" value="all">${msg.C000000508}</label> <!-- 전체 -->
						    	<label><input type="radio" name="useYnSch" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
						    	<label><input type="radio" name="useYnSch" value="N" >${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
						</tr>
					</table>
				</form>

			</div>
			<div class="subCon2">
				<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
				<div id="bplcGrid" class="mgT15" style="width:100%; height:350px; margin:0 auto;"></div>
			</div>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script src="/viewScript/sys/BplcM.js?ver=20230511"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
