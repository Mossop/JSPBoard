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
          <a href='<%= context %>/edit/thread.jsp?id=<%= thread.getField("id") %>'>
						Administration
					</a>
        </td>
      </tr>
      <tr>
      	<td align="center" colspan="2" width="578">
      		<jspb:SelectMessage var="msg" thread='<%= thread.getField("id") %>'>
						<jspb:window>
							<jspb:header>
	              <table width="100%">
	                <tr>
	                  <td align="left"><img src="<%= context %>/images/read.gif" align="middle" alt="read">
											<jspb:SelectPerson var="person" id='<%= msg.getField("owner") %>'>
												Posted by <%= person.getField("nickname") %>
											</jspb:SelectPerson>
										</td>
	                  <td align="right"><%= msg.getField("created") %></td>
	                </tr>
	                <tr>
	                  <td colspan="2">
	                    <table>
	                      <tr>
	                        <td valign="middle">
	                        	<img align="middle" src="<%= context %>/images/paperclip.gif">
	                          <a href="">
															Attach File
														</a>
	                        </td>
	                        <td>|</td>
	                        <td valign="middle">
	                        	<img align="middle" src="<%= context %>/images/edit.gif">
	                          <a href="">
															Edit
														</a>
	                        </td>
	                        <td>|</td>
	                        <td valign="middle">
	                        	<img align="middle" src="<%= context %>/images/delete.gif">
	                          <a href="">
															Delete
														</a>
	                        </td>
	                      </tr>
	                    </table>
	                  </td>
	                </tr>
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
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <hr>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <h2>Add a new reply to this thread:</h2>
          <form action="xdf.php" method="post">
          	<table>
            	<tr>
                <td>
                  <textarea name="content1" rows="15" cols="60"></textarea>
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
    </table>
	</jspb:includes>
</jspb:SelectThread>
