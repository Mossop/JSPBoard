package com.blueprintit.jspboard;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class RequestMultiplex extends HttpServletRequestWrapper
{
	private class EmptyEnumeration implements Enumeration
	{
		public boolean hasMoreElements()
		{
			return false;
		}
		
		public Object nextElement()
		{
			throw new NoSuchElementException("No more elements");
		}
	}
	
	private boolean multipart = false;
	private MultipartRequest mpr = null;
	private ServletContext context;
	
	public RequestMultiplex(HttpServletRequest request, ServletContext cntxt)
	{
		super(request);
		context=cntxt;
		String content = request.getHeader("content-type");
		if ((content!=null)&&(content.startsWith("multipart/form-data;")))
		{
			multipart=true;
			String path = context.getRealPath("/files");
			try
			{
				mpr = new MultipartRequest(request,path,10000000,new DefaultFileRenamePolicy());
			}
			catch (Exception e)
			{
			}
		}
	}
	
	public String getContentType(String name)
	{
		if (multipart)
		{
			return mpr.getContentType(name);
		}
		else
		{
			return null;
		}
	}

	public String getFilesystemName(String name)
	{
		if (multipart)
		{
			return mpr.getFilesystemName(name);
		}
		else
		{
			return null;
		}
	}

	public String getOriginalFileName(String name)
	{
		if (multipart)
		{
			return mpr.getOriginalFileName(name);
		}
		else
		{
			return null;
		}
	}

	public String getParameter(String name)
	{
		if (multipart)
		{
			return mpr.getParameter(name);
		}
		else
		{
			return super.getParameter(name);
		}
	}
	
	public Enumeration getParameterNames()
	{
		if (multipart)
		{
			return mpr.getParameterNames();
		}
		else
		{
			return super.getParameterNames();
		}
	} 
	
	public String[] getParameterValues(String name)
	{
		if (multipart)
		{
			return mpr.getParameterValues(name);
		}
		else
		{
			return super.getParameterValues(name);
		}
	}
	
	public Enumeration getFileNames()
	{
		if (multipart)
		{
			return mpr.getFileNames();
		}
		else
		{
			return new EmptyEnumeration();
		}
	}
	
	public File getFile(String name)
	{
		if (multipart)
		{
			return mpr.getFile(name);
		}
		else
		{
			return null;
		}
	}
}
