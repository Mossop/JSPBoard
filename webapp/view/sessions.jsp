<%@ page import="com.blueprintit.jspboard.ContextManager, com.blueprintit.jspboard.Manager, java.util.Iterator, java.util.Date, java.text.SimpleDateFormat" %>
<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	<table border="0">
    <tr>
      <td valign="top" colspan="4" width="578">
        <h1>WSWYM Bulletin Board Sessions</h1>
      </td>
    </tr>
    <tr>
      <td>
        <b>User</b>
      </td>
      <td>
        <b>Created</b>
      </td>
      <td>
        <b>Last access</b>
      </td>
      <td></td>
    </tr>
    <%
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    	ContextManager handler = (ContextManager)pageContext.findAttribute("jspboard.ContextManager");
    	Iterator loop = handler.getManagers().iterator();
    	while (loop.hasNext())
    	{
    		Manager manager = (Manager)loop.next();
    %>
    	<tr>
    		<td>
    			<%= manager.getUsername() %>
    		</td>
    		<td>
    			<%= format.format(new Date(manager.getSession().getCreationTime())) %>
    		</td>
    		<td>
    			<%= format.format(new Date(manager.getSession().getLastAccessedTime())) %>
    		</td>
    		<td>
    			<%
    				if (!manager.getSession().getId().equals(session.getId()))
    				{
    			%>
   					Invalidate
    			<%
    				}
    			%>
    		</td>
    	</tr>
    <%
    	}
    %>
  </table>
</jspb:includes>
