<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectFile var="file" id='<%= request.getParameter("id") %>'>
	<jspb:SelectMessage var="msg" id='<%= file.getField("message") %>'>
		<jspb:includes>
	  	<h1>Editing file</h1>
			<form action="<%= context %>/update/file" method="post">
				<input type="hidden" name="id" value='<%= file.getField("id") %>'>
				<input type="hidden" name="redirect" value='/view/thread.jsp?id=<%= msg.getField("thread") %>'>
		    <table>
		      <tr>
		        <td>
		          Description:
		        </td>
		        <td>
		        	<input name="description" value='<%= file.getField("description") %>'>
		        </td>
		      </tr>
		      <tr>
						<td colspan="2" align="center">
	         		<input type="submit" value="Update">
		        </td>
	        </tr>
		    </table>
		  </form>
		</jspb:includes>
	</jspb:SelectMessage>
</jspb:SelectFile>
