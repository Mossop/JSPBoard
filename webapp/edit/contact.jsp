<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectPerson var="person" id='<%= request.getParameter("id") %>'>
	<jspb:includes>
		<table>
			<tr>
				<td>
					<h1>Editing <%= person.getField("fullname") %></h1>
				</td>
				<td align="right">
					<jspb:secure groups="contactadmin">
						<jspb:form action="/delete/person" method="post">
							<input type="hidden" name="id" value='<%= person.getField("id") %>'>
							<input type="hidden" name="redirect" value="/view/contacts.jsp">
							<input type="submit" value="Delete">
						</jspb:form>
					</jspb:secure>
				</td>
			</tr>
		</table>
		<jspb:form action="/update/person" method="post">
			<input type="hidden" name="id" value='<%= person.getField("id") %>'>
			<input type="hidden" name="redirect" value='/view/contact.jsp?id=<%= person.getField("id") %>'>
			<table>
	    	<tr>
	    		<td><b>Title:</b></td>
	    		<td><input name="title" value='<%= person.getField("title") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>First names:</b></td>
	    		<td><input name="firstnames" value='<%= person.getField("firstnames") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>Surname:</b></td>
	    		<td><input name="surname" value='<%= person.getField("surname") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>Display name:</b></td>
	    		<td><input name="displayname" value='<%= person.getField("displayname") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>Description:</b></td>
	    		<td><input name="description" value='<%= person.getField("description") %>'></td>
	      </tr>
	      <tr>
	      	<td><b>Category:</b></td>
	      	<td>
	      		<select name="category">
	      			<jspb:SelectCategory var="category">
	      				<option value='<%= category.getField("id") %>'<% if (category.getField("id").equals(person.getField("category"))) { %> selected <% } %>>
	      					<%= category.getField("description") %>
	      				</option>
	      			</jspb:SelectCategory>
	      		</select>
	      	</td>
	      </tr>
	    	<tr>
	    		<td><b>Email:</b></td>
	    		<td><input name="email" value='<%= person.getField("email") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>Home phone:</b></td>
	    		<td><input name="homephone" value='<%= person.getField("homephone") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>Mobile phone:</b></td>
	    		<td><input name="mobilephone" value='<%= person.getField("mobilephone") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>Work phone:</b></td>
	    		<td><input name="workphone" value='<%= person.getField("workphone") %>'></td>
	      </tr>
	    	<tr>
	    		<td><b>Fax number:</b></td>
	    		<td><input name="fax" value='<%= person.getField("fax") %>'></td>
	      </tr>
	      <tr>
	      	<td colspan="2" align="center">
	      		<input type="submit" value="Update">
	      	</td>
	      </tr>
	    </table>
	  </jspb:form>
	  <jspb:secure groups="loginadmin">
	  	<hr>
	  	Add a login:
	  	<jspb:form action="/add/login">
	  		<input type="hidden" name="redirect" value='/view/contact.jsp?id=<%= person.getField("id") %>'>
	  		<input type="hidden" name="person" value='<%= person.getField("id") %>'>
	  		Username: <input type="text" name="id"><br>
	  		Password: <input type="test" name="password">
	  		<input type="submit" value="Add">
	  	</jspb:form>
	  </jspb:secure>
	</jspb:includes>
</jspb:SelectPerson>
