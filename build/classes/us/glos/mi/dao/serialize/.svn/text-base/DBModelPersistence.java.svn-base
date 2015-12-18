package us.glos.mi.dao.serialize;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.glc.DefaultTimeZoneImpl;
import org.glc.DBConnFactory;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.dao.MIDataSource;


public class DBModelPersistence extends DefaultTimeZoneImpl implements IPersistent<ModelInfoPersistContext> {

	private static final String SQL_WRITE_MODEL_DRAFT="insert into model_data_draft(mdd_data,mdd_status_id,mdd_owner_id) values(?,?,?);";
	private static final String SQL_UPDATE_MODEL_DRAFT="update model_data_draft set mdd_data=? where mdd_id=?;";
	private static final String SQL_READ_MODEL_DRAFT="select mdd_data,mdd_last_update from model_data_draft where mdd_id=?;";
	private static final String SQL_READ_MODELS_DRAFT_BY_OWNER="select mdd_id,mdd_data,mdd_last_update from model_data_draft where mdd_owner_id=?;";
	private static final String SQL_REMOVE_MODEL_BY_DRAFT_ID="delete from model_data_draft where mdd_id=?;";
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	protected static boolean active=false;
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
	}
	@Override
	public byte[] read(ModelInfoPersistContext context) {
		// TODO Auto-generated method stub
		byte[] result=null;
		if(active&&context!=null&&context.draftId>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_READ_MODEL_DRAFT);
			    st.setInt(1, context.draftId);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	context.lastUpdateDate=rs.getTimestamp(1, this.cal);
			    	result=rs.getBytes(2);
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
	public boolean writeTo(ModelInfoPersistContext context, Object data) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(active&&context!=null&&data!=null&&data instanceof byte[])
		{
			Connection conn=null;
			PreparedStatement st=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    if(context.draftId<=0)
			    {
			        st=conn.prepareStatement(SQL_WRITE_MODEL_DRAFT);
			        st.setBytes(1, (byte[])data);
				    st.setInt(2, 1);
				    st.setInt(3, context.uid);
				    //st.setTimestamp(4, new Timestamp(this.cal.getTimeInMillis()));
			    }
			    else
			    {
			    	st=conn.prepareStatement(SQL_UPDATE_MODEL_DRAFT);
			    	st.setBytes(1, (byte[])data);
			    	st.setInt(2, context.draftId);
			    }
			    
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
	public ArrayList<byte[]> readMore(ModelInfoPersistContext context) {
		// TODO Auto-generated method stub
		ArrayList<byte[]> models=null;
		if(context!=null&&context.uid>0)
		{
			
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);

				st=conn.prepareStatement(SQL_READ_MODELS_DRAFT_BY_OWNER);
				st.setInt(1, context.uid);
				rs=st.executeQuery();
				while(rs.next())
				{
					if(models==null)
						models=new ArrayList<byte[]>();
					if(context.draftIds==null)
						context.draftIds=new ArrayList<Integer>();
					if(context.lastUpdateDates==null)
						context.lastUpdateDates=new ArrayList<java.util.Date>();
					context.draftIds.add(rs.getInt(1));
					models.add(rs.getBytes(2));
					context.lastUpdateDates.add(rs.getTimestamp(3, this.cal));
					
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
		return models;
	}

	@Override
	public boolean remove(ModelInfoPersistContext context) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(context!=null&&context.draftId>=0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);

				st=conn.prepareStatement(SQL_REMOVE_MODEL_BY_DRAFT_ID);
				st.setInt(1, context.draftId);
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

}
