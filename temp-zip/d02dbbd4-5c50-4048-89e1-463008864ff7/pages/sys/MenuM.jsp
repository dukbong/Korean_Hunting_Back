<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="title">메뉴 관리</tiles:putAttribute>
    <tiles:putAttribute name="body">
		<div class="subContent">
			<div class="subCon1">
				<h2>${msg.C000001060}</h2> <!-- 메뉴 목록 -->
				<div class="btnWrap">
					<button id="changeSortOdr" class="etcBtn"><img src="/assets/image/updownBtn.png"></button> <!-- 정렬 순서변경 -->
					<button id="saveSortOrd" class="save">${msg.C000000988}</button> <!-- 정렬 순서 저장 -->
					<button id="btnSearch" class="search">${msg.C000000989}</button> <!-- 조회 -->
				</div>
				<form id="searchFrm" name="searchFrm" onsubmit="return false">
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
							<th>${msg.C000000171}</th> <!-- 메뉴 1차 분류 -->
							<td><select id="menuOne" name="menuOne" class="schClass"></select></td>
							<th>${msg.C000000172}</th> <!-- 메뉴 2차 분류 -->
							<td><select id="menuTwo" name="menuTwo" class="schClass"></select></td>
							<th>${msg.C000000175}</th> <!-- 메뉴 명 -->
							<td><input type="text" id="menuNmSch" name="menuNmSch" onsubmit="return false" class="schClass"></td>
							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td>
								<label><input name="useYnSch" value="" type="radio">${msg.C000000508}</label> <!-- 전체 -->
								<label><input name="useYnSch" value="Y" type="radio" checked="checked">${msg.C000000238}</label> <!-- 사용 -->
								<label><input name="useYnSch" value="N" type="radio">${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
						</tr>
					</table>
				</form>
			</div>

			<div class="subCon2">
				<div id="menuGrid" style="height: 420px"></div>
			</div>

			<div class="subCon1 mgT20">
				<h2>${msg.C000001062}</h2> <!-- 메뉴 상세 정보 -->
				<div class="btnWrap">
					<button id="btnNew" class="reset">${msg.C000000993}</button> <!-- 초기화 -->
					<button id="btnManual" class="etcBtn" style="display: none">${msg.C000000950}</button> <!-- 매뉴얼 관리-->
					<button id="btnSave" class="save">${msg.C000000984}</button> <!-- 저장 -->
				</div>
				<form id="menuDtlFrm" name="menuDtlFrm" onsubmit="return false" data-match="menuGrid">
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
							<th>${msg.C000000266}</th> <!-- 상위 메뉴 -->
							<td><select class="wd100p" id="upMenuSn" name="upMenuSn"></select>
							<th>${msg.C000000316}</th> <!-- 시스템 구분 -->
							<td><select class="wd100p" id="sysSeCd" name="sysSeCd" disabled></select>
							<th class="necessary">${msg.C000000175}</th> <!-- 메뉴 명 -->
							<td><input type="text" name="menuNm" id="menuNm" required maxlength="200"></td>
							<th>${msg.C000000043}</th> <!-- URL -->
							<td><input type="text" name="menuUrl" id="menuUrl" maxlength="200"></td>
						</tr>
						<tr>
							<th>${msg.C000000170}</th> <!-- 매뉴얼 사용 여부 -->
							<td>
								<label><input type="radio" name="mnlUseAt" id="mnlUseY" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" name="mnlUseAt" id="mnlUseN" value="N">${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
							<th>${msg.C000000306}</th> <!-- 승인 요청 여부 -->
							<td>
								<label><input type="radio" name="aprvDmndYn" id="atrzUseYnY" value="Y" >${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" name="aprvDmndYn" id="atrzUseYnN" value="N" checked>${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
							<th id="aprv" >${msg.C000000301}</th> <!-- 승인 메뉴 -->
							<td><select id="aprvMenuSn" name="aprvMenuSn" style="width: 100%"></select>
							<th>${msg.C000000249}</th> <!-- 사용 여부 -->
							<td>
								<label><input type="radio" name="useYn" id="useY" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" name="useYn" id="useN" value="N">${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
						</tr>
						<tr>
							<th>${msg.C000001405}</th> <!-- 설정 전용 여부 -->
							<td>
								<label><input type="radio" name="stngDvrsnYn" id="stngDvrsnY" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
								<label><input type="radio" name="stngDvrsnYn" id="stngDvrsnN" value="N">${msg.C000000248}</label> <!-- 사용 안함 -->
							</td>
							<th>${msg.C000000176}</th> <!-- 메뉴 설명 -->
							<td colspan="5"><textarea name="menuExpln" id="menuExpln" rows="2" maxlength="4000"></textarea></td>
						</tr>
					</table>
					<input type="hidden" name="menuSn" id="menuSn">
					<input type="hidden" name="mnlCn" id="mnlCn">
					<input type="hidden" name="menuLv" id="menuLv" value="1">
				</form>
			</div>
		</div>
    </tiles:putAttribute>
	<tiles:putAttribute name="head">
		<script type="text/javascript" src="/viewScript/sys/MenuM.js?ver=20230913"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>
