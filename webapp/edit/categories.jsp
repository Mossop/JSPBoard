<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
	<h1>Editing Categories</h1>
	<table>
		<jspb:SelectCategory var="category">
			<tr>
				<form action="<%= context %>/update/category" method="post">
					<input type="hidden" name="id" value='<%= category.getField("id") %>'>
					<input type="hidden" name="redirect" value="/edit/categories.jsp">
					<td>
						<input name="description" value='<%= category.getField("description") %>'>
					</td>
					<td>
						<input type="submit" value="Rename">
					</td>
				</form>
				<form action="<%= context %>/delete/category" method="post">
					<input type="hidden" name="id" value='<%= category.getField("id") %>'>
					<input type="hidden" name="redirect" value="/edit/categories.jsp">
					<td>
						<input type="submit" value="Delete">
					</td>
				</form>
			</tr>
		</jspb:SelectCategory>
		<form action="<%= context %>/add/category" method="post">
			<input type="hidden" name="redirect" value="/edit/categories.jsp">
			<tr>
				<td>
					<input name="description">
				</td>
				<td colspan="2">
					<input type="submit" value="Create">
				</td>
			</tr>
		</form>
	</table>
</jspb:includes>
