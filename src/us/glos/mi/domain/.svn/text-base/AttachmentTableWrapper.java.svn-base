package us.glos.mi.domain;

import java.util.ArrayList;

import org.glc.IDateFormater;
import org.glc.utils.HTMLUtilities;

import us.glos.mi.domain.Attachment;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

public class AttachmentTableWrapper extends DataTableWrapper<ArrayList<Attachment>> {

	private IDateFormater formatter;
	private DataTableParams dp;
	public AttachmentTableWrapper(ArrayList<Attachment> o, DataTableParams dp,IDateFormater formatter) {
		super(o, dp);
		// TODO Auto-generated constructor stub
		this.formatter=formatter;
		this.dp=dp;
	}
	@Override
	protected void fillData(StringBuilder sb) {
		// TODO Auto-generated method stub
		int extra=dp.getExtraCols();
		for(Attachment att:this.obj)
		{
			sb.append("[");
			
			sb.append(String.format("%d,",att.getId()));
			sb.append(String.format("%d,",att.getTypeId()));
			sb.append(String.format("%d,",att.getReferenceId()));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(att.getDescription()))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(att.getPath()))));
			
     		if(this.formatter!=null)
			    sb.append(String.format("\"%s\",", HTMLUtilities.filterDisplay(this.formatter.format(att.getLastUpdateDate()))));
			else
				sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(att.getLastUpdateDate().toString()))));
     		sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(att.isChecked()?"Checked":"Unchecked"))));
			sb.append(String.format("\"%s\",", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(att.getUser().getEmail()))));
			sb.append(String.format("\"%s %s\"", HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(att.getUser().getLastName())),
												 HTMLUtilities.JSONStringify(HTMLUtilities.filterDisplay(att.getUser().getFirstName()))));
			
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
