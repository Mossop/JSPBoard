package com.blueprintit.jspboard.servlets;

import java.util.Map;
import java.util.Iterator;

public abstract class TableAdd extends TableModify
{
	protected String generateQuery(Map updates)
	{
		StringBuffer query = new StringBuffer("INSERT INTO "+getTable());
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		Iterator loop = updates.keySet().iterator();
		String field;
		while (loop.hasNext())
		{
			field=loop.next().toString();
			fields.append(field);
			values.append(updates.get(field));
			if (loop.hasNext())
			{
				fields.append(",");
				values.append(",");
			}
		}
		query.append("("+fields+") VALUES ("+values+");");
		return query.toString();
	}
}
