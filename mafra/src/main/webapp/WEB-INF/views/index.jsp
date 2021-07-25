<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>        
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 <script src="https://code.jquery.com/jquery-latest.js"></script> 
<title>농림축산 경매 시세</title>
</head>
<style>
*        { margin:0; padding: 0; }
body{
         font-family: "맑은 고딕";
         font-size: 0.75em;
         color: #333;
}
#loginForm{
         width:200px;
         margin:100px auto;
         border: 1px solid gray;
         border-radius: 5px;
         padding: 15px;
}

/* inline이였던 요소들을 block으로 바꿈 */
#loginForm input, #loginForm label{
         display:block;
}

#loginForm label{
         margin-top: 10px;
}

#loginForm input{
         margin-top: 5px;
}

/* 애트리뷰트 선택자 */
#loginForm input[type='button']{
         margin: 10px auto;
}


#signForm{
         width:200px;
         margin:20px auto;
         border: 1px solid gray;
         border-radius: 5px;
         padding: 15px;
}

/* inline이였던 요소들을 block으로 바꿈 */
#signForm input, #signForm label{
         display:block;
}

#signForm label{
         margin-top: 10px;
}

#signForm input{
         margin-top: 5px;
}

/* 애트리뷰트 선택자 */
#signForm input[type='button']{
         margin: 10px auto;
}

.modal_wrap{
        display: none;
        width: 250px;
        height: 250px;
        position: absolute;
        top:50%;
        left: 59%;
        margin: -250px 0 0 -250px;
        background:skyblue;
        z-index: 2;
    }
    .black_bg{
        display: none;
        position: absolute;
        content: "";
        width: 100%;
        height: 100%;
        background-color:rgba(0, 0,0, 0.5);
        top:0;
        left: 0;
        z-index: 1;
    }
    .modal_close{
        width: 26px;
        height: 26px;
        position: absolute;
        top: -30px;
        right: 0;
    }
    .modal_close> a{
        display: block;
        width: 100%;
        height: 100%;
        background:url(https://img.icons8.com/metro/26/000000/close-window.png);
        text-indent: -9999px;
    }
    
    
    /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }
    
        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 30%; /* Could be more or less, depending on screen size */                          
        }
    
</style>

