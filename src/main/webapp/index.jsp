<%@page import="org.apache.commons.lang3.StringUtils"%>
<html>
<body>
<h2>Hello World!</h2>

<%

out.println(StringUtils.abbreviate("Hello", 4));
%>

</body>
</html>
