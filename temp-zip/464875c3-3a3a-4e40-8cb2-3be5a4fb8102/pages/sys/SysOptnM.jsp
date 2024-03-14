<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="base-definition">
<tiles:putAttribute name="title">시스템 설정</tiles:putAttribute>
<tiles:putAttribute name="body">
<!--  body 시작 -->
<div class="subContent">
    <div class="subCon1">
        <h2>${msg.C000001096}</h2> <!-- 시스템 설정 -->
        <div class="btnWrap">
            <button type="button" id="passwordReset" class="save">${msg.C000000961}</button> <!-- 비밀번호 초기화 -->
            <button type="button" id="btnSave" class="save">${msg.C000000984}</button> <!-- 저장 -->
        </div>
        <form id="SysOptnMForm" onsubmit="return false;">
            <input type="hidden" id="sysOptnSn" name="sysOptnSn">
            <table class="subTable1">
                <colgroup>
                    <col style="width:25%"/>
                    <col style="width:25%"/>
                    <col style="width:25%"/>
                    <col style="width:25%"/>
                </colgroup>
                <tr>
                    <th>${msg.C000000584}</th> <!-- 초기화 비밀번호 -->
                    <td><input type="text" id="initlPswd" name="initlPswd" class="wd100p" style="min-width:10em;"></td>
                    <th >${msg.C000000229}</th> <!-- 비밀번호 관리 실행 여부 -->
                    <td style="text-align: left;">
                        <label><input type="radio" id="pswdMngExcnYn_Y" name="pswdMngExcnYn" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
                        <label><input type="radio" id="pswdMngExcnYn_N" name="pswdMngExcnYn" value="N" >${msg.C000000248}</label> <!-- 사용 안함 -->
                    </td>
                </tr>
                <tr>
                    <th id="lockNumot">${msg.C000000233}</th> <!-- 비밀번호 최소 자릿수 -->
                    <td><input type="text" id="pswdMinCpr" name="pswdMinCpr" class="wd100p numChk" style="min-width:10em;" maxlength="5"></td>
                    <th class="lockNumot">${msg.C000000553}</th> <!-- 중복 문자 제한 횟수 -->
                    <td><input type="text" id="dpcnLmtNmtm" name="dpcnLmtNmtm" class="wd100p numChk" style="min-width:10em;" maxlength="5"></td>
                </tr>
                <tr>
                    <th class="lockNumot" >${msg.C000000499}</th> <!-- 비밀번호 재사용 가능 횟수 -->
                    <td><input type="text" id="reusePsbltyNmtm" name="reusePsbltyNmtm" class="wd100p numChk" style="min-width:10em;" maxlength="10"></td>
                    <th class="necessary">${msg.C000000397}</th> <!-- 연속 문자 비밀번호 사용 여부 -->
                    <td style="text-align: left;">
                        <label><input type="radio" id="cntnuPswdUseYn_Y" name="cntnuPswdUseYn" value="Y" checked>${msg.C000000238}</label> <!-- 사용 -->
                        <label><input type="radio" id="cntnuPswdUseYn_N" name="cntnuPswdUseYn" value="N">${msg.C000000248}</label> <!-- 사용 안함 -->
                    </td>
                </tr>
                <tr>
                    <th >${msg.C000000232}</th> <!-- 비밀번호 조합 옵션 코드 -->
                    <td style="text-align: left;">
                        <c:forEach var="item" items="${pwmo}" varStatus="status">
                            <label><input type="radio" name="mixOptnCd" id="mixOptnCd_${status.count}" value="${item.value}"> ${item.key}</label>
                        </c:forEach>
                    </td>
                    <th class="">${msg.C000000230}</th> <!-- 비밀번호 변경 기한 일 -->
                    <td><input type="text" id="pswdChgTermDay" name="pswdChgTermDay" class="wd100p numChk" style="min-width:10em;" maxlength="3"></td>
                </tr>
                <tr>
                    <th class="necessary">${msg.C000000167}</th> <!-- 로그인 실패 잠금 횟수 -->
                    <td><input type="text" id="lgnFailLockNmtm" name="lgnFailLockNmtm" class="wd100p numChk" style="min-width:10em;" maxlength="3"></td>
                    <th class="necessary">${msg.C000000021}</th> <!-- ELN 사용 여부 -->
                    <td style="text-align: left;">
                        <label><input type="radio" id="elnUseYn_Y" name="elnUseYn" value="Y">${msg.C000000238}</label> <!-- 사용 -->
                        <label><input type="radio" id="elnUseYn_N" name="elnUseYn" value="N" checked>${msg.C000000248}</label> <!-- 사용 안함 -->
                    </td>
                </tr>
                <tr>
                    <th class="necessary">${msg.C000000029}</th> <!-- LIMS 물질 차감 관리 여부 -->
                    <td style="text-align: left;">
                        <label><input type="radio" id="limsMtrlSbtrnMngYn_Y" name="limsMtrlSbtrnMngYn" value="Y">${msg.C000000158}</label> <!-- 대응 -->
                        <label><input type="radio" id="limsMtrlSbtrnMngYn_N" name="limsMtrlSbtrnMngYn" value="N" checked>${msg.C000000185}</label> <!-- 미대응 -->
                    </td>
                    <td colspan="2"/>
                </tr>
                <tr>
                    <th>${msg.C000000317}</th> <!-- 시스템 로고 -->
                    <td>
                        <input type="file" id="sysLogoImg" name="sysLogoImg" accept="image/*">
                        <img src="/assets/logo/logo.png" alt="로고" width="186" height="36">
                    </td>
                    <th>${msg.C000000625}</th> <!-- 탭 아이콘 -->
                    <td>
                        <input type="file" id="faviconImg" name="faviconImg" accept="image/*">
                        <img src="/assets/logo/favicon.ico" alt="아이콘" width="64" height="64">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</tiles:putAttribute>
<tiles:putAttribute name="head">
    <script>var bplcCdValue = '${UserMVo.bplcCd}';</script>
    <script src="/viewScript/sys/SysOptnM.js?ver=20230605"></script>
</tiles:putAttribute>
</tiles:insertDefinition>
