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
						<jspb:link href="/update/thread">
							<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
							<jspb:param name="folder"><%= id %></jspb:param>
							<jspb:param name="redirect">/view/thread.jsp?id=<%= thread.getField("id") %></jspb:param>
							<img align="top" src="<%= context %>/images/closedfolder.gif">
						</jspb:link>
					</td>
					<td colspan="<%= Integer.parseInt(maxdepth)-Integer.parseInt(depth)+1 %>">
						<jspb:link style="closedfolder" href="/update/thread">
							<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
							<jspb:param name="folder"><%= id %></jsbp:param>
							<jspb:param name="redirect">/view/thread.jsp?id=<%= thread.getField("id") %></jspb:param>
							<%= name %>
						</jspb:link>
					</td>
				</tr>
	  	</jspb:FolderTree>
	  </table>
	  <hr>
	  <jspb:form action="/delete/thread" method="post">
	  	<input type="hidden" name="redirect" value='/view/folder.jsp?id=<%= thread.getField("folder") %>'>
	  	<input type="hidden" name="id" value='<%= thread.getField("id") %>'>
	  	Delete this thread: <input type="submit" value="Delete">
	  </jspb:form>
	</jspb:includes>
</jspb:SelectThread>
