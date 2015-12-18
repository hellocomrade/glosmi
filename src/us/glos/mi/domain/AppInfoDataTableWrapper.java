package us.glos.mi.domain;

import java.util.ArrayList;

import org.glc.IDateFormater;
import org.glc.utils.HTMLUtilities;

import us.glos.mi.domain.AppInfo;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

public class AppInfoDataTableWrapper extends DataTableWrapper<ArrayList<AppInfo>> {
	private IDateFormater formatter;
	private DataTableParams dp;
	public AppInfoDataTableWrapper(ArrayList<AppInfo> o, DataTableParams dp,IDateFormater formatter) {
		super(o, dp);
		// TODO Auto-generated constructor stub
		this.formatter=formatter;
		this.dp=dp;
	}

	@Override
	protected void fillData(StringBuilder sb) {
		// TODO Auto-generated method stub
		int extra=dp.getExtraCols();
		for(AppInfo ai:this.obj)
		{
			sb.append("[");
			
			sb.append(String.format("%d,",ai.getId()));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(ai.getName()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(ai.getModelName()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(ai.getAppDescription()))));
			//sb.append(String.format("\"%s\",", HTMLUtilities.filterDisplay(mi.getName())));
			//sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(mi.getVersionNo()))));
			if(this.formatter!=null)
			    sb.append(String.format("\"%s\",", HTMLUtilities.filterDisplay(this.formatter.format(ai.getLastUpdateDate()))));
			else
				sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(ai.getLastUpdateDate().toString()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(ai.getStatus()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(ai.getOwnerEmail()))));
			sb.append(String.format("\"%s\"", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(ai.getOwnerName()))));
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
