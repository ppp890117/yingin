<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
</head>
<div> <h1><%%></h1></div>
<div style="text-align:center;"><img src="https://i.imgur.com/FfzXArX.png"width="300" height="200"></div>
<body>
<%
String[][] orderList = (String[][])  request.getAttribute("query");
String keywordlist = request.getParameter("keyword");
for(int i =0 ; i < orderList.length;i++){%>
	<a href='<%= orderList[i][1] %>'><%= orderList[i][0] %></a><br><h style="font-size:10px ;"><%= orderList[i][1] %></h><br><br>
<%
}
%>
<div> <h1><font color="#0000a0" size = 4><%=keywordlist%></font><font size =4 >的相關搜尋</font></h1></div>
</body>
</html>