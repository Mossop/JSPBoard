package com.blueprintit.jspboard.servlets.convert;

public class IntegerConvertor extends Convertor
{
	private IntegerConvertor()
	{
		super();
	}
	
	public String convert(String value)
	{
		if (validate(value))
		{
			return ""+value;
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
	
	private static IntegerConvertor instance;
	
	static Convertor getInstance()
	{
		if (instance==null)
		{
			instance = new IntegerConvertor();
		}
		return instance;
	}
}
