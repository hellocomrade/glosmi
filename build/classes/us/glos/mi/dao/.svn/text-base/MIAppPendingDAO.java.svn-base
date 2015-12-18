package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.glc.DBConnFactory;
import org.glc.Logger;
import org.glc.utils.ServerUtilities;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.IDraft;

public class MIAppPendingDAO extends MIDraftDAO {
	public MIAppPendingDAO()
	{
		SQL_GET_DRAFT_BY_DID="select m.adp_data,m.adp_id,m.adp_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_pending m inner join admin_user a on a.aur_id=m.adp_owner_id where m.adp_id=?";
		SQL_GET_DRAFT_BY_UID="select m.adp_data,m.adp_id,m.adp_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_pending m inner join admin_user a on a.aur_id=m.adp_owner_id where m.adp_owner_id=?";
		SQL_GET_DRAFT_BY_DID_UID="select adp_data from app_data_pending where adp_id=? and adp_owner_id=?;";
		SQL_GET_DRAFT_BY_PAGE_FMT="select m.adp_data,m.adp_id,m.adp_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_pending m inner join admin_user a on a.aur_id=m.adp_owner_id order by %s %s limit ? offset ?;";
		SQL_GET_DRAFT_BY_PAGE_AND_UID_FMT="select m.adp_data,m.adp_id,m.adp_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from app_data_pending m inner join admin_user a on a.aur_id=m.adp_owner_id where m.adp_owner_id=? order by %s %s limit ? offset ?;";
		SQL_REMOVE_DRAFT_BY_DID_AND_UID="delete from app_data_pending where adp_id=? and adp_owner_id=?;";
		SQL_REMOVE_DRAFT_BY_DID="delete from app_data_pending where adp_id=?;";
		SQL_GET_DRAFT_COUNT="select count(*) from app_data_pending;";
		SQL_GET_DRAFT_COUNT_BY_UID="select count(*) from app_data_pending where adp_owner_id=?;";
		SQL_WRITE_MODEL_DRAFT="insert into app_data_pending(adp_data,adp_status_id,adp_owner_id,adp_app_name) values(?,?,?,?) returning adp_id;";
		SQL_UPDATE_MODEL_DRAFT="update app_data_pending set adp_data=?,adp_app_name=? where adp_id=?;";
		SQL_GET_OWNER_ID="select adp_owner_id from app_data_pending where adp_id=?;";
		_DRAFT_BY_PAGE_FIELDS=new String[]{"m.adp_id","m.adp_app_name","m.adp_last_update","a.aur_email","a.aur_last_name"};
	}
	@Override
	public int insertDraftByUId(int uid,IDraft model) {
		// TODO Auto-generated method stub
		int result=0;
		if(active&&uid>0&&model!=null&&model.getName()!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_WRITE_MODEL_DRAFT);
			    st.setBytes(1, ServerUtilities.WriteObjectToBinary(model));
				st.setInt(2, 2);
				st.setInt(3, uid);
				st.setString(4, model.getName());
				rs=st.executeQuery();
				if(rs.next())
					result=rs.getInt(1);
			    	    
			}
			catch(SQLException se)
			{
				Logger.writeLog(se.getMessage(), LogLevel.SEVERE);
			}
			finally
			{
				try
				{
					if(rs!=null)
						rs.close();
					if(st!=null)
						st.close();
					if(conn!=null)
						conn.close();
					rs=null;
					st=null;
					conn=null;
				}
				catch(Exception e)
				{
					Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
				}
			}
			
		}
		return result;
	}
}
