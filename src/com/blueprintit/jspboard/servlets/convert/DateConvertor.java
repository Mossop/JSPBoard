package com.blueprintit.jspboard.servlets.convert;

public class DateConvertor extends Convertor
{
	private DateConvertor()
	{
		super();
	}
	
	public String convert(String value)
	{
		if (validate(value))
		{
			return "'"+value+"'";
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	
	public boolean validate(String value)
	{
		return true;
	}
	
	private static DateConvertor instance;
	
	static Convertor getInstance()
	{
		if (instance==null)
		{
			instance = new DateConvertor();
		}
		return instance;
	}
}
