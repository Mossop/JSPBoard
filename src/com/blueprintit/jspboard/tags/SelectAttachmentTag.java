package com.blueprintit.jspboard.tags;

public class SelectAttachmentTag extends DBResultTag
{
	private String order = "filename";
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
		StringBuffer where = new StringBuffer("WHERE Attachment.message=Message.id AND Message.thread=Thread.id AND Thread.folder=Folder.id");
		if (message!=null)
		{
			where.append(" AND Attachment.message="+message);
		}
		where.insert(0,"SELECT Attachment.*,directory FROM Attachment,Message,Thread,Folder ");
		where.append(" ORDER BY "+order+";");
		return where.toString();
	}
}
