package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.glc.DBConnFactory;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;
import org.glc.domain.User;
import org.glc.DefaultTimeZoneImpl;

public class MIDefaultUserDAO extends DefaultTimeZoneImpl implements IUserDAO{

	private static final String SQL_INSERT_ADMIN_USER="insert into admin_user(aur_email,aur_first_name,aur_last_name,aur_passwd,aur_mask,aur_street1,aur_street2,aur_city,aur_state,aur_country,aur_zipcode,aur_phone,aur_org_id) "+ 
	                                                   " values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private static final String SQL_GET_ADMIN_USER_BY_EMAIL="select a.aur_passwd,a.aur_first_name,a.aur_last_name,a.aur_mask,a.aur_id,a.aur_last_login_date,"+
															"a.aur_street1,a.aur_street2,a.aur_city,a.aur_state,a.aur_country,a.aur_zipcode,a.aur_phone,a.aur_org_id,a.aur_activated,o.agn_agnid,o.agn_name from admin_user a left join agencies o on o.agn_id=a.aur_org_id where a.aur_email=?;";
	private static final String SQL_GET_ADMIN_USER_BY_ID="select a.aur_email,a.aur_first_name,a.aur_last_name,a.aur_mask,a.aur_last_login_date,"+
	"a.aur_street1,a.aur_street2,a.aur_city,a.aur_state,a.aur_country,a.aur_zipcode,a.aur_phone,a.aur_org_id,a.aur_activated,o.agn_agnid,o.agn_name from admin_user a left join agencies o on o.agn_id=a.aur_org_id where a.aur_id=?;";
	
