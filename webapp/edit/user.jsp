<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectLogin var="login" id='<%= request.getParameter("id") %>'>
	<jspb:includes>
		<h1>Editing <%= login.getField("id") %></h1>
		<jspb:form action="/update/login" method="post">
			<input type="hidden" name="redirect" value="/view/users.jsp">
			<input type="hidden" name="id" value='<%= login.getField("id") %>'>
			Set password: <input type="text" name="password">
			<input type="submit" value="Set">
		</jspb:form>
		<hr>
		<jspb:form action="/delete/login" method="post">
			<input type="hidden" name="redirect" value="/view/users.jsp">
			<input type="hidden" name="id" value='<%= login.getField("id") %>'>
			<input type="submit" value="Delete Login">
		</jspb:form>
	</jspb:includes>
</jspb:SelectLogin>
