package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public abstract class TableUpdate extends HttpServlet
{
	private static final int STRING = 1;
	private static final int INTEGER = 2;
	private static final int DATETIME = 3;

	private Map fields;
	
	protected abstract String getTable();
	
	private boolean makeUpdate(StringBuffer buffer, String param, String value)
	{
		Object test = fields.get(param);
		if (test!=null)
		{
			int type = ((Integer)test).intValue();
			if (type==STRING)
			{
				buffer.append(param+"='"+value+"'");
				return true;
			}
			else if (type==INTEGER)
			{
				try
				{
					Integer.parseInt(value);
					buffer.append(param+"="+value);
					return true;
				}
				catch (Exception e)
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	private void prepareFields(ResultSetMetaData data) throws Exception
	{
		fields = new HashMap();
		for (int loop=1; loop<=data.getColumnCount(); loop++)
		{
			String type = data.getColumnTypeName(loop);
			if (type.equals("VARCHAR"))
			{
				fields.put(data.getColumnName(loop),new Integer(STRING));
			}
			else if (type.equals("LONG"))
			{
				fields.put(data.getColumnName(loop),new Integer(INTEGER));
			}
			else if (type.equals("DATETIME"))
			{
				fields.put(data.getColumnName(loop),new Integer(DATETIME));
			}
			else if (type.equals("BLOB"))
			{
				fields.put(data.getColumnName(loop),new Integer(STRING));
			}
			else
			{
				throw new Exception("Unknown column type - "+type);
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			Connection conn = (Connection)request.getSession().getAttribute("jspboard.DBConnection");
			prepareFields(conn.createStatement().executeQuery("SELECT * FROM "+getTable()+" WHERE 0=1;").getMetaData());
			Enumeration loop = request.getParameterNames();
			StringBuffer updateset = new StringBuffer();
			while (loop.hasMoreElements())
			{
				String param = loop.nextElement().toString();
				if ((!param.equals("id"))&&(!param.equals("redirect"))&&(!param.equals("error")))
				{
					if (!makeUpdate(updateset,param,request.getParameter(param)))
					{
						throw new IllegalArgumentException(param);
					}
					if (loop.hasMoreElements())
					{
						updateset.append(", ");
					}
				}
			}
			String id = request.getParameter("id");
			String redirect = request.getParameter("redirect");
			if ((id!=null)&&(redirect!=null)&&(updateset.length()>0))
			{
				String query = "UPDATE "+getTable()+" SET "+updateset+" WHERE id="+id+";";
				conn.createStatement().executeUpdate(query);
				request.getRequestDispatcher(redirect).forward(request,response);
			}
			else
			{
				throw new Exception("Invalid information");
			}
		}
		catch (IllegalArgumentException e)
		{
			String redirect = request.getParameter("error");
			if (redirect!=null)
			{
				request.setAttribute("error-param",e.getMessage());
				try
				{
					request.getRequestDispatcher(redirect).forward(request,response);
				}
				catch (Exception d)
				{
					log("Error redirecting to error doc",d);
					throw new ServletException("Failed trying to update database",d);
				}
			}
			else
			{
				log("Error with param and no error doc given",e);
				throw new ServletException("Failed trying to update database",e);
			}
		}
		catch (Exception e)
		{
			log("Exception",e);
			throw new ServletException("Failed trying to update database",e);
		}
	}
}
