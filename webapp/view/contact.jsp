<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectPerson var="person" id='<%= request.getParameter("id") %>'>
	<jspb:includes>
		<table width="578">
			<tr>
				<td>
					<h1>Details for <%= person.getField("fullname") %></h1>
				</td>
				<td valign="top" align="right">
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
		</table>
		<table>
    	<tr>
    		<td><b>Title:</b></td>
    		<td><%= person.getField("title") %></td>
      </tr>
    	<tr>
    		<td><b>First names:</b></td>
    		<td><%= person.getField("firstnames") %></td>
      </tr>
    	<tr>
    		<td><b>Surname:</b></td>
    		<td><%= person.getField("surname") %></td>
      </tr>
    	<tr>
    		<td><b>Display name:</b></td>
    		<td><%= person.getField("displayname") %></td>
      </tr>
    	<tr>
    		<td><b>Description:</b></td>
    		<td>
   				<%= person.getField("description") %>
    		</td>
      </tr>
      <tr>
      	<td><b>IEE Membership Number:</b></td>
      	<td>
      		<%= person.getField("membership") %>
      	</td>
      </tr>
      <tr>
      	<td><b>Category:</b></td>
      	<td>
      		<jspb:SelectCategory var="category" id='<%= person.getField("category") %>'>
						<%= category.getField("description") %>
      		</jspb:SelectCategory>
      	</td>
      </tr>
    	<tr>
    		<td><b>Email:</b></td>
    		<td>
    			<a href='mailto:<%= person.getField("email") %>'>
    				<%= person.getField("email") %>
    			</a>
    		</td>
      </tr>
    	<tr>
    		<td><b>Home phone:</b></td>
    		<td><%= person.getField("homephone") %></td>
      </tr>
    	<tr>
    		<td><b>Mobile phone:</b></td>
    		<td><%= person.getField("mobilephone") %></td>
      </tr>
    	<tr>
    		<td><b>Work phone:</b></td>
    		<td><%= person.getField("workphone") %></td>
      </tr>
    	<tr>
    		<td><b>Fax number:</b></td>
    		<td><%= person.getField("fax") %></td>
      </tr>
      <jspb:secure groups="loginview" person='<%= person.getField("id") %>'>
      	<tr>
      		<td><b>Logins:</b></td>
      		<td>
      			<jspb:secure groups="loginadmin">
	      			<jspb:SelectLogin person='<%= person.getField("id") %>' var="login">
								<jspb:link href="/edit/user.jsp">
									<jspb:param name="id"><%= login.getField("id") %></jspb:param>
									<%= login.getField("id") %>
								</jspb:link>
	      			</jspb:SelectLogin>
	      		</jspb:secure>
      			<jspb:notsecure groups="loginadmin">
	      			<jspb:SelectLogin person='<%= person.getField("id") %>' var="login">
								<%= login.getField("id") %>
	      			</jspb:SelectLogin>
	      		</jspb:notsecure>
      		</td>
      	</tr>
      </jspb:secure>
    </table>
	</jspb:includes>
</jspb:SelectPerson>
