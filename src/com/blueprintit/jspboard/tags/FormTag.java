package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormTag extends TagSupport
{
	private String action;
	private String method;
	private String enctype;
	private String onsubmit;
	private String name;
	
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String value)
	{
		action=value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String value)
	{
		name=value;
	}
	
	public String getOnSubmit()
	{
		return onsubmit;
	}
	
	public void setOnSubmit(String value)
	{
		onsubmit=value;
	}
	
	public String getEnctype()
	{
		return enctype;
	}
	
	public void setEnctype(String value)
	{
		enctype=value;
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
			if (enctype!=null)
			{
				pageContext.getOut().print(" enctype=\""+enctype+"\"");
			}
			if (name!=null)
			{
				pageContext.getOut().print(" name=\""+name+"\"");
			}
			if (onsubmit!=null)
			{
				pageContext.getOut().print(" onSubmit=\""+onsubmit+"\"");
			}
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
