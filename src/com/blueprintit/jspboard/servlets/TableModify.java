package com.blueprintit.jspboard.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import com.blueprintit.jspboard.servlets.convert.Convertor;
import com.blueprintit.jspboard.RequestMultiplex;
import com.blueprintit.jspboard.Manager;
import com.blueprintit.jspboard.ContextManager;

public abstract class TableModify extends HttpServlet
{
	protected Map fields;
	
	private void prepareFields() throws Exception
	{
		Connection conn = ((ContextManager)getServletContext().getAttribute("jsboard.ContextManager")).getConnection();
		prepareFields(conn);
		conn.close();
	}
	
	protected void prepareFields(Connection conn) throws Exception
	{
		ResultSetMetaData data = conn.createStatement().executeQuery("SELECT * FROM "+getTable()+" WHERE 0=1;").getMetaData();
		fields = new HashMap();
		for (int loop=1; loop<=data.getColumnCount(); loop++)
		{
			String type = data.getColumnTypeName(loop);
			fields.put(data.getColumnName(loop),Convertor.getConvertor(data.getColumnTypeName(loop)));
		}
	}
	
	public void init() throws ServletException
	{
		try
		{
			prepareFields();
		}
		catch (Exception e)
		{
			throw new ServletException("Error preparing fields",e);
		}
	}
	
	protected abstract String getTable();
	
	protected boolean allowQuery(Connection conn, Map updates, HttpServletRequest request)
	{
		return true;
	}
	
	protected abstract String generateQuery(Connection conn, Map updates, HttpServletRequest request);
	
	protected void postModification(Connection conn, Map updates, HttpServletRequest request, ResultSet keys) throws SQLException
	{
		postModification(conn,updates,request);
	}
	
	protected void postModification(Connection conn, Map updates, HttpServletRequest request) throws SQLException
	{
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException
	{
		try
		{
			RequestMultiplex request = new RequestMultiplex(req,getServletContext());
			Connection conn = ((Manager)request.getSession().getAttribute("jspboard.Manager")).getDBConnection();
			Enumeration loop = request.getParameterNames();
			Map updates = new HashMap();
			boolean valid=true;
			Convertor conv;
			while (loop.hasMoreElements())
			{
				String param = loop.nextElement().toString();
				if ((!param.equals("redirect"))&&(!param.equals("error")))
				{
					conv = (Convertor)fields.get(param);
					if (conv!=null)
					{
						if (conv.validate(request.getParameter(param)))
						{
							updates.put(param,conv.convert(request.getParameter(param)));
						}
						else
						{
							valid=false;
						}
					}
					else
					{
						updates.put(param,request.getParameter(param));
					}
				}
			}
			String redirect = request.getParameter("redirect");
			if ((redirect!=null)&&(valid))
			{
				if ((!updates.isEmpty())&&(allowQuery(conn,updates,request)))
				{
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(generateQuery(conn,new HashMap(updates),request));
					postModification(conn,updates,request,stmt.getGeneratedKeys());
				}
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+redirect));
			}
			else
			{
				String error = request.getParameter("error");
				if (error!=null)
				{
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+error));
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