	private static final String SQL_GET_TOTAL_USER_COUNT="select count(*) from admin_user;";
	private static final String SQL_RESET_ADMIN_USER_PASSWORD="update admin_user set aur_passwd=? where aur_email=?;";
	private static final String SQL_UPDATE_LOGIN_TIME="update admin_user set aur_last_login_date=? where aur_id=?;";
	private static final String SQL_GET_AGENCY_ID="select agn_id from agencies where lower(agn_agnid)=lower(?) or lower(agn_name)=lower(?);";
	private final static String SQL_GET_NULL_AGENCY_ID="select agn_id from agencies where agn_agnid is NULL and agn_name is NULL;";
	//private static final String SQL_IS_AGENCY_EXIST="select count(*) from agencies where lower(agn_agnid)=lower(?) or lower(agn_name)=lower(?);";
	private static final String SQL_UPDATE_ADMIN_USER="update admin_user set aur_first_name=?,aur_last_name=?,aur_street1=?,aur_street2=?,aur_city=?,aur_state=?,aur_country=?,aur_zipcode=?,aur_phone=?,aur_org_id=? where aur_email=?;";
	private static final String SQL_IS_ADMIN_USER_EXIST="select count(*) from admin_user where aur_email=?;";
	private static final String SQL_SEARCH_USERS="select distinct(a.aur_id),a.aur_email,a.aur_first_name,a.aur_last_name,a.aur_last_login_date,a.aur_activated,o.agn_name from admin_user a left join agencies o on o.agn_id=a.aur_org_id where lower(a.aur_first_name) like ? or lower(a.aur_last_name) like ? or lower(a.aur_email) like ?;";
	private static final String SQL_SET_PRIVILEGE="update admin_user set aur_mask=? where aur_email=?;";
	private static final String SQL_SET_USER_STATUS="update admin_user set aur_activated=? where aur_email=?;";
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	protected static boolean active=false;
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
	}
	public MIDefaultUserDAO()
	{
		super();
		
	}
	@Override
	public boolean addUser(User usr) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		//ResultSet rs=null;
		if(true==isUserExist(usr.getEmail()))
			return false;
		int oid=-1;
		if(usr.getOrgnization().getAbbrev()!=null||usr.getOrgnization().getName()!=null)
			oid=getOrgnizationId(usr.getOrgnization().getAbbrev(),usr.getOrgnization().getName());
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_W);
		    
		    st=conn.prepareStatement(SQL_INSERT_ADMIN_USER);
		    
		    st.setString(1, usr.getEmail());
		    st.setString(2, usr.getFirstName());
		    st.setString(3, usr.getLastName());
		    st.setBytes(4, usr.getPassword());
		    st.setInt(5, usr.getPrivilegeLevel());
		    st.setString(6, usr.getAddress().getAddress1());
		    st.setString(7, usr.getAddress().getAddress2());
		    st.setString(8, usr.getAddress().getCity());
		    st.setString(9, usr.getAddress().getState());
		    st.setString(10, usr.getAddress().getCountry());
		    st.setString(11, usr.getAddress().getZipcode());
		    st.setString(12, usr.getAddress().getPhone());
		    if(oid>0)
		    	st.setInt(13, oid);
		    else
		    	st.setNull(13, java.sql.Types.INTEGER);
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
		
		return false;
	}
	public boolean isValidUser(String email,byte[] passwd)
	{
		if(email!=null&&passwd!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_ADMIN_USER_BY_EMAIL);
			    
			    st.setString(1, email);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	byte[] pasd=rs.getBytes(1);
			    	if(pasd!=null&&pasd.length>0
			    	   &&passwd.length==pasd.length)
			    	{
			    		int len=pasd.length;
			    		byte[] paswd=passwd;
			    		for(int i=0;i<len;++i)
			    			if(pasd[i]!=paswd[i])
			    				return false;
			    		
			    		if(rs.getBoolean(15)==false)return false;
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
	public boolean isValidUser(User usr) {
		// TODO Auto-generated method stub
		if(usr!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_ADMIN_USER_BY_EMAIL);
			    
			    st.setString(1, usr.getEmail());
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	byte[] pasd=rs.getBytes(1);
			    	if(pasd!=null&&pasd.length>0
			    	   &&usr.getPassword()!=null&&usr.getPassword().length==pasd.length)
			    	{
			    		int len=pasd.length;
			    		byte[] paswd=usr.getPassword();
			    		for(int i=0;i<len;++i)
			    			if(pasd[i]!=paswd[i])
			    				return false;
			    		//Address ad=new Address();
			    		//Orgnization org=new Orgnization();
			    		usr.setFirstName(rs.getString(2));
			    		usr.setLastName(rs.getString(3));
			    		usr.setPrivilegeLevel(rs.getInt(4));
			    		usr.setId(rs.getInt(5));
			    		usr.setLastLoginDate(rs.getTimestamp(6, this.cal));
			    		usr.setUsername(usr.getEmail());
			    		usr.getAddress().setId(-1);
			    		usr.getAddress().setAddress1(rs.getString(7));
			    		usr.getAddress().setAddress2(rs.getString(8));
			    		usr.getAddress().setCity(rs.getString(9));
			    		usr.getAddress().setState(rs.getString(10));
			    		usr.getAddress().setCountry(rs.getString(11));
			    		usr.getAddress().setZipcode(rs.getString(12));
			    		usr.getAddress().setPhone(rs.getString(13));
			    		usr.getOrgnization().setId(rs.getInt(14));
			    		usr.setActivate(rs.getBoolean(15));
			    		
			    		usr.getOrgnization().setAbbrev(rs.getString(16));
			    		usr.getOrgnization().setName(rs.getString(17));
			    		
			    		//usr.setAddress(ad);
			    		//usr.setOrgnization(org);
			    		if(usr.isActivate()==false)return false;
			    		st.close();
			    		st=conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
			    		
			    		st.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()), this.cal);
			    		st.setInt(2, usr.getId());
			    		if(1==st.executeUpdate())
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
	public boolean removeUser(User usr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetPassword(User usr, byte[] passwd) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		if(usr.getPassword()!=null)
		{
			if(this.isValidUser(usr)==false)
				return false;
		}
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_W);
		    
		    st=conn.prepareStatement(SQL_RESET_ADMIN_USER_PASSWORD);
		    
		    st.setBytes(1, passwd);
		    st.setString(2, usr.getEmail());
		    
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
		return false;
	}

	@Override
	public boolean setPrivilege(User usr) {
		// TODO Auto-generated method stub
		if(usr!=null)
		{
			if(usr.getEmail()!=null)
			{
				Connection conn=null;
				PreparedStatement st=null;
				
				try
				{
				    conn=DBConnFactory.getConnection(DB_SOURCE_W);
				    
				    st=conn.prepareStatement(SQL_SET_PRIVILEGE);
				    
				    st.setInt(1, usr.getPrivilegeLevel());
				    st.setString(2, usr.getEmail());
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
		}
		return false;
	}
	public int getOrgnizationId(String abbrev,String name)
	{
		//if(abbrev==null||abbrev.equals("")||name==null||name.equals(""))
		//	return -1;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    
		    if((abbrev==null||abbrev.equals(""))
				   	&&name==null||name.equals(""))
				   	st=conn.prepareStatement(SQL_GET_NULL_AGENCY_ID);
			else
			{
				  	st=conn.prepareStatement(SQL_GET_AGENCY_ID);
				   	st.setString(1, abbrev);
				   	st.setString(2, name);
			}
		    rs=st.executeQuery();
		    int id=0;
		    if(rs.next())
		    	id=rs.getInt(1);
		    else
		    	return -1;
		    //if multiple records returned, there is a conflict needs to be resolved
		    if(rs.next())
		    	return -2;
		    else
		    	return id;
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
				st=null;
				conn=null;
			}
			catch(Exception e)
			{
				Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
			}
		}
		return -1;
		    	
		
	}
	@Override
	public boolean updateUserInfo(User usr,int oid) {
		// TODO Auto-generated method stub
		if(usr!=null)
		{
			if(oid<=0)
				oid=this.getOrgnizationId(usr.getOrgnization().getAbbrev(),usr.getOrgnization().getName());
			if(oid>0)
			{
				Connection conn=null;
				PreparedStatement st=null;
				try
				{
					conn=DBConnFactory.getConnection(DB_SOURCE_W);
					st=conn.prepareStatement(SQL_UPDATE_ADMIN_USER);
					st.setString(1, usr.getFirstName());
					st.setString(2, usr.getLastName());
					st.setString(3, usr.getAddress().getAddress1());
					st.setString(4, usr.getAddress().getAddress2());
					st.setString(5, usr.getAddress().getCity());
					st.setString(6, usr.getAddress().getState());
					st.setString(7, usr.getAddress().getCountry());
					st.setString(8, usr.getAddress().getZipcode());
					st.setString(9, usr.getAddress().getPhone());
					st.setInt(10, oid);
					st.setString(11, usr.getEmail());
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
			
		}
		return false;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserExist(String email) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(email!=null)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);

				st=conn.prepareStatement(SQL_IS_ADMIN_USER_EXIST);

				st.setString(1, email);

				rs=st.executeQuery();
				if(rs.next()&&rs.getInt(1)==1)
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
	public int getTotalUserCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.createStatement();
			rs=st.executeQuery(SQL_GET_TOTAL_USER_COUNT);

			if(rs.next())
				return rs.getInt(1);
			else
				return -1;
			

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
		return 0;
	}
	@Override
	public ArrayList<User> searchUsers(String fname,String lname,String email) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<User> list=null;
		User usr=null;
		if(fname!=null||lname!=null||email!=null)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);

				st=conn.prepareStatement(SQL_SEARCH_USERS);
				if(fname!=null)fname=fname.toLowerCase();
				if(lname!=null)lname=lname.toLowerCase();
				if(email!=null)email=email.toLowerCase();
				if(fname!=null)
					st.setString(1, "%"+fname+"%");
				else
					st.setString(1, fname);
				if(lname!=null)
					st.setString(2, "%"+lname+"%");
				else
					st.setString(2, lname);
				if(email!=null)
					st.setString(3, "%"+email+"%");
				else
					st.setString(3, email);
				rs=st.executeQuery();
				
				while(rs.next())
				{
					if(list==null)
						list=new ArrayList<User>();
					usr=new User();
					usr.setId(rs.getInt(1));
					usr.setUsername(rs.getString(2));
					usr.setEmail(usr.getUsername());
					usr.setFirstName(rs.getString(3));
					usr.setLastName(rs.getString(4));
					usr.setLastLoginDate(rs.getTimestamp(5, this.cal));
					usr.setActivate(rs.getBoolean(6));
					usr.getOrgnization().setName(rs.getString(7));
					list.add(usr);
				}
				return list;
				

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
		return list;
	}
	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		User usr=null;
		
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;

		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.prepareStatement(SQL_GET_ADMIN_USER_BY_ID);

			st.setInt(1, id);
			rs=st.executeQuery();
			if(rs.next())
			{
				usr=new User();
				usr.setId(id);
				usr.setEmail(rs.getString(1));
				usr.setUsername(usr.getEmail());
				usr.setFirstName(rs.getString(2));
				usr.setLastName(rs.getString(3));
				usr.setPrivilegeLevel(rs.getInt(4));
				usr.setLastLoginDate(rs.getTimestamp(5, this.cal));
				
				usr.getAddress().setId(-1);
				usr.getAddress().setAddress1(rs.getString(6));
				usr.getAddress().setAddress2(rs.getString(7));
				usr.getAddress().setCity(rs.getString(8));
				usr.getAddress().setState(rs.getString(9));
				usr.getAddress().setCountry(rs.getString(10));
				usr.getAddress().setZipcode(rs.getString(11));
				usr.getAddress().setPhone(rs.getString(12));
				usr.getOrgnization().setId(rs.getInt(13));
				usr.setActivate(rs.getBoolean(14));

				usr.getOrgnization().setAbbrev(rs.getString(15));
				usr.getOrgnization().setName(rs.getString(16));

		
				
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
		
		return usr;
	}
	@Override
	public boolean updateUserStatus(User usr,boolean active) {
		// TODO Auto-generated method stub
		
		if(usr!=null&&usr.getEmail()!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);

				st=conn.prepareStatement(SQL_SET_USER_STATUS);
				st.setBoolean(1, active);
				st.setString(2, usr.getEmail());
				if(st.executeUpdate()==1)
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
		
}
