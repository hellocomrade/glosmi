package us.glos.mi.domain;

import java.util.ArrayList;

import org.glc.IDateFormater;
import org.glc.utils.HTMLUtilities;

import us.glos.mi.util.DataTableParamParser.DataTableParams;

public class DraftDataTableWrapper extends DataTableWrapper<ArrayList<ModelInfo>> {

	private IDateFormater formatter;
	private DataTableParams dp;
	public DraftDataTableWrapper(ArrayList<ModelInfo> o, DataTableParams dp,IDateFormater formatter) {
		super(o, dp);
		// TODO Auto-generated constructor stub
		this.formatter=formatter;
		this.dp=dp;
	}
	@Override
	protected void fillData(StringBuilder sb) {
		// TODO Auto-generated method stub
		int extra=dp.getExtraCols();
		for(ModelInfo mi:this.obj)
		{
			sb.append("[");
			
			sb.append(String.format("%d,",mi.getSerialId()));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(mi.getName()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(mi.getDescription()))));
			//sb.append(String.format("\"%s\",", HTMLUtilities.filterDisplay(mi.getName())));
     		if(this.formatter!=null)
			    sb.append(String.format("\"%s\",", HTMLUtilities.filterDisplay(this.formatter.format(mi.getLastUpdateDate()))));
			else
				sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(mi.getLastUpdateDate().toString()))));
			String foo=this.formatter.format(mi.getLastUpdateDate());
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(mi.getOwnerEmail()))));
			sb.append(String.format("\"%s\"", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(mi.getOwnerName()))));
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
