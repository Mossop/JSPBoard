<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectThread var="thread" id='<%= request.getParameter("id") %>'>
	<%
		request.setAttribute("folder",thread.getField("folder"));
	%>
	<jspb:includes>
  	<h1>Editing the thread "<%= thread.getField("name") %>"</h1>
  	<p>Select a folder below to move this thread.</p>
  	<table>
	  	<jspb:FolderTree>
				<tr>
					<% if (Integer.parseInt(depth)>0) { %>
						<td colspan="<%= depth %>"></td>
					<% } %>
					<td colspan="1">
						<a href='<%= context %>/update/thread?id=<%= thread.getField("id") %>&folder=<%= id %>&redirect=/view/thread.jsp%3fid%3d<%= thread.getField("id") %>'><img align="top" src="<%= context %>/images/closedfolder.gif"></a>
					</td>
					<td colspan="<%= Integer.parseInt(maxdepth)-Integer.parseInt(depth)+1 %>">
						<a class="closedfolder" href='<%= context %>/update/thread?id=<%= thread.getField("id") %>&folder=<%= id %>&redirect=/view/thread.jsp%3fid%3d<%= thread.getField("id") %>'><%= name %></a>
					</td>
				</tr>
	  	</jspb:FolderTree>
	  </table>
	  <hr>
	  <form action="<%= context %>/delete/thread" method="post">
	  	<input type="hidden" name="redirect" value='/view/folder.jsp?id=<%= thread.getField("folder") %>'>
	  	<input type="hidden" name="id" value='<%= thread.getField("id") %>'>
	  	Delete this thread: <input type="submit" value="Delete">
	  </form>
	</jspb:includes>
</jspb:SelectThread>
