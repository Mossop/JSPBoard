package com.blueprintit.jspboard;

import java.util.StringTokenizer;
import java.io.StringReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Stylise
{
	private static String strip(String line)
	{
		if (line!=null)
		{
			StringBuffer real = new StringBuffer(line);
			while ((real.length()>0)&&(Character.isWhitespace(real.charAt(0))))
			{
				real.delete(0,1);
			}
			while ((real.length()>0)&&(Character.isWhitespace(real.charAt(real.length()-1))))
			{
				real.delete(real.length()-1,real.length());
			}
			return real.toString();
		}
		else
		{
			return null;
		}
	}
	
	private static String linkEmail(String line)
	{
		Pattern urlreg = Pattern.compile("((.*\\s)|(^))([\\w\\.]*@[\\w\\.]*)(($)|(\\s.*$))");
		Matcher match = urlreg.matcher(line);
		if (match.matches())
		{
			return linkEmail(match.group(1))+"<a href=\"mailto:"+match.group(4)+"\">"+match.group(4)+"</a>"+linkEmail(match.group(5));
		}
		else
		{
			return line;
		}
	}
	
	private static String linkUrl(String line)
	{
		Pattern urlreg = Pattern.compile("((.*\\s)|(^))(https?://[\\w\\./:\\?=&#~]*)(($)|(\\s.*$))");
		Matcher match = urlreg.matcher(line);
		if (match.matches())
		{
			return linkUrl(match.group(1))+"<a href=\""+match.group(4)+"\" target=\"_blank\">"+match.group(4)+"</a>"+linkUrl(match.group(5));
		}
		else
		{
			return linkEmail(line);
		}
	}
	
	private static String applySmiley(String smiley, String line, String img)
	{
		if (line.length()>=smiley.length())
		{
			Pattern smlreg = Pattern.compile("((.*\\s)|(^))(\\Q"+smiley+"\\E)((\\s.*$)|($))",Pattern.CASE_INSENSITIVE);
			Matcher match = smlreg.matcher(line);
			if (match.matches())
			{
				return applySmiley(smiley,match.group(1),img)+"<img src=\"../images/smileys/"+img+"\" alt=\""+smiley+"\" title=\""+smiley+"\">"+applySmiley(smiley,match.group(5),img);
			}
			else
			{
				return line;
			}
		}
		else
		{
			return line;
		}
	}
	
	private static String applySmilies(String line)
	{
		line=applySmiley(":)",line,"01.gif");
		line=applySmiley(":(",line,"02.gif");
		line=applySmiley(";)",line,"03.gif");
		line=applySmiley(":D",line,"04.gif");
		line=applySmiley(";;)",line,"05.gif");
		line=applySmiley(":-/",line,"06.gif");
		line=applySmiley(":x",line,"07.gif");
		line=applySmiley(":\">",line,"08.gif");
		line=applySmiley(":P",line,"09.gif");
		line=applySmiley(":-*",line,"10.gif");
		line=applySmiley(":O",line,"11.gif");
		line=applySmiley("X-(",line,"12.gif");
		line=applySmiley(":>",line,"13.gif");
		line=applySmiley("B-)",line,"14.gif");
		line=applySmiley(":-S",line,"15.gif");
		line=applySmiley(">:)",line,"16.gif");
		line=applySmiley(":((",line,"17.gif");
		line=applySmiley(":))",line,"18.gif");
		line=applySmiley(":|",line,"19.gif");
		line=applySmiley("/:)",line,"20.gif");
		line=applySmiley("0:)",line,"21.gif");
		line=applySmiley(":-B",line,"22.gif");
		line=applySmiley("=;",line,"23.gif");
		line=applySmiley("I-)",line,"24.gif");
		line=applySmiley("8-|",line,"25.gif");
		line=applySmiley(":-&",line,"26.gif");
		line=applySmiley(":-$",line,"27.gif");
		line=applySmiley("[-(",line,"28.gif");
		line=applySmiley(":o)",line,"29.gif");
		line=applySmiley("8-}",line,"30.gif");
		line=applySmiley("(:|",line,"31.gif");
		line=applySmiley("=P~",line,"32.gif");
		line=applySmiley(":-?",line,"33.gif");
		line=applySmiley("#-o",line,"34.gif");
		line=applySmiley("=D>",line,"35.gif");
		line=applySmiley(":@)",line,"36.gif");
		line=applySmiley("3:-O",line,"37.gif");
		line=applySmiley(":(|)",line,"38.gif");
		line=applySmiley("~:>",line,"39.gif");
		line=applySmiley("@};-",line,"40.gif");
		line=applySmiley("%%-",line,"41.gif");
		line=applySmiley("**==",line,"42.gif");
		line=applySmiley("(~~)",line,"43.gif");
		line=applySmiley("~o)",line,"44.gif");
		line=applySmiley("*-:)",line,"45.gif");
		line=applySmiley("8-X",line,"46.gif");
		line=applySmiley("=:)",line,"47.gif");
		line=applySmiley(">-)",line,"48.gif");
		line=applySmiley(":-L",line,"49.gif");
		line=applySmiley("<):)",line,"50.gif");
		line=applySmiley("[-o<",line,"51.gif");
		line=applySmiley("@-)",line,"52.gif");
		line=applySmiley("$-)",line,"53.gif");
		line=applySmiley(":-\"",line,"54.gif");
		line=applySmiley(":^o",line,"55.gif");
		line=applySmiley("b-(",line,"56.gif");
		line=applySmiley(":)>-",line,"57.gif");
		line=applySmiley("[-X",line,"58.gif");
		line=applySmiley("\\:D/",line,"59.gif");
		line=applySmiley(">:D<",line,"60.gif");
		return line;
	}
	
	private static String doUBBC(String full)
	{
		Pattern ubbcreg = Pattern.compile("^(.*?)(\\[(.*)(=(.*?))?\\])(.*?)(\\[/\\3\\])(.*)$",Pattern.DOTALL);
		Matcher match = ubbcreg.matcher(full);
		if (match.matches())
		{
			String start = doUBBC(match.group(1));
			String end = doUBBC(match.group(8));
			String tag = match.group(3);
			String value = match.group(5);
			if (tag.equalsIgnoreCase("b"))
			{
				return start+"<b>"+doUBBC(match.group(6))+"</b>"+end;
			}
			else if (tag.equalsIgnoreCase("i"))
			{
				return start+"<i>"+doUBBC(match.group(6))+"</i>"+end;
			}
			else if (tag.equalsIgnoreCase("u"))
			{
				return start+"<u>"+doUBBC(match.group(6))+"</u>"+end;
			}
			else if (tag.equalsIgnoreCase("img"))
			{
				return start+"<img src=\""+match.group(6)+"\" alt=\"image\">"+end;
			}
			else
			{
				return start+doUBBC(match.group(6))+end;
			}
		}
		else
		{
			return full;
		}
	}
	
	private static String styleFull(String full, boolean email)
	{
		return doUBBC(full);
	}
	
	private static String styleLine(String line, boolean email)
	{
		if (!email)
		{
			return applySmilies(linkUrl(line));
		}
		else
		{
			return linkUrl(line);
		}
	}
	
	public static String styliseWebPage(String full)
	{
		return stylise(full,false);
	}
	
	public static String styliseEmail(String full)
	{
		return stylise(full,true);
	}
	
	private static String stylise(String full, boolean email)
	{
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader in = new BufferedReader(new StringReader(styleFull(full,email)));
			String line = strip(in.readLine());
			while ((line!=null)&&(line.length()==0))
			{
				line=strip(in.readLine());
			}
			int count=0;
			while (line!=null)
			{
				line=styleLine(line,email);
				buffer.append(line);
				buffer.append("<br>\n");
				line=strip(in.readLine());
				while ((line!=null)&&(line.length()==0))
				{
					count++;
					line=strip(in.readLine());
				}
				if (line!=null)
				{
					while (count>0)
					{
						buffer.append("<br>\n");
						count--;
					}
				}
			}
		}
		catch (IOException e)
		{
		}
		return buffer.toString();
	}
}
