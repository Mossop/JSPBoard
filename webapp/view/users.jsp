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
					<jspb:secure groups="loginadmin">
						<a href='<%= context %>/edit/user.jsp?id=<%= login.getField("id") %>'>
					</jspb:secure>
						<%= login.getField("id") %>
					<jspb:secure groups="loginadmin">
						</a>
					</jspb:secure>
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
						<%= login.getDate("lastaccess") %>
					<%
						}
					%>
				</td>
				<td>
					<jspb:SelectPerson id='<%= login.getField("person") %>' var="person">
						<a href='contact.jsp?id=<%= person.getField("id") %>'>
							<%= person.getField("fullname") %>
						</a>
					</jspb:SelectPerson>
				</td>
			</tr>
		</jspb:SelectLogin>
  </table>
</jspb:includes>
