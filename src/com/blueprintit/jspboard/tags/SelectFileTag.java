package com.blueprintit.jspboard.tags;

public class SelectFileTag extends DBResultTag
{
	private String order = "name";
	private String message;
	
	public void setMessage(String value)
	{
		message=value;
	}
	
	public String getMessage()
	{
		return message;
	}
		
	public void setOrder(String value)
	{
		order=value;
	}
	
	public String getOrder()
	{
		return order;
	}

	public String generateQuery()
	{
		StringBuffer where = new StringBuffer();
		if (id!=null)
		{
			where.append("id="+id+" AND ");
		}
		if (message!=null)
		{
			where.append("message="+message+" AND ");
		}
		if (where.length()>0)
		{
			where.delete(where.length()-5,where.length());
			where.insert(0," WHERE ");
		}
		where.insert(0,"SELECT * FROM File");
		where.append(" ORDER BY "+order+";");
		return where.toString();
	}
}
