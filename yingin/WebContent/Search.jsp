<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>
</head>
<div style="text-align:center;"><img src="https://i.imgur.com/FfzXArX.png"width="300" height="200"></div>
<body>
<div style="text-align:center;"><h1><font face="cursive">Searching YinGin</font></h1></div>
	
<form action='${requestUri}' method='get'>
<div style="text-align:center;"><input type='text' name='keyword' placeholder = 'keyword' size = 50/></div>
<div style="text-align:center;"><input type='submit' value='submit' /></div>
</form>
</body>
</html>