package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

public class SecureTag extends TagSupport
{
	private String groups;
	
	public int doStartTag()
	{
		try
		{
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			if (request.isUserInRole("admin"))
			{
				return EVAL_BODY_INCLUDE;
			}
			StringTokenizer tokens = new StringTokenizer(groups,",");
			while (tokens.hasMoreTokens())
			{
				String group = tokens.nextToken();
				if (request.isUserInRole(group))
				{
					return EVAL_BODY_INCLUDE;
				}
			}
			return SKIP_BODY;
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("SecureTag: Exception",e);
			return SKIP_BODY;
		}
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
