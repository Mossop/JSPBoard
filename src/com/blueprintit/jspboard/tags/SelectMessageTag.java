package com.blueprintit.jspboard.tags;

public class SelectMessageTag extends DBResultTag
{
	private String thread;
	private String owner;
	private String order = "created";
	
	public void setOrder(String value)
	{
		order=value;
	}
	
	public String getOrder()
	{
		return order;
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
		where.insert(0,"SELECT * FROM Message");
		where.append(" ORDER BY "+order+";");
		return where.toString();
	}
}
