<%@ page import="com.blueprintit.jspboard.SessionHandler" %>
<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	<table border="0">
    <tr>
      <td valign="top" colspan="4" width="578">
        <h1>WSWYM Bulletin Board Users</h1>
      </td>
    </tr>
    <tr>
      <td>
        <b>User</b>
      </td>
      <td>
        <b>Last access</b>
      </td>
      <td>
        <b>Person</b>
      </td>
    </tr>
		<jspb:SelectLogin var="login">
			<tr>
				<td>
					<%= login.getField("id") %>
				</td>
				<td>
					<%
						SessionHandler handler = (SessionHandler)pageContext.findAttribute("jspboard.SessionHandler");
						if (handler.isLoggedIn(login.getField("id")))
						{
					%>
						Logged in
					<%
						}
						else
						{
					%>
						<%= login.getField("lastaccess") %>
					<%
						}
					%>
				</td>
				<td>
					<jspb:SelectPerson id='<%= login.getField("person") %>' var="person">
						<%= person.getField("fullname") %>
					</jspb:SelectPerson>
				</td>
			</tr>
		</jspb:SelectLogin>
  </table>
</jspb:includes>
