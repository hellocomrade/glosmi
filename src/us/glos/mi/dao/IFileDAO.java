package us.glos.mi.dao;


import java.util.ArrayList;

import org.glc.ITimeZoneAware;

import us.glos.mi.domain.Attachment;

public interface IFileDAO extends ITimeZoneAware{
	int saveFileRecord(int rid,int sid,int uid,int tid,String path,String url,String desc);
	boolean removeFileRecordById(int id);
	ArrayList<Attachment> getFilePathBySection(int sid,int rid,int tid);
	boolean updateFileDescription(int rid,String desc);
	boolean updateFileStatus(int id,boolean isChecked);
	boolean isOwnerOfFile(int id,int uid);
	int countByIds(int rid,int sid);
	String getFilePathById(int id);
	int getCountByUID(int uid);
	int getTypeId(int id);
	ArrayList<Attachment> getAttachmentByOwnerAndPage(int uid,int start,int count,int sortByColIdx,boolean isAsc);
}
