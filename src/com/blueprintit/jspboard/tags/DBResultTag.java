package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.Tag;
import java.sql.Connection;
import com.blueprintit.jspboard.DBResults;
import com.blueprintit.jspboard.Manager;

public abstract class DBResultTag extends TagSupport
{
	private DBResults results;
	protected String extra;
	private String var;
	
	public void setVar(String value)
	{
		var=value;
	}
	
	public String getVar()
	{
		return var;
	}
	
	public void setExtra(String value)
	{
		extra=value;
	}
	
	public String getExtra()
	{
		return extra;
	}

	public abstract String generateQuery();

	public DBResults getResults()
	{
		return results;
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
			if ((id==null)||(result.getVar().equals(id)))
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
			Connection conn = ((Manager)pageContext.findAttribute("jspboard.Manager")).getDBConnection();
			if (conn!=null)
			{
				results = new DBResults(conn.createStatement().executeQuery(query),this);
				if (results.next(this))
				{
					if (var!=null)
					{
						pageContext.setAttribute(var,results);
					}
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
			if (results.next(this))
			{
				return EVAL_BODY_AGAIN;
			}
			else
			{
				if (var!=null)
				{
					pageContext.removeAttribute(var);
				}
				return SKIP_BODY;
			}
		}
		catch (Exception e)
		{
			return SKIP_BODY;
		}
	}
}
