package com.blueprintit.jspboard.tags;

public class SelectCategoryTag extends DBResultTag
{
	private String order = "description";
	
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
		if (where.length()>0)
		{
			where.delete(where.length()-5,where.length());
			where.insert(0," WHERE ");
		}
		where.insert(0,"SELECT * FROM Category");
		where.append(" ORDER BY "+order+";");
		return where.toString();
	}
}
