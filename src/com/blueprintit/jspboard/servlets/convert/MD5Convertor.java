package com.blueprintit.jspboard.servlets.convert;

import java.security.MessageDigest;
import com.blueprintit.jspboard.HexUtils;

public class MD5Convertor extends Convertor
{
	private MD5Convertor()
	{
		super();
	}
	
	public String convert(String value)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			String result = HexUtils.convert(digest.digest(value.getBytes()));
			return "'"+result+"'";
		}
		catch (Exception e)
		{
			return "'"+value+"'";
		}
	}
	
	public boolean validate(String value)
	{
		return true;
	}
	
	private static MD5Convertor instance = null;
	
	static Convertor getInstance()
	{
		if (instance==null)
		{
			instance = new MD5Convertor();
		}
		return instance;
	}
}
