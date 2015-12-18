package us.glos.mi.dao;

public class MIAppDraftDAO extends MIDraftDAO {
	{
	SQL_GET_DRAFT_BY_DID="select m.add_data,m.add_id,m.add_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_draft m inner join admin_user a on a.aur_id=m.add_owner_id where m.add_id=?";
	SQL_GET_DRAFT_BY_UID="select m.add_data,m.add_id,m.add_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_draft m inner join admin_user a on a.aur_id=m.add_owner_id where m.add_owner_id=?";
	SQL_GET_DRAFT_BY_DID_UID="select add_data from app_data_draft where add_id=? and add_owner_id=?;";
	SQL_GET_DRAFT_BY_PAGE_FMT="select m.add_data,m.add_id,m.add_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_draft m inner join admin_user a on a.aur_id=m.add_owner_id order by %s %s limit ? offset ?;";
	SQL_GET_DRAFT_BY_PAGE_AND_UID_FMT="select m.add_data,m.add_id,m.add_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_draft m inner join admin_user a on a.aur_id=m.add_owner_id where m.add_owner_id=? order by %s %s limit ? offset ?;";
	SQL_REMOVE_DRAFT_BY_DID_AND_UID="delete from app_data_draft where add_id=? and add_owner_id=?;";
	SQL_REMOVE_DRAFT_BY_DID="delete from app_data_draft where add_id=?;";
	SQL_GET_DRAFT_COUNT="select count(*) from app_data_draft;";
	SQL_GET_DRAFT_COUNT_BY_UID="select count(*) from app_data_draft where add_owner_id=?;";
	SQL_WRITE_MODEL_DRAFT="insert into app_data_draft(add_data,add_status_id,add_owner_id,add_model_name) values(?,?,?,?) returning add_id;";
	SQL_UPDATE_MODEL_DRAFT="update app_data_draft set add_data=?,add_model_name=? where add_id=?;";
	_DRAFT_BY_PAGE_FIELDS=new String[]{"m.add_id","m.add_model_name","","m.add_last_update","a.aur_email","a.aur_last_name"};
	}
}
