<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	You must enter your old password and the new password twice!
	<jspb:form action="/update/password" method="post">
		<input type="hidden" name="redirect" value="/view/folder.jsp">
		<input type="hidden" name="error" value="/edit/badpw.jsp">
		<table align="center">
			<tr><td>Enter your old password:</td><td><input type="password" name="oldpassword"></td></tr>
			<tr><td>Enter your new password:</td><td><input type="password" name="password1"></td></tr>
			<tr><td>Reenter your new password:</td><td><input type="password" name="password2"></td></tr>
			<tr><td colspan="2" align="center"><input type="submit" value="Set"></td></tr>
		</table>
	</jspb:form>
</jspb:includes>