<script>

    window.onload = function() { 
	    function onClick() {
	        document.querySelector('.modal_wrap').style.display ='block';
	        document.querySelector('.black_bg').style.display ='block';
	    }   
	    function offClick() {
	        document.querySelector('.modal_wrap').style.display ='none';
	        document.querySelector('.black_bg').style.display ='none';
	    }
	 
	    document.getElementById('modal_btn').addEventListener('click', onClick);
	    document.querySelector('.modal_close').addEventListener('click', offClick);	 
	};

	
	function signIn() {		
	
		if (!/^[a-z0-9]{4,20}$/.test($("#signId").val())) {
		  alert("아이디는 영 소문자, 숫자 4~20자리로 입력해주세요.");
		  return;
		}

		if(!chkPW()) return;

		if(!/^[가-힣]{2,10}$/.test($("#signName").val())){
		   alert("이름은 한글 2~10자리로 입력해주세요.");
		   return;
		}				
				
		var signId   = $("#signId").val();
		var signPwd  = $("#signPwd").val();
		var signName = $("#signName").val();
		
		$.ajax({
			url:'/mafra/signIn',
			type:'POST',
			dataType:'json',
			data:{
				signId:signId,
				signPwd:signPwd,
				signName:signName
			},
			success:function(data) { 
			
				var results = data;				
				console.log(results.result);
				
				if(results.result == "success"){
					  document.querySelector('.modal_wrap').style.display ='none';
	                  document.querySelector('.black_bg').style.display ='none';
	                  alert("회원가입 되었습니다.");
				
				}else if(results.result == "duplicated"){					
					  document.querySelector('.modal_wrap').style.display ='none';
	                  document.querySelector('.black_bg').style.display ='none';
	                  alert("아이디가 중복되었습니다.");
				
				}else if(results.result == "failed"){				
					  document.querySelector('.modal_wrap').style.display ='none';
	                  document.querySelector('.black_bg').style.display ='none';
					  alert("회원가입에 실패했습니다.");		
				}								
			}
		});	
	}

	function login() {	
		
		var loginId   = $("#loginId").val();
		var loginPwd  = $("#loginPwd").val();
		
		$.ajax({
			url:'/mafra/login',
			type:'POST',
			dataType:'json',
			data:{
				loginId:loginId,
				loginPwd:loginPwd
			},
			success:function(data) { 
			
				var results = data;
				
				if(data.result == "success"){					
					$('#myModal').show();	
				}else if(data.result == "dateNotMatched"){
					alert("사용날짜가 초과하였습니다.");
				}else if(data.result == "ipNotMatched"){
					alert("회원가입 IP와 다릅니다.");
				}else if(data.result == "pwdNotMatched"){
					alert("비밀번호가 다릅니다.");
				}else if(data.result == "idNotMatched"){
					alert("아이디가 없습니다.");
				}else{
					alert("로그인에 실패했습니다.");
				}																								
			}
		});	
	}
	
	
	function chkPW(){

		 var pw = $("#signPwd").val();
		 var num = pw.search(/[0-9]/g);
		 var eng = pw.search(/[a-z]/ig);
		 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		
		 if(pw.length < 8 || pw.length > 20){		
		     alert("비밀번호는 8자리 ~ 20자리 이내로 입력해주세요.");
		     return false;
		 }else if(pw.search(/\s/) != -1){
		     alert("비밀번호는 공백 없이 입력해주세요.");
		     return false;
		 }else if(num < 0 || eng < 0 || spe < 0 ){
		     alert("비밀번호는 영문,숫자, 특수문자를 혼합하여 입력해주세요.");
		     return false;
		 }else {
		     return true;
		 }
		
		}

</script>

<body>

     <form id="loginForm">
         <label class="legend">아이디</label>
         <input name="userid" type="text" id="loginId">
         <label class="legend">패스워드</label>
         <input name="passwprd" type="password" id="loginPwd">
         <input type="button" onclick="login();" value="로그인">
         <input type="button" id="modal_btn" value="회원가입">
     </form>
     <div class="black_bg"></div>
     <div class="modal_wrap">
    	<div class="modal_close"><a href="#">close</a></div>
    	<div>
    		<form id="signForm">		
	        	<label class="legend">아이디</label>
		        <input name="userid" type="text" id="signId">
		        <label class="legend">패스워드</label>
		        <input name="passwprd" type="password" id="signPwd">
	        	<label class="legend">사업장 명</label>
		        <input name="userName" type="text" id="signName">
		        <input type="button" onclick="signIn();" value="가입하기">
	        </form>
    	</div>
	 </div>
	 
	  <!-- The Modal -->
      <div id="myModal" class="modal">
	      <!-- Modal content -->
	      <div class="modal-content">
              <p style="text-align: center;"><span style="font-size: 14pt;"><b><span style="font-size: 24pt;"></span></b></span></p>
              <p style="text-align: center; line-height: 1.5;"><br />로그인에 성공하였습니다.</p>
              <p><br /></p>
	          <div style="cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;">		          
		          <button id="popupClose" class="pop_bt" style="font-size: 13pt;">
		           닫기
		          </button>
	          </div>
      	 </div>
    </div>
    <!--End Modal-->
<script>

	$("#popupClose").click(function(e){	  
		location.href="/mafra/loginMain";
	})

    /*
	$("#popupClose").click(function(e){	  
	  $('#myModal').hide();	   
      window.open("/mafra/mafraList","농림축산 경매 시세","location=no,width=870px,height=660px,scrollbars=no,resizable=yes");	
	
      window.open('about:blank', '_self').close();
	})
	*/
</script>
</body>
</html>