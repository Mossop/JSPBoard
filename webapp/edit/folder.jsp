<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectFolder var="folder" id='<%= request.getParameter("id") %>'>
	<%
		request.setAttribute("folder",folder.getField("id"));
	%>
	<jspb:includes>
		<h1><%= folder.getField("name") %></h1>
		<jspb:form action="/update/folder" method="post">
			<input type="hidden" name="id" value='<%= folder.getField("id") %>'>
			<input type="hidden" name="redirect" value='/view/folder.jsp?id=<%= folder.getField("id") %>'>
			<table>
				<tr>
					<td><b>Name:</b></td>
					<td><input name="name" value='<%= folder.getField("name") %>'>
					<td><input type="submit" value="Update"></td>
				</tr>
			</table>
		</jspb:form>
		<% if (!folder.getField("parent").equals("-1")) { %>
		<hr>
		<jspb:form action="/delete/folder" action="post">
			<input type="hidden" name="id" value='<%= folder.getField("id") %>'>
			<input type="hidden" name="redirect" value='/view/folder.jsp?id=<%= folder.getField("parent") %>'>
			<input type="submit" value="Delete Folder"> (This will only work if the folder contains no threads)
		</jspb:form>
		<hr>
		<p>If you wish to move this folder, simply select where to put it below:</p>
  	<table>
	  	<jspb:FolderTree ignore='<%= folder.getField("id") %>'>
				<tr>
					<% if (Integer.parseInt(depth)>0) { %>
						<td colspan="<%= depth %>"></td>
					<% } %>
					<td colspan="1">
						<jspb:link href="/update/folder">
							<jspb:param name="id"><%= folder.getField("id") %></jspb:param>
							<jspb:param name="parent"><%= id %></jspb:param>
							<jspb:param name="redirect">/view/folder.jsp?id=<%= folder.getField("id") %></jspb:param>
							<img align="top" src="<%= context %>/images/closedfolder.gif">
						</jspb:link>
					</td>
					<td colspan="<%= Integer.parseInt(maxdepth)-Integer.parseInt(depth)+1 %>">
						<jspb:link style="closedfolder" href="/update/folder">
							<jspb:param name="id"><%= folder.getField("id") %></jspb:param>
							<jspb:param name="parent"><%= id %></jspb:param>
							<jspb:param name="redirect">/view/folder.jsp?id=<%= folder.getField("id") %></jspb:param>
							<%= name %>
						</jspb:link>
					</td>
				</tr>
	  	</jspb:FolderTree>
	  </table>
	  <% } %>
	</jspb:includes>
</jspb:SelectFolder>
