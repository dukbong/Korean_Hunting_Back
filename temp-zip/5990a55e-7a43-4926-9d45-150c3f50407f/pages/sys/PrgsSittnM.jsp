<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="base-definition">
    <tiles:putAttribute name="title">진행상황관리</tiles:putAttribute>
    <tiles:putAttribute name="body">
        <div class="subContent" id="middle_wrap" style="height: 640px;">
            <div class="subCon1">
                <h2><i class="fi-rr-apps"></i>진행상황 목록</h2> <!-- 메뉴 목록 -->
                <div class="btnWrap">
                    <button id="moveRowsToUp" class="etcBtn">위로</button> <!-- 조회 -->
                    <button id="moveRowsToDown" class="etcBtn">아래로</button> <!-- 조회 -->
                    <button id="btnSave" class="save">저장</button> <!-- 저장 -->
                    <button id="btnSearch" class="search">조회</button> <!-- 조회 -->
                </div>
                <form id="searchFrm" name="searchFrm" onsubmit="return false">
                    <table cellpadding="0" cellspacing="0" width="100%" class="subTable1">
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
                            <th>품목 그룹</th> <!-- 품목 그룹 -->
                            <td><select id="itemGroupCdSch" name="itemGroupCd"></select></td>
                        </tr>
                    </table>
                    <input type="hidden" id="upComnCd" name="upComnCd" value="PRGS">
                </form>
            </div>
            <div class="subCon2">
                    <div id="prgsSittnGrid" style="width:100%; height:500px; margin:0 auto;"></div>
            </div>
        </div>
    </tiles:putAttribute>
    <tiles:putAttribute name="head">
        <script type="text/javascript" src="/viewScript/sys/PrgsSittnM.js?ver=20230519"></script>
    </tiles:putAttribute>
</tiles:insertDefinition>
