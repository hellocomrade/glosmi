package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.glc.DBConnFactory;
import org.glc.DefaultTimeZoneImpl;
import org.glc.Logger;
import org.glc.domain.Address;
import org.glc.domain.Orgnization;
import org.glc.domain.User;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.Contact;



public class MIDefaultAppDAO extends DefaultTimeZoneImpl implements IAppDAO {

	protected static boolean active=false;
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	
	private final static String[] _APPINFO_BY_PAGE_FIELDS=new String[]{"a.app_id","a.app_name","m.mod_name","a.app_desc","a.app_last_mod_date","ras.ras_desc","au.aur_email","au.aur_last_name"};
	private final static String SQL_GET_APPINFO_BY_UID_PAGE_FMT="select a.app_id,m.mod_name,a.app_name,a.app_desc,a.app_last_mod_date,ras.ras_desc,au.aur_email,au.aur_last_name,au.aur_first_name from application_data a inner join model_data m on m.mod_id=a.app_model_id inner join ref_app_status ras on ras.ras_id=a.app_status inner join admin_user au on au.aur_id=a.app_owner_id where a.app_owner_id=? order by %s %s limit ? offset ?;";
	private final static String SQL_GET_APPINFO_BY_PAGE_FMT="select a.app_id,m.mod_name,a.app_name,a.app_desc,a.app_last_mod_date,ras.ras_desc,au.aur_email,au.aur_last_name,au.aur_first_name from application_data a inner join model_data m on m.mod_id=a.app_model_id inner join ref_app_status ras on ras.ras_id=a.app_status inner join admin_user au on au.aur_id=a.app_owner_id order by %s %s limit ? offset ?;";
	private final static String SQL_GET_APP_COUNT="select count(*) from application_data;";
	private final static String SQL_GET_APP_COUNT_BY_OWNER="select count(*) from application_data where app_owner_id=?;";
	private final static String SQL_GET_APP_INFO_BY_ID="select a.app_id,a.app_name,a.app_desc,a.app_purpose,a.app_note,a.app_last_mod_date,ras.ras_desc,au.aur_email,au.aur_last_name,au.aur_first_name,m.mod_name,a.app_model_id,a.app_location,a.app_url from application_data a inner join ref_app_status ras on ras.ras_id=a.app_status inner join admin_user au on au.aur_id=a.app_owner_id inner join model_data m on m.mod_id=a.app_model_id where a.app_id=?;";
	private final static String SQL_GET_PEOPLE_BY_APP_ID="select c.cnt_id,c.cnt_first_name,c.cnt_last_name,c.cnt_street1,c.cnt_street2,c.cnt_city,c.cnt_country,c.cnt_state,c.cnt_zip,c.cnt_phone,c.cnt_email,c.cnt_org,c.cnt_agn_no,c.cnt_website,ac.act_rcc_no,a.agn_name,a.agn_agnid from contacts c inner join app_contacts ac on ac.act_cnt_no=c.cnt_id inner join agencies a on a.agn_id=c.cnt_agn_no where ac.act_app_no=?";
	private final static String SQL_GET_APP_INFO_BY_ID_UID="select a.app_id,a.app_name,a.app_desc,a.app_purpose,a.app_note,a.app_last_mod_date,ras.ras_desc,au.aur_email,au.aur_last_name,au.aur_first_name,m.mod_name,a.app_model_id,a.app_location,a.app_url from application_data a inner join ref_app_status ras on ras.ras_id=a.app_status inner join admin_user au on au.aur_id=a.app_owner_id inner join model_data m on m.mod_id=a.app_model_id where a.app_id=? and a.app_owner_id=?;";
	private final static String SQL_GET_USER_ID="select adp_owner_id from app_data_pending where adp_id=?;";
	private final static String SQL_INSERT_CONTACT="insert into contacts(cnt_first_name,cnt_last_name,cnt_org,cnt_street1,cnt_street2,cnt_city,cnt_state,cnt_country,cnt_zip,cnt_phone,cnt_website,cnt_email,cnt_agn_no) "+
	"values(?,?,?,?,?,?,?,?,?,?,?,?,?) returning cnt_id;";
	private final static String SQL_GET_AGENCY_ID="select agn_id from agencies where lower(agn_agnid)=lower(?) or lower(agn_name)=lower(?);";
	private final static String SQL_GET_NULL_AGENCY_ID="select agn_id from agencies where agn_agnid is NULL and agn_name is NULL;";
	private final static String SQL_GET_CONTACT_ID="select cnt_id from contacts where lower(cnt_first_name)=lower(?) and lower(cnt_last_name)=lower(?) and lower(cnt_email)=lower(?);";
	private final static String SQL_UPDATE_CONTACT="update contacts set cnt_first_name=?,cnt_last_name=?,cnt_org=?,cnt_street1=?,cnt_street2=?,cnt_city=?,cnt_state=?,cnt_country=?,cnt_zip=?,cnt_phone=?,cnt_website=?,cnt_email=?,cnt_agn_no=? where cnt_id=?;";
	
