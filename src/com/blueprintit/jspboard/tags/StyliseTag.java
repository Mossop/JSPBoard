package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class StyliseTag extends BodyTagSupport
{
	public int doAfterBody()
	{
		try
		{
			String body = getBodyContent().getString();
			getPreviousOut().print(body);
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("WindowTag: Exception",e);
		}
		return SKIP_BODY;
	}
}
