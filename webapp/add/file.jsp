<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:SelectMessage var="msg" id='<%= request.getParameter("id") %>'>
	<jspb:includes>
  	<h1>File upload:</h1>
  	<jspb:form action="/add/file" enctype="multipart/form-data">
			<input type="hidden" name="message" value='<%= msg.getField("id") %>'>
			<input type="hidden" name="redirect" value='/view/thread.jsp?id=<%= msg.getField("thread") %>'>
      <table>
        <tr>
          <td>Description:</td>
          <td>
            <input name="description" type="text">
          </td>
        </tr>
        <tr>
          <td>Send this file:</td>
          <td>
            <input name="file" type="file">
          </td>
        </tr>
        <tr>
          <td colspan="2" align="center">
            <input type="submit" value="Send File">
          </td>
        </tr>
      </table>
    </jspb:form>
	</jspb:includes>
</jspb:SelectMessage>
