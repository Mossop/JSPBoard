<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String folderid = request.getParameter("id");
	if (folderid==null)
	{
%>
	<jspb:SelectFolder var="rootfinder" parentId="-1">
		<% folderid=rootfinder.getField("id"); %>
	</jspb:SelectFolder>
<%
	}
	request.setAttribute("folder",folderid);
%>

<jspb:SelectFolder var="folder" id="<%= folderid %>">
	<jspb:includes>
		<script language="JavaScript">
			<!--
			function validate()
			{
				if (document.forms[0].elements[2].value=='')
				{
					alert('You must enter a title for the thread.');
					return false;
				}
				return true;
			}
			//-->
		</script>
		<table border="0">
			<tr>
				<td valign="top">
					<h1><%= folder.getField("name") %></h1>
				</td>
				<td valign="top" align="right">
					<jspb:secure groups="boardadmin">
						<jspb:link href="/edit/folder.jsp">
							<jspb:param name="id"><%= folderid %></jspb:param>
							Administration
						</jspb:link>
					</jspb:secure>
				</td>
			</tr>
			<tr>
				<td colspan="2" width="578">
					<table border="0" cellspacing="1">
						<tr>
							<td width="15"></td>
							<td width="323">
								<b>Thread</b>
							</td>
							<td width="100">
								<b>Author</b>
							</td>
							<td width="120" align="right">
								<b>Started</b>
							</td>
						</tr>
						<jspb:SelectLogin var="login" id="<%= request.getRemoteUser() %>">
							<jspb:SelectThread var="thread" folder="<%= folderid %>" countUnread='<%= login.getField("person") %>'>
								<tr>
									<td>
										<% if (thread.getField("unreadcount").equals("0")) { %>
											<jspb:link href="/view/thread.jsp">
												<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
												<img src="<%= context %>/images/read.gif" alt="read" align="middle">
											</jspb:link>
										<% } else { %>
											<jspb:link href="/view/thread.jsp" name="unread">
												<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
												<img src="<%= context %>/images/unread.gif" alt="unread" align="middle">
											</jspb:link>
										<% } %>
									</td>
									<td>
										<% if (thread.getField("unreadcount").equals("0")) { %>
											<jspb:link href="/view/thread.jsp">
												<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
												<%= thread.getField("name") %>
											</jspb:link>
										<% } else { %>
											<jspb:link href="/view/thread.jsp" name="unread">
												<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
												<i><%= thread.getField("name") %> (<%= thread.getField("unreadcount") %>)</i>
											</jspb:link>
										<% } %>
									</td>
									<td>
										<jspb:SelectPerson var="person" id='<%= thread.getField("owner") %>'>
											<jspb:link href="/view/contact.jsp">
												<jspb:param name="id"><%= person.getField("id") %></jspb:param>
												<%= person.getField("displayname") %>
											</jspb:link>
										</jspb:SelectPerson>
									</td>
									<td align="right">
										<%= thread.getDate("created") %>
									</td>
								</tr>
							</jspb:SelectThread>
						</jspb:SelectLogin>
					</table>
				</td>
			</tr>
			<tr>
				<%
					String groups = "messageadd";
					if (folder.getField("parent").equals("-1"))
					{
						groups="boardadmin";
					}
				%>
				<jspb:secure groups="<%= groups %>">
					<td colspan="2">
						<hr>
						<h2>Post a new thread:</h2>
						<jspb:form name="mainform" onSubmit="return validate()" action="/add/message" method="post" enctype="multipart/form-data">
							<input type="hidden" name="folder" value='<%= folder.getField("id") %>'>
							<input type="hidden" name="redirect" value='/view/folder.jsp?id=<%= folderid %>'>
							<table>
								<tr>
									<td>Subject:</td>
									<td>
										<input type="text" name="name" size="70">
									</td>
								</tr>
	              <tr>
	              	<td>Attach a file:</td>
	              	<td><input name="file" type="file"></td>
	              </tr>
	              <tr>
	              	<td>File description:</td>
	              	<td><input name="description"></td>
	              </tr>
								<tr>
									<td colspan="2">
										<textarea name="content" rows="15" cols="60"></textarea>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<input type="submit" value="Add">
									</td>
								</tr>
							</table>
						</jspb:form>
					</td>
				</jspb:secure>
			</tr>
		</table>
	</jspb:includes>
</jspb:SelectFolder>
