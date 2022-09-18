
 /********************************************************
 * @desc comment.js 댓글 기능 자바스크립트 작성
 * @desc 댓글의 댓글만 구현됨, 무한댓글 아님
 * @todo 저장 / 삭제만 구현, 수정 기능없음
 */

//로그인 사용자 아이디 전역변수 사용
const LOGIN_ID = $("#loginId").val() || "";

 /********************************************************
 * @desc 페이지 로드
 * @desc $('document').ready(function(){}) 을 쓰는 이유?  'html문서의 로딩이 다 끝나면' 을 의미
 */
$(document).ready(function(){
	$("#commentWriter").val(LOGIN_ID);
   	//html문서의 로딩이 다 끝나고 댓글 목록 ajax 호출
   	fnCommentList();   
})

 /********************************************************
 * @desc 댓글목록 저장
 * @param parentNo 부모 댓글번호
 * @param commentDepth 댓글/대댓글유무 0=댓글 , 1=대댓글
 */
function fnCommentSave(parentNo, commentDepth){

	//ajax로 전송 할 값 셋팅
   	let param= {};

	//댓글 save 파라미터 셋팅
   	if(commentDepth == 0){

 		if($.trim($("#commentContent").val()) == ""){
        	Swal.fire({
   				icon: "warning", // Alert 타입 
         		title: "댓글 내용을 입력해주세요.", // Alert 제목 
    		});               
        	$("#commentContent").focus();         
 			return false;
  		}
   
		param = {
	 		memberId : LOGIN_ID      //작성자 (로그인 세션의 아이디)
	     	, boardNo : $("#boardnum").val()         //게시판 번호
	     	, commentDepth : commentDepth   //댓글or대댓글 유무 0=댓글 1=대댓글
	     	, commentContent : $("#commentContent").val()         //댓글 내용
	  	}

   	//대댓글 save 파라미터 셋팅   
   }else{

		if($.trim($("#commentContent2").val()) == ""){
   			Swal.fire({
           		icon: "warning", // Alert 타입 
             	title: "대댓글 내용을 입력해주세요.", // Alert 제목 
        	});               
            $("#commentContent").focus();         
 			return false;
  		}
   
  		param = {
     		memberId : LOGIN_ID   //대댓글 작성자 (로그인 세션의 아이디)
         	, boardNo : $("#boardnum").val()         //게시판 번호
         	, parentNo : parentNo               //부모 댓글 번호
         	, commentDepth : commentDepth   //댓글or대댓글 유무 0=댓글 1=대댓글
         	, commentContent : $("#commentContent2").val()         //대댓글 내용
      	}      
	}

   
      
   //ajax URL 셋팅
   let callAjaxUrl = "/comment/commentSave";

   Swal.fire({ 
      title: '댓글을 등록 하시겠습니까?',
         //text: '다시 되돌릴 수 없습니다. 신중하세요.',
         icon: 'warning',
         showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
         confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '확인', // confirm 버튼 텍스트 지정
         cancelButtonText: '취소', // cancel 버튼 텍스트 지정
   }).then(function(result){
      if(result.isConfirmed == true){      
         //공통 ajax 함수 호출
         fnCallAjax(param, callAjaxUrl, fnCommentSaveSuccess);   
      }
   });      
      
}

 /********************************************************
 * @desc 공통 ajax 호출 함수
 * @param param ajax로 보내려는 파라미터값 
 * @param url ajax 호출 URL
 * @param callbackFunction ajax 호출 성공후 수행할 함수
 */
function fnCallAjax(param,url,callbackFunction){
   $.post(
      url
         , param
        , callbackFunction
   )      
}

 /********************************************************
 * @desc 댓글 저장 호출 후 성공 함수
 * @param data 컨트롤러에서 받은 ajax 응답값
 */
function fnCommentSaveSuccess(data){
   console.log("save success data");
   console.log(data);

   //댓글 저장후 댓글 목록 재 호출
   fnCommentList()
}

 /********************************************************
 * @desc 댓글목록 호출
 */
function fnCommentList(){
   fnCallAjax( { boardNo : $("#boardnum").val() } , "/comment/commentList" , fnCommentListSuccess );   
}

 /********************************************************
 * @desc 댓글 목록 호출 후 성공 함수
 * @param data 컨트롤러에서 받은 ajax 응답값
 */
