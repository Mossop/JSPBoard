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
						<jspb:link href="/edit/user.jsp">
							<jspb:param name="id"><%= login.getField("id") %></jspb:param>
							<%= login.getField("id") %>
						</jspb:link>
					</jspb:secure>
					<jspb:notsecure groups="loginadmin">
						<%= login.getField("id") %>
					</jspb:notsecure>
				</td>
				<td>
					<%= login.getDate("lastaccess") %>
				</td>
				<td>
					<jspb:SelectPerson id='<%= login.getField("person") %>' var="person">
						<jspb:link href="contact.jsp">
							<jspb:param name="id"><%= person.getField("id") %></jspb:param>
							<%= person.getField("fullname") %>
						</jspb:link>
					</jspb:SelectPerson>
				</td>
			</tr>
		</jspb:SelectLogin>
  </table>
</jspb:includes>
