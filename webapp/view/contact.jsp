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
		</table>
		<table>
    	<tr>
    		<td><b>Fullname:</b></td>
    		<td><%= person.getField("fullname") %></td>
      </tr>
    	<tr>
    		<td><b>Nickname:</b></td>
    		<td><%= person.getField("nickname") %></td>
      </tr>
    	<tr>
    		<td><b>Description:</b></td>
    		<td>
   				<%= person.getField("description") %>
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
      			<jspb:SelectLogin person='<%= person.getField("id") %>' var="login">
							<jspb:secure groups="loginadmin">
								<a href='<%= context %>/edit/user.jsp?id=<%= login.getField("id") %>'>
							</jspb:secure>
								<%= login.getField("id") %>
							<jspb:secure groups="loginadmin">
								</a>
							</jspb:secure>
      			</jspb:SelectLogin>
      		</td>
      	</tr>
      </jspb:secure>
    </table>
	</jspb:includes>
</jspb:SelectPerson>
