<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="popup popContentHalf" style="padding: 1rem; width: 600px;">
    <article class="ctsInnerArea mgL10 mgR10">
        <div class="ctsInnerAreaTop">
            <div class="ctsInnerAreaTopLft modal-header">
                <h3 class="modal-title">사유 입력</h3>
            </div>
        </div>
        <div class='content modal-body'>
            <div class="warp">
                <div class="pb-05">
                    <form id="editRsnForm" >
                        <table cellpadding="0" cellspacing="0" width="100%" class="subTable1">
                            <colgroup>
                                <col width="30%">
                                <col width="70%">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td class="tab_bg font-12">사유</td>
                                    <td>
                                        <select style="width: 100%;" id="mdfcnRsnCd" name="mdfcnRsnCd" required></select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tab_bg font-12">세부내역</td>
                                    <td>
                                        <textarea id="mdfcnDtlRsn" name="mdfcnDtlRsn" required></textarea>
                                    </td>
                                </tr>

                            </tbody>

                        </table>
                    </form>

                </div>

            </div>
        </div>
        <div class="modal-footer">
            <button type="button" id="btn_rsnSave" class="btn btn-primary" data-divid="editRsn" style="font-size: 12px !important;" >확인</button>
            <button type="button" id="btn_rsnClose" class="btn btn-primary" data-divid="editRsn" style="font-size: 12px !important;">닫기</button>
        </div>
    </article>
</div>
<script src="/viewScript/com/popup/editRsn.js?ver=20230314 "></script>
<style>
    *{
        margin: 0px;
        padding: 0px;
        box-sizing: border-box;
    }

    td {
        padding: 5px;
        border-bottom: 1px solid rgba(0,0,0,.125);
    }

    table .tab_bg{
        background: #f8f8f8;
        border-left: 1px solid rgba(0,0,0,.125);
        border-right: 1px solid rgba(0,0,0,.125);

    }

    tbody .font-12{
        text-align: center;
        font-size: 12px;
    }

    .modal-header{
        display:flex;
        align-items:flex-start;
        justify-content:space-between;
        padding:1rem;
        border-bottom:1px solid #e9ecef;
        border-top-left-radius:.3rem;
        border-top-right-radius:.3rem
    }

    .modal-title{
        margin-bottom: 0;
        line-height: 1.5;
    }

    .modal-body{
        position: relative;
        flex: 1 1 auto;
        padding: 1rem;
    }


    .warp{
        padding: 15px;
        font-family: 'Noto sans KR', sans-serif;
        font-weight: 500;
        min-width: 100%;
    }

    .pb-05 {
        padding: 3px;
        border: 1px solid rgba(0,0,0,.125);
    }

    input[type="checkbox"] {
        vertical-align: baseline !important;
        box-sizing: border-box;
        padding: 0;
    }

    .modal-footer {
        display: flex;
        -ms-flex-align: center;
        align-items: center;
        -ms-flex-pack: end;
        justify-content: flex-end;
        padding: 1rem;
        border-top: 1px solid #e9ecef;
    }

    .btn {
        display: inline-block;
        font-weight: 400;
        text-align: center;
        white-space: nowrap;
        vertical-align: middle;
        user-select: none;
        border: 1px solid transparent;
        padding: 0.375rem 0.75rem;
        font-size: 1rem;
        line-height: 1.5;
        border-radius: 0.25rem;
        transition: color .15s
    }

    .btn-primary{
        color: #fff;
        background-color: #007bff;
        border-color: #007bff;
    }

    .modal-footer>:not(:last-child) {
        margin-right: 0.25rem;
    }
</style>
