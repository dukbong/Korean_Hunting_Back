<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="popup popContentHalf wd45p">
    <article class="ctsInnerArea">
        <div class="ctsInnerAreaTop">
            <div class="ctsInnerAreaTopLft modal-header">
                <h5 class="modal-title">파일 업로드</h5>
            </div>
        </div>
        <div class="content modal-body">
            <div id="main_wrap">
                <div class="subCon1">
                    <form id="popUploadFileForm" onSubmit="return false;">
                        <table class="subTable1">
                            <colgroup>
                                <col style="width:20%"/>
                                <col style="width:80%"/>
                            </colgroup>
                            <tr>
                                <th class="tab_bg font-12">첨부 파일</th> <!-- 첨부 파일 -->
                                <td>
                                    <!-- 파일첨부영역 -->
                                    <div id="dropZoneArea"></div>
                                    <input type="text" id="atchmnflSeqno" name="atchmnflSeqno" class="wd33p" style="min-width: 10em; display: none;" maxlength="10">
                                    <input type="hidden" id="btnSave_file">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" id="btnUploadFileSave" class="btn btn-primary popup-close" style="font-size: 12px !important;">저장</button>
            <button type="button" id="btnUploadFileClose" class="btn btn-primary popup-close" style="font-size: 12px !important;">닫기</button>
        </div>

    </article>
</div>
<script src="/viewScript/com/popup/UploadFileP.js?ver=20230411"/>
