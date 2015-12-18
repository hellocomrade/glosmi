package us.glos.mi.dao;

import java.util.ArrayList;

import org.glc.ITimeZoneAware;
import org.glc.domain.User;
import us.glos.mi.domain.AppInfo;

public interface IAppDAO extends ITimeZoneAware{
	ArrayList<AppInfo> getAppInfoByUID(int id,boolean isRequireContact);
	ArrayList<AppInfo> getAppInfoByPage(int start,int count,int sortByColIdx,boolean isAsc);
	ArrayList<AppInfo> getAppInfoByOwnerAndPage(int uid,int start,int count,int sortByColIdx,boolean isAsc);
	ArrayList<AppInfo> getAppInfoByModId(int modid);
	String[] getFriendlyPagingFldNames();
	AppInfo getAppInfoById(int id);
	AppInfo getAppInfoByIdAndUId(int mid,int uid);
	int addAppInfo(AppInfo mod,int uid);
	boolean removeAppInfoById(int mid);
	boolean updateAppInfo(AppInfo mod);
	boolean isOwnerOfApp(int uid,int mid);
	boolean isAttachedModel(int id,int mid);
	int getAppCount();
	int getAppCountByOwner(int uid);
	boolean updateAppOwner(int modid,int uid);
	boolean updateAppStatus(int modid,boolean isActive);
	User isPossibleAppOwner(String email);
	int getOrgnizationId(String abbrev,String name);
	String getOwnerEmailByAppId(int appid);
	int getOwnerIdByAppId(int id);
}
