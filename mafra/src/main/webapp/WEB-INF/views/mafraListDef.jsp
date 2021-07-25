<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>        
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>

.mafTable{
  	width:100%;
  	border-collapse: collapse;  	
  }
  
.mafTable th{
	background:red;
	border: 1px groove black;
}
.mafTable tbody{
	border: 1px groove black;
}
.mafTable td{
	border: 1px groove black;
}


</style>
<title>${title}</title>
</head>
<body>

	<div> 
		<table class="mafTable">
		    <thead>
	            <tr style="text-align: center;">
	                <th>거래일자</th>
	                <th>도매시장</th>
	                <th>도매법인</th>
	                <th>수량</th>
                	<th>거래금액</th>
	                <th>등급</th>
	                <th>중량</th>
	                <th>단위</th>
	                <th>단위가격</th>
	            </tr>
               </thead>
               <tbody class="mafbody" id="defMafList">
           			<c:forEach var="list" items="${list}" varStatus="status">       
           			             			      
						<c:if test="${status.count%2 == 0}">
							<tr style="background:white;">
						</c:if>
           			    <c:if test="${status.count%2 != 0}">
							<tr style="background:yellow;">
						</c:if>         			      		           			      
           				
	           				<td style="text-align:center;">${list.B_DATE}</td>
	           				<td style="text-align:center;">${list.PBLMNG_WHSAL_MRKT_NM}</td>
	           				<td style="text-align:center;">${list.CPR_NM}</td>
	           				<td style="text-align:right;">${list.DELNG_QY}</td>
	           				<td style="text-align:right;"><fmt:formatNumber value="${list.AVRG_AMT}" pattern="#,###.##"/></td>
	           				<td style="text-align:center;">${list.GRAD}</td>
	           				<td style="text-align:right;">${list.DELNGBUNDLE_QY}</td>
	           				<td style="text-align:center;">${list.STNDRD}</td>
	           					           					           				
	           				<c:if test="${list.DELNGBUNDLE_QY == '0'}">
	           					<td style="text-align:right;"><fmt:formatNumber value="${list.AVRG_AMT/(10)}" pattern="#,###.##"/></td>
	           				</c:if>           				
	           				<c:if test="${list.DELNGBUNDLE_QY != '0'}">
	           					<td style="text-align:right;"><fmt:formatNumber value="${list.AVRG_AMT/(list.DELNGBUNDLE_QY * 10)}" pattern="#,###.##"/></td>
	           				</c:if>
	           					           				
						</tr>			
					</c:forEach>
           			
           		</tbody>
	    </table>
	</div>

</body>

</html>