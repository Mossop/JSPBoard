package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;

public class IncludesTag extends TagSupport
{
	public int doStartTag()
	{
		try
		{
			pageContext.getSession().setAttribute("jspboard.user",((HttpServletRequest)pageContext.getRequest()).getRemoteUser());
			pageContext.include("/include/header.jsp");
			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e)
		{
			return SKIP_BODY;
		}
	}
	
	public int getEndTag()
	{
		try
		{
			pageContext.include("/include/footer.jsp");
			return EVAL_PAGE;
		}
		catch (Exception e)
		{
			return SKIP_PAGE;
		}
	}
}
