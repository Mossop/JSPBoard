package com.blueprintit.jspboard.tags;

import java.sql.ResultSet;
import java.sql.Connection;
import com.blueprintit.jspboard.Manager;

public class SelectMessageTag extends DBResultTag
{
	private String thread;
	private String owner;
	private String order = "created";
	private String checkunread;
	private String lister;
	
	public void setOrder(String value)
	{
		order=value;
	}
	
	public String getOrder()
	{
		return order;
	}
	
	public void setCheckUnread(String value)
	{
		checkunread=value;
	}
	
	public String getCheckUnread()
	{
		return checkunread;
	}
	
	public void setThread(String value)
	{
		thread=value;
	}
	
	public String getThread()
	{
		return thread;
	}

	public void setOwner(String value)
	{
		owner=value;
	}
	
	public String getOwner()
	{
		return owner;
	}

	public String generateQuery()
	{
		StringBuffer where = new StringBuffer();
		if (id!=null)
		{
			where.append("id="+id+" AND ");
		}
		if (thread!=null)
		{
			where.append("thread="+thread+" AND ");
		}
		if (owner!=null)
		{
			where.append("owner="+owner+" AND ");
		}
		if (where.length()>0)
		{
			where.delete(where.length()-5,where.length());
			where.insert(0," WHERE ");
		}
		if (checkunread!=null)
		{
			StringBuffer lister = new StringBuffer(where.toString());
			lister.insert(0,"SELECT id FROM Message");
			lister.append(";");
			this.lister=lister.toString();
			where.insert(0,"SELECT Message.*,count(person) AS unread FROM Message LEFT JOIN UnreadMessage ON message=id AND person="+checkunread);
			where.append(" GROUP BY id ORDER BY "+order+";");
		}
		else
		{
			where.insert(0,"SELECT * FROM Message");
			where.append(" ORDER BY "+order+";");
		}
		return where.toString();
	}

	public int doAfterBody()
	{
		int result = super.doAfterBody();
		if ((result==SKIP_BODY)&&(checkunread!=null))
		{
			try
			{
				Connection conn = ((Manager)pageContext.findAttribute("jspboard.Manager")).getDBConnection();
				ResultSet msgs = conn.createStatement().executeQuery(lister);
				while (msgs.next())
				{
					conn.createStatement().executeUpdate("DELETE FROM UnreadMessage WHERE person="+checkunread+" AND message="+msgs.getString(1)+";");
				}
			}
			catch (Exception e)
			{
			}
		}
		return result;
	}
}
