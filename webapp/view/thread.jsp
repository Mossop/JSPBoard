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
	          <jspb:link href="/edit/thread.jsp">
	          	<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
							Administration
						</jspb:link>
					</jspb:secure>
        </td>
      </tr>
      <tr>
      	<td valign="top" align="right" colspan="2">
        	<jspb:link href="/view/printable.jsp">
        		<jspb:param name="id"><%= thread.getField("id") %></jspb:param>
        		Printable view
        	</jspb:link>
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
												Posted by 
												<jspb:link href="/view/contact.jsp">
													<jspb:param name="id"><%= person.getField("id") %></jspb:param>
													<%= person.getField("displayname") %>
												</jspb:link>
											</jspb:SelectPerson>
										</td>
	                  <td align="right"><%= msg.getDate("created") %></td>
	                </tr>
	                <jspb:SelectEdits var="edit" message='<%= msg.getField("id") %>'>
	                	<tr>
	                		<td></td>
	                		<td>
												<jspb:SelectPerson var="person" id='<%= edit.getField("person") %>'>
													Edited by 
													<jspb:link href="/view/contact.jsp">
														<jspb:param name="id"><%= person.getField("id") %></jspb:param>
														<%= person.getField("displayname") %>
													</jspb:link>
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
		                          <jspb:link href="/add/file.jsp">
		                          	<jspb:param name="id"><%= msg.getField("id") %></jspb:param>
																Attach File
															</jspb:link>
		                        </td>
		                        <td>|</td>
		                        <td valign="middle">
		                        	<img align="middle" src="<%= context %>/images/edit.gif">
		                          <jspb:link href="/edit/message.jsp">
		                          	<jspb:param name="id"><%= msg.getField("id") %></jspb:param>
																Edit
															</jspb:link>
		                        </td>
		                        <td>|</td>
		                        <td valign="middle">
		                        	<img align="middle" src="<%= context %>/images/delete.gif">
		                          <jspb:link href="/delete/message">
		                          	<jspb:param name="id"><%= msg.getField("id") %></jspb:param>
		                          	<jspb:param name="redirect">/view/thread.jsp?id=<%= thread.getField("id") %></jspb:param>
																Delete
															</jspb:link>
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
                      <td width="400">
                      	<%= file.getField("description") %>
				                <jspb:secure person='<%= msg.getField("owner") %>' groups="messageadmin">
													<jspb:link href="/edit/file.jsp">
														<jspb:param name="id"><%= file.getField("id") %></jspb:param>
														[Edit name]
													</jspb:link>
	                      </jspb:secure>
				              </td>
                      <td align="right" width="158">
                      	<jspb:link href='<%= "/files/"+file.getField("name").replaceAll(" ","%20") %>'>
                      		<jspb:param name="id"><%= file.getField("id") %></jspb:param>
													<%= file.getField("name") %>
												</jspb:link>
                      </td>
                      <td>
				                <jspb:secure person='<%= msg.getField("owner") %>' groups="messageadmin">
	                        <jspb:link href="/delete/file">
	                        	<jspb:param name="id"><%= file.getField("id") %></jspb:param>
	                        	<jspb:param name="redirect">/view/thread.jsp?id=<%= thread.getField("id") %></jspb:param>
	                        	<img src="<%= context %>/images/delete.gif" alt="delete" align="middle">
	                        </jspb:link>
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
	          <jspb:form action="/add/message" method="post" enctype="multipart/form-data">
	          	<input type="hidden" name="thread" value='<%= thread.getField("id") %>'>
	          	<input type="hidden" name="redirect" value='/view/thread.jsp?id=<%= thread.getField("id") %>'>
	          	<table>
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
	              		<input type="checkbox" name="sendemail" value="true"> Email this message out to the committee.
	              	</td>
	              </tr>
	            	<tr>
	                <td colspan="2">
	                  <textarea name="content" rows="15" cols="60"></textarea>
	                </td>
	              </tr>
	              <tr>
	                <td align="center" colspan="2">
	                	<input type="submit" value="Add">
	                </td>
	              </tr>
	            </table>
	          </jspb:form>
	        </td>
	      </tr>
	    </jspb:secure>
    </table>
	</jspb:includes>
</jspb:SelectThread>
