package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.Tag;

public class SelectFolderTag extends DBResultTag
{
	private String parentid;
	private String order = "name";
	
	public void setOrder(String value)
	{
		order=value;
	}
	
	public String getOrder()
	{
		return order;
	}
	
	public void setParentId(String value)
	{
		parentid=value;
	}
	
	public String getParentId()
	{
		return parentid;
	}
	
	public String generateQuery()
	{
		StringBuffer where = new StringBuffer();
		if (id!=null)
		{
			where.append("id="+id+" AND ");
		}
		if (parentid!=null)
		{
			where.append("parent="+parentid+" AND ");
		}
		if (where.length()>0)
		{
			where.delete(where.length()-5,where.length());
			where.insert(0," WHERE ");
		}
		where.insert(0,"SELECT * FROM Folder");
		where.append(" ORDER BY "+order+";");
		return where.toString();
	}
}
