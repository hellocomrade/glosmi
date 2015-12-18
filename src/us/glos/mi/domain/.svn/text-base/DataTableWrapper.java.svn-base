package us.glos.mi.domain;

import org.glc.IJSONEnabled;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

public abstract class DataTableWrapper<T> implements IJSONEnabled {

	protected T obj=null;
	private DataTableParams param=null;
	public DataTableWrapper(T o,DataTableParams dp)
	{
		this.obj=o;
		this.param=dp;
	}
	@Override
	public String ToJSON() {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		if(this.obj!=null&&this.param!=null)
		{
			sb.append("{");
			sb.append("\"sEcho\":");
			sb.append(param.getsEcho());
			sb.append(",");
			sb.append("\"iTotalRecords\":");
			sb.append(param.getTotalRecords());
			sb.append(",");
			sb.append("\"iTotalDisplayRecords\":");
			sb.append(param.getTotalFiltered());
			sb.append(",");
			sb.append("\"aaData\":[");
			fillData(sb);
			sb.append("]}");
			
		}
		return sb.toString();
	}
	protected void fillData(StringBuilder sb)
	{
		return;
	}
	
}
