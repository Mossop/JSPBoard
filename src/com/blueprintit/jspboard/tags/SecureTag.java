package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;

public class SecureTag extends TagSupport
{
	private String groups;
	
	public int doStartTag()
	{
		try
		{
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			if (request.isUserInRole(groups))
			{
				return EVAL_BODY_INCLUDE;
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
	
	public void setGroups(String value)
	{
		groups=value;
	}
	
	public String getGroups()
	{
		return groups;
	}
}
