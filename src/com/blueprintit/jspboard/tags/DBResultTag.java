package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.Tag;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.HashMap;

public abstract class DBResultTag extends TagSupport
{
	private ResultSet results;
	private ResultSetMetaData metadata;
	private Map fields = new HashMap();
	private Map tablefields = new HashMap();
	private int pos;
	
	public abstract String generateQuery();

	public ResultSet getResults()
	{
		return results;
	}
	
	public ResultSetMetaData getMetaData()
	{
		return metadata;
	}
	
	public int getPos()
	{
		return pos;
	}
	
	public String getField(String field)
	{
		int col = findField(field);
		if (col>=0)
		{
			try
			{
				return results.getString(col);
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
	
	public static DBResultTag findResults(Tag target, String id)
	{
		if (target==null)
		{
			return null;
		}
		if (target instanceof DBResultTag)
		{
			DBResultTag result = (DBResultTag)target;
			if ((id==null)||(result.getId().equals(id)))
			{
				return result;
			}
		}
		return findResults(target.getParent(),id);
	}
	
	public int doStartTag()
	{
		try
		{
			String query = generateQuery();
			Connection conn = (Connection)pageContext.findAttribute("jspboard.DBConnection");
			if (conn!=null)
			{
				results = conn.createStatement().executeQuery(query);
				metadata=results.getMetaData();
				if (results.next())
				{
					for (int loop=1; loop<=metadata.getColumnCount(); loop++)
					{
						Integer thisone = new Integer(loop);
						fields.put(metadata.getColumnName(loop),thisone);
						tablefields.put(metadata.getTableName(loop)+"."+metadata.getColumnName(loop),thisone);
					}
					pos=0;
					return EVAL_BODY_INCLUDE;
				}
				else
				{
					return SKIP_BODY;
				}
			}
			else
			{
				return SKIP_BODY;
			}
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("DBResultTag: Exception",e);
			return SKIP_BODY;
		}
	}
	
	public int doAfterBody()
	{
		try
		{
			if (results.next())
			{
				pos++;
				return EVAL_BODY_AGAIN;
			}
			else
			{
				return SKIP_BODY;
			}
		}
		catch (Exception e)
		{
			return SKIP_BODY;
		}
	}
}
