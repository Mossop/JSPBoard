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
		<hr>
		<p>If you wish to move this folder, simply select where to put it below:</p>
  	<table>
	  	<jspb:FolderTree ignore='<%= folder.getField("id") %>'>
				<tr>
					<% if (Integer.parseInt(depth)>0) { %>
						<td colspan="<%= depth %>"></td>
					<% } %>
					<td colspan="1">
						<a href='<%= context %>/update/folder?id=<%= folder.getField("id") %>&parent=<%= id %>&redirect=%2fview%2ffolder.jsp'><img align="top" src="<%= context %>/images/closedfolder.gif"></a>
					</td>
					<td colspan="<%= Integer.parseInt(maxdepth)-Integer.parseInt(depth)+1 %>">
						<a class="closedfolder" href='<%= context %>/update/folder?id=<%= folder.getField("id") %>&parent=<%= id %>&redirect=%2Fview%2Ffolder.jsp'><%= name %></a>
					</td>
				</tr>
	  	</jspb:FolderTree>
	  </table>
	</jspb:includes>
</jspb:SelectFolder>
