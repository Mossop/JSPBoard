package com.blueprintit.jspboard.tags;

public class DBQueryTag extends DBResultTag
{
	private String query;
	
	public void setQuery(String value)
	{
		query=value;
	}
	
	public String getQuery()
	{
		return query;
	}

	public String generateQuery()
	{
		return query;
	}
}
