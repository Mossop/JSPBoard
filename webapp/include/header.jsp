<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<html>
  <head>
    <title>IEE Wales South West Younger Members Bulletin Board</title>
    <link rel="stylesheet" href="styles/branches.css" type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  </head>
  <body bgcolor="#FFFFFF">
    <table cellspacing="0" cellPadding="0" border="0" width="778">
      <tr>
        <td width="100" align="center" valign="top" rowspan="2"><img src="images/logo.gif" alt="IEE Logo"></td>
        <td valign="top" align="left" class="fullwidth" colspan="2"><img src="images/banner.jpg" alt="Communities image"></td>
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
              	<jsp:include page="/include/folderview.jsp"/>
              	<hr>
              	<p>Change Password</p>
								<p>Logout</p>
              </td>
              <td width="578" valign="top">
