package com.blueprintit.jspboard.servlets.convert;

public abstract class Convertor
{
	public abstract String convert(String value);
	
	public abstract boolean validate(String value);
	
	public static Convertor getConvertor(String type)
	{
		if ((type.equals("VARCHAR"))||(type.equals("BLOB")))
		{
			return StringConvertor.getInstance();
		}
		else if (type.equals("LONG"))
		{
			return IntegerConvertor.getInstance();
		}
		else if (type.equals("DATETIME"))
		{
			return DateConvertor.getInstance();
		}
		else
		{
			throw new IllegalArgumentException("No convertor available for type "+type);
		}
	}
}