function fnCommentListSuccess(data){

   console.log("data");
   console.log(data);   
   
   //댓글 목록 영역 초기화
   $("#commentList").empty();
   
   let appendHtml = "";
   
   if(data.length > 0){
      
      data.forEach(function(value, index){

         //삼항연선자를 이용해 대댓글 일때 들여쓰기 css 효과를 준다. 0=댓글 1=대댓글
         appendHtml +=   '<div class="row" style="padding: 5px 5px 5px '+(value.commentDepth == "0" ? "5px;" : "20px;")+'" data-parent="'+value.parentNo+'">';            
         appendHtml +=      '<div class="form-group col-sm-8">';
         appendHtml +=          '<span>'+value.commentContent+'</span>';
         appendHtml +=       '</div>';
         appendHtml +=       '<div class="form-group col-sm-2">';
         appendHtml +=          '<span>'+value.memberId+'</span>';
         appendHtml +=         '</div>';
         appendHtml +=         '<div class="form-group col-sm-2">';
         
         //로그인 했을때
         if(LOGIN_ID != ""){
            //로그인한 사용자와 댓글을 작성한 사용자가 같으면 삭제버튼 활성화
            if(LOGIN_ID == value.memberId){
               appendHtml +=         '<button type="button" class="btn btn-outline-danger btn-sm btn-block" onclick="javascript:fnCommentDelete('+value.commentNo+')">';
               appendHtml +=             '<i class="bi bi-trash"></i> 삭제';
               appendHtml +=            '</button>';
               
               /*
               appendHtml +=         '<button type="button" class="btn btn-primary btn-sm btn-block">';
               appendHtml +=             '<i class="bi bi-trash"></i> 수정';
               appendHtml +=            '</button>';
               */
            }else {
               //댓글 일때만 대댓글 아이콘표시
               if(value.commentDepth == "0" ){
                  appendHtml +=         '<button type="button" class="btn btn-outline-success btn-sm btn-block" onclick="javascript:fnReply2Form(this,'+value.parentNo+')">';
                  appendHtml +=             '<i class="bi bi-reply"></i> 댓글';
                  appendHtml +=            '</button>';
               }
            }
         }
         appendHtml +=      '</div>';
         appendHtml +=   '</div>';   
      });
      
   }else{
      /* 댓글이 없을 때 */
      appendHtml +=   '<div class="row">';
      appendHtml +=      '<div class="form-group col-sm-8">';
      appendHtml +=         '<span>등록된 댓글이 없습니다.</span>';
      appendHtml +=      '</div>';
      appendHtml +=   '</div>';
   }
   
   //commentList 안에 html로 생성한다.
   $("#commentList").html(appendHtml);
   //댓글 개수 셋팅
   $("#commnetCount").text(data.length+"개의 댓글");
}

 /********************************************************
 * @desc 대댓글 폼 생성 함수
 * @param el 현재 클릭한 엘리먼트 객체
 * @param parent 부모 댓글 번호
 */
function fnReply2Form(el,parent){

   //다른 댓글 버튼 누를시 댓글폼 지우기
   $(".reply2Form").remove();

   //this로 얻은 element를 제이쿼리로 사용하기위해 $()로 감싼다.
   let $el = $(el);

   //부모 댓글번호
   let parentNo = parent;

   //대댓글 폼 생성
   let generatorHtml = ""; 
   generatorHtml +=      '<div class="card-body reply2Form" style="padding: 15px 5px 5px 30px;">';
   generatorHtml +=          '<div class="row">';
   generatorHtml +=             '<div class="form-group col-sm-8">';
   generatorHtml +=                '<input class="form-control input-sm" type="text"    id="commentContent2"   name="commentContent2"   placeholder="대댓글 입력...">';
   generatorHtml +=             '</div>';
   generatorHtml +=             '<div class="form-group col-sm-2">';
   generatorHtml +=                '<input class="form-control input-sm" type="text" id="commentWriter2"   name="commentWriter2" readonly placeholder="작성자">';
   generatorHtml +=             '</div>';
   generatorHtml +=             '<div class="form-group col-sm-2">';
   generatorHtml +=                '<button type="button" class="btn btn-outline-primary btn-sm btn-block"  onclick="javascript:fnCommentSave(\''+parentNo+'\', 1);">';
   generatorHtml +=                   '<i class="bi bi-save"></i> 저장';
   generatorHtml +=                '</button>';
   generatorHtml +=             '</div>';
   generatorHtml +=          '</div>';
   generatorHtml +=       '</div>';

   $el.parents(".row").append(generatorHtml);
   
   //대댓글 작성자폼에 로그인 아이디셋팅
   $("#commentWriter2").val(LOGIN_ID);
   
}

 /********************************************************
 * @desc 댓글삭제 함수
 * @param commentNo 삭제 할 댓글 번호
 */
function fnCommentDelete(commentNo){
   
   //대댓글이 달렸는지 개수 체크
   let parentLength = $("[data-parent='"+commentNo+"']").length;
   
   //대댓글이 달려 있으면 삭제불가능
   if(parentLength > 1){
         Swal.fire({
            icon: "warning", // Alert 타입 
              title: "대댓글이 있으므로<br/>삭제가 불가능합니다.", // Alert 제목 
         });               
         $("#commentContent").focus();         
      return false;
   }      
   
   Swal.fire({ 
      title: '댓글을 삭제 하시겠습니까?',
         text: '다시 되돌릴 수 없습니다. 신중하세요.',
         icon: 'warning',
         showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
         confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '확인', // confirm 버튼 텍스트 지정
         cancelButtonText: '취소', // cancel 버튼 텍스트 지정
   }).then(function(result){
      if(result.isConfirmed == true){      
         //공통 ajax 함수 호출
         fnCallAjax( {commentNo: commentNo} ,"/comment/commentDelete",fnCommentList);
      }
   });   
   
}