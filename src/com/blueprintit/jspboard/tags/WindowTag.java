package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class WindowTag extends BodyTagSupport
{
	private String header;
	private String footer;
	private String bordersize = "2";
	
	public String getBorderSize()
	{
		return bordersize;
	}
	
	public void setBorderSize(String value)
	{
		bordersize=value;
	}
	
	public void setHeader(String value)
	{
		header=value;
	}
	
	public void setFooter(String value)
	{
		footer=value;
	}
	
	public int doAfterBody()
	{
		try
		{
			int width = Integer.parseInt(bordersize);
			int height = Integer.parseInt(bordersize);
      getPreviousOut().println("    <table width=\"578\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("        <td rowspan=\"8\" class=\"shadelight\" width=\""+width+"\"></td>");
      getPreviousOut().println("        <td colspan=\"5\" class=\"shadelight\" height=\""+height+"\"></td>");
      getPreviousOut().println("        <td class=\"lightdark\" width=\""+width+"\" height=\""+height+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("        <td rowspan=\"7\" class=\"shadenormal\" width=\""+width+"\"></td>");
      getPreviousOut().println("        <td colspan=\"3\" class=\"shadenormal\" height=\""+height+"\"></td>");
      getPreviousOut().println("        <td rowspan=\"7\" class=\"shadenormal\" width=\""+width+"\"></td>");
      getPreviousOut().println("        <td rowspan=\"8\" class=\"shadedark\" width=\""+width+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("      	<td rowspan=\"2\" class=\"shadenormal\" width=\""+width+"\"></td>");
      getPreviousOut().println("        <td class=\"windowheader\">");
      getPreviousOut().println(header);
      getPreviousOut().println("        </td>");
      getPreviousOut().println("        <td class=\"shadenormal\" rowspan=\"2\" width=\""+width+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("      	<td class=\"shadenormal\" height=\""+height+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("       	<td rowspan=\"2\" class=\"shadedark\" width=\""+width+"\"></td>");
      getPreviousOut().println("     		<td class=\"shadedark\" height=\""+height+"\"></td>");
      getPreviousOut().println("        <td class=\"darklight\" width=\""+width+"\" height=\""+height+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("        <td class=\"windowbody\">");
      getPreviousOut().println(footer);
      getPreviousOut().println("        </td>");
      getPreviousOut().println("        <td rowspan=\"2\" class=\"shadelight\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("        <td class=\"darklight\" width=\""+width+"\" height=\""+height+"\"></td>");
      getPreviousOut().println("        <td class=\"shadelight\" height=\""+height+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("        <td colspan=\"3\" class=\"shadenormal\" height=\""+height+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("      <tr>");
      getPreviousOut().println("        <td class=\"lightdark\" width=\""+width+"\" height=\""+height+"\"></td>");
      getPreviousOut().println("        <td colspan=\"5\" class=\"shadedark\" height=\""+height+"\"></td>");
      getPreviousOut().println("      </tr>");
      getPreviousOut().println("    </table>");
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("WindowTag: Exception",e);
		}
		return SKIP_BODY;
	}
}
