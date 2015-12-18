package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.glc.DBConnFactory;
import org.glc.DefaultTimeZoneImpl;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;
import org.glc.utils.ServerUtilities;

import us.glos.mi.domain.ModelInfo;


public class _MIModDraftDAO extends DefaultTimeZoneImpl implements IDraftDAO{

	protected String SQL_GET_DRAFT_BY_DID="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_id=?";
	protected String SQL_GET_DRAFT_BY_UID="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_owner_id=?";
	protected String SQL_GET_DRAFT_BY_DID_UID="select mdd_data from model_data_draft where mdd_id=? and mdd_owner_id=?;";
	protected String SQL_GET_DRAFT_BY_PAGE_FMT="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id order by %s %s limit ? offset ?;";
	protected String SQL_GET_DRAFT_BY_PAGE_AND_UID_FMT="select m.mdd_data,m.mdd_id,m.mdd_last_update,a.aur_email,a.aur_first_name,a.aur_last_name from model_data_draft m inner join admin_user a on a.aur_id=m.mdd_owner_id where m.mdd_owner_id=? order by %s %s limit ? offset ?;";
	protected String SQL_REMOVE_DRAFT_BY_DID_AND_UID="delete from model_data_draft where mdd_id=? and mdd_owner_id=?;";
	protected String SQL_REMOVE_DRAFT_BY_DID="delete from model_data_draft where mdd_id=?;";
	protected String SQL_GET_DRAFT_COUNT="select count(*) from model_data_draft;";
	protected String SQL_GET_DRAFT_COUNT_BY_UID="select count(*) from model_data_draft where mdd_owner_id=?;";
	protected String SQL_WRITE_MODEL_DRAFT="insert into model_data_draft(mdd_data,mdd_status_id,mdd_owner_id,mdd_model_name) values(?,?,?,?) returning mdd_id;";
	protected String SQL_UPDATE_MODEL_DRAFT="update model_data_draft set mdd_data=?,mdd_model_name=? where mdd_id=?;";
	protected static String[] _DRAFT_BY_PAGE_FIELDS=new String[]{"m.mdd_id","m.mdd_model_name","","m.mdd_last_update","a.aur_email","a.aur_last_name"};
	protected static boolean active=false;
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
	}
	//private ModelInfoPersistProvider serialProvider=null;
	
	@Override
	public ArrayList<ModelInfo> getDraftByOwnerAndPage(int uid, int start,
			int count, int sortByColIdx, boolean isAsc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> infos=null;
		ModelInfo info=null;
		String sql=null;
		if(uid>0&&start>=0&&count>0&&sortByColIdx>=0&&sortByColIdx<_DRAFT_BY_PAGE_FIELDS.length)
		{
			if(sortByColIdx==2)
				sortByColIdx=0;
			try
			{
				if(_DRAFT_BY_PAGE_FIELDS[sortByColIdx].equals("a.aur_last_name"))
					sql=String.format(SQL_GET_DRAFT_BY_PAGE_AND_UID_FMT,
							_DRAFT_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
							"a.aur_first_name"+" "+(isAsc?"asc":"desc"));
				else
				    sql=String.format(SQL_GET_DRAFT_BY_PAGE_AND_UID_FMT,
				    		_DRAFT_BY_PAGE_FIELDS[sortByColIdx],
						    isAsc?"asc":"desc");
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(sql);
				st.setInt(1, uid);
				st.setInt(2, count);
				st.setInt(3, start);
				rs=st.executeQuery();
				while(rs.next())
				{
					if(infos==null)
						infos=new ArrayList<ModelInfo>();
					info=(ModelInfo)ServerUtilities.ReadObjectFromBinary(rs.getBytes(1));
					if(info!=null)
					{
						info.setSerialId(rs.getInt(2));
						info.setLastUpdateDate(rs.getTimestamp(3,this.cal));
						info.setOwnerEmail(rs.getString(4));
			    		info.setOwnerName(rs.getString(6)+" "+rs.getString(5));
						infos.add(info);
					}
				}
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
		return infos;
	}

	@Override
	public ArrayList<ModelInfo> getDraftByPage(int start, int count,
			int sortByColIdx, boolean isAsc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> infos=null;
		ModelInfo info=null;
		String sql=null;
		if(start>=0&&count>0&&sortByColIdx>=0&&sortByColIdx<_DRAFT_BY_PAGE_FIELDS.length)
		{
			if(sortByColIdx==2)
				sortByColIdx=0;
			try
			{
				if(_DRAFT_BY_PAGE_FIELDS[sortByColIdx].equals("a.aur_last_name"))
					sql=String.format(SQL_GET_DRAFT_BY_PAGE_FMT,
							_DRAFT_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
							"a.aur_first_name"+" "+(isAsc?"asc":"desc"));
				else
				    sql=String.format(SQL_GET_DRAFT_BY_PAGE_FMT,
				    		_DRAFT_BY_PAGE_FIELDS[sortByColIdx],
						    isAsc?"asc":"desc");
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(sql);
				st.setInt(1, count);
				st.setInt(2, start);
				rs=st.executeQuery();
				while(rs.next())
				{
					if(infos==null)
						infos=new ArrayList<ModelInfo>();
					info=(ModelInfo)ServerUtilities.ReadObjectFromBinary(rs.getBytes(1));
					if(info!=null)
					{
						info.setSerialId(rs.getInt(2));
						info.setLastUpdateDate(rs.getTimestamp(3,this.cal));
						info.setOwnerEmail(rs.getString(4));
			    		info.setOwnerName(rs.getString(6)+" "+rs.getString(5));
						infos.add(info);
					}
				}
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
		return infos;
	}

	@Override
	public ArrayList<ModelInfo> getDraftByUID(int id) {
		// TODO Auto-generated method stub
		ArrayList<ModelInfo> result=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ModelInfo mod=null;
		if(id>0)
		{
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_DRAFT_BY_UID);
			    st.setInt(1, id);
			    rs=st.executeQuery();
			    while(rs.next())
			    {
			    	mod=(ModelInfo)ServerUtilities.ReadObjectFromBinary(rs.getBytes(1));
			    	if(mod!=null)
			    	{
			    		mod.setSerialId(rs.getInt(2));
			    		mod.setLastUpdateDate(rs.getTimestamp(3, this.cal));
			    		mod.setOwnerEmail(rs.getString(4));
			    		mod.setOwnerName(rs.getString(6)+" "+rs.getString(5));
			    		if(result==null)
			    			result=new ArrayList<ModelInfo>();
			    		result.add(mod);
			    	}
			    }
			    
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

	@Override
	public ModelInfo getDraftByIdAndOwner(int did, int uid) {
		// TODO Auto-generated method stub
		ModelInfo result=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		
		if(uid>0&&did>0)
		{
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_DRAFT_BY_DID_UID);
			    st.setInt(1, did);
			    st.setInt(2, uid);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	result=(ModelInfo)ServerUtilities.ReadObjectFromBinary(rs.getBytes(1));
			    	if(result!=null)
			    		result.setSerialId(did);
			    	
			    }
			    
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
	
	@Override
	public ModelInfo getDraftById(int did) {
		// TODO Auto-generated method stub
		ModelInfo result=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		
		if(did>0)
		{
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_DRAFT_BY_DID);
			    st.setInt(1, did);
			       rs=st.executeQuery();
			    if(rs.next())
			    {
			    	result=(ModelInfo)ServerUtilities.ReadObjectFromBinary(rs.getBytes(1));
			    	if(result!=null)
			    	{
			    		result.setSerialId(did);
			    		result.setLastUpdateDate(rs.getTimestamp(3, this.cal));
			    		result.setOwnerEmail(rs.getString(4));
			    		result.setOwnerName(rs.getString(6)+" "+rs.getString(5));
			    	}
			    	
			    }
			    
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

	@Override
	public int insertDraftByUId(int uid,ModelInfo model) {
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
				st.setInt(2, 1);
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
	@Override
	public boolean updateDraft(int uid,int did,ModelInfo model) 
	{
		boolean result=false;
		if(active&&uid>0&&did>0&&model!=null&&model.getName()!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_UPDATE_MODEL_DRAFT);
			    st.setBytes(1, ServerUtilities.WriteObjectToBinary(model));
			    st.setString(2, model.getName());
			    st.setInt(3, did);
			    
			    
			    //st.setString(4, context.draftName);
			    result=(1==st.executeUpdate());
			    
			    
			}
			catch(SQLException se)
			{
				Logger.writeLog(se.getMessage(), LogLevel.SEVERE);
			}
			finally
			{
				try
				{
					if(st!=null)
						st.close();
					if(conn!=null)
						conn.close();
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
	
	@Override
	public boolean removeDraftById(int did) {
		// TODO Auto-generated method stub
		boolean result=false;
		Connection conn=null;
		PreparedStatement st=null;
		if(did>0)
		{
			
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_REMOVE_DRAFT_BY_DID);
			    st.setInt(1, did);
			    if(1==st.executeUpdate())
			    	result=true;
			    
			}
			catch(SQLException se)
			{
				Logger.writeLog(se.getMessage(), LogLevel.SEVERE);
			}
			finally
			{
				try
				{
					if(st!=null)
						st.close();
					if(conn!=null)
						conn.close();
					
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

	@Override
	public boolean removeByDraftIdAndOwner(int uid,int did) {
		// TODO Auto-generated method stub
		boolean result=false;
		Connection conn=null;
		PreparedStatement st=null;
		if(did>0&&uid>0)
		{
			
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_REMOVE_DRAFT_BY_DID_AND_UID);
			    st.setInt(1, did);
			    st.setInt(2, uid);
			    if(1==st.executeUpdate())
			    	result=true;
			    
			}
			catch(SQLException se)
			{
				Logger.writeLog(se.getMessage(), LogLevel.SEVERE);
			}
			finally
			{
				try
				{
					if(st!=null)
						st.close();
					if(conn!=null)
						conn.close();
					
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

	@Override
	public int getDraftCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		int count=0;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.createStatement();
		    
		    rs=st.executeQuery(SQL_GET_DRAFT_COUNT);
		    
		    if(rs.next())
		    {
		    	count=rs.getInt(1);
		    }
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
		return count;
	}

	@Override
	public int getDraftCountByOwner(int uid) {
		// TODO Auto-generated method stub
		if(uid<=0)return 0;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int count=0;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_GET_DRAFT_COUNT_BY_UID);
		    st.setInt(1, uid);
		    rs=st.executeQuery();
		    
		    if(rs.next())
		    {
		    	count=rs.getInt(1);
		    }
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
		return count;
	}

	

}
