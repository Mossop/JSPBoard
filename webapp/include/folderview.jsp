<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%! String parent; %>
<%! int depth; %>

<%
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

<table>
	<jsp:include page="folder.jsp">
		<jsp:param name="parent" value="<%= parent %>"/>
		<jsp:param name="depth" value="<%= depth %>"/>
	</jsp:include>
</table>
