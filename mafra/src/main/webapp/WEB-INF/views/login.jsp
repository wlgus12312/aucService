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
<script>
function  selectMaf(){
	//상시조회팝업
	window.open("/mafra/mafraList","농림축산 경매 시세","location=no,width=870px,height=660px,scrollbars=no,resizable=yes");
	
}

function  selectDeMaf(){
	//상세조회팝업
	window.open("/mafra/defList","농림축산 상품 조회","location=no,width=880px,height=660px,scrollbars=no,resizable=no");
	
	
}

function  selectConfig(){
	//상세조회설정팝업
	window.open("/mafra/config","농림축산 조회 설정","location=no,width=870px,height=660px,scrollbars=no,resizable=no");		
	
}

</script>
<body>

<h1>${user.name}</h1>

<div>
	<input type="button" onclick="selectMaf();" value="상시조회">
	<input type="button" onclick="selectDeMaf();" value="상세조회">
	<input type="button" onclick="selectConfig();" value="상시조회 설정">
</div>

</body>
</html>