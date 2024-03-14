<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
<tiles:putAttribute name="title">${msg.C000001099}</tiles:putAttribute>
	<tiles:putAttribute name="body">
	<!--  body 시작 -->
	<div class="subContent">
		<div class="subCon1">
			<h2><i class="fi-rr-apps"></i>${msg.C000001097}</h2> <!-- 시험 이력 목록 -->
			<div class="btnWrap">
				<button id="btn_search" name="btn_search" class="btn1 search">${msg.C000000989}</button> <!-- 조회 -->
			</div>
			<!-- Main content -->
			<form id="frmSearch">
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
						<th >${msg.C000000435}</th> <!-- 이력 구분 -->
						<td>
							<label><select id="hstrySeCdSch" name="hstrySeCdSch"></select></label>
						</td>
						<th >${msg.C000000437}</th> <!-- 이력 대상 값 -->
						<td>
							<label><input type="text" id="hstryTrgtVlSch" name="hstryTrgtVlSch" class="schClass"></label>
						</td>
						<th>발생 일자</th> <!-- 발생 일자 -->
						<td>
							<input type="text" id="crtDtBeginSch" name="crtDtBeginSch" class="dateChk wd6p schClass" style="min-width: 7em;">
							~
							<input type="text" id="crtDtEndSch" name="crtDtEndSch" class="dateChk wd6p schClass" style="min-width: 7em;">
						</td>
						<%-- <th>${msg.C000000316}</th> <!-- 시스템 구분 -->
						<td>
							<select id="sysSeCdSch" name="sysSeCdSch"/>
						</td> --%>
						
					</tr>
					<tr>
						<th>${msg.C000000171}</th> <!-- 메뉴 1차 분류 -->
						<td><select id="menuOne" name="menuOne" class="schClass" style="width: 100%"></select></td>
						<th>${msg.C000000172}</th> <!-- 메뉴 2차 분류 -->
						<td><select id="menuTwo" name="menuTwo" class="schClass" style="width: 100%"></select></td>
						<th>${msg.C000000173}</th> <!-- 메뉴 3차 분류 -->
						<td><select id="menuThree" name="menuThree" class="schClass" style="width: 100%"></select></td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 에이유아이 그리드가 이곳에 생성됩니다. -->
		<div class="subCon2 mgT15">
			<div id="processLogMGridId" style="height:500px;"></div>
			<%--<div style="width:20%;display:inline-block;">
				<textarea id="processDetailTx" name="processDetailTx" rows="20" style="width: 100%;" readonly></textarea>
			</div>--%>
		</div>
		<div class="subCon1 mgT30">
			<h3>${msg.C000001098}</h3> <!-- 시험 이력 상세 -->
		</div>
		<div class="subCon2"> <!-- 변경 전 그리드 -->
			<div id="processDetailGrid" class="mgT15" style="height: 300px;"></div>
		</div>
		<!-- 행 데이터 상태 표시 라벨 -->
		<div class="mapkey">
			<label class="bg-aprvRjct">${msg.C000000197}</label> <!-- 변경 데이터 -->
		</div>


	</div>
	<!--  body 끝 -->
	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script src="/viewScript/sys/TestHstryInqM.js?ver=20230511"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
