package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.Tag;
import java.sql.Connection;
import java.io.File;
import java.util.Iterator;
import java.util.Arrays;
import com.blueprintit.jspboard.Manager;
import com.blueprintit.jspboard.FileInfo;

public class SelectFileTag extends TagSupport
{
	private String var;
	private String folder;
	private Iterator it;
	
	public void setDirectory(String value)
	{
		folder=value;
	}

	public String getDirectory()
	{
		return folder;
	}

	public void setVar(String value)
	{
		var=value;
	}
	
	public String getVar()
	{
		return var;
	}
	
	public boolean selectNextFile()
	{
		if (it.hasNext())
		{
			File file = (File)it.next();
			if (!file.isFile())
			{
				return selectNextFile();
			}
			else
			{
				pageContext.setAttribute(var,new FileInfo(folder,file));
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	public int doStartTag()
	{
		try
		{
			String dir = pageContext.getServletContext().getInitParameter("jspboard.Repository")+folder;
			File base = new File(dir);
			it=Arrays.asList(base.listFiles()).iterator();
			if (selectNextFile())
			{
				return EVAL_BODY_INCLUDE;
			}
			else
			{
				return SKIP_BODY;
			}
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("SelectFileTag: Exception",e);
			return SKIP_BODY;
		}
	}
	
	public int doAfterBody()
	{
		if (selectNextFile())
		{
			return EVAL_BODY_AGAIN;
		}
		else
		{
			return SKIP_BODY;
 		}
	}
}
