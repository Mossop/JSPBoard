<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	<table width="578">
		<tr>
			<td>
			  <h1>WSWYM Bulletin Board Contacts</h1>
			</td>
			<td align="right" valign="top">
				<jspb:secure groups="contactadmin">
					<jspb:link href="/edit/categories.jsp">Modify Categories</jspb:link>
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
						<jspb:link href="/view/contact.jsp">
							<jspb:param name="id"><%= person.getField("id") %></jspb:param>
							<%= person.getField("fullname") %>
						</jspb:link>
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
								<jspb:link href="/edit/contact.jsp">
									<jspb:param name="id"><%= person.getField("id") %></jspb:param>
									Edit
								</jspb:link>
							</jspb:secure>
						<% } else {%>
							<jspb:secure groups="contactedit">
								<jspb:link href="/edit/contact.jsp">
									<jspb:param name="id"><%= person.getField("id") %></jspb:param>
									Edit
								</jspb:link>
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
					<jspb:link href="/view/contact.jsp">
						<jspb:param name="id"><%= person.getField("id") %></jspb:param>
						<%= person.getField("fullname") %>
					</jspb:link>
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
							<jspb:link href="/edit/contact.jsp">
								<jspb:param name="id"><%= person.getField("id") %></jspb:param>
								Edit
							</jspb:link>
						</jspb:secure>
					<% } else {%>
						<jspb:secure groups="contactedit">
							<jspb:link href="/edit/contact.jsp">
								<jspb:param name="id"><%= person.getField("id") %></jspb:param>
								Edit
							</jspb:link>
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
	  <jspb:form action="/add/person" method="post">
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
	  </jspb:form>
	</jspb:secure>
</jspb:includes>
