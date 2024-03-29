package com.blueprintit.jspboard.servlets.convert;

public abstract class Convertor
{
	public abstract String convert(String value);
	
	public abstract boolean validate(String value);
	
	public static Convertor getConvertor(String type)
	{
		if ((type.equals("VARCHAR"))||(type.equals("BLOB"))||(type.equals("TEXT")))
		{
			return StringConvertor.getInstance();
		}
		else if ((type.equals("LONG"))||(type.equals("TINY")))
		{
			return IntegerConvertor.getInstance();
		}
		else if (type.equals("DATETIME"))
		{
			return DateConvertor.getInstance();
		}
		else if (type.equals("MD5PASS"))
		{
			return MD5Convertor.getInstance();
		}
		else
		{
			throw new IllegalArgumentException("No convertor available for type "+type);
		}
	}
}
