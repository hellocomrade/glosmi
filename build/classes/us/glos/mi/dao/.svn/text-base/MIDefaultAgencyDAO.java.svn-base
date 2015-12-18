package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.glc.DBConnFactory;
import org.glc.Logger;
import org.glc.domain.Orgnization;
import org.glc.xmlconfig.LogLevel;


public class MIDefaultAgencyDAO implements IAgencyDAO {

	private static final String SQL_GET_ALL_AGENCIES="select agn_id,agn_agnid,agn_name from agencies order by agn_name asc;";
	private final static String SQL_GET_ORG="select agn_id,agn_name,agn_agnid,agn_desc,agn_url from agencies where lower(agn_agnid) like lower(?) or lower(agn_name) like lower(?);";
	private final static String SQL_GET_ORG_BY_ID="select agn_id,agn_name,agn_agnid,agn_desc,agn_url from agencies where agn_id=?;";
	private final static String SQL_UPDATE_ORG_BY_ID="update agencies set agn_name=?,agn_agnid=?,agn_desc=?,agn_url=? where agn_id=?;";
	private final static String SQL_INSERT_ORG="insert into agencies(agn_name,agn_agnid,agn_desc,agn_url) values(?,?,?,?);";
	private final static String SQL_IS_AGENCY_NAME="select count(*) from agencies where lower(agn_name)=lower(?)";
	private final static String SQL_IS_AGENCY_ABBREV="select count(*) from agencies where lower(agn_agnid)=lower(?)";
	private final static String SQL_GET_ID_BY_NAME="select agn_id from agencies where lower(agn_name)=lower(?)";
	private final static String SQL_GE_ID_BY_ABBREV="select agn_id from agencies where lower(agn_agnid)=lower(?)";
	protected static boolean active=false;
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
	}
	
	@Override
	public int getAgencyIdByAbbrev(String abbrev) {
		// TODO Auto-generated method stub
		if(abbrev!=null&&!abbrev.equals(""))
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GE_ID_BY_ABBREV);
			    
			    st.setString(1, abbrev);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	return rs.getInt(1);
			    	
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
		return 0;
	}

	@Override
	public int getAgencyIdByName(String name) {
		// TODO Auto-generated method stub
		if(name!=null&&!name.equals(""))
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GET_ID_BY_NAME);
			    
			    st.setString(1, name);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	return rs.getInt(1);
			    	
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
		return 0;
	}

	@Override
	public boolean isAgencyAbbrevExist(String abbrev) {
		// TODO Auto-generated method stub
		if(abbrev!=null&&!abbrev.equals(""))
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_IS_AGENCY_ABBREV);
			    st.setString(1, abbrev);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	return rs.getInt(1)>=1;
			    	
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
		return true;
	}

	@Override
	public boolean isAgencyNameExist(String name) {
		// TODO Auto-generated method stub
		if(name!=null&&!name.equals(""))
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_IS_AGENCY_NAME);
			    st.setString(1, name);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	return rs.getInt(1)>=1;
			    	
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
		return true;
	}

	@Override
	public Orgnization getAgencyById(int id) {
		// TODO Auto-generated method stub
		
		Orgnization org=null;
		if(id>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GET_ORG_BY_ID);
			    st.setInt(1, id);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	org=new Orgnization();
			    	org.setId(rs.getInt(1));
			    	org.setName(rs.getString(2));
			    	org.setAbbrev(rs.getString(3));
			    	org.setDescription(rs.getString(4));
			    	org.setUrl(rs.getString(5));
			    	
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
		return org;
	}

	@Override
	public ArrayList<Orgnization> searchAgencies(String key) {
		// TODO Auto-generated method stub
		ArrayList<Orgnization> orgs=null;
		Orgnization org=null;
		if(key!=null&&!key.trim().equals(""))
		{
			key=key.trim();
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GET_ORG);
			    st.setString(1, "%"+key+"%");
			    st.setString(2, "%"+key+"%");
			    rs=st.executeQuery();
			    while(rs.next())
			    {
			    	org=new Orgnization();
			    	org.setId(rs.getInt(1));
			    	org.setName(rs.getString(2));
			    	org.setAbbrev(rs.getString(3));
			    	org.setDescription(rs.getString(4));
			    	org.setUrl(rs.getString(5));
			    	if(orgs==null)
			    		orgs=new ArrayList<Orgnization>();
			    	orgs.add(org);
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
		return orgs;
	}

	@Override
	public boolean addAgency(String name, String abbrev,String desc,String url) {
		// TODO Auto-generated method stub
		ArrayList<Orgnization> orgs=null;
		Orgnization org=null;
		if(name!=null&&!name.trim().equals("")&&abbrev!=null&&!abbrev.trim().equals(""))
		{
			name=name.trim();
			abbrev=abbrev.trim();
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_INSERT_ORG);
			    st.setString(1, name);
			    st.setString(2, abbrev);
			    if(desc==null)
			    	st.setNull(3, Types.VARCHAR);
			    else
			    	st.setString(3, desc);
			    if(url==null)
			    	st.setNull(4, Types.VARCHAR);
			    else
			    	st.setString(4, url);
			    return 1==st.executeUpdate(); 
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
	public boolean removeAgencyByAbbrev(String abbrev) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAgencyById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAgencyByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAgencyByName(Orgnization org) {
		// TODO Auto-generated method stub
		if(org!=null&&org.getId()>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_UPDATE_ORG_BY_ID);
			    st.setString(1, org.getName());
			    st.setString(2, org.getAbbrev());
			    if(org.getDescription()==null)
			    	st.setNull(3, Types.VARCHAR);
			    else
			    	st.setString(3, org.getDescription());
			    if(org.getUrl()==null)
			    	st.setNull(4, Types.VARCHAR);
			    else
			    	st.setString(4, org.getUrl());
			    st.setInt(5, org.getId());
			    
			    return 1==st.executeUpdate(); 
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
	public ArrayList<Orgnization> getAllAgencies() {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<Orgnization> list=null;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_GET_ALL_AGENCIES);
		    
		    rs=st.executeQuery();
		    
		    while(rs.next())
		    {
		    	if(list==null)
		    		list=new ArrayList<Orgnization>();
		    	Orgnization org=new Orgnization();
		    	org.setId(rs.getInt(1));
		    	org.setAbbrev(rs.getString(2));
		    	org.setName(rs.getString(3));
		    	list.add(org);
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

}
