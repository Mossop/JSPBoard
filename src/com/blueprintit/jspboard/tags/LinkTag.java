package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LinkTag extends BodyTagSupport
{
	private String href;
	private String name;
	private String style;
	private StringBuffer params = new StringBuffer();
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String value)
	{
		name=value;
	}
	
	public String getStyle()
	{
		return style;
	}
	
	public void setStyle(String value)
	{
		style=value;
	}
	
	public String getHref()
	{
		return href;
	}
	
	public void setHref(String value)
	{
		href = value;
	}
	
	public void addParam(String name, String value)
	{
		if (params.length()>0)
		{
			params.append("&");
		}
		else
		{
			params.append("?");
		}
		params.append(name);
		params.append("=");
		params.append(value.replaceAll("%","%25").replaceAll("=","%3d").replaceAll("&","%26").replaceAll("\\?","%3f").replaceAll("\\+","%2b").replaceAll(" ","+"));
	}
	
	public int doAfterBody() throws JspException
	{
		String context = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
		String url = context+href+params;
		if (name!=null)
		{
			url=url+"#"+name;
		}
		url = ((HttpServletResponse)pageContext.getResponse()).encodeURL(url);
		try
		{
			getPreviousOut().print("<a href=\""+url+"\"");
			if (style!=null)
			{
				getPreviousOut().print(" class=\""+style+"\"");
			}
			getPreviousOut().print(">");
			getBodyContent().writeOut(getPreviousOut());
			getPreviousOut().print("</a>");
			params = new StringBuffer();
			return SKIP_BODY;
		}
		catch (Exception e)
		{
			throw new JspException("Problem in LinkTag",e);
		}
	}
}
