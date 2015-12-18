package us.glos.mi.dao;

import java.util.ArrayList;

import org.glc.ITimeZoneAware;
import org.glc.domain.User;
import us.glos.mi.domain.ModelInfo;

public interface IModDAO extends ITimeZoneAware{
	ArrayList<ModelInfo> getModelInfoByUID(int id);
	ArrayList<ModelInfo> getModelInfoByPage(int start,int count,int sortByColIdx,boolean isAsc);
	ArrayList<ModelInfo> getModelInfoByOwnerAndPage(int uid,int start,int count,int sortByColIdx,boolean isAsc);
	String[] getFriendlyPagingFldNames();
	ModelInfo getModelInfoById(int id);
	ModelInfo getModelInfoByIdAndUId(int mid,int uid);
	int addModelInfo(ModelInfo mod,int uid);
	boolean removeModelInfoById(int mid);
	boolean updateModelInfo(ModelInfo mod);
	boolean isOwnerOfModel(int uid,int mid);
	int getModelCount();
	int getModelCountByOwner(int uid);
	boolean updateModelOwner(int modid,int uid);
	boolean updateModelStatus(int modid,boolean isActive);
	User isPossibleModelOwner(String email);
	//boolean isModelEnabled(int modid);
	int getOrgnizationId(String abbrev,String name);
	String getOwnerEmailByModelId(int modid);
	int getOwnerIdByModId(int id);
}
