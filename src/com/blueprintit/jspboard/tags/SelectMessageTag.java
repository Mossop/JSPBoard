package com.blueprintit.jspboard.tags;

public class SelectMessageTag extends DBResultTag
{
	private String thread;
	private String owner;
	private String order = "created";
	private String checkunread;
	
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
}
