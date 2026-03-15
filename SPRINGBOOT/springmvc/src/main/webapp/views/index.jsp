<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Simple JSP Page</title>
</head>
<body>
<h1>Hello, World!</h1>
<form action="addalien" method="post">
<%--    <input type="number" name="num1">--%>
<%--    <input type="number" name="num2">--%>

    <input type="number" name="aid">
    <input type="text" name="aname">
    <button type="submit">Submit</button>
</form>

<form action="getalien">
    <input type="number" name="id">
    <button type="submit">Get</button>
</form>
</body>
</html>

