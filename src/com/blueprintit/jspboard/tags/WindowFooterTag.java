package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class WindowFooterTag extends BodyTagSupport
{
	public int doAfterBody()
	{
		try
		{
			WindowTag window = (WindowTag)getParent();
			window.setFooter(getBodyContent().getString());
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("WindowTag: Exception",e);
		}
		return SKIP_BODY;
	}
}
