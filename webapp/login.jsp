<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String context = ((HttpServletRequest)request).getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>IEE Wales South West Younger Members Bulletin Board</title>
    <link rel="stylesheet" href="<%= context %>/styles/branches.css" type="text/css">
    <meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">
	</head>

	<body bgcolor="#FFFFFF">

  	<table cellSpacing="0" cellPadding="0" border="0" width="778">
    	<tr>
      	<td width="100" align="center" valign="top" rowspan="2">
      	  <img src="<%= context %>/images/logo.gif" ALT="IEE Logo">
      	</td>
      	<td vAlign="top" align="left" class="fullwidth">
      	  <img src="<%= context %>/images/banner.jpg" alt="Communities image">
      	</td>
    	</tr>
    	<tr>
				<td>
					<p>Please enter your username and password to login to the board.</p>
					<jspb:form action="j_security_check">
						<table align=center>
							<tr>
								<td>Username:</td>
								<td>
									<input type="text" name="j_username" value="">
								</td>
							</tr>
							<tr>
								<td>Password:</td>
								<td>
									<input type="password" name="j_password" value="">
								</td>
							</tr>
							<tr>
				  			<td colspan=2 align=center>
							  	<input type="submit" value="Login">
								</td>
							</tr>
						</table>
				  </jspb:form>
				</td>
			</tr>
		</table>
	</body>
</html>
