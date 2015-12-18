package us.glos.mi.domain;

import org.glc.IJSONEnabled;
import org.glc.utils.HTMLUtilities;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.ModAdminParam;

public class ModelInfoWrapper implements IJSONEnabled {
	private ModelInfo model=null;
	public ModelInfoWrapper(ModelInfo mod)
	{
		this.model=mod;
	}
	@Override
	public String ToJSON() {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		if(this.model!=null)
		{
			ModAdminParam modparam=new ModAdminParam();
			sb.append('{');
			
			sb.append(String.format("\"%s\":",modparam.getModIdParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(String.format("%d", model.getId()))));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",modparam.getModNameParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(model.getName())));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",modparam.getModVerNoParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(model.getVersionNo())));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",modparam.getModDescParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(model.getDescription())));
			sb.append('"');
						
			sb.append('}');
		}
		return sb.toString();
	}

}
