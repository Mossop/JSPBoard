package com.blueprintit.jspboard.tags;

public class SelectThreadTag extends DBResultTag
{
	private String folder;
	private String owner;
	
	public void setFolder(String value)
	{
		folder=value;
	}
	
	public String getFolder()
	{
		return folder;
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
		if (folder!=null)
		{
			where.append("folder="+folder+" AND ");
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
		where.insert(0,"SELECT * FROM Thread");
		where.append(";");
		return where.toString();
	}
}
