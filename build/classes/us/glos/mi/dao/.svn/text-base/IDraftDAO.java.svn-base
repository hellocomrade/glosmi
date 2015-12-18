package us.glos.mi.dao;

import java.util.ArrayList;

import org.glc.ITimeZoneAware;


public interface IDraftDAO<T> extends ITimeZoneAware{
	ArrayList<T> getDraftByUID(int id);
	T getDraftById(int id);
	ArrayList<T> getDraftByPage(int start,int count,int sortByColIdx,boolean isAsc);
	ArrayList<T> getDraftByOwnerAndPage(int uid,int start,int count,int sortByColIdx,boolean isAsc);
	T getDraftByIdAndOwner(int did,int uid);
	int insertDraftByUId(int uid,T model);
	boolean updateDraft(int uid,int did,T model);
	boolean removeDraftById(int did);
	boolean removeByDraftIdAndOwner(int uid,int did);
	int getDraftCount();
	int getDraftCountByOwner(int uid);
	int getOwnerIdByDraftId(int did);
}
