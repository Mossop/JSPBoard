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
            	<jspb:secure groups="contactview">
	              <td>
	                <jspb:link href="/view/contacts.jsp">Contacts</jspb:link>
	              </td>
              </jspb:secure>
            	<jspb:secure groups="loginview">
	              <td>
	                <jspb:link href="/view/users.jsp">Users</jspb:link>
	              </td>
              </jspb:secure>
            	<jspb:secure groups="admin">
	              <td>
	                <jspb:link href="/view/sessions.jsp">Sessions</jspb:link>
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
              <td width="200" valign="top" height="0"></td>
              <td width="578" valign="top" rowspan="2">
