<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	<table border="0">
    <tr>
      <td valign="top" colspan="4" width="578">
        <h1>WSWYM Bulletin Board Contacts</h1>
      </td>
    </tr>
    <tr>
      <td>
        <b>Name</b>
      </td>
      <td>
        <b>Email</b>
      </td>
      <td>
        <b>Mobile Phone</b>
      </td>
      <td></td>
    </tr>
		<jspb:SelectPerson var="person">
			<tr>
				<td>
					<a href='contact.jsp?id=<%= person.getField("id") %>'>
						<%= person.getField("fullname") %>
					</a>
				</td>
				<td>
					<a href='mailto:<%= person.getField("email") %>'>
						<%= person.getField("email") %>
					</a>
				</td>
				<td><%= person.getField("mobilephone") %></td>
				<td>
					<% boolean haslogin=false; %>
					<jspb:SelectLogin person='<%= person.getField("id") %>'>
						<% haslogin=true; %>
					</jspb:SelectLogin>
					<% if (haslogin) { %>
						<jspb:secure groups="contactadmin" person='<%= person.getField("id") %>'>
							<a href='<%= context %>/edit/contact.jsp?id=<%= person.getField("id") %>'>
								Edit
							</a>
						</jspb:secure>
					<% } else {%>
						<jspb:secure groups="contactview">
							<a href='<%= context %>/edit/contact.jsp?id=<%= person.getField("id") %>'>
								Edit
							</a>
						</jspb:secure>
					<% } %>
				</td>
			</tr>
		</jspb:SelectPerson>
  </table>
</jspb:includes>
