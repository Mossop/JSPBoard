<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	You must enter the same password twice!
	<form action="<%= context %>/update/password" method="post">
		<input type="hidden" name="redirect" value="/view/folder.jsp">
		<input type="hidden" name="error" value="/edit/badpw.jsp">
		<table align="center">
			<tr><td>Enter a new password:</td><td><input type="password" name="password1"></td></tr>
			<tr><td>Reenter the password:</td><td><input type="password" name="password2"></td></tr>
			<tr><td colspan="2" align="center"><input type="submit" value="Set"></td></tr>
		</table>
	</form>
</jspb:includes>
