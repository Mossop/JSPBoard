package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;

public class GetFieldTag extends TagSupport
{
	private String field;
	
	public void setField(String value)
	{
		field=value;
	}
	
	public String getField()
	{
		return field;
	}
	
	public int doStartTag()
	{
		DBResultTag tag = DBResultTag.findResults(this,id);
		if (tag!=null)
		{
			try
			{
				pageContext.getOut().print(tag.getField(field));
			}
			catch (Exception e)
			{
				pageContext.getServletContext().log("GetFieldTag: Exception",e);
			}
		}
		return SKIP_BODY;
	}
}
