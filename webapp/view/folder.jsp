<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String folderid = request.getParameter("id");
	if (folderid==null)
	{
		folderid="48";
	}
	request.setAttribute("folder",folderid);
%>

<jspb:SelectFolder var="folder" id="<%= folderid %>">
	<jspb:includes>
		<table border="0">
			<tr>
				<td valign="top">
					<h1><%= folder.getField("name") %></h1>
				</td>
				<td valign="top" align="right">
					<jspb:secure groups="boardadmin">
						<a href="<%= context %>/edit/folder.jsp?id=<%= folderid %>">
							Administration
						</a>
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
						<jspb:SelectThread var="thread" folder="<%= folderid %>">
							<tr>
								<td>
									<img src="<%= context %>/images/read.gif" alt="read" align="middle">
								</td>
								<td>
									<a href='<%= context %>/view/thread.jsp?id=<%= thread.getField("id") %>'><%= thread.getField("name") %></a>
								</td>
								<td>
									<jspb:SelectPerson var="person" id='<%= thread.getField("owner") %>'>
										<a href='<%= context %>/view/contact.jsp?id=<%= person.getField("id") %>'>
											<%= person.getField("nickname") %>
										</a>
									</jspb:SelectPerson>
								</td>
								<td align="right">
									<%= thread.getDate("created") %>
								</td>
							</tr>
						</jspb:SelectThread>
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
						<form action="<%= context %>/add/message" method="post">
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
						</form>
					</td>
				</jspb:secure>
			</tr>
		</table>
	</jspb:includes>
</jspb:SelectFolder>
