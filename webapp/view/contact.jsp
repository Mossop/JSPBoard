<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectPerson var="person" id='<%= request.getParameter("id") %>'>
	<jspb:includes>
		<h1>Details for <%= person.getField("fullname") %></h1>
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
    </table>
	</jspb:includes>
</jspb:SelectPerson>
