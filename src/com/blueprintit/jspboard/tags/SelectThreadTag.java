package com.blueprintit.jspboard.tags;

public class SelectThreadTag extends DBResultTag
{
	private String folder;
	private String owner;
	private String order = "created";
	private String countunread;
	
	public void setOrder(String value)
	{
		order=value;
	}
	
	public String getOrder()
	{
		return order;
	}
	
	public void setFolder(String value)
	{
		folder=value;
	}
	
	public String getFolder()
	{
		return folder;
	}

	public void setCountUnread(String value)
	{
		countunread=value;
	}
	
	public String getCountUnread()
	{
		return countunread;
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
		if (countunread!=null)
		{
			where.append("Thread.id=Message.thread AND ");
		}
		if (id!=null)
		{
			where.append("Thread.id="+id+" AND ");
		}
		if (folder!=null)
		{
			where.append("Thread.folder="+folder+" AND ");
		}
		if (owner!=null)
		{
			where.append("Thread.owner="+owner+" AND ");
		}
		if (where.length()>0)
		{
			where.delete(where.length()-5,where.length());
			where.insert(0," WHERE ");
		}
		if (countunread!=null)
		{
			where.insert(0,"SELECT Thread.*,COUNT(person) AS unreadcount FROM Thread,Message LEFT JOIN UnreadMessage ON UnreadMessage.message=Message.id AND UnreadMessage.person="+countunread);
			where.append(" GROUP BY "+order+";");
		}
		else
		{
			where.insert(0,"SELECT * FROM Thread");
			where.append(" ORDER BY "+order+";");
		}
		return where.toString();
	}
}
