package com.blueprintit.jspboard.tags;

public class SelectLoginTag extends DBResultTag
{
	private String order = "id";
	private String person;
	
	public void setPerson(String value)
	{
		person=value;
	}
	
	public String getPerson()
	{
		return person;
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
			where.append("id='"+id+"' AND ");
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
		where.insert(0,"SELECT * FROM Login");
		where.append(" ORDER BY "+order+";");
		return where.toString();
	}
}
