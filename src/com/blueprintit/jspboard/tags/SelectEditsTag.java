package com.blueprintit.jspboard.tags;

public class SelectEditsTag extends DBResultTag
{
	private String order = "altered";
	private String message;
	private String person;
	
	public void setPerson(String value)
	{
		person=value;
	}
	
	public String getPerson()
	{
		return person;
	}
	
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
		if (message!=null)
		{
			where.append("message="+message+" AND ");
		}
		if (person!=null)
		{
			where.append("person="+person+" AND ");
		}
		if (where.length()>0)
		{
			where.delete(where.length()-5,where.length());
			where.insert(0," WHERE ");
		}
		where.insert(0,"SELECT * FROM EditedMessage");
		where.append(" ORDER BY "+order+";");
		return where.toString();
	}
}
