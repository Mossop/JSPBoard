package com.blueprintit.jspboard;

public class FolderInfo
{
	private int id;
	private int parent;
	private String name;
	private int depth;
	
	public FolderInfo(String id, String parent, String name, int depth)
	{
		try
		{
			this.id=Integer.parseInt(id);
		}
		catch (Exception e)
		{
		}
		try
		{
			this.parent=Integer.parseInt(parent);
		}
		catch (Exception e)
		{
		}
		this.name=name;
		this.depth=depth;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getParent()
	{
		return parent;
	}
}
