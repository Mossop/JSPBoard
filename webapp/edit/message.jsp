<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectMessage var="msg" id='<%= request.getParameter("id") %>'>
	<jspb:includes>
  	<h1>Editing message</h1>
		<form action="<%= context %>/update/message" method="post">
			<input type="hidden" name="id" value='<%= msg.getField("id") %>'>
			<input type="hidden" name="redirect" value='/view/thread.jsp?id=<%= msg.getField("thread") %>'>
	    <table>
	      <tr>
	        <td>
	          <textarea name="content" rows="15" cols="60"><%= msg.getField("content") %></textarea>
	        </td>
	      </tr>
	      <tr>
					<td align="center">
         		<input type="submit" value="Update">
	        </td>
        </tr>
	    </table>
	  </form>
	</jspb:includes>
</jspb:SelectMessage>
