package com.blueprintit.jspboard;

public class FolderInfo
{
	private int id;
	private int parent;
	private String name;
	private int depth;
	private int unread;
	
	public FolderInfo(String id, String parent, String name, String unread, int depth)
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
		try
		{
			this.unread=Integer.parseInt(unread);
		}
		catch (Exception e)
		{
		}
		this.name=name;
		this.depth=depth;
	}
	
	public int getUnread()
	{
		return unread;
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
