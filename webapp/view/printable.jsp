<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<html>
<jspb:SelectThread var="thread" id='<%= request.getParameter("id") %>'>
<head>
  <title><%= thread.getField("name") %></title>
  </head>
  <body>
    <h1>Messages in the thread "<%= thread.getField("name") %>"</h1>
      		<jspb:SelectMessage var="msg" thread='<%= thread.getField("id") %>'>
	              <table width="100%">
	                <tr>
	                	<td align="left" width="15">
		                		<img src="/iee/images/read.gif" align="middle" alt="read">
	                	</td>
	                  <td align="left">
											<jspb:SelectPerson var="person" id='<%= msg.getField("owner") %>'>
												<b>Posted by <%= person.getField("displayname") %></b>
											</jspb:SelectPerson>
										</td>
	                  <td align="right"><b><%= msg.getDate("created") %></b></td>
	                </tr>
	                <jspb:SelectEdits var="edit" message='<%= msg.getField("id") %>'>
	                	<tr>
	                		<td></td>
	                		<td>
												<jspb:SelectPerson var="person" id='<%= edit.getField("person") %>'>
													<b>Edited by <%= person.getField("displayname") %></b>
												</jspb:SelectPerson>
	                		</td>
	                		<td align="right">
	                			<b><%= edit.getDate("altered") %></b>
	                		</td>
	                	</tr>
	                </jspb:SelectEdits>
	              </table>
	            	<jspb:stylise>
		            	<%= msg.getField("content") %>
		            </jspb:stylise>
	          <hr>
	        </jspb:SelectMessage>
	</body>
</jspb:SelectThread>
</html>
