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
import com.blueprintit.jspboard.servlets.convert.Convertor;

public abstract class TableModify extends HttpServlet
{
	private Map fields;
	
	private void prepareFields(ResultSetMetaData data) throws Exception
	{
		fields = new HashMap();
		for (int loop=1; loop<=data.getColumnCount(); loop++)
		{
			String type = data.getColumnTypeName(loop);
			fields.put(data.getColumnName(loop),Convertor.getConvertor(data.getColumnTypeName(loop)));
		}
	}
	
	protected abstract String getTable();
	
	protected abstract String generateQuery(String id, Map updates);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		try
		{
			Connection conn = (Connection)request.getSession().getAttribute("jspboard.DBConnection");
			prepareFields(conn.createStatement().executeQuery("SELECT * FROM "+getTable()+" WHERE 0=1;").getMetaData());
			Enumeration loop = request.getParameterNames();
			Map updates = new HashMap();
			boolean valid=true;
			Convertor conv;
			while (loop.hasMoreElements())
			{
				String param = loop.nextElement().toString();
				if ((!param.equals("id"))&&(!param.equals("redirect"))&&(!param.equals("error")))
				{
					conv = (Convertor)fields.get(param);
					if ((conv!=null)&&(conv.validate(request.getParameter(param))))
					{
						updates.put(param,conv.convert(request.getParameter(param)));
					}
					else
					{
						valid=false;
					}
				}
			}
			String id = request.getParameter("id");
			String redirect = request.getParameter("redirect");
			if ((redirect!=null)&&(valid))
			{
				if (!updates.isEmpty())
				{
					conn.createStatement().executeUpdate(generateQuery(id,updates));
				}
				request.getRequestDispatcher(redirect).forward(request,response);
			}
			else
			{
				String error = request.getParameter("error");
				if (error!=null)
				{
					request.getRequestDispatcher(error).forward(request,response);
				}
				else
				{
					throw new IllegalArgumentException("Invalid parameters passed");
				}
			}
		}
		catch (Exception e)
		{
			log("Exception",e);
			throw new ServletException("Failed trying to update database",e);
		}
	}
}
