package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormTag extends TagSupport
{
	private String action;
	private String method;
	
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String value)
	{
		action=value;
	}
	
	public String getMethod()
	{
		return method;
	}
	
	public void setMethod(String value)
	{
		method=value;
	}
	
	public int doStartTag()
	{
		try
		{
			if (!action.equals("j_security_check"))
			{
				action=((HttpServletRequest)pageContext.getRequest()).getContextPath()+action;
			}
			String url = ((HttpServletResponse)pageContext.getResponse()).encodeURL(action);
			pageContext.getOut().print("<form action=\""+url+"\"");
			if (method!=null)
			{
				pageContext.getOut().print(" method=\""+method+"\">");
			}
			else
			{
				pageContext.getOut().print(" method=\"POST\">");
			}
			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e)
		{
			return SKIP_BODY;
		}
	}
	
	public int doEndTag()
	{
		try
		{
			pageContext.getOut().print("</form>");
			return EVAL_PAGE;
		}
		catch (Exception e)
		{
			return SKIP_PAGE;
		}
	}
}
