<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
  <h1>WSWYM Bulletin Board Contacts</h1>
	<table border="0">
    <jspb:SelectCategory var="category">
    	<tr>
    		<td colspan="5" width="578">
    			<b><%= category.getField("description") %></b>
    			<hr>
    		</td>
    	</tr>
	    <tr>
	    	<td width="20"></td>
	      <td>
	        <b>Name</b>
	      </td>
	      <td>
	      	<b>Description</b>
	      </td>
	      <td>
	        <b>Email</b>
	      </td>
	      <td></td>
	    </tr>
			<jspb:SelectPerson var="person" category='<%= category.getField("id") %>'>
				<tr>
					<td width="20"></td>
					<td>
						<a href='<%= context %>/view/contact.jsp?id=<%= person.getField("id") %>'>
							<%= person.getField("fullname") %>
						</a>
					</td>
					<td><%= person.getField("description") %></td>
					<td>
						<a href='mailto:<%= person.getField("email") %>'>
							<%= person.getField("email") %>
						</a>
					</td>
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
							<jspb:secure groups="contactedit">
								<a href='<%= context %>/edit/contact.jsp?id=<%= person.getField("id") %>'>
									Edit
								</a>
							</jspb:secure>
						<% } %>
					</td>
				</tr>
			</jspb:SelectPerson>
			<tr>
				<td colspan="4"><hr></td>
			</tr>
		</jspb:SelectCategory>
  </table>
  <jspb:secure groups="contactedit">
	  <form action="<%= context %>/add/person" method="post">
	  	<input type="hidden" name="redirect" value="/view/contacts.jsp">
	  	Add a new contact: <input type="text" name="fullname">
	  	<input type="submit" value="Add">
	  	<select name="category">
	  		<jspb:SelectCategory var="category">
	  			<option value='<%= category.getField("id") %>'>
	  				<%= category.getField("description") %>
	  			</option>
	  		</jspb:SelectCategory>
	  	</select>
	  </form>
	</jspb:secure>
</jspb:includes>
