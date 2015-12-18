package us.glos.mi.domain;

import org.glc.IJSONEnabled;
import org.glc.utils.HTMLUtilities;

import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.AppAdminParam;
public class AppInfoWrapper implements IJSONEnabled {
	AppInfo app=null;
	public AppInfoWrapper(AppInfo app)
	{
		this.app=app;
	}
	@Override
	public String ToJSON() {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		if(this.app!=null)
		{
			AppAdminParam ap=new AppAdminParam();
			sb.append('{');
			sb.append(String.format("\"%s\":",ap.getAppIdParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(String.format("%d", app.getId()))));
			sb.append('"');
			sb.append(',');
			sb.append(String.format("\"%s\":",ap.getAppNameParamName()));
			sb.append('"');
			sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(String.format("%s", app.getName()))));
			sb.append('"');
			sb.append('}');
		}
		return sb.toString();
	}

}
