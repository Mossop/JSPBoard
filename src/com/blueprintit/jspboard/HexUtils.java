package com.blueprintit.jspboard;

public final class HexUtils
{
	/**
   * Convert a byte array into a printable format containing a
   * String of hexadecimal digit characters (two per byte).
   *
   * @param bytes Byte array representation
   */
  public static String convert(byte bytes[])
	{
		StringBuffer sb = new StringBuffer(bytes.length * 2);
    for (int i = 0; i < bytes.length; i++)
		{
    	sb.append(convertDigit((int) (bytes[i] >> 4)));
      sb.append(convertDigit((int) (bytes[i] & 0x0f)));
    }
    return (sb.toString());
	}

	private static char convertDigit(int value)
	{
		value &= 0x0f;
    if (value >= 10)
   		return ((char) (value - 10 + 'a'));
    else
      return ((char) (value + '0'));
	}
}
