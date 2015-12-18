package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.glc.DBConnFactory;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.NameValuePair;

public abstract class MINameValuePairDAO implements INameValueDAO<NameValuePair> {

	protected static boolean active=false;
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
	}
	protected  String SQL_GET_ALL_THEMES="";
	protected  String SQL_GET_THEME_COUNT="";
	protected  String SQL_GET_THEME_BY_ID="";
	protected  String SQL_INSERT_THEME="";
	protected  String SQL_UPDATE_VALUE_BY_NAME="";
	@Override
	public boolean addNameValuePair(NameValuePair nv) {
		// TODO Auto-generated method stub
		if(nv!=null&&nv.name!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_INSERT_THEME);
			    st.setString(1, nv.name);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	int id=rs.getInt(1);
			    	if(id>0)
			    	{
			    		nv.id=id;
			    		return true;
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
		return false;
	}

	@Override
	public boolean updateValueByName(NameValuePair nv) {
		// TODO Auto-generated method stub
		if(nv!=null&&nv.name!=null&&nv.id>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_UPDATE_VALUE_BY_NAME);
			    st.setString(1, nv.name);
			    st.setInt(2, nv.id);
			    if(1==st.executeUpdate())
				    return true;
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
		return false;
	}

	@Override
	public ArrayList<NameValuePair> getAllNameValuePairs() {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<NameValuePair> list=null;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_GET_ALL_THEMES);
		    
		    rs=st.executeQuery();
		    
		    while(rs.next())
		    {
		    	if(list==null)
		    		list=new ArrayList<NameValuePair>();
		    	NameValuePair the=new NameValuePair();
		    	the.id=rs.getInt(1);
		    	the.name=rs.getString(2);
		    	list.add(the);
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
		return list;
	}

	@Override
	public NameValuePair getPairByKey(Object key) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		NameValuePair the=null;
		if(key==null||!(key instanceof Integer))
			return null;
		
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_GET_THEME_BY_ID);
		    st.setInt(1, (Integer)key);
		    rs=st.executeQuery();
		    
		    if(rs.next())
		    {
		    	the=new NameValuePair();
		    	the.id=rs.getInt(1);
		    	the.name=rs.getString(2);
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
		return the;
	}

	@Override
	public int getPairCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		int count=0;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.createStatement();
		    
		    rs=st.executeQuery(SQL_GET_THEME_COUNT);
		    
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

