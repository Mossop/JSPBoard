package com.blueprintit.jspboard.servlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.io.File;
import com.blueprintit.jspboard.servlets.convert.Convertor;

public class FolderAdd extends TableAdd
{
	private String newDirectory;

	protected String generateQuery(Connection conn, Map updates, HttpServletRequest request)
	{
		try
		{
			String parent = (String)updates.get("parent");
			ResultSet results = conn.createStatement().executeQuery("SELECT directory FROM Folder WHERE id="+parent+";");
			String name = (String)updates.get("name");
			name=name.substring(1,name.length()-1);
			results.next();
			name=name.replace('/','_');
			newDirectory=results.getString(1)+name+"/";
			Convertor conv = (Convertor)fields.get("directory");
			updates.put("directory",conv.convert(newDirectory));
		}
		catch (Exception e)
		{
		}
		return super.generateQuery(conn,updates,request);
	}

	protected void postModification(Connection conn, Map updates, HttpServletRequest request)
	{
		String rep = getServletContext().getInitParameter("jspboard.Repository");
		(new File(rep+newDirectory)).mkdir();
	}

	protected boolean allowQuery(Connection conn, Map fields, HttpServletRequest request)
	{
		if ((request.isUserInRole("admin"))||(request.isUserInRole("boardadmin")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	protected String getTable()
	{
		return "Folder";
	}
}
