package us.glos.mi.util;

import java.util.List;
import java.util.ArrayList;

public class Converter {
	public static List<String> ToStringList(int[] ids)
	{
		if(ids!=null)
		{
			ArrayList<String> list=new ArrayList<String>();
			for(int i:ids)
			{
				list.add(Integer.toString(i));
			}
			return list;
		}
		else
			return null;
	}
}
