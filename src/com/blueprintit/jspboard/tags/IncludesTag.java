package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import com.blueprintit.jspboard.Manager;

public class IncludesTag extends TagSupport
{
	public int doStartTag()
	{
		try
		{
			String user = ((HttpServletRequest)pageContext.getRequest()).getRemoteUser();
			Manager manager = (Manager)pageContext.findAttribute("jspboard.Manager");
			if ((user!=null)&&(manager!=null))
			{
				manager.setUsername(user);
			}
			pageContext.include("/include/header.jsp");
			pageContext.setAttribute("context",((HttpServletRequest)pageContext.getRequest()).getContextPath());
			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("IncludesTag: Exception - "+e.getMessage(),e);
			System.out.println("BURRBLE");
			return SKIP_BODY;
		}
	}
	
	public int doEndTag()
	{
		try
		{
			pageContext.include("/include/footer.jsp");
			return EVAL_PAGE;
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("IncludesTag: Exception",e);
			return SKIP_PAGE;
		}
	}
}
