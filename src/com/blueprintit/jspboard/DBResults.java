package com.blueprintit.jspboard;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;

public class DBResults
{
	private ResultSet results;
	private ResultSetMetaData metadata;
	private Map fields = new HashMap();
	private Map tablefields = new HashMap();
	private int pos;
	private Object lock;

	public DBResults(ResultSet r, Object maker) throws SQLException
	{
		results=r;
		metadata=r.getMetaData();
		for (int loop=1; loop<=metadata.getColumnCount(); loop++)
		{
			Integer thisone = new Integer(loop);
			fields.put(metadata.getColumnName(loop),thisone);
			tablefields.put(metadata.getTableName(loop)+"."+metadata.getColumnName(loop),thisone);
		}
		pos=0;
		lock=maker;
	}
	
	public boolean isNull(String field)
	{
		int col = findField(field);
		if (col>=0)
		{
			try
			{
				String ans = results.getString(col);
				return results.wasNull();
			}
			catch (Exception e)
			{
			}
		}
		return true;
	}
	
	public String getField(String field)
	{
		int col = findField(field);
		if (col>=0)
		{
			try
			{
				String ans = results.getString(col);
				if (results.wasNull())
				{
					return "";
				}
				else
				{
					return ans;
				}
			}
			catch (Exception e)
			{
			}
		}
		return "";
	}
	
	public int findField(String field)
	{
		try
		{
			int col = Integer.parseInt(field);
			return col;
		}
		catch (NumberFormatException e)
		{
			if (field.indexOf(".")>=0)
			{
				Integer result = (Integer)tablefields.get(field);
				if (result!=null)
				{
					return result.intValue();
				}
			}
			else
			{
				Integer result = (Integer)fields.get(field);
				if (result!=null)
				{
					return result.intValue();
				}
			}
		}
		return -1;
	}

	public boolean next(Object caller) throws SQLException
	{
		if (caller==lock)
		{
			return results.next();
		}
		else
		{
			return false;
		}
	}
}
