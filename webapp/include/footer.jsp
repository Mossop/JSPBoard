<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<%
	String context = ((HttpServletRequest)request).getContextPath();
	String folder = (String)pageContext.findAttribute("folder");
	if (folder==null)
	{
		folder="-1";
	}
%>

              </td>
            </tr>
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
													<jspb:link href="/view/folder.jsp">
														<jspb:param name="id"><%= id %></jspb:param>
														<img align="top" src="<%= context %>/images/openfolder.gif">
													</jspb:link>
												<% } else { %>
													<jspb:link href="/view/folder.jsp?id=<%= id %>">
														<jspb:param name="id"><%= id %></jspb:param>
														<img align="top" src="<%= context %>/images/closedfolder.gif">
													</jspb:link>
												<% } %>
											</td>
											<td colspan="<%= Integer.parseInt(maxdepth)-Integer.parseInt(depth)+1 %>">
												<% String style = "closedfolder"; %>
												<% if (folder.equals(id)) { style="openfolder"; }%>
												<jspb:link style="<%= style %>" href="/view/folder.jsp">
													<jspb:param name="id"><%= id %></jspb:param>
													<% if (!unread.equals("0")) { %>
													<i>
													<% } %>
														<%= name %>
													<% if (!unread.equals("0")) { %>
														(<%= unread %>)</i>
													<% } %>
												</jspb:link>
											</td>
										</tr>
	              	</jspb:FolderTree>
	              </table>
              	<hr>
              	<jspb:link href="/edit/password.jsp">Change Password</jspb:link><br>
              	<jspb:link href="/logout">Logout</jspb:link>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
