package com.blueprintit.jspboard;

import java.io.File;

public class FileInfo
{
	private String folder;
	private File file;

	public FileInfo(String folder, File file)
	{
		this.folder=folder;
		this.file=file;
	}

	public String getDirectory()
	{
		return folder;
	}

	public String getFilename()
	{
		return file.getName();
	}

	private String valueToString(double value)
	{
		String result = value+"";
		int pos = result.indexOf(".");
		if (pos>0)
		{
			return result.substring(0,Math.min(pos+3,result.length()));
		}
		else
		{
			return result;
		}
	}

	public String getFileSize()
	{
		long value = file.length();
		if (value>1024)
		{
			double fvalue = value/1024.0;
			if (fvalue>1024)
			{
				fvalue=fvalue/1024;
				return valueToString(fvalue)+" MB";
			}
			else
			{
				return valueToString(fvalue)+" KB";
			}
		}
		else
		{
			return value+" B";
		}
	}

	public String getFileType()
	{
		int pos = getFilename().lastIndexOf(".");
		if (pos>0)
		{
			String extension=getFilename().substring(pos+1).toLowerCase();
			if (extension.equals("jpeg"))
				return "image";
			if (extension.equals("jpg"))
				return "image";
			if (extension.equals("gif"))
				return "image";
			if (extension.equals("doc"))
				return "word";
			if (extension.equals("dot"))
				return "word";
			if (extension.equals("ppt"))
				return "powerpoint";
			if (extension.equals("xls"))
				return "excel";
			if (extension.equals("xlt"))
				return "excel";
			if (extension.equals("pdf"))
				return "acrobat";
			return "unknown";
		}
		else
		{
			return "unknown";
		}
	}
}
