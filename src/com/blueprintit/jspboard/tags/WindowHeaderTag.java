package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class WindowHeaderTag extends BodyTagSupport
{
	public int doAfterBody()
	{
		try
		{
			WindowTag window = (WindowTag)getParent();
			window.setHeader(getBodyContent().getString());
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("WindowTag: Exception",e);
		}
		return SKIP_BODY;
	}
}
