package com.blueprintit.jspboard.tags;

public class SelectFolderTag extends DBResultTag
{
	private String parent;
	
	public void setParentId(String value)
	{
		parent=value;
	}
	
	public String getParentId()
	{
		return parent;
	}

	public String generateQuery()
	{
		StringBuffer where = new StringBuffer();
		if (id!=null)
		{
			where.append("id="+id+" AND ");
		}
		if (parent!=null)
		{
			where.append("parent="+parent+" AND ");
		}
		if (where.length()>0)
		{
			where.delete(where.length()-5,where.length());
			where.insert(0," WHERE ");
		}
		where.insert(0,"SELECT * FROM Folder");
		where.append(";");
		return where.toString();
	}
}
