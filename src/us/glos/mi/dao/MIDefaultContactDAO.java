package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimeZone;

import org.glc.DBConnFactory;
import org.glc.DefaultTimeZoneImpl;
import org.glc.Logger;
import org.glc.domain.User;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.Contact;

public class MIDefaultContactDAO extends DefaultTimeZoneImpl implements IUserDAO {

	private static final String SQL_INSERT_CONTACT="insert into contacts(cnt_email,cnt_first_name,cnt_last_name,cnt_org,cnt_street1,cnt_street2,cnt_city,cnt_state,cnt_country,cnt_zip,cnt_phone,cnt_website,cnt_agn_no) "+ 
    " values (?,?,?,?,?,?,?,?,?) returning cnt_id;";
	private final static String SQL_GET_CONTACT="select cnt_first_name,cnt_last_name,cnt_agn_no,cnt_org,cnt_street1,cnt_street2,cnt_zip,cnt_city,cnt_state,cnt_country,cnt_phone,cnt_email from contacts where cnt_id=?;";
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	protected static boolean active=false;
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
	}
	@Override
	public boolean addUser(User usr) {
		// TODO Auto-generated method stub
		boolean result=false;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		boolean isPerson=true;
		if(usr!=null&&usr instanceof Contact)
		{
			Contact u=(Contact)usr;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    
			    st=conn.prepareStatement(SQL_INSERT_CONTACT);
			    if(u.getEmail()==null)
			    {
			    	isPerson=false;
			    	st.setNull(1, java.sql.Types.VARCHAR);
			    	st.setNull(2, java.sql.Types.VARCHAR);
			    	st.setNull(3, java.sql.Types.VARCHAR);
			    }
			    else
			    {
			    	st.setString(1, u.getEmail());
			    	st.setString(2, u.getFirstName());
			    	st.setString(3, u.getLastName());
			    }
			    if(true==isPerson)
			    	st.setNull(4, java.sql.Types.VARCHAR);
			    else
			    	st.setString(4, u.getOrgnization().getName());
			    st.setString(5, u.getAddress().getAddress1());
			    st.setString(6, u.getAddress().getAddress2());
			    st.setString(7, u.getAddress().getCity());
			    st.setString(8, u.getAddress().getState());
			    st.setString(9, u.getAddress().getCountry());
			    st.setString(10, u.getAddress().getZipcode());
			    st.setString(11, u.getAddress().getPhone());
			    st.setString(12, u.getWebsite());
			    if(true==isPerson)
			    	st.setInt(13, u.getOrgnization().getId());
			    else
			    	st.setNull(13, java.sql.Types.INTEGER);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	u.setId(rs.getInt(1));
			    	result=true;
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
	public int getTotalUserCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		Contact cnt=null;
		
		if(id>0)
		{
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_CONTACT);
			    st.setInt(1, id);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	cnt=new Contact();
			    	
			    	cnt.setId(id);
			    	cnt.setFirstName(rs.getString(1));
			    	cnt.setLastName(rs.getString(2));
			    	cnt.getOrgnization().setId(rs.getInt(3));
			    	cnt.getOrgnization().setName(rs.getString(4));
			    	cnt.getAddress().setAddress1(rs.getString(5));
			    	cnt.getAddress().setAddress2(rs.getString(6));
			    	cnt.getAddress().setZipcode(rs.getString(7));
			    	cnt.getAddress().setCity(rs.getString(8));
			    	cnt.getAddress().setState(rs.getString(9));
			    	cnt.getAddress().setCountry(rs.getString(10));
			    	cnt.getAddress().setPhone(rs.getString(11));
			    	cnt.setEmail(rs.getString(12));
			    	cnt.setUsername(rs.getString(12));
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
		return cnt;
	}

	@Override
	public boolean isUserExist(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidUser(User usr) {
		// TODO Auto-generated method stub
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
		return false;
	}

	@Override
	public ArrayList<User> searchUsers(String fname, String lname, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setPrivilege(User usr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserInfo(User usr,int oid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserStatus(User usr, boolean active) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TimeZone getTimeZone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTimeZone(TimeZone tz) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getOrgnizationId(String abbrev, String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isValidUser(String email, byte[] passwd) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
