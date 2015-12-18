package us.glos.mi.dao;

public class MIModDraftDAO extends MIDraftDAO {
	{
	SQL_GET_DRAFT_BY_DID="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_id=?";
	SQL_GET_DRAFT_BY_UID="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_owner_id=?";
	SQL_GET_DRAFT_BY_DID_UID="select mdd_data from model_data_draft where mdd_id=? and mdd_owner_id=?;";
	SQL_GET_DRAFT_BY_PAGE_FMT="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id order by %s %s limit ? offset ?;";
	SQL_GET_DRAFT_BY_PAGE_AND_UID_FMT="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_owner_id=? order by %s %s limit ? offset ?;";
	SQL_REMOVE_DRAFT_BY_DID_AND_UID="delete from model_data_draft where mdd_id=? and mdd_owner_id=?;";
	SQL_REMOVE_DRAFT_BY_DID="delete from model_data_draft where mdd_id=?;";
	SQL_GET_DRAFT_COUNT="select count(*) from model_data_draft;";
	SQL_GET_DRAFT_COUNT_BY_UID="select count(*) from model_data_draft where mdd_owner_id=?;";
	SQL_WRITE_MODEL_DRAFT="insert into model_data_draft(mdd_data,mdd_status_id,mdd_owner_id,mdd_model_name) values(?,?,?,?) returning mdd_id;";
	SQL_UPDATE_MODEL_DRAFT="update model_data_draft set mdd_data=?,mdd_model_name=? where mdd_id=?;";
	_DRAFT_BY_PAGE_FIELDS=new String[]{"m.mdd_id","m.mdd_model_name","","m.mdd_last_update","a.aur_email","a.aur_last_name"};
	}
}
