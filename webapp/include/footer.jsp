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
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
