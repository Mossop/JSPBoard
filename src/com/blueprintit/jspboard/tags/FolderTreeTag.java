package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.SQLException;
import com.blueprintit.jspboard.DBResults;
import com.blueprintit.jspboard.FolderInfo;

public class FolderTreeTag extends TagSupport
{
	private String rootfolder;
	private String ignore = "-1";
	private Iterator loop;
	private Connection conn;
	private int maxdepth;
	private List folders;

	public void setIgnore(String value)
	{
		ignore=value;
	}
	
	public String getIgnore()
	{
		return ignore;
	}
		
	public void setRootFolder(String value)
	{
		rootfolder=value;
	}
	
	public String getRootFolder()
	{
		return rootfolder;
	}
			
	private void scanFolder(int id, int depth) throws SQLException
	{
		DBResults results = new DBResults(conn.createStatement().executeQuery("SELECT * FROM Folder WHERE parent="+id+" ORDER BY name;"),this);
		if (results.next(this))
		{
			if (depth>maxdepth)
			{
				maxdepth=depth;
			}
			do
			{
				FolderInfo info = new FolderInfo(results.getField("id"),results.getField("parent"),results.getField("name"),depth);
				if (!String.valueOf(info.getId()).equals(ignore))
				{
					folders.add(info);
					scanFolder(info.getId(),depth+1);
				}
			} while (results.next(this));
		}
	}
	
	private void apply(FolderInfo info)
	{
		pageContext.setAttribute("name",info.getName());
		pageContext.setAttribute("id",String.valueOf(info.getId()));
		pageContext.setAttribute("depth",String.valueOf(info.getDepth()));
	}
	
	public int doStartTag()
	{
		try
		{
			conn = (Connection)pageContext.findAttribute("jspboard.DBConnection");
			folders = new LinkedList();
			maxdepth=0;
			if (rootfolder==null)
			{
				scanFolder(-1,0);
			}
			else
			{
				DBResults results = new DBResults(conn.createStatement().executeQuery("SELECT * FROM Folder WHERE id="+rootfolder+";"),this);
				if (results.next(this))
				{
					FolderInfo root = new FolderInfo(results.getField("id"),results.getField("parent"),results.getField("name"),1);
					if (!String.valueOf(root.getId()).equals(ignore))
					{
						folders.add(root);
						scanFolder(root.getId(),1);
					}
				}
			}
			loop=folders.iterator();
			if (loop.hasNext())
			{
				FolderInfo info = (FolderInfo)loop.next();
				pageContext.setAttribute("maxdepth",String.valueOf(maxdepth));
				apply(info);
				return EVAL_BODY_INCLUDE;
			}
			else
			{
				return SKIP_BODY;
			}
		}
		catch (Exception e)
		{
			pageContext.getServletContext().log("FolderTreeTag: Exception",e);
			return SKIP_BODY;
		}
	}
	
	public int doAfterBody()
	{
		if (loop.hasNext())
		{
			FolderInfo info = (FolderInfo)loop.next();
			apply(info);
			return EVAL_BODY_AGAIN;
		}
		else
		{
			return SKIP_BODY;
		}
	}
	
	public int doEndTag()
	{
		pageContext.removeAttribute("name");
		pageContext.removeAttribute("id");
		pageContext.removeAttribute("depth");
		pageContext.removeAttribute("maxdepth");
		return EVAL_PAGE;
	}
}
