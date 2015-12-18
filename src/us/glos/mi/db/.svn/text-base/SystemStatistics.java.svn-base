package us.glos.mi.db;

import javax.servlet.ServletContext;
import org.glc.utils.ServerUtilities;
import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.domain.UserStats;

public class SystemStatistics {
	private boolean isAdmin;
	private boolean isMod;
	private boolean isApp;
	private final ServletContext context;
	public SystemStatistics(int priv,final ServletContext context)
	{
		this.isAdmin=UserPrivilegeMask.IsAdmin(priv);
		this.isMod=UserPrivilegeMask.IsModeler(priv);
		this.isApp=UserPrivilegeMask.IsApplication(priv);
		this.context=context;
	}
	
	public int getOnlineUserCount()
	{
		if(isAdmin&&this.context!=null)
			return ServerUtilities.getCurrentSessionCount(this.context);
		else
			return -1;
	}
	public int getTotalUserCount()
	{
		return SqlHelper.getTotalUserCount();
	}
	public UserStats getUserStats()
	{
		if(isAdmin)
			return SqlHelper.getUserStats();
		else
			return null;
	}
	public int[] getModelCounts(int uid)
	{
		if(isAdmin)
			return SqlHelper.getModelCounts();
		else
			return SqlHelper.getModelCounts(uid);
	}
	public int[] getAppCounts(int uid)
	{
		if(isAdmin)
			return SqlHelper.getAppCounts();
		else
			return SqlHelper.getAppCounts(uid);
	}
	public int[] getAttachmentCounts(int uid)
	{
		if(isAdmin)
			return SqlHelper.getAttachmentCounts();
		else
			return SqlHelper.getAttachmentCounts(uid);
	}
	public int[] getPendings()
	{
		if(isAdmin)
			return SqlHelper.getPendingCount();
		else
			return null;
	}
}
