package com.blueprintit.jspboard.tags;

public class SelectPersonTag extends DBResultTag
{
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
		where.insert(0,"SELECT * FROM Person");
		where.append(";");
		return where.toString();
	}
}