	private final static String SQL_INSERT_APP="insert into application_data(app_model_id,app_name,app_purpose,app_desc,app_note,app_status,app_location,app_url,app_owner_id) "+
	 "values(?,?,?,?,?,?,?,?,?) returning app_id";
	private final static String SQL_UPDATE_APP="update application_data set app_model_id=?,app_name=?,app_purpose=?,app_desc=?, app_note=?,app_status=?,app_location=?,app_url=?,app_last_mod_date=now() where app_id=?";
	private final static String SQL_REMOVE_APP="delete from application_data where app_id=?";
	private final static String SQL_INSERT_CONTACTS_LINK_BY_APPID="insert into app_contacts(act_app_no,act_rcc_no,act_cnt_no) values(?,?,?);";
	private final static String SQL_REMOVE_CONTACTS_LINK_BY_APPID="delete from app_contacts where act_app_no=?;";
	private final static String SQL_REMOVE_PENDING_BY_ID="delete from app_data_pending where adp_id=?;";
	private final static String SQL_IS_OWNER="select count(*) from application_data where app_id=? and app_owner_id=?;";
	private final static String SQL_GET_OWNERID_BY_EMAIL="select aur_id,aur_mask from admin_user where lower(aur_email)=lower(?);";
	private final static String SQL_UPDATE_APP_OWNER="update application_data set app_owner_id=? where app_id=?;";
	private final static String SQL_UPDATE_APP_STATUS="update application_data set app_status=? where app_id=?;";
	private final static String SQL_GET_OWNER_EMAIL_BY_APPID="select a.aur_email from admin_user a inner join application_data m on m.app_owner_id=a.aur_id where m.app_id=?;";
	private final static String SQL_GET_OWNER_ID_BY_APPID="select app_owner_id from application_data where app_id=?;";
	private final static String SQL_GET_APP_BY_MID="select a.app_id,a.app_name,a.app_desc,a.app_purpose,a.app_note,a.app_last_mod_date,ras.ras_desc,au.aur_email,au.aur_last_name,au.aur_first_name,m.mod_name,a.app_model_id,a.app_location,a.app_url from application_data a inner join ref_app_status ras on ras.ras_id=a.app_status inner join admin_user au on au.aur_id=a.app_owner_id inner join model_data m on m.mod_id=a.app_model_id where a.app_status=3 and a.app_model_id=?;";
	private final static String SQL_GET_APP_BY_UID="select a.app_id,a.app_name,a.app_desc,a.app_purpose,a.app_note,a.app_last_mod_date,ras.ras_desc,au.aur_email,au.aur_last_name,au.aur_first_name,m.mod_name,a.app_model_id,a.app_location from application_data a inner join ref_app_status ras on ras.ras_id=a.app_status inner join admin_user au on au.aur_id=a.app_owner_id inner join model_data m on m.mod_id=a.app_model_id inner join app_contacts ac on ac.act_app_no=a.app_id where a.app_status=3 and ac.act_cnt_no=?;";
	private final static String SQL_IS_ATTACHED2MODEL="select count(*) from application_data where app_id=? and app_model_id=?;";
	private final static String SQL_UPDATE_APP_FILES_OWNER="update files set fuid=? where fuid=? and fref_id=? and fsection_id=3";
	
