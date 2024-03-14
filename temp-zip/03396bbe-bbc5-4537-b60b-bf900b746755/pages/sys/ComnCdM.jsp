<%@page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
	<tiles:putAttribute name="body">
		<div class="subContent" id="middle_wrap">
			<div class="subCon1">
				<h2><i class="fi-rr-apps"></i>${msg.C000001037}</h2> <!-- 공통 코드 목록 -->
					<div class="btnWrap">
						<button id="addRowBestGroupCode" class="etcBtn">${msg.C000000994}</button> <!-- 최상위 코드 추가 -->
						<button id="addRowDetailCode" class="etcBtn">${msg.C000001002}</button> <!-- 하위 코드 추가 -->
						<button id="removeRowGroupCode" class="etcBtn"><img src="/assets/image/minusBtn.png"></button> <!-- 행삭제 -->
						<button id="changeSortOdr" class="etcBtn"><img src="/assets/image/updownBtn.png"></button> <!-- 정렬 순서변경 -->
						<button id="saveSortOrd" class="save">${msg.C000000988}</button> <!-- 정렬 순서 저장 -->
						<button id="saveGroupCode" class="save">${msg.C000000984}</button> <!-- 저장 -->
						<button id="selectGroupCode" class="search" >${msg.C000000989}</button> <!-- 조회 -->
					</div>
				<form id="SearchForm">
					<table class="subTable1">
						<colgroup>
							<col style="width:10%"/>
							<col style="width:15%"/>
							<col style="width:10%"/>
							<col style="width:15%"/>
							<col style="width:10%"/>
							<col style="width:15%"/>
							<col style="width:10%"/>
							<col style="width:15%"/>
						</colgroup>
						<tr>
							<th>${msg.C000000097}</th> <!-- 공통 코드 -->
							<td><input type="text" id="comnCdSch" name="comnCdSch" class="schClass"></td>
							<th>${msg.C000000098}</th> <!-- 공통 코드 명 -->
							<td><input type="text" id="comnCdNmSch" name="comnCdNmSch" class="schClass"></td>
							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td>
								<input name="useYnSch" value="all" type="radio">${msg.C000000508} <!-- 전체 -->
								<input name="useYnSch" value="Y" type="radio" checked="checked">${msg.C000000238} <!-- 사용 -->
								<input name="useYnSch" value="N" type="radio">${msg.C000000248} <!-- 사용 안함 -->
							</td>
						</tr>
					</table>
				</form>
			</div>

			<div class="subCon2">
				<div id="comnCodeGrid" style="height: 440px;"></div>
			</div>
		</div>

	</tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script src="/viewScript/sys/ComnCdM.js?ver=20230511"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
