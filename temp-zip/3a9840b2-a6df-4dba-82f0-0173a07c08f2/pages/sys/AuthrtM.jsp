<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
	<tiles:putAttribute name="title">권한 관리</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="subContent" style="height: 900px;">
			<div class="subCon1">
				<h2><i class="fi-rr-apps"></i>${msg.C000001048}</h2> <!-- 권한 목록 -->
				<div class="btnWrap">
					<button id="btnAddRow" class="etcBtn"><img src="/assets/image/plusBtn.png"></button> <!-- 행추가 -->
					<button id="btnRemoveRow" class="etcBtn"><img src="/assets/image/minusBtn.png"></button> <!-- 행삭제 -->
					<button id="btnSaveAth" class="save">${msg.C000000984}</button> <!-- 저장 -->
					<button id="btnSearchAth" class="search">${msg.C000000989}</button>  <!-- 조회 -->
				</div>
				<form id="searchForm">
					<input type="text" id="authrtSn" name="authrtSn" style="display: none"/>
					<input type="hidden" id="authrtCd" name="authrtCd"/>
					<table class="subTable1">
						<colgroup>
							<col style="width: 10%"/>
							<col style="width: 15%"/>
							<col style="width: 10%"/>
							<col style="width: 15%"/>
							<col style="width: 10%"/>
							<col style="width: 15%"/>
							<col style="width: 10%"/>
							<col style="width: 15%"/>
						</colgroup>
						<tr>
							<th>${msg.C000000119}</th> <!-- 권한 그룹 명 -->
							<td><input type="text" class="wd100p schClass" name="authrtNm" id="authrtNm"></td>
							<th>${msg.C000000257}</th> <!-- 사용자 명 -->
							<td><input type="text" class="wd100p schClass" name="userSn" id="userSn"></td>
							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td style="text-align: left;">
								<label><input type="radio" name="useYn" value="">${msg.C000000508}</label> <!-- 전체 -->
								<label><input type="radio" name="useYn" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" name="useYn" value="N">${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
							<td colspan="2"></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="subCon2"> <!-- 권한목록 그리드 -->
				<div id="authrtGrid" class="mgT15" style="width: 100%; height: 250px; margin: 0 auto;"></div>
			</div>

			<div class="mgT20" style="display: flex">
				<div class="wd50p fL mgR20">
					<div class="subCon1">
						<h3>${msg.C000001047}</h3> <!-- 권한 메뉴 -->
						<div class="btnWrap">
							<button id="btnSaveAthMenu" class="save">${msg.C000000984}</button> <!-- 저장 -->
						</div>
					</div>
					<div class="subCon2"> <!-- 권한메뉴 그리드 -->
						<div id="authMenuGrid" class="mgT15" style="height: 353px;"></div>
					</div>
				</div>

				<div class="wd50p">
					<div class="subCon1">
						<h3>${msg.C000001049}</h3> <!-- 권한 사용자 -->
						<div class="btnWrap">
							<button id="btnSaveAthUser" class="save">${msg.C000000984}</button> <!-- 저장 -->
							<button id="btnSearchAthUser" class="search">${msg.C000000989}</button> <!-- 조회 -->
						</div>
						<form id="userSearchForm" onsubmit="return false">
							<table class="subTable1 mgT14">
								<colgroup>
									<col style="width: 20%"/>
									<col style="width: 30%"/>
									<col style="width: 20%"/>
									<col style="width: 30%"/>
								</colgroup>
								<tr>
									<th>${msg.C000000214}</th> <!-- 부서 -->
									<td><select id="deptSn" name="deptSn"></select></td>
									<th>${msg.C000000257}</th> <!-- 사용자 명 -->
									<td><input type="text" id="userNm" name="userNm" placeholder="사용자 명"/></td> <!-- 사용자 명 -->
								</tr>
							</table>
						</form>
					</div>
					<div class="subcon2"> <!-- 권한사용자 그리드 -->
						<div id="authUserGrid" class="mgT15" style="height: 300px;"></div>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script type="text/javascript" src="/viewScript/sys/AuthrtM.js?ver=20231013"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
