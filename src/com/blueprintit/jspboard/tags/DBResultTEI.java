package com.blueprintit.jspboard.tags;

import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;
import javax.servlet.jsp.tagext.TagData;

public class DBResultTEI extends TagExtraInfo
{
	public VariableInfo[] getVariableInfo(TagData data)
	{
		Object varname = data.getAttribute("var");
		VariableInfo[] results;
		if ((varname!=null)&&(varname!=TagData.REQUEST_TIME_VALUE))
		{
			results = new VariableInfo[1];
			results[0] = new VariableInfo(varname.toString(),"com.blueprintit.jspboard.DBResults",true,VariableInfo.NESTED);
		}
		else
		{
			results = new VariableInfo[0];
		}
		return results;
	}
}
