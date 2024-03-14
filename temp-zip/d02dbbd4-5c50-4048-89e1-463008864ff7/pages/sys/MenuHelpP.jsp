<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="popup-Manual">
    <tiles:putAttribute name="body">
     	<!-- CKEditor -->
		<script src="/ckeditor/ckeditor5-build-classic/build/ckeditor.js"></script>
    	<div class="subContent wd95p" style="background-color: white;">
			<div id="grid" class="wd30p fL mgR25">
				<div class="subCon1">
					<h3>${msg.C000001060}</h3> <!-- 메뉴 목록 -->
				</div>
				<div class="subCon2 mgT0">
					<div id="menuGrid" style="height: 440px"></div> <!-- 메뉴 그리드 -->
				</div>
			</div>

			<div class="subCon1" id="noticeWriteArticle">
				<h2>메뉴얼 등록</h2> <!-- 메뉴얼 등록 -->
				<div class="btnWrap">
					<button id="btn_save" class="save" style="border: 0 none; cursor: pointer">${msg.C000000984}</button> <!-- 저장 -->
				</div>
				<form id="menuPWriteForm" name="menuPWriteForm" onsubmit="return false">
					<div id="ckEditor5" class="wd100p mgT15" style="min-width:10em;"></div> <!-- 담당자 보여줄 에디터 -->
					<div id="userManual" class="wd100p mgT15" style="text-align:left;"></div> <!-- 일반사용자 보여줄 html -->
					<input type="hidden" name="mnlCn"/>
					<input type="hidden" name="menuSn"/>
					<input type="hidden" name="menuUrl"/>
				</form>
			</div>
		</div>
 	</tiles:putAttribute>
	<tiles:putAttribute name="script">
		<style>
			.ck-editor__editable_inline{min-height:400px;}
			#sub_wrap{margin-left:230px;transition:all 0.3s;}
			#userManual img{max-width: 900px; height : auto;}
			#userManual figure{margin: 5px;}
		</style>
 		<script>
			function div2Resize() {
				const div2 = document.querySelectorAll("img");
				const grid = document.querySelectorAll("#menuGrid");
				const form = document.querySelectorAll("#noticeWriteArticle");
				const ckEditor = document.querySelectorAll("#ckEditor5");
				const editorContent = document.querySelectorAll(".ck-content");

				for (let i = 0; i < div2.length; i++) {
					div2[i].style.maxWidth = (window.innerWidth - 100).toString().concat("px");
				}

				let windowHeight = $(window).height();
				grid[0].style.height = (windowHeight - 97).toString().concat("px");
				form[0].style.height = (windowHeight - 140).toString().concat("px");
				ckEditor[0].style.height = (windowHeight - 140).toString().concat("px");
				editorContent[0].style.height = (windowHeight - 140).toString().concat("px");
			}
			window.addEventListener("resize", div2Resize);

			let menuGrid = "menuGrid";
			const editBeforeRow = "";
			const url = new URL(window.location.href);
			const urlParams = url.searchParams;
			const menuPWriteForm = "menuPWriteForm";
			const menuUrl = atob(urlParams.get("menuUrl"));
			let menuSn = atob(urlParams.get("menuSn"));
			$("input[name='menuSn']").val(menuSn);
			$("input[name='menuUrl']").val(menuUrl);

			//CK5에디터
			let myEditor;
			ClassicEditor.create(document.querySelector("#ckEditor5"), {
				ckfinder: { uploadUrl: "/com/ckeditor5ImageUpload.lims" }
			}).then(function(data) {
				myEditor = data;
			})['catch'](function(error) {
				console.error(error);
			});

			$(() => {
				div2Resize();
				grntCtrl();
				setButtonEvent();
				buildMenuGrid();
				auiGridMenuEvent();
				searchMenu();
				selectAuthMenu();
			});

			//권한별 컨트롤
			function grntCtrl() {
				if (auth === "GRNT000001") { //시스템관리자
					$("#btn_save").show();
					$(".ck-editor").css("display", "block");
					$("#userManual").hide();
				} else {
					$("#btn_save").hide();
					$(".ck-editor").css("display", "none");
					$("#userManual").show();
				}
			}

			function setButtonEvent() {
				$("#btn_new").click(() => {
					pageReset([menuPWriteForm]);
					myEditor.setData("");
				});

				$("#btn_save").click(() => {
					saveManual();
				});
			}

			function buildMenuGrid() {
				const col = [];
				auigridCol(col);

				col
				.addColumnCustom("menuNm", lang.C000000175, "*", true) //메뉴명
				.addColumnCustom("menuSn", "메뉴", "*", false)
				.addColumnCustom("upMenuSn", "상위 메뉴", "*", false)
				.addColumnCustom("sysSeNm", "시스템 구분", "*", false)
				.addColumnCustom("authrtSn", "권한 그룹 코드", "*", false);

				menuGrid = createAUIGrid(col, menuGrid, {
					editable: false,
					displayTreeOpen: true,
					flat2tree: true,
					treeIdField: "menuSn",
					treeIdRefField: "upMenuSn"
				});
			}

			function auiGridMenuEvent() {
				AUIGrid.bind(menuGrid, "cellDoubleClick", (event) => {
					$("input[name='menuSn']").val(event.item.menuSn);
					$("input[name='menuUrl']").val(event.item.menuUrl);
					$("#noticeWriteArticle h2").text(event.item.menuNm);
					searchEditorData();
				});
			}

			async function selectAuthMenu() {
				const authMenu = await customAjax({
					url: "/sys/getAuthrtMenuByUser.lims",
					data: null,
				});

				for (let i = 0; i < authMenu.length; i++) {
					for (let j = i + 1; j < authMenu.length - 1; j++) {
						if (authMenu[i].menuSn === authMenu[j].menuSn) {
							authMenu.splice(j, 1);
							j--;
						}
					}
				}
				AUIGrid.setGridData(menuGrid, authMenu);
			}

			async function searchMenu() {
				const formParam = $("#menuPWriteForm").serializeObject();
				const result = await customAjax({ url: "/sys/getMenuList.lims", data: formParam });
				if (result.length > 0) {
					if (result[0].mnlCn) {
						myEditor.setData(result[0].mnlCn);
						$("#userManual").html(result[0].mnlCn);
					}

					$("#noticeWriteArticle h2").text(result[0].menuNm);
					menuSn = result[0].menuSn;
					searchEditorData();
				}
			}

			async function saveManual() {
				visibleLoadingBar("");

				const data = {
					menuSn: $("input[name='menuSn']").val(),
					mnlCnStr: myEditor.getData()
				};

				const result = await customAjax({url: "/sys/insertMenual.lims", data: data});
				if (result > 0) {
					alert(lang.C000000729); /*완료되었습니다.*/
				}

				visibleLoadingBar("none");
			}

			//메뉴얼내용 조회
			async function searchEditorData() {
				const result = await ajaxJsonForm("/sys/getEditorData.lims", menuPWriteForm);
				if (result.mnlCnStr) {
					if (auth === "GRNT000001") { //관리자
						myEditor.setData(result.mnlCnStr);
					} else { //일반사용자
						//에디터를 사용하지않고 html로 표데이터 그려줄 경우 css를 추가하여 이미지 로드
						let tableStyle = "<style>table,td,th{border:1px solid black;}table{border-collapse:collapse;}</style>";
						tableStyle += result.mnlCnStr;
						$("#userManual").html(tableStyle);
					}
				} else {
					myEditor.setData("");
				}
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
