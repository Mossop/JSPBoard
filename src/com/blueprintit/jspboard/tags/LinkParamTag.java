package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class LinkParamTag extends BodyTagSupport
{
	private String name;
	
	public void setName(String value)
	{
		name=value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int doAfterBody()
	{
		try
		{
			Tag test = getParent();
			if (test instanceof LinkTag)
			{
				LinkTag link = (LinkTag)test;
				link.addParam(name,getBodyContent().getString());
			}
			else if (test instanceof FormTag)
			{
				getPreviousOut().print("<input type=\"hidden\" name=\""+name+"\" value=\"");
				getBodyContent().writeOut(getPreviousOut());
				getPreviousOut().print("\">");
			}
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("LinkParamTag: Exception",e);
		}
		return SKIP_BODY;
	}
}
