<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	<table width="578">
		<tr>
			<td>
			  <h1>WSWYM Bulletin Board Contacts</h1>
			</td>
			<td align="right" valign="top">
				<jspb:secure groups="contactadmin">
					<a href="<%= context %>/edit/categories.jsp">Modify Categories</a>
				</jspb:secure>
			</td>
		</tr>
	</table> 
	<table border="0">
		<% boolean displayed = false; %>
    <jspb:SelectCategory var="category" order="id">
    	<% displayed=false; %>
			<jspb:SelectPerson var="person" category='<%= category.getField("id") %>'>
				<% if (!displayed) { %>
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
		    <% displayed=true; } %>
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
			<% if (displayed) { %>
				<tr>
					<td colspan="5"><hr></td>
				</tr>
			<% displayed=false; } %>
		</jspb:SelectCategory>
		<jspb:SelectPerson var="person" category="-1">
			<% if (!displayed) { %>
	    	<tr>
	    		<td colspan="5" width="578">
	    			<b>No Category</b>
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
	    <% displayed=true; } %>
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
		<% if (displayed) { %>
			<tr>
				<td colspan="5"><hr></td>
			</tr>
		<% } %>
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
