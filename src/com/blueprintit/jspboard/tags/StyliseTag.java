package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;
import com.blueprintit.jspboard.Stylise;

public class StyliseTag extends BodyTagSupport
{
	public int doAfterBody()
	{
		try
		{
			getPreviousOut().println(Stylise.styliseWebPage(getBodyContent().getString()));
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("StyliseTag: Exception",e);
		}
		return SKIP_BODY;
	}
}
