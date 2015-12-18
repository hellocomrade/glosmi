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
import us.glos.mi.domain.ModelInfo;

public class MIModPendingDAO extends MIDraftDAO {
	
	public MIModPendingDAO()
	{
		SQL_GET_DRAFT_BY_DID="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_pending m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_id=?";
		SQL_GET_DRAFT_BY_UID="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_pending m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_owner_id=?";
		SQL_GET_DRAFT_BY_DID_UID="select mdd_data from model_data_pending where mdd_id=? and mdd_owner_id=?;";
		SQL_GET_DRAFT_BY_PAGE_FMT="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_pending m inner join admin_user a on a.aur_id=m.mdd_owner_id order by %s %s limit ? offset ?;";
		SQL_GET_DRAFT_BY_PAGE_AND_UID_FMT="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_pending m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_owner_id=? order by %s %s limit ? offset ?;";
		SQL_REMOVE_DRAFT_BY_DID_AND_UID="delete from model_data_pending where mdd_id=? and mdd_owner_id=?;";
		SQL_REMOVE_DRAFT_BY_DID="delete from model_data_pending where mdd_id=?;";
		SQL_GET_DRAFT_COUNT="select count(*) from model_data_pending;";
		SQL_GET_DRAFT_COUNT_BY_UID="select count(*) from model_data_pending where mdd_owner_id=?;";
		SQL_WRITE_MODEL_DRAFT="insert into model_data_pending(mdd_data,mdd_status_id,mdd_owner_id,mdd_model_name) values(?,?,?,?) returning mdd_id;";
		SQL_UPDATE_MODEL_DRAFT="update model_data_pending set mdd_data=?,mdd_model_name=? where mdd_id=?;";
		SQL_GET_OWNER_ID="select mdd_owner_id from model_data_pending where mdd_id=?;";
		_DRAFT_BY_PAGE_FIELDS=new String[]{"m.mdd_id","m.mdd_model_name","","m.mdd_last_update","a.aur_email","a.aur_last_name"};
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
