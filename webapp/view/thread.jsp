<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectThread var="thread" id='<%= request.getParameter("id") %>'>
	<%
		request.setAttribute("folder",thread.getField("folder"));
	%>
	<jspb:includes>
		<table>
    	<tr>
      	<td valign="top">
        	<h1>Messages in the thread "<%= thread.getField("name") %>"</h1>
        </td>
        <td valign="top" align="right">
        	<jspb:secure person='<%= thread.getField("owner") %>' groups="messageadmin">
	          <a href='<%= context %>/edit/thread.jsp?id=<%= thread.getField("id") %>'>
							Administration
						</a>
					</jspb:secure>
        </td>
      </tr>
      <tr>
      	<td align="center" colspan="2" width="578">
      		<% boolean foundfirst=false; %>
      		<jspb:SelectLogin var="login" id="<%= request.getRemoteUser() %>">
      		<jspb:SelectMessage var="msg" thread='<%= thread.getField("id") %>' checkUnread='<%= login.getField("person") %>'>
						<jspb:window>
							<jspb:header>
	              <table width="100%">
	                <tr>
	                	<td align="left" width="15">
	                		<% if (msg.getField("unread").equals("1")) { %>
	                			<% if (!foundfirst) { %>
	                				<a name="unread"></a>
	                			<% foundfirst=true; } %>
		                		<img src="<%= context %>/images/unread.gif" align="middle" alt="read">
	                		<% } else { %>
		                		<img src="<%= context %>/images/read.gif" align="middle" alt="read">
		                	<% } %>
	                	</td>
	                  <td align="left">
											<jspb:SelectPerson var="person" id='<%= msg.getField("owner") %>'>
												Posted by <a href='contact.jsp?id=<%= person.getField("id") %>'><%= person.getField("nickname") %></a>
											</jspb:SelectPerson>
										</td>
	                  <td align="right"><%= msg.getDate("created") %></td>
	                </tr>
	                <jspb:SelectEdits var="edit" message='<%= msg.getField("id") %>'>
	                	<tr>
	                		<td></td>
	                		<td>
												<jspb:SelectPerson var="person" id='<%= edit.getField("person") %>'>
													Edited by <a href='contact.jsp?id=<%= person.getField("id") %>'><%= person.getField("nickname") %></a>
												</jspb:SelectPerson>
	                		</td>
	                		<td align="right">
	                			<%= edit.getDate("altered") %>
	                		</td>
	                	</tr>
	                </jspb:SelectEdits>
	                <jspb:secure person='<%= msg.getField("owner") %>' groups="messageadmin">
		                <tr>
		                  <td colspan="3">
		                    <table>
		                      <tr>
		                        <td valign="middle">
		                        	<img align="middle" src="<%= context %>/images/paperclip.gif">
		                          <a href='<%= context %>/add/file.jsp?id=<%= msg.getField("id") %>'>
																Attach File
															</a>
		                        </td>
		                        <td>|</td>
		                        <td valign="middle">
		                        	<img align="middle" src="<%= context %>/images/edit.gif">
		                          <a href='<%= context %>/edit/message.jsp?id=<%= msg.getField("id") %>'>
																Edit
															</a>
		                        </td>
		                        <td>|</td>
		                        <td valign="middle">
		                        	<img align="middle" src="<%= context %>/images/delete.gif">
		                          <a href='<%= context %>/delete/message?id=<%= msg.getField("id") %>&redirect=/view/thread.jsp%3fid%3d<%= thread.getField("id") %>'>
																Delete
															</a>
		                        </td>
		                      </tr>
		                    </table>
		                  </td>
		                </tr>
		              </jspb:secure>
	              </table>
                <table>
									<% int count=0; %>
									<jspb:SelectFile message='<%= msg.getField("id") %>' var="file">
										<% count++; %>
                  	<tr>
                    	<td width="10"><img src="<%= context %>/images/paperclip.gif"></td>
                      <td width="20"><%= count %>:</td>
                      <td width="400"><%= file.getField("description") %></td>
                      <td align="right" width="158">
                      	<a href='<%= context %>/files/<%= file.getField("name") %>?id=<%= file.getField("id") %>'>
													<%= file.getField("name") %>
												</a>
                      </td>
                      <td>
				                <jspb:secure person='<%= msg.getField("owner") %>' groups="messageadmin">
	                        <a href='<%= context %>/delete/file?id=<%= file.getField("id") %>&redirect=/view/thread.jsp%3fid%3d<%= thread.getField("id") %>'>
	                        	<img src="<%= context %>/images/delete.gif" alt="delete" align="middle">
	                        </a>
	                      </jspb:secure>
                      </td>
                    </tr>
									</jspb:SelectFile>
                </table>
	            </jspb:header>
	            <jspb:footer>
	            	<jspb:stylise>
		            	<%= msg.getField("content") %>
		            </jspb:stylise>
		          </jspb:footer>
	          </jspb:window>
	          <br>
	        </jspb:SelectMessage>
	        </jspb:SelectLogin>
        </td>
      </tr>
      <jspb:secure groups="messageadd">
	      <tr>
	        <td colspan="2">
	          <hr>
	        </td>
	      </tr>
	      <tr>
	        <td colspan="2">
	          <h2>Add a new reply to this thread:</h2>
	          <form action="<%= context %>/add/message" method="post">
	          	<input type="hidden" name="thread" value='<%= thread.getField("id") %>'>
	          	<input type="hidden" name="redirect" value='/view/thread.jsp?id=<%= thread.getField("id") %>'>
	          	<table>
	            	<tr>
	                <td>
	                  <textarea name="content" rows="15" cols="60"></textarea>
	                </td>
	              </tr>
	              <tr>
	                <td align="center">
	                	<input type="submit" value="Add">
	                </td>
	              </tr>
	            </table>
	          </form>
	        </td>
	      </tr>
	    </jspb:secure>
    </table>
	</jspb:includes>
</jspb:SelectThread>
