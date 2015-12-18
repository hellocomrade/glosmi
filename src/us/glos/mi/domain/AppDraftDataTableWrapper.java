package us.glos.mi.domain;

import java.util.ArrayList;

import org.glc.IDateFormater;
import org.glc.utils.HTMLUtilities;

import us.glos.mi.domain.AppInfo;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

public class AppDraftDataTableWrapper extends DataTableWrapper<ArrayList<AppInfo>> {
	private IDateFormater formatter;
	private DataTableParams dp;
	public AppDraftDataTableWrapper(ArrayList<AppInfo> o, DataTableParams dp,IDateFormater formatter) {
		super(o, dp);
		// TODO Auto-generated constructor stub
		this.formatter=formatter;
		this.dp=dp;
	}
	@Override
	protected void fillData(StringBuilder sb) {
		// TODO Auto-generated method stub
		int extra=dp.getExtraCols();
		for(AppInfo app:this.obj)
		{
			sb.append("[");
			
			sb.append(String.format("%d,",app.getSerialId()));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(app.getName()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(app.getModelName()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(app.getAppDescription()))));
     		if(this.formatter!=null)
			    sb.append(String.format("\"%s\",", HTMLUtilities.filterDisplay(this.formatter.format(app.getLastUpdateDate()))));
			else
				sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(app.getLastUpdateDate().toString()))));
			//String foo=this.formatter.format(app.getLastUpdateDate());
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(app.getOwnerEmail()))));
			sb.append(String.format("\"%s\"", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(app.getOwnerName()))));
			if(extra>0)
			{
				sb.append(",");
				for(int i=0;i<extra;++i)
				    sb.append("\"\",");
			    sb.deleteCharAt(sb.length()-1);
			}
			sb.append("],");
			
		}
		sb.deleteCharAt(sb.length()-1);
	}
}
