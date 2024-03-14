<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="popup popContentHalf" style="width: 510px;">
    <article class="ctsInnerArea">
        <div class="ctsInnerAreaTop modal-header">
              <h5 class="modal-title"><i class="fi-rs-exclamation"></i>${msg.C000000507}</h5> <!-- 전자 서명을 사용하시려면 아래의 내용을 동의하셔야 합니다. -->
        </div>
        <div class='content modal-body'>
            <div id='main_wrap'>
                <div id="top_wrap">
                    <span>
						${msg.C000000433} <!-- 이 전자서명은 PharmMate 시스템 내부에서 -->
						<br>
						${msg.C000000287} <!-- 수기서명과 동일한 효력을 가집니다. -->
					</span>
                </div>
				<div class="check">
					<label for="signAgree"><input id="signAgree" type="checkbox">
					&nbsp;${msg.C000000420}</label> <!-- 위 내용에 동의합니다. -->
				</div>
            </div>

            <div class="col-md-12">
                <div class="sign_popup">
                    <form id="editRsnForm" onsubmit="return false;">
						<div class="d_flex">
							<label>${msg.C000000293}</label> <!-- 수정 사유 코드 -->
							<select id="mdfcnRsnCd" name="mdfcnRsnCd"></select>
						</div>
						<div class="d_flex" >
							<label>${msg.C000000292}</label> <!-- 수정 사유 내용-->
							<textarea id="mdfcnRsn" name="mdfcnRsn"></textarea>
						</div>
					</form>
					<form id="signForm" class="row">
						<div class="popup_user">
							<p class="d_flex">
								<label>ID</label>
								<input type="text" id="lgnId" name="lgnId">
							</p>
							<p class="d_flex mt10">
								<label>Password</label>
								<input type="password" id="elecSignPswd" name="pswd">
							</p>
						</div>
					</form>
                </div>

            </div>
        </div>
        <div class="modal-footer">
        	<div class="modal-footer_inner">
            	<button type="button" id="btn_signSave" class="btn btn-primary popup-complete" data-divid="eSign1" style="font-size: 13px !important;" >${msg.C000001006}</button> <!-- 확인 -->
            	<button type="button" id="btn_signClose" class="btn btn-primary popup-close" data-divid="eSign1" style="font-size: 13px !important;">${msg.C000000948}</button> <!-- 닫기 -->
            </div>
        </div>
    </article>
</div>
<script src="/viewScript/com/popup/ElecSign.js?ver=20230324"></script>
<style>
    *{
        margin: 0px;
        padding: 0px;
        box-sizing: border-box;
    }


	i.fi-rs-exclamation{color:#fff; vertical-align:middle; margin-right:5px; }
	.d_flex{display:flex; }

	.sign_popup form > div:nth-child(2){margin-top:10px}
	.sign_popup label{width:88px; vertical-align:middle}
	.sign_popup select{width:calc(100% - 88px)}
	.sign_popup textarea{width:calc(100% - 88px)}
	.sign_popup{margin-top:10px; padding:10px 20px; border:10px solid #f8f8f8}
	.sign_popup input[type="text"]{display:block; border:1px solid #ccc; height:30px; width:calc(100% - 88px); margin-right:0; padding-left:5px; }
	.sign_popup input[type="password"]{display:block; border:1px solid #ccc; height:30px; width:calc(100% - 88px); margin-right:0; padding-left:5px; }
	.sign_popup input[type="checkbox"]{vertical-align:middle}
	.sign_popup input + input{margin-top:10px}
	.sign_popup select{height:30px; border:1px solid #ccc}
	.sign_popup textarea{border:1px solid #ccc}
	.sign_popup p + p{margin-top:10px}
	.check{text-align: right; padding-right:4px; margin-top:10px}

	.modal-body{padding:1rem 2rem}
    .modal-header{background:#f18f77; height:60px; display:flex; align-items:center; justify-content:center; border-top-left-radius: 5px; border-top-right-radius:5px}
    .modal-title{font-weight:400; font-size:18px; color:#fff; display:flex; align-items:center; line-height:1 }

    #top_wrap{background:#f5f5f5; padding:20px 30px; text-align:center}
    #top_wrap span{line-height:1.5; word-break:keep-all; font-size:16px}
    .popup_user{margin-top:20px}


    input[type=" checkbox"] {
        vertical-align: baseline !important;
        box-sizing: border-box;
        padding: 0;
    }

    .modal-footer {
        display: flex;
        -ms-flex-align: center;
        align-items: center;
        -ms-flex-pack: end;
        justify-content: center;
        border-top:1px solid #ccc;
    }
	.modal-footer_inner{padding:15px 30px}

    .btn {
        display: inline-block;
        font-weight: 400;
        text-align: center;
        white-space: nowrap;
        vertical-align: middle;
        user-select: none;
        border: 1px solid transparent;
        padding: 0.375rem 1.75rem;
        font-size: 1rem;
        line-height: 1.5;
        border-radius: 0.25rem;
        transition: color .15s
    }

    .btn-primary{
        color: #fff;
        background-color:#595757; transition:0.3s
    }

	.btn-primary:hover{background:#333}
    .modal-footer>:not(:last-child) {
        margin-right: 0.25rem;
    }
</style>
