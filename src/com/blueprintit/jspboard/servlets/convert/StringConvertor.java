package com.blueprintit.jspboard.servlets.convert;

public class StringConvertor extends Convertor
{
	private StringConvertor()
	{
		super();
	}
	
	public String convert(String value)
	{
		return "'"+value+"'";
	}
	
	public boolean validate(String value)
	{
		return true;
	}
	
	private static StringConvertor instance = null;
	
	static Convertor getInstance()
	{
		if (instance==null)
		{
			instance = new StringConvertor();
		}
		return instance;
	}
}
