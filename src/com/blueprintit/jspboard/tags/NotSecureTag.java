package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;
import java.sql.Connection;
import java.sql.ResultSet;
import com.blueprintit.jspboard.Manager;

public class NotSecureTag extends TagSupport
{
	private String groups;
	private String person;
	
	public int doStartTag()
	{
		try
		{
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			if (request.isUserInRole("admin"))
			{
				return SKIP_BODY;
			}
			if (groups!=null)
			{
				StringTokenizer tokens = new StringTokenizer(groups,",");
				while (tokens.hasMoreTokens())
				{
					String group = tokens.nextToken();
					if (request.isUserInRole(group))
					{
						return SKIP_BODY;
					}
				}
			}
			if (person!=null)
			{
				Connection conn = ((Manager)pageContext.findAttribute("jspboard.Manager")).getDBConnection();
				if (conn!=null)
				{
					ResultSet results = conn.createStatement().executeQuery("SELECT person FROM Login WHERE id='"+request.getRemoteUser()+"' AND person="+person+";");
					if (results.next())
					{
						return SKIP_BODY;
					}
				}
			}
			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("SecureTag: Exception",e);
			return SKIP_BODY;
		}
	}
	
	public void setPerson(String value)
	{
		person=value;
	}
	
	public String getPerson()
	{
		return person;
	}
	
	public void setGroups(String value)
	{
		groups=value;
	}
	
	public String getGroups()
	{
		return groups;
	}
}
