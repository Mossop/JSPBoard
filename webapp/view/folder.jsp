<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String folderid = request.getParameter("id");
	if (folderid==null)
	{
		folderid="47";
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
					<a href="<%= context %>/edit/folder.jsp?id=<%= folderid %>">
						Administration
					</a>
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
									<a href='<%= context %>/view/thread.jsp?id=<%= thread.getField("id") %>'><jspb:GetField field="name"/></a>
								</td>
								<td>
									<jspb:SelectPerson var="person" id='<%= thread.getField("owner") %>'>
										<jspb:GetField field="nickname"/>
									</jspb:SelectPerson>
								</td>
								<td align="right">
									<jspb:GetField field="created"/>
								</td>
							</tr>
						</jspb:SelectThread>
					</table>
				</td>
			</tr>
			<tr>
				<jspb:secure groups="admin">
					<td colspan="2">
						<hr>
						<h2>Post a new thread:</h2>
						<form action="xdf.php" method="post">
							<table>
								<tr>
									<td>Subject:</td>
									<td>
										<input type="text" name="name1" size="70">
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<textarea name="content2" rows="15" cols="60"></textarea>
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
