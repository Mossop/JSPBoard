package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.StringTokenizer;
import java.io.BufferedReader;

public class StyliseTag extends BodyTagSupport
{
	private String strip(String line)
	{
		if (line!=null)
		{
			StringBuffer real = new StringBuffer(line);
			while ((real.length()>0)&&(Character.isWhitespace(real.charAt(0))))
			{
				real.delete(0,1);
			}
			while ((real.length()>0)&&(Character.isWhitespace(real.charAt(real.length()-1))))
			{
				real.delete(real.length()-1,real.length());
			}
			return real.toString();
		}
		else
		{
			return null;
		}
	}
	
	public int doAfterBody()
	{
		try
		{
			BufferedReader in = new BufferedReader(getBodyContent().getReader());
			String line = strip(in.readLine());
			while ((line!=null)&&(line.length()==0))
			{
				line=strip(in.readLine());
			}
			int count=0;
			while (line!=null)
			{
				getPreviousOut().println(line+"<br>");
				line=strip(in.readLine());
				while ((line!=null)&&(line.length()==0))
				{
					count++;
					line=strip(in.readLine());
				}
				if (line!=null)
				{
					while (count>0)
					{
						getPreviousOut().println("<br>");
						count--;
					}
				}
			}
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("WindowTag: Exception",e);
		}
		return SKIP_BODY;
	}
}
