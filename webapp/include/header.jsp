<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String context = ((HttpServletRequest)request).getContextPath();
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
              <td>
                <a href="index.jsp">Announcements</a>
              </td>
              <td>
                <a href="contacts.jsp">Contacts</a>
              </td>
              <td>
                <a href="users.jsp">Users</a>
              </td>
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
												<a href=""><img align="top" src="images/closedfolder.gif"></a>
											</td>
											<td colspan="<%= Integer.parseInt(maxdepth)-Integer.parseInt(depth)+1 %>">
												<a class="closedfolder" href=""><%= name %></a>
											</td>
										</tr>
	              	</jspb:FolderTree>
	              </table>
              	<hr>
              	Change Password
              </td>
              <td width="578" valign="top">
