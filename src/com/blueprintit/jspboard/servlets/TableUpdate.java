package com.blueprintit.jspboard.servlets;

import java.util.Map;
import java.util.Iterator;

public abstract class TableUpdate extends TableModify
{
	protected String generateQuery(String id, Map updates)
	{
		if (id!=null)
		{
			StringBuffer query = new StringBuffer("UPDATE "+getTable()+" SET ");
			Iterator loop = updates.keySet().iterator();
			String field;
			while (loop.hasNext())
			{
				field=loop.next().toString();
				query.append(field+"="+updates.get(field));
				if (loop.hasNext())
				{
					query.append(",");
				}
			}
			query.append(" WHERE id="+id+";");
			return query.toString();
		}
		else
		{
			throw new IllegalArgumentException("id field must be specified");
		}
	}
}
