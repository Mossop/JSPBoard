<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String parent;
	int depth;
	
	parent=request.getParameter("parent");
	if (parent==null)
	{
		parent="-1";
	}

	depth=0;
	try
	{
		depth = Integer.parseInt(request.getParameter("depth"));
	}
	catch (Exception e)
	{
	}
%>

<jspb:SelectFolder var="folder" parentId="<%= parent %>">

	<tr>
		<% if (depth>0) { %>
			<td colspan="<%= depth %>"></td>
		<% } %>
		<td colspan="1">
			<a href=""><img align="top" src="images/closedfolder.gif"></a>
		</td>
		<td colspan="<%= 4-depth %>">
			<a class="closedfolder" href=""><%= folder.getField("name") %></a>
		</td>
	</tr>

	<jsp:include page="folder.jsp">
		<jsp:param name="parent" value='<%= folder.getField("id") %>'/>
		<jsp:param name="depth" value="<%= (depth+1) %>"/>
	</jsp:include>

</jspb:SelectFolder>
