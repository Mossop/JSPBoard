package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.blueprintit.jspboard.Manager;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IncludesTag extends TagSupport
{
	public int doStartTag()
	{
		SimpleDateFormat httpdate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
		response.addHeader("Expires","Mon, 26 Jul 1997 05:00:00 GMT");
		response.addHeader("Last-Modified",httpdate.format(new Date())+" GMT");
		response.addHeader("Cache-Control","no-cache, must-revalidate");
		response.addHeader("Pragma","no-cache");
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
