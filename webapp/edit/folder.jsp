<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectFolder var="folder" id='<%= request.getParameter("id") %>'>
	<jspb:includes>
		<h1><%= folder.getField("name") %></h1>
		<form action="<%= context %>/update/folder" method="post">
			<input type="hidden" name="id" value='<%= folder.getField("id") %>'>
			<input type="hidden" name="redirect" value='/view/folder.jsp?id=<%= folder.getField("id") %>'>
			<table>
				<tr>
					<td><b>Name:</b></td>
					<td><input name="name" value='<%= folder.getField("name") %>'>
					<td><input type="submit" value="Update"></td>
				</tr>
			</table>
		</form>
	</jspb:includes>
</jspb:SelectFolder>
