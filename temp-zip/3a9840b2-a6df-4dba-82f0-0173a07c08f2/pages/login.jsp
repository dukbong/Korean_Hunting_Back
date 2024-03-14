<%@page import="lims.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:insertDefinition name="login-definition">
    <tiles:putAttribute name="title">Login</tiles:putAttribute>
    <tiles:putAttribute name="body">

    <div class="login_box">
        <h1>
            <img src="${contextPath}/assets/logo/logo.png">
        </h1>
        <form  class="iLogin" id="frmLogin" name="frmLogin" action="./loginCheck.lims" method="POST">
            <select name="langCd" id="langCdComboBox" class="">
                <c:if test="${null != languageList && !empty languageList}">
                    <c:forEach var="item" items="${languageList}">
                        <option value="${item.cmmnCode}">${item.cmmnCodeNm}</option>
                    </c:forEach>
                </c:if>
            </select>
            <div class="login_inner">
                <div class="login_txt">
                    <c:if test="${empty securityexceptionmsg}">
                        <input class="iLoginUser" type="text" placeholder="User Id" id="lgnId" name="lgnId" value="" autocomplete="off">
                    </c:if>
                    <c:if test="${not empty securityexceptionmsg}">
                        <input class="iLoginUser" type="text" placeholder="User Id" id="lgnId" name="lgnId" value="${lgnId}" autocomplete="off">
                    </c:if>
                    <c:if test="${empty securityexceptionmsg}">
                        <input class="iLoginPass" type="password" placeholder="Password" id="pswd" name="pswd" value="" autocomplete="off">
                    </c:if>
                    <c:if test="${not empty securityexceptionmsg}">
                        <input class="iLoginPass" type="password" placeholder="Password" id="pswd" name="pswd" value="${pswd}" autocomplete="off">
                    </c:if>
                    <c:if test="${not empty securityexceptionmsg}">
                        <input type="hidden" name="ip" value="${pageContext.request.localAddr}"/>
                    </c:if>
                </div>
                <div class="loginBtn">
                    <button type="button" class="loginBtn01" id="btnLogin">LOGIN</button>
                </div>
                <div class="loginBtn">
                    <button type="button" id="btnUserJoin" class="loginBtn02" onsubmit="return false;">${msg.C100001252}</button> <%-- 사용자 신규등록 --%>
                    <input type="hidden" name="loginRedirect" value="${loginRedirect}" />
                    <input type="hidden" id="targetUrlParameter" name="targetUrlParameter" value='${sessionScope.targetUrl}'/>
                </div>

            </div>
        </form>

        <!-- 다국어 재설정 리로드 폼 -->
        <form id="reloadPage" action="${contextPath}/login.lims" method="post" onsubmit="return false;">
            <input type="hidden" id="reloadLangCd" name="langCd">
            <%--<input type="hidden" id="url" name="url">--%>
        </form>
    </div>


    </tiles:putAttribute>

   <tiles:putAttribute name="script">
<script>
    $(function() {
        //신규 사용자 등록
        let langCd = "${locale}";
        debugger
        langCd = (langCd == null || langCd == '' || langCd == 'undefined')?'SY06000001':langCd;
        $('#langCdComboBox').val(langCd);

        $("#btnLogin").click(function() {
            if($("#lgnId").val() == "") {
                alert('${msg.C100000300}'); //로그인 ID를 입력해 주세요.
                $("#lgnId").focus();
            } else if($("#pswd").val() == "") {
                alert('${msg.C100000299}'); //로그인 비밀번호를 입력해 주세요.
                $("#pswd").focus();
            } else {
                var langCd = $('#langCdComboBox').val();
                //Cookie.set('langCd', langCd, 365);

                var dpLoginChk = '${dpLoginChk}';

                if(dpLoginChk == "Y"){
                    var param = $("#frmLogin").serializeObject();

                    customAjax({
                        'url' : "${contextPath}/isu/duplicationLoginCheck.lims",
                        'data' : param,
                        'showLoading' : true,
                        'successFunc' : function(data){
                            console.log("successFunc>>>>>",data);
                            if(data){
                                $("#frmLogin").submit();
                            }else{
                                confirm("${msg.C000000750}", () => { /*다른 기기에서 로그인중입니다. 로그인 하시겠습니까?*/
                                    $("#frmLogin").submit();
                                });
                            }
                        },
                        'errorFunc' : function(data){
                            console.log("ErrData>>>>>",data);
                        }
                    });
                }else{
                    $("#frmLogin").submit();
                }
            }
        });

        $("#lgnId").keydown(function (key){
            if(key.keyCode == 13)
                $("#pswd").focus();
        });

        $("#pswd").keydown(function (key){
            if(key.keyCode == 13)
                $("#btnLogin").trigger("click");
        });

        <c:if test="${not empty securityexceptionmsg}">
            alert("${securityexceptionmsg}");
        </c:if>

        // 언어 변경 이벤트.
        var langCdComboBox = document.getElementById("langCdComboBox");
        var reloadPage = document.getElementById("reloadPage");
        var reloadLangCd = document.getElementById("reloadLangCd");
        langCdComboBox.onchange = function(e) {
            reloadLangCd.value = langCdComboBox.value;
            reloadPage.submit();
        }

        var failMessage = function(){
                return alert("${msg.C000000775}"), false; /*붙여넣기 기능은 사용하실 수 없습니다.*/
            },
            preventEvent = {
                "keydown" : function(e) {
                    var keycode = function(e){
                            return ('which' in e ? e.which : e.keyCode)
                        }(e),
                        ctrl_cv = (e.ctrlKey && (keycode == 118 || keycode == 86)),
                        shift_insert = (e.shiftKey && keycode == 45);
                    if (ctrl_cv || shift_insert){
                        return failMessage();
                    }
                }
                ,"mousedown" : function(e) {
                    var rightClick = (e.button == 2);
                    if(rightClick){
                        return failMessage();
                    }
                }
                ,"contextmenu" : function(e) {
                    return failMessage();
                }
            };
        $("#pswd").bind(preventEvent);

    });

    function changeLogin(id, pw) {
        $("#lgnId").val(id);
        $("#pswd").val(pw);
        $("#btnLogin").trigger("click");
    }

</script>

	</tiles:putAttribute>
</tiles:insertDefinition>
