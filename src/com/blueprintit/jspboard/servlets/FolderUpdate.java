package com.blueprintit.jspboard.servlets;

import com.blueprintit.jspboard.servlets.convert.Convertor;
import java.io.File;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderUpdate extends TableUpdate
{
	private String previousDirectory;
	private String newDirectory;

	protected String generateQuery(Connection conn, Map updates, HttpServletRequest request)
	{
		try
		{
			String id = (String)updates.get("id");
			ResultSet results = conn.createStatement().executeQuery("SELECT parent,directory,name FROM Folder where id="+id+";");
			results.next();
			previousDirectory=results.getString(2);
			String parent = (String)updates.get("parent");
			String name = (String)updates.get("name");
			if (parent==null)
			{
				parent=results.getString(1);
			}
			if (!parent.equals("-1"))
			{
				if (name==null)
				{
					name=results.getString(2);
				}
				else
				{
					name=name.substring(1,name.length()-1);
				}
				name=name.replace('/','_');
				results = conn.createStatement().executeQuery("SELECT directory FROM Folder where id="+parent+";");
				results.next();
				newDirectory=results.getString(1)+name+"/";
				Convertor conv = (Convertor)fields.get("directory");
				if (!newDirectory.equals(previousDirectory))
					updates.put("directory",conv.convert(newDirectory));
			}
			else
			{
				newDirectory=previousDirectory;
			}
		}
		catch (SQLException e)
		{
		}
		return super.generateQuery(conn,updates,request);
	}

	protected void postModification(Connection conn, Map updates, HttpServletRequest request) throws SQLException
	{
		if (!previousDirectory.equals(newDirectory))
		{
			conn.createStatement().executeUpdate("UPDATE Folder set directory=CONCAT('"+newDirectory+"',SUBSTRING(directory,"+(previousDirectory.length()+1)+")) WHERE SUBSTRING(directory,1,"+previousDirectory.length()+")='"+previousDirectory+"';");
			String rep = getServletContext().getInitParameter("jspboard.Repository");
			(new File(rep+previousDirectory)).renameTo(new File(rep+newDirectory));
		}
	}

	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		return ((request.isUserInRole("admin"))||(request.isUserInRole("boardadmin")));
	}
	
	protected String getTable()
	{
		return "Folder";
	}
}
