package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import com.blueprintit.jspboard.DBResults;
import com.blueprintit.jspboard.Manager;
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
			
	private void scanFolder(String person, int id, int depth, boolean showrestricted) throws SQLException
	{
		String query = "SELECT * FROM Folder WHERE parent="+id+" ";
		if (!showrestricted)
		{
			query=query+"AND restricted=0 ";
		}
		query=query+"ORDER BY name;";
		DBResults results = new DBResults(conn.createStatement().executeQuery(query),this);
		if (results.next(this))
		{
			if (depth>maxdepth)
			{
				maxdepth=depth;
			}
			do
			{
				ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(Message.id) FROM Thread,Message,UnreadMessage WHERE Thread.id=Message.thread AND Message.id=UnreadMessage.message AND UnreadMessage.person="+person+" AND Thread.folder="+results.getField("id")+";");
				rs.next();
				FolderInfo info = new FolderInfo(results.getField("id"),results.getField("parent"),results.getField("name"),rs.getString(1),depth);
				if (!String.valueOf(info.getId()).equals(ignore))
				{
					folders.add(info);
					scanFolder(person,info.getId(),depth+1,showrestricted);
				}
			} while (results.next(this));
		}
	}
	
	private void apply(FolderInfo info)
	{
		pageContext.setAttribute("name",info.getName());
		pageContext.setAttribute("id",String.valueOf(info.getId()));
		pageContext.setAttribute("depth",String.valueOf(info.getDepth()));
		pageContext.setAttribute("unread",String.valueOf(info.getUnread()));
	}
	
	public int doStartTag()
	{
		try
		{
			boolean showrestricted=false;
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
      if (request.isUserInRole("admin"))
			{
				showrestricted=true;
			}
			Manager manager = (Manager)pageContext.findAttribute("jspboard.Manager");
			String user = manager.getUsername();
			conn = manager.getDBConnection();
			ResultSet rs = conn.createStatement().executeQuery("SELECT person FROM Login WHERE id='"+user+"';");
			rs.next();
			String person = rs.getString(1);
			folders = new LinkedList();
			maxdepth=0;
			if (rootfolder==null)
			{
				scanFolder(person,-1,0,showrestricted);
			}
			else
			{
				DBResults results = new DBResults(conn.createStatement().executeQuery("SELECT * FROM Folder WHERE id="+rootfolder+" ORDER BY name;"),this);
				if (results.next(this))
				{
					String parent = results.getField("parent");
					String name = results.getField("name");
					rs = conn.createStatement().executeQuery("SELECT COUNT(Message.id) FROM Thread,Message,UnreadMessage WHERE Thread.id=Message.thread AND Message.id=UnreadMessage.message AND UnreadMessage.person="+person+" AND Thread.folder="+rootfolder+";");
					rs.next();
					FolderInfo root = new FolderInfo(rootfolder,parent,name,rs.getString(1),1);
					if (!String.valueOf(root.getId()).equals(ignore))
					{
						folders.add(root);
						scanFolder(person,root.getId(),1,showrestricted);
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
