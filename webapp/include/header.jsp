<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String context = ((HttpServletRequest)request).getContextPath();
	String folder = (String)pageContext.findAttribute("folder");
	if (folder==null)
	{
		folder="-1";
	}
%>

<html>
  <head>
    <title>IEE Wales South West Younger Members Bulletin Board</title>
    <link rel="stylesheet" href="<%= context %>/styles/branches.css" type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  </head>
  <body bgcolor="#FFFFFF">
    <table cellspacing="0" cellPadding="0" border="0" width="778">
      <tr>
        <td width="100" align="center" valign="top" rowspan="2"><img src="<%= context %>/images/logo.gif" alt="IEE Logo"></td>
        <td valign="top" align="left" class="fullwidth" colspan="2"><img src="<%= context %>/images/banner.jpg" alt="Communities image"></td>
      </tr>
      <tr>
        <td>
          <table>
            <tr>
            	<jspb:secure groups="messageview">
	              <td>
	                <a href="<%= context %>\view\folder.jsp">Announcements</a>
	              </td>
              </jspb:secure>
            	<jspb:secure groups="contactview">
	              <td>
	                <a href="<%= context %>\view\contacts.jsp">Contacts</a>
	              </td>
              </jspb:secure>
            	<jspb:secure groups="loginview">
	              <td>
	                <a href="<%= context %>\view\users.jsp">Users</a>
	              </td>
              </jspb:secure>
            </tr>
          </table>
        </td>
        <td align="right">
							Currently logged in as
							<%= request.getRemoteUser() %>
						</td>
      </tr>
      <tr>
        <td colspan="3" width="778">
          <hr>
          <table border="0">
            <tr>
              <td width="200" valign="top">
              	<table border="0">
									<jspb:FolderTree>
										<tr>
											<% if (Integer.parseInt(depth)>0) { %>
												<td colspan="<%= depth %>"></td>
											<% } %>
											<td colspan="1">
												<% if (folder.equals(id)) { %>
													<a href="<%= context %>/view/folder.jsp?id=<%= id %>"><img align="top" src="<%= context %>/images/openfolder.gif"></a>
												<% } else { %>
													<a href="<%= context %>/view/folder.jsp?id=<%= id %>"><img align="top" src="<%= context %>/images/closedfolder.gif"></a>
												<% } %>
											</td>
											<td colspan="<%= Integer.parseInt(maxdepth)-Integer.parseInt(depth)+1 %>">
												<% if (folder.equals(id)) { %>
													<a class="openfolder" href="<%= context %>/view/folder.jsp?id=<%= id %>">
												<% } else { %>
													<a class="closedfolder" href="<%= context %>/view/folder.jsp?id=<%= id %>">
												<% } %>
												<% if (!unread.equals("0")) { %>
												<i>
												<% } %>
													<%= name %>
												<% if (!unread.equals("0")) { %>
													(<%= unread %>)</i>
												<% } %>
												</a>
											</td>
										</tr>
	              	</jspb:FolderTree>
	              </table>
              	<hr>
              	<a href="<%= context %>/edit/password.jsp">Change Password</a><br>
              	<a href="<%= context %>/logout">Logout</a>
              </td>
              <td width="578" valign="top">
