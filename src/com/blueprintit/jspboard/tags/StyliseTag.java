package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
	
	private String linkEmail(String line)
	{
		Pattern urlreg = Pattern.compile("((.*\\s)|(^))([\\w\\.]*@[\\w\\.]*)(($)|(\\s.*$))");
		Matcher match = urlreg.matcher(line);
		if (match.matches())
		{
			return linkEmail(match.group(1))+"<a href=\"mailto:"+match.group(4)+"\">"+match.group(4)+"</a>"+linkEmail(match.group(5));
		}
		else
		{
			return line;
		}
	}
	
	private String linkUrl(String line)
	{
		Pattern urlreg = Pattern.compile("((.*\\s)|(^))((https?)|(ftp)://[\\w\\./]*)(($)|(\\s.*$))");
		Matcher match = urlreg.matcher(line);
		if (match.matches())
		{
			return linkUrl(match.group(1))+"<a href=\""+match.group(4)+"\">"+match.group(4)+"</a>"+linkUrl(match.group(7));
		}
		else
		{
			return linkEmail(line);
		}
	}
	
	private String style(String line)
	{
		return linkUrl(line);
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
				line=style(line);
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
