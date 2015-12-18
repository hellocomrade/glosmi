package us.glos.mi.dao;

import java.util.ArrayList;

import us.glos.mi.domain.AppInfo;

public interface IAppDraftDAO {
	ArrayList<AppInfo> getDraftByUID(int id);
	AppInfo getDraftById(int id);
	ArrayList<AppInfo> getDraftByPage(int start,int count,int sortByColIdx,boolean isAsc);
	ArrayList<AppInfo> getDraftByOwnerAndPage(int uid,int start,int count,int sortByColIdx,boolean isAsc);
	AppInfo getDraftByIdAndOwner(int did,int uid);
	int insertDraftByUId(int uid,AppInfo model);
	boolean updateDraft(int uid,int did,AppInfo model);
	boolean removeDraftById(int did);
	boolean removeByDraftIdAndOwner(int uid,int did);
	int getDraftCount();
	int getDraftCountByOwner(int uid);
}
