package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.VariableInfo;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public abstract class DBTableTEI extends TagExtraInfo
{
	public abstract String getTable();
	
	public VariableInfo[] getVariableInfo(TagData data)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JSPBoard","JSPBoard","jspb379");
			ResultSetMetaData meta = conn.createStatement().executeQuery("SELECT * FROM "+getTable()+" WHERE 0=1;").getMetaData();
			VariableInfo[] vars = new VariableInfo[meta.getColumnCount()];
			for (int loop=0; loop<meta.getColumnCount(); loop++)
			{
				vars[loop] = new VariableInfo(meta.getColumnName(loop+1),"java.lang.String",true,VariableInfo.NESTED);
			}
			return vars;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