	@Override
	public boolean isAttachedModel(int id, int mid) {
		// TODO Auto-generated method stub
		if(id>0&&mid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_IS_ATTACHED2MODEL);
			    
			    st.setInt(1, id);
			    st.setInt(2, mid);
			    rs=st.executeQuery();
			    if(rs.next())
			    	return rs.getInt(1)==1;
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
		}
		return false;
	}
	@Override
	public ArrayList<AppInfo> getAppInfoByModId(int modid) {
		// TODO Auto-generated method stub
		ArrayList<AppInfo> apps=null;
		AppInfo app=null;
		if(modid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			PreparedStatement st1=null;
			ResultSet rs=null;
			ResultSet rs1=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_APP_BY_MID);
			    
			    st.setInt(1, modid);
			    
			    rs=st.executeQuery();
			    
			    while(rs.next())
			    {
			    	if(apps==null)
			    		apps=new ArrayList<AppInfo>();
			    	app=new AppInfo();
					app.setId(rs.getInt(1));
					app.setName(rs.getString(2));
					app.setAppDescription(rs.getString(3));
					app.setAppPurpose(rs.getString(4));
					app.setAppNote(rs.getString(5));
					app.setLastUpdateDate(rs.getTimestamp(6, this.cal));
					app.setStatus(rs.getString(7));
					app.setOwnerEmail(rs.getString(8));
					app.setOwnerName(rs.getString(9)+" "+rs.getString(10));
					app.setModelName(rs.getString(11));
					app.setModId(rs.getInt(12));
					app.setLocation(rs.getString(13));
					app.setUrl(rs.getString(14));
					//rs.close();
					//st.close();
										
					st1=conn.prepareStatement(SQL_GET_PEOPLE_BY_APP_ID);
					st1.setInt(1, app.getId());
					rs1=st1.executeQuery();
					this.fillPeople(app, rs1);
					rs1.close();
					st1.close();
			    	apps.add(app);
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
					if(rs1!=null)
						rs1.close();
					if(rs!=null)
						rs.close();
					if(st1!=null)
						st1.close();
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
		return apps;
	}
	@Override
	public int getOwnerIdByAppId(int appid) {
		// TODO Auto-generated method stub
		if(appid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_OWNER_ID_BY_APPID);
			    
			    st.setInt(1, appid);
			    
			    rs=st.executeQuery();
			    
			    if(rs.next())
			    	return rs.getInt(1);
			    
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
		}
		return -1;
	}
	@Override
	public String getOwnerEmailByAppId(int appid) {
		// TODO Auto-generated method stub
		if(appid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_OWNER_EMAIL_BY_APPID);
			    
			    st.setInt(1, appid);
			    
			    rs=st.executeQuery();
			    
			    if(rs.next())
			    	return rs.getString(1);
			    
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
		}
		return null;
	}
	@Override
	public int addAppInfo(AppInfo app,int uid) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
			if(app!=null)//&&app.getSerialId()>0)
			{
				
				if(uid>0)
				{
					conn=DBConnFactory.getConnection(DB_SOURCE_W);
					conn.setAutoCommit(false);
					HashMap<String,Contact> distinctNewCnt=new HashMap<String,Contact>();
					for(Contact cnt:app.getContacts())
					{
						if(cnt.isNew())
						{
							if(!distinctNewCnt.containsKey(cnt.getEmail().toLowerCase()))
							{
								cnt.setId(-1);
								st=conn.prepareStatement(SQL_INSERT_CONTACT);
								this.fillContact(st, cnt);
								rs=st.executeQuery();
								if(rs.next())
								{
									cnt.setId(rs.getInt(1));
									distinctNewCnt.put(cnt.getEmail().toLowerCase(), cnt);
								}
								else
									throw new SQLException();
								rs.close();
								st.close();
							}
							else//avoid duplicated insertion
								cnt.setId(distinctNewCnt.get(cnt.getEmail().toLowerCase()).getId());
						}
						else if(cnt.isUpdate())
						{
							int cid=this.getContactId(cnt.getFirstName(), cnt.getLastName(), cnt.getEmail());
							if(cid>0)
							{
								st=conn.prepareStatement(SQL_UPDATE_CONTACT);
								cnt.setId(cid);
								this.fillContact(st, cnt);
								if(1!=st.executeUpdate())
									throw new SQLException();
								st.close();
							}
							else
								throw new SQLException(this.getClass().getName()+" addAppInfo:update failed. Contact doesn't exist.");
						}
						else if(cnt.isExist())
						{
							int cid=this.getContactId(cnt.getFirstName(), cnt.getLastName(), cnt.getEmail());
							if(cid>0)
							{
								cnt.setId(cid);
								continue;
							}
							else
								throw new SQLException(this.getClass().getName()+" addAppInfo:check existing id failed. Contact doesn't exist.");
						}
					}
					st=conn.prepareStatement(SQL_INSERT_APP);
					this.fillApp(st, app, uid);
					rs=st.executeQuery();
					if(rs.next()==false)
						throw new SQLException();
					int aid=rs.getInt(1);
					rs.close();
					st.close();
					
					st=conn.prepareStatement(SQL_INSERT_CONTACTS_LINK_BY_APPID);
					for(Contact cnt:app.getContacts())
					{
						st.setInt(1, aid);
						st.setInt(2, 3);
						st.setInt(3, cnt.getId());
						st.addBatch();
					}
					int[] re=st.executeBatch();
					st.close();
					for(int i:re)
						if(i!=1)
							throw new SQLException();
					
					if(app.getSerialId()>0)
					{
						st=conn.prepareStatement(SQL_REMOVE_PENDING_BY_ID);
						st.setInt(1, app.getSerialId());
						if(1!=st.executeUpdate())
							throw new SQLException();
					}
					
					conn.setAutoCommit(true);
					return aid;
				}
			}
			
		}
		catch(SQLException se)
		{
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
			}
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
		return -1;
	}
	private void fillApp(PreparedStatement st, AppInfo app, int uid) throws SQLException
	{
		if(st!=null&&app!=null&&uid>0)
		{
			st.setInt(1, app.getModId());
			st.setString(2, app.getName());
			st.setString(3, app.getAppPurpose());
			st.setString(4, app.getAppDescription());
			if(app.getAppNote()!=null)
				st.setString(5, app.getAppNote());
			else
				st.setNull(5, java.sql.Types.LONGVARCHAR);
			st.setInt(6, 3);
			st.setString(7, app.getLocation());
			if(app.getUrl()!=null)
				st.setString(8, app.getUrl());
			else
				st.setNull(8, java.sql.Types.LONGVARCHAR);
			st.setInt(9, uid);
			
		}
	}
	private int getUserIdByDraftId(int id)
	{
		if(id<=0)return -1;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    
		    st=conn.prepareStatement(SQL_GET_USER_ID);
		    
		    st.setInt(1, id);
		    rs=st.executeQuery();
		    
		    if(rs.next())
		    	return rs.getInt(1);
		    
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
	private void fillContact(PreparedStatement st,Contact cnt) throws SQLException
	{
		if(st!=null&&cnt!=null)
		{
			int aid=-1;
			if(cnt.getOrgnization()!=null)
			{
				if(cnt.getOrgnization().getId()>0)//it should be checked in the upper layer already
					aid=cnt.getOrgnization().getId();
				else
					aid=getOrgnizationId(cnt.getOrgnization().getAbbrev(),cnt.getOrgnization().getName());
				
			}
			//if(aid>0)
			{
				if(cnt.getFirstName()!=null)
					st.setString(1, cnt.getFirstName());
				else
					st.setNull(1, java.sql.Types.LONGVARCHAR);
				if(cnt.getLastName()!=null)
					st.setString(2, cnt.getLastName());
				else
					st.setNull(2, java.sql.Types.LONGVARCHAR);
				
				if(aid>0)//aid larger than 0 means that the org exists in db and should be reference here
					st.setString(3, cnt.getOrgnization().getName());
				else
					st.setNull(3, java.sql.Types.INTEGER);
				if(cnt.getAddress()!=null&&cnt.getAddress().getAddress1()!=null)
					st.setString(4, cnt.getAddress().getAddress1());
				else
					st.setNull(4, java.sql.Types.LONGVARCHAR);

				if(cnt.getAddress()!=null&&cnt.getAddress().getAddress2()!=null)
					st.setString(5, cnt.getAddress().getAddress2());
				else
					st.setNull(5, java.sql.Types.LONGVARCHAR);

				if(cnt.getAddress()!=null&&cnt.getAddress().getCity()!=null)
					st.setString(6, cnt.getAddress().getCity());
				else
					st.setNull(6, java.sql.Types.LONGVARCHAR);

				if(cnt.getAddress()!=null&&cnt.getAddress().getState()!=null)
					st.setString(7, cnt.getAddress().getState());
				else
					st.setNull(7, java.sql.Types.LONGVARCHAR);

				if(cnt.getAddress()!=null&&cnt.getAddress().getCountry()!=null)
					st.setString(8, cnt.getAddress().getCountry());
				else
					st.setNull(8, java.sql.Types.LONGVARCHAR);

				if(cnt.getAddress()!=null&&cnt.getAddress().getZipcode()!=null)
					st.setString(9, cnt.getAddress().getZipcode());
				else
					st.setNull(9, java.sql.Types.LONGVARCHAR);

				if(cnt.getAddress()!=null&&cnt.getAddress().getPhone()!=null)
					st.setString(10, cnt.getAddress().getPhone());
				else
					st.setNull(10, java.sql.Types.LONGVARCHAR);

				if(cnt.getWebsite()!=null)
					st.setString(11, cnt.getWebsite());
				else
					st.setNull(11, java.sql.Types.LONGVARCHAR);

				if(cnt.getEmail()!=null)
					st.setString(12, cnt.getEmail());
				else
					st.setNull(12, java.sql.Types.LONGVARCHAR);
				st.setInt(13, aid);
				if(cnt.getId()>0)
					st.setInt(14, cnt.getId());
			}
			
		}
	}
	@Override
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
	private int getContactId(String fname,String lname,String email)
	{
		if(fname!=null&&lname!=null&&email!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			int cid=0;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_CONTACT_ID);
			    
			    st.setString(1, fname);
			    st.setString(2, lname);
			    st.setString(3, email);
			    rs=st.executeQuery();
			    
			    if(rs.next())
			    	cid=rs.getInt(1);
			    else
			    	return -1;
			    return cid;
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
			
		}
		return -1;
	}
	@Override
	public int getAppCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		int count=0;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.createStatement();
		    
		    rs=st.executeQuery(SQL_GET_APP_COUNT);
		    
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
	public int getAppCountByOwner(int uid) {
		// TODO Auto-generated method stub
		if(uid<=0)return 0;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int count=0;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_GET_APP_COUNT_BY_OWNER);
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
	private void fillPeople(AppInfo app,ResultSet rs) throws SQLException
	{
		if(app!=null&&rs!=null)
		{
			Contact cnt=null;
			Orgnization org=null;
			Address add=null;
			int type=0;
			while(rs.next())
			{
				cnt=new Contact();
				add=new Address();
				org=new Orgnization();
				cnt.setId(rs.getInt(1));
				cnt.setFirstName(rs.getString(2));
				cnt.setLastName(rs.getString(3));
				add.setAddress1(rs.getString(4));
				add.setAddress2(rs.getString(5));
				add.setCity(rs.getString(6));
				add.setCountry(rs.getString(7));
				add.setState(rs.getString(8));
				add.setZipcode(rs.getString(9));
				add.setPhone(rs.getString(10));
				cnt.setAddress(add);
				cnt.setEmail(rs.getString(11));
				org.setId(rs.getInt(13));
				cnt.setWebsite(rs.getString(14));
				type=rs.getInt(15);
				org.setName(rs.getString(16));
				org.setAbbrev(rs.getString(17));
				cnt.setOrgnization(org);
				if(type==1)
					app.setDeveloper(cnt);
				else if(type==2)
					app.setDistributor(cnt);
				else if(type==3)
					app.setContact(cnt);
			}
		}
	}
	@Override
	public AppInfo getAppInfoById(int id) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		AppInfo app=null;
		
		if(id>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(SQL_GET_APP_INFO_BY_ID);
				st.setInt(1, id);
				
				rs=st.executeQuery();
				if(rs.next())
				{
					app=new AppInfo();
					app.setId(rs.getInt(1));
					app.setName(rs.getString(2));
					app.setAppDescription(rs.getString(3));
					app.setAppPurpose(rs.getString(4));
					app.setAppNote(rs.getString(5));
					app.setLastUpdateDate(rs.getTimestamp(6, this.cal));
					app.setStatus(rs.getString(7));
					app.setOwnerEmail(rs.getString(8));
					app.setOwnerName(rs.getString(9)+" "+rs.getString(10));
					app.setModelName(rs.getString(11));
					app.setModId(rs.getInt(12));
					app.setLocation(rs.getString(13));
					app.setUrl(rs.getString(14));
					rs.close();
					st.close();
										
					st=conn.prepareStatement(SQL_GET_PEOPLE_BY_APP_ID);
					st.setInt(1, id);
					rs=st.executeQuery();
					this.fillPeople(app, rs);
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
		return app;
	}

	@Override
	public AppInfo getAppInfoByIdAndUId(int aid, int uid) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		AppInfo app=null;
		
		if(aid>0&&uid>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(SQL_GET_APP_INFO_BY_ID_UID);
				st.setInt(1, aid);
				st.setInt(2, uid);
				rs=st.executeQuery();
				if(rs.next())
				{
					app=new AppInfo();
					app.setId(rs.getInt(1));
					app.setName(rs.getString(2));
					app.setAppDescription(rs.getString(3));
					app.setAppPurpose(rs.getString(4));
					app.setAppNote(rs.getString(5));
					app.setLastUpdateDate(rs.getTimestamp(6, this.cal));
					app.setStatus(rs.getString(7));
					app.setOwnerEmail(rs.getString(8));
					app.setOwnerName(rs.getString(9)+" "+rs.getString(10));
					app.setModelName(rs.getString(11));
					app.setModId(rs.getInt(12));
					app.setLocation(rs.getString(13));
					app.setUrl(rs.getString(14));
					rs.close();
					st.close();
					st=conn.prepareStatement(SQL_GET_PEOPLE_BY_APP_ID);
					st.setInt(1, aid);
					rs=st.executeQuery();
					this.fillPeople(app, rs);
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
		return app;
	}

	@Override
	public ArrayList<AppInfo> getAppInfoByOwnerAndPage(int uid, int start,
			int count, int sortByColIdx, boolean isAsc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<AppInfo> infos=null;
		AppInfo info=null;
		String sql=null;
		if(uid>0&&start>=0&&count>0&&sortByColIdx>=0&&sortByColIdx<_APPINFO_BY_PAGE_FIELDS.length)
		{
			try
			{
				if(_APPINFO_BY_PAGE_FIELDS[sortByColIdx].equals("au.aur_last_name"))
					sql=String.format(SQL_GET_APPINFO_BY_UID_PAGE_FMT,
							_APPINFO_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
							"au.aur_first_name"+" "+(isAsc?"asc":"desc"));
				else
				    sql=String.format(SQL_GET_APPINFO_BY_UID_PAGE_FMT,
				    		_APPINFO_BY_PAGE_FIELDS[sortByColIdx],
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
						infos=new ArrayList<AppInfo>();
					info=new AppInfo();
					info.setId(rs.getInt(1));
					info.setModelName(rs.getString(2));
					info.setName(rs.getString(3));
					info.setAppDescription(rs.getString(4));
					info.setLastUpdateDate(rs.getTimestamp(5, this.cal));
					
					info.setStatus(rs.getString(6));
					info.setOwnerEmail(rs.getString(7));
					info.setOwnerName(rs.getString(8)+" "+rs.getString(9));
					infos.add(info);
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
	public ArrayList<AppInfo> getAppInfoByPage(int start, int count,
			int sortByColIdx, boolean isAsc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<AppInfo> infos=null;
		AppInfo info=null;
		String sql=null;
		if(start>=0&&count>0&&sortByColIdx>=0&&sortByColIdx<_APPINFO_BY_PAGE_FIELDS.length)
		{
			try
			{
				if(_APPINFO_BY_PAGE_FIELDS[sortByColIdx].equals("au.aur_last_name"))
					sql=String.format(SQL_GET_APPINFO_BY_PAGE_FMT,
							_APPINFO_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
							"au.aur_first_name"+" "+(isAsc?"asc":"desc"));
				else
				    sql=String.format(SQL_GET_APPINFO_BY_PAGE_FMT,
				    		_APPINFO_BY_PAGE_FIELDS[sortByColIdx],
						    isAsc?"asc":"desc");
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(sql);
				st.setInt(1, count);
				st.setInt(2, start);
				rs=st.executeQuery();
				while(rs.next())
				{
					if(infos==null)
						infos=new ArrayList<AppInfo>();
					info=new AppInfo();
					info.setId(rs.getInt(1));
					info.setModelName(rs.getString(2));
					info.setName(rs.getString(3));
					info.setAppDescription(rs.getString(4));
					info.setLastUpdateDate(rs.getTimestamp(5, this.cal));
					
					info.setStatus(rs.getString(6));
					info.setOwnerEmail(rs.getString(7));
					info.setOwnerName(rs.getString(8)+" "+rs.getString(9));
					infos.add(info);
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
	public ArrayList<AppInfo> getAppInfoByUID(int id,boolean isRequireContact) {
		// TODO Auto-generated method stub
		ArrayList<AppInfo> apps=null;
		AppInfo app=null;
		if(id>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			PreparedStatement st1=null;
			ResultSet rs=null;
			ResultSet rs1=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_APP_BY_UID);
			    
			    st.setInt(1, id);
			    
			    rs=st.executeQuery();
			    
			    while(rs.next())
			    {
			    	if(apps==null)
			    		apps=new ArrayList<AppInfo>();
			    	app=new AppInfo();
					app.setId(rs.getInt(1));
					app.setName(rs.getString(2));
					app.setAppDescription(rs.getString(3));
					app.setAppPurpose(rs.getString(4));
					app.setAppNote(rs.getString(5));
					app.setLastUpdateDate(rs.getTimestamp(6, this.cal));
					app.setStatus(rs.getString(7));
					app.setOwnerEmail(rs.getString(8));
					app.setOwnerName(rs.getString(9)+" "+rs.getString(10));
					app.setModelName(rs.getString(11));
					app.setModId(rs.getInt(12));
					app.setLocation(rs.getString(13));
					//rs.close();
					//st.close();
					if(isRequireContact)
					{
						st1=conn.prepareStatement(SQL_GET_PEOPLE_BY_APP_ID);
						st1.setInt(1, app.getId());
						rs1=st1.executeQuery();
						this.fillPeople(app, rs1);
						rs1.close();
						st1.close();
					}
			    	apps.add(app);
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
					if(rs1!=null)
						rs1.close();
					if(rs!=null)
						rs.close();
					if(st1!=null)
						st1.close();
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
		return apps;
	}

	@Override
	public String[] getFriendlyPagingFldNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOwnerOfApp(int uid, int aid) {
		// TODO Auto-generated method stub
		if(uid<=0||aid<=0)return false;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		boolean result=false;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_IS_OWNER);
		    st.setInt(1, aid);
		    st.setInt(2, uid);
		    rs=st.executeQuery();
		    
		    if(rs.next())
		    {
		    	result=(1==rs.getInt(1));
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
		return result;
	}

	@Override
	public User isPossibleAppOwner(String email) {
		// TODO Auto-generated method stub
		User uid=null;
		if(email!=null&&email.length()>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GET_OWNERID_BY_EMAIL);
			    st.setString(1,email);
			    rs=st.executeQuery();
			    
			    if(rs.next())
			    {
			      	uid=new User();
			    	uid.setId(rs.getInt(1));
			    	uid.setPrivilegeLevel(rs.getInt(2));
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
		return uid;
	}

	@Override
	public boolean removeAppInfoById(int aid) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		if(aid>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
				conn.setAutoCommit(false);
				st=conn.prepareStatement(SQL_REMOVE_CONTACTS_LINK_BY_APPID);
				st.setInt(1, aid);
				
				if(st.executeUpdate()<=0)
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_APP);
				st.setInt(1, aid);
				if(1!=st.executeUpdate())
					throw new SQLException();
				st.close();
				conn.setAutoCommit(true);
				return true;
			}
			catch(Exception se)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
				}
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
	public boolean updateAppInfo(AppInfo app) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(app!=null&&app.getId()>0)
		{
			try
			{
				HashMap<String,Contact> distinctNewCnt=new HashMap<String,Contact>();
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
				conn.setAutoCommit(false);
				for(Contact cnt:app.getContacts())
				{
					if(cnt.isNew())
					{
						if(!distinctNewCnt.containsKey(cnt.getEmail().toLowerCase()))
						{
							cnt.setId(-1);
							st=conn.prepareStatement(SQL_INSERT_CONTACT);
							this.fillContact(st, cnt);
							rs=st.executeQuery();
							if(rs.next())
							{
								cnt.setId(rs.getInt(1));
								distinctNewCnt.put(cnt.getEmail().toLowerCase(), cnt);
							}
							else
								throw new SQLException();
							rs.close();
							st.close();
						}
						else//avoid duplicated insertion
							cnt.setId(distinctNewCnt.get(cnt.getEmail().toLowerCase()).getId());
					}
					else if(cnt.isUpdate())
					{
						int cid=this.getContactId(cnt.getFirstName(), cnt.getLastName(), cnt.getEmail());
						if(cid>0)
						{
							st=conn.prepareStatement(SQL_UPDATE_CONTACT);
							cnt.setId(cid);
							this.fillContact(st, cnt);
							if(1!=st.executeUpdate())
								throw new SQLException();
							st.close();
						}
						else
							throw new SQLException();
					}
					else if(cnt.isExist())
					{
						int cid=this.getContactId(cnt.getFirstName(), cnt.getLastName(), cnt.getEmail());
						if(cid>0)
						{
							cnt.setId(cid);
							continue;
						}
						else
							throw new SQLException();
					}
				}
				
				st=conn.prepareStatement(SQL_UPDATE_APP);
				this.fillApp(st, app, app.getId());
				if(1!=st.executeUpdate())
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_CONTACTS_LINK_BY_APPID);
				st.setInt(1, app.getId());
				if(st.executeUpdate()<=0)
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_INSERT_CONTACTS_LINK_BY_APPID);
				for(Contact cnt:app.getContacts())
				{
					st.setInt(1, app.getId());
					st.setInt(2, 3);
					st.setInt(3, cnt.getId());
					st.addBatch();
				}
				int[] re=st.executeBatch();
				st.close();
				for(int i:re)
					if(i!=1)
						throw new SQLException();
				
				if(app.getSerialId()>0)
				{
					st=conn.prepareStatement(SQL_REMOVE_PENDING_BY_ID);
					st.setInt(1, app.getSerialId());
					if(1!=st.executeUpdate())
						throw new SQLException();
				}
				
				conn.setAutoCommit(true);
				return true;
			}
			catch(Exception se)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
				}
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
	public boolean updateAppOwner(int modid, int uid) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(modid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			int oldowner=-1;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GET_OWNER_ID_BY_APPID);
			    st.setInt(1, modid);
			    rs=st.executeQuery();
			    if(rs.next())
			    	oldowner=rs.getInt(1);
			    rs.close();
			    st.close();
			    conn.close();
			    rs=null;
			    st=null;
			    conn=null;
			    if(oldowner>0)
			    {
			    	try
			    	{
			    		conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    		conn.setAutoCommit(false);
			    		st=conn.prepareStatement(SQL_UPDATE_APP_FILES_OWNER);
			    		st.setInt(1, uid);
			    		st.setInt(2, oldowner);
			    		st.setInt(3, modid);
			    		st.executeUpdate();
			    		st.close();
			    		st=conn.prepareStatement(SQL_UPDATE_APP_OWNER);
			    		st.setInt(1, uid);
			    		st.setInt(2, modid);
			    		if(1==st.executeUpdate())
			    		{
			    			conn.setAutoCommit(true);
			    			result=true;
			    		}
			    		else
			    			throw new Exception("MIDefaultAppDAO::updateAppOwner: Failed to update app owner");
			    	}
			    	catch (Exception e) {
						// TODO Auto-generated catch block
			    		try {
			    			Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
							conn.rollback();
						} catch (SQLException se) {
							// TODO Auto-generated catch block
							Logger.writeLog(se.getMessage(), LogLevel.SEVERE);
						}
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
	public boolean updateAppStatus(int modid, boolean isActive) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(modid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_UPDATE_APP_STATUS);
			    st.setInt(1, isActive?3:4);
			    st.setInt(2, modid);
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

	

}
