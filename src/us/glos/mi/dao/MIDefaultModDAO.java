package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import org.glc.DBConnFactory;
import org.glc.DefaultTimeZoneImpl;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;
import org.glc.domain.Address;
import org.glc.domain.Orgnization;
import org.glc.domain.User;

import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.Contact;

public class MIDefaultModDAO extends DefaultTimeZoneImpl implements IModDAO {

	protected static boolean active=false;
	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	
	//private final static String SQL_GET_MODELINFO_BY_PAGE="select m.mod_id,m.mod_name,m.mod_desc,m.mod_last_mod_date,m.mod_status,c.cnt_first_name,c.cnt_last_name from model_data m inner join model_contacts mc on mc.mc_mod_no=m.mod_id inner join contacts c on c.cnt_id=mc.mc_cnt_no where mc.mc_mod_rcc_no=1;";
	private final static String SQL_GET_MODELINFO_BY_PAGE_FMT="select m.mod_id,m.mod_name,m.mod_desc,m.mod_last_mod_date,m.mod_ver_no,rms.rms_desc,au.aur_email,au.aur_last_name,au.aur_first_name from model_data m inner join ref_mod_status rms on rms.rms_id=m.mod_status inner join admin_user au on au.aur_id=m.mod_owner_id order by %s %s limit ? offset ?;";
	private final static String SQL_GET_MODELINFO_BY_UID_PAGE_FMT="select m.mod_id,m.mod_name,m.mod_desc,m.mod_last_mod_date,m.mod_ver_no,rms.rms_desc,au.aur_email,au.aur_last_name,au.aur_first_name from model_data m inner join ref_mod_status rms on rms.rms_id=m.mod_status inner join admin_user au on au.aur_id=m.mod_owner_id where m.mod_owner_id=? order by %s %s limit ? offset ?;";
	private final static String[] MODELINFO_BY_PAGE_FIELDS=new String[]{"Id","Name","Description","Last Update","Version","Status","Owner Email","Owner Last Name","Owner First Name"};
	private final static String[] _MODELINFO_BY_PAGE_FIELDS=new String[]{"m.mod_id","m.mod_name","m.mod_desc","m.mod_ver_no","m.mod_last_mod_date","rms.rms_desc","au.aur_email","au.aur_last_name"};
	private final static String SQL_GET_MODEL_COUNT="select count(*) from model_data;";
	private final static String SQL_GET_MODEL_COUNT_BY_OWNER="select count(*) from model_data where mod_owner_id=?;";
	private final static String SQL_IS_OWNER="select count(*) from model_data where mod_id=? and mod_owner_id=?;";
	private final static String SQL_GET_MODEL_INFO_BY_ID="select m.mod_id,m.mod_name,m.mod_desc,m.mod_ver_no,m.mod_attri,m.mod_avail_no,m.mod_data_req,m.mod_skill,m.mod_note,m.mod_last_mod_date,m.mod_modid,rms.rms_desc,m.mod_strength,au.aur_email,au.aur_last_name,au.aur_first_name,m.mod_url from model_data m inner join ref_mod_status rms on rms.rms_id=m.mod_status inner join admin_user au on au.aur_id=m.mod_owner_id where m.mod_id=?;";
	private final static String SQL_GET_MODEL_INFO_BY_ID_UID="select m.mod_id,m.mod_name,m.mod_desc,m.mod_ver_no,m.mod_attri,m.mod_avail_no,m.mod_data_req,m.mod_skill,m.mod_note,m.mod_last_mod_date,m.mod_modid,rms.rms_desc,m.mod_strength,au.aur_email,au.aur_last_name,au.aur_first_name,m.mod_url from model_data m inner join ref_mod_status rms on rms.rms_id=m.mod_status inner join admin_user au on au.aur_id=m.mod_owner_id where m.mod_id=? and m.mod_owner_id=?;";
	private final static String SQL_GET_THEME_BY_ID="select thm_no from theme_data where thm_mod_id=?";
	private final static String SQL_GET_CATEGORY_BY_ID="select cat_no from cat_data where cat_mod_id=?";
	private final static String SQL_GET_PEOPLE_BY_MODEL_ID="select c.cnt_id,c.cnt_first_name,c.cnt_last_name,c.cnt_street1,c.cnt_street2,c.cnt_city,c.cnt_country,c.cnt_state,c.cnt_zip,c.cnt_phone,c.cnt_email,c.cnt_org,c.cnt_agn_no,c.cnt_website,mc.mc_mod_rcc_no,a.agn_name,a.agn_agnid from contacts c inner join model_contacts mc on mc.mc_cnt_no=c.cnt_id inner join agencies a on a.agn_id=c.cnt_agn_no where mc.mc_mod_no=?";
	
	private final static String SQL_GET_CONTACT_ID="select cnt_id from contacts where lower(cnt_first_name)=lower(?) and lower(cnt_last_name)=lower(?) and lower(cnt_email)=lower(?);";
	private final static String SQL_REMOVE_CONTACTS_LINK_BY_MODID="delete from model_contacts where mc_mod_no=?;";
	private final static String SQL_INSERT_CONTACTS_LINK_BY_MODID="insert into model_contacts(mc_mod_no,mc_mod_rcc_no,mc_cnt_no) values(?,?,?);";
	private final static String SQL_INSERT_CONTACT="insert into contacts(cnt_first_name,cnt_last_name,cnt_org,cnt_street1,cnt_street2,cnt_city,cnt_state,cnt_country,cnt_zip,cnt_phone,cnt_website,cnt_email,cnt_agn_no) "+
													"values(?,?,?,?,?,?,?,?,?,?,?,?,?) returning cnt_id;";
	private final static String SQL_UPDATE_CONTACT="update contacts set cnt_first_name=?,cnt_last_name=?,cnt_org=?,cnt_street1=?,cnt_street2=?,cnt_city=?,cnt_state=?,cnt_country=?,cnt_zip=?,cnt_phone=?,cnt_website=?,cnt_email=?,cnt_agn_no=? where cnt_id=?;";
	private final static String SQL_GET_AGENCY_ID="select agn_id from agencies where lower(agn_agnid)=lower(?) or lower(agn_name)=lower(?);";
	private final static String SQL_GET_NULL_AGENCY_ID="select agn_id from agencies where agn_agnid is NULL and agn_name is NULL;";
	private final static String SQL_GET_USER_ID="select mdd_owner_id from model_data_pending where mdd_id=?;";
	private final static String SQL_GET_OWNER_ID="select mod_owner_id from model_data where mod_id=?;";
	private final static String SQL_INSERT_MODEL="insert into model_data(mod_name,mod_modid,mod_desc,mod_ver_no,mod_attri,mod_avail_no,mod_data_req,mod_skill,mod_note,mod_status,mod_strength,mod_url,mod_owner_id) "+
												 "values(?,?,?,?,?,?,?,?,?,?,?,?,?) returning mod_id";
	private final static String SQL_UPDATE_MODEL="update model_data set mod_name=?,mod_modid=?,mod_desc=?,mod_ver_no=?,mod_attri=?,mod_avail_no=?,mod_data_req=?,mod_skill=?,mod_note=?,mod_status=?,mod_strength=?,mod_url=?,mod_last_mod_date=now() where mod_id=?";
	private final static String SQL_REMOVE_MODEL="delete from model_data where mod_id=?";
	private final static String SQL_REMOVE_PENDING_BY_ID="delete from model_data_pending where mdd_id=?;";
	private final static String SQL_INSERT_MODEL_THEME="insert into theme_data(thm_mod_id,thm_no) values(?,?);";
	private final static String SQL_REMOVE_MODEL_THEME="delete from theme_data where thm_mod_id=?;";
	private final static String SQL_INSERT_MODEL_CATEGORY="insert into cat_data(cat_mod_id,cat_no) values(?,?);";
	private final static String SQL_REMOVE_MODEL_CATEGORY="delete from cat_data where cat_mod_id=?;";
	private final static String SQL_REMOVE_MODEL_INDEX="delete from model_index where midx_mod_id=?;";
	private final static String SQL_GET_OWNERID_BY_EMAIL="select aur_id,aur_mask from admin_user where lower(aur_email)=lower(?);";
	private final static String SQL_UPDATE_MODEL_OWNER="update model_data set mod_owner_id=? where mod_id=?;";
	private final static String SQL_UPDATE_MODEL_FILES_OWNER="update files set fuid=? where fuid=? and fref_id=? and (fsection_id=1 or fsection_id=2)";
	private final static String SQL_UPDATE_MODEL_STATUS="update model_data set mod_status=? where mod_id=?;";
	private final static String SQL_GET_OWNER_BY_MID="select a.aur_email from admin_user a inner join model_data m on m.mod_owner_id=a.aur_id where m.mod_id=?;";
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
		
	}
	
	@Override
	public String getOwnerEmailByModelId(int modid) {
		// TODO Auto-generated method stub
		if(modid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_OWNER_BY_MID);
			    
			    st.setInt(1, modid);
			    
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
	private void fillModel(PreparedStatement st,ModelInfo mod,int uid) throws SQLException
	{
		if(mod!=null&&st!=null)
		{
			st.setString(1, mod.getName());
			if(mod.getAbbreviation()!=null)
				st.setString(2, mod.getAbbreviation());
			else
				st.setNull(2, java.sql.Types.LONGVARCHAR);
			
			if(mod.getDescription()!=null)
				st.setString(3, mod.getDescription());
			else
				st.setNull(3, java.sql.Types.LONGVARCHAR);
			
			if(mod.getVersionNo()!=null)
				st.setString(4, mod.getVersionNo());
			else
				st.setNull(4, java.sql.Types.LONGVARCHAR);
			
			if(mod.getAttribute()!=null)
				st.setString(5, mod.getAttribute());
			else
				st.setNull(5, java.sql.Types.LONGVARCHAR);
			
			if(mod.getAvailableId()>0)
				st.setInt(6, mod.getAvailableId());
			else
				st.setNull(6, java.sql.Types.INTEGER);
			
			if(mod.getDataRequirement()!=null)
				st.setString(7, mod.getDataRequirement());
			else
				st.setNull(7, java.sql.Types.LONGVARCHAR);
			
			if(mod.getSkillLevel()!=null)
				st.setString(8, mod.getSkillLevel());
			else
				st.setNull(8, java.sql.Types.LONGVARCHAR);
			
			if(mod.getNote()!=null)
				st.setString(9, mod.getNote());
			else
				st.setNull(9, java.sql.Types.LONGVARCHAR);
			
			st.setInt(10, 3);
						
			if(mod.getStrength()!=null)
				st.setString(11, mod.getStrength());
			else
				st.setNull(11, java.sql.Types.LONGVARCHAR);
			if(mod.getUrl()!=null)
				st.setString(12, mod.getUrl());
			else
				st.setNull(12, java.sql.Types.LONGVARCHAR);
			if(uid>0)
				st.setInt(13, uid);
			
		}
	}
	private int getContactId(String fname,String lname,String email)
	{
		if(fname!=null&&lname!=null&&email!=null)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    
			    st=conn.prepareStatement(SQL_GET_CONTACT_ID);
			    
			    st.setString(1, fname);
			    st.setString(2, lname);
			    st.setString(3, email);
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
	@Override
	public int getOwnerIdByModId(int id)
	{
		if(id<=0)return -1;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    
		    st=conn.prepareStatement(SQL_GET_OWNER_ID);
		    
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
	private void processContact(Connection conn,ArrayList<Contact> cnts,HashMap<String,Contact> distinctNewCnt) throws SQLException
	{
		if(conn!=null)
		{
			PreparedStatement st=null;
			ResultSet rs=null;
			for(Contact cnt:cnts)
			{
				if(cnt.isNew())
				{
					if(!distinctNewCnt.containsKey(cnt.getEmail().toLowerCase()))
					{
						//clean up the id, if this new contact is created based upon an existing one
						//although id is provided, it should be removed
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
					//bAddNewCnt=true;
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
							throw new SQLException(this.getClass().getName()+" processContact:update failed. Contact doesn't exist.");
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
						throw new SQLException(this.getClass().getName()+" processContact:check existing id failed. Contact doesn't exist.");
				}
			}
		}
	}
	@Override
	public int addModelInfo(ModelInfo mod,int uid) {
		// TODO Auto-generated method stub
		int result=-1;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		//pending id could be null if an auditor submit through an approved case
		if(mod!=null)//&&mod.getSerialId()>0)
		{
			try
			{
				if(uid>0)
				{
					HashMap<String,Contact> distinctNewCnt=new HashMap<String,Contact>();
					conn=DBConnFactory.getConnection(DB_SOURCE_W);
					conn.setAutoCommit(false);

					this.processContact(conn, mod.getDevelopers(), distinctNewCnt);

					this.processContact(conn, mod.getDistributors(), distinctNewCnt);

					this.processContact(conn, mod.getContacts(), distinctNewCnt);
					
					st=conn.prepareStatement(SQL_INSERT_MODEL);
					this.fillModel(st, mod, uid);
					rs=st.executeQuery();
					if(rs.next()==false)
						throw new SQLException();
					int mid=rs.getInt(1);
					rs.close();
					st.close();
					
					st=conn.prepareStatement(SQL_INSERT_MODEL_THEME);
					for(int i:mod.getThemeIds())
					{
						st.setInt(1, mid);
						st.setInt(2, i);
						st.addBatch();
					}
					int[] re=st.executeBatch();
					for(int i:re)
						if(i!=1)
							throw new SQLException();
					st.close();
					re=null;
					
					st=conn.prepareStatement(SQL_INSERT_MODEL_CATEGORY);
					for(int i:mod.getCategoryIds())
					{
						st.setInt(1, mid);
						st.setInt(2, i);
						st.addBatch();
					}
					re=st.executeBatch();
					for(int i:re)
						if(i!=1)
							throw new SQLException();
					
					st.close();
					re=null;
					//if(bAddNewCnt)
					{
						st=conn.prepareStatement(SQL_INSERT_CONTACTS_LINK_BY_MODID);

						for(Contact cnt:mod.getDevelopers())
							//if(cnt.isNew())
							{
								st.setInt(1, mid);
								st.setInt(2, 1);
								st.setInt(3, cnt.getId());
								st.addBatch();

							}
						for(Contact cnt:mod.getDistributors())
							//if(cnt.isNew())
							{
								st.setInt(1, mid);
								st.setInt(2, 2);
								st.setInt(3, cnt.getId());
								st.addBatch();

							}
						for(Contact cnt:mod.getContacts())
							//if(cnt.isNew())
							{
								st.setInt(1, mid);
								st.setInt(2, 3);
								st.setInt(3, cnt.getId());
								st.addBatch();

							}
						re=st.executeBatch();
						st.close();
						for(int i:re)
							if(i!=1)
								throw new SQLException();
						
					}
					
					if(mod.getSerialId()>0)
					{
						st=conn.prepareStatement(SQL_REMOVE_PENDING_BY_ID);
						st.setInt(1, mod.getSerialId());
						if(1!=st.executeUpdate())
							throw new SQLException();
						st.close();
					}
					
					st=conn.prepareCall("{call public.create_model_index(?)}");
					st.setInt(1, mid);
					rs=st.executeQuery();
					if(rs.next())
					{
						if(rs.getBoolean(1))
						{
							result=mid;
							conn.setAutoCommit(true);
						}
						else
							throw new SQLException();
					}
					
				}
			}
			catch(Exception se)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
				}
				Logger.writeLog(this.getClass().getName()+" "+se.getMessage(), LogLevel.SEVERE);
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
	public boolean updateModelInfo(ModelInfo mod) {
		// TODO Auto-generated method stub
		boolean result=false;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		
		if(mod!=null&&mod.getId()>0)
		{
			try
			{
				HashMap<String,Contact> distinctNewCnt=new HashMap<String,Contact>();
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
				conn.setAutoCommit(false);

				this.processContact(conn, mod.getDevelopers(), distinctNewCnt);

				this.processContact(conn, mod.getDistributors(), distinctNewCnt);

				this.processContact(conn, mod.getContacts(), distinctNewCnt);


				st=conn.prepareStatement(SQL_UPDATE_MODEL);
				this.fillModel(st, mod, mod.getId());
				if(1!=st.executeUpdate())
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_CONTACTS_LINK_BY_MODID);
				st.setInt(1, mod.getId());
				
				if(st.executeUpdate()<=0)
					throw new SQLException();
					
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_MODEL_THEME);
				st.setInt(1, mod.getId());
				if(st.executeUpdate()<=0)
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_MODEL_CATEGORY);
				st.setInt(1, mod.getId());
				if(st.executeUpdate()<=0)
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_INSERT_MODEL_THEME);
				for(int i:mod.getThemeIds())
				{
					st.setInt(1, mod.getId());
					st.setInt(2, i);
					st.addBatch();
				}
				int[] re=st.executeBatch();
				for(int i:re)
					if(i!=1)
						throw new SQLException();
				st.close();
				re=null;
				
				st=conn.prepareStatement(SQL_INSERT_MODEL_CATEGORY);
				for(int i:mod.getCategoryIds())
				{
					st.setInt(1, mod.getId());
					st.setInt(2, i);
					st.addBatch();
				}
				re=st.executeBatch();
				for(int i:re)
					if(i!=1)
						throw new SQLException();
				
				st.close();
				re=null;
				
				st=conn.prepareStatement(SQL_INSERT_CONTACTS_LINK_BY_MODID);

				for(Contact cnt:mod.getDevelopers())
				{
					st.setInt(1, mod.getId());
					st.setInt(2, 1);
					st.setInt(3, cnt.getId());
					st.addBatch();

				}
				for(Contact cnt:mod.getDistributors())
				{
					st.setInt(1, mod.getId());
					st.setInt(2, 2);
					st.setInt(3, cnt.getId());
					st.addBatch();

				}
				for(Contact cnt:mod.getContacts())
				{
					st.setInt(1, mod.getId());
					st.setInt(2, 3);
					st.setInt(3, cnt.getId());
					st.addBatch();

				}
				re=st.executeBatch();
				st.close();
				for(int i:re)
					if(i!=1)
						throw new SQLException();
				
				if(mod.getSerialId()>0)
				{
					st=conn.prepareStatement(SQL_REMOVE_PENDING_BY_ID);
					st.setInt(1, mod.getSerialId());
					if(1!=st.executeUpdate())
						throw new SQLException();
					st.close();
				}
				
				st=conn.prepareCall("{call public.create_model_index(?)}");
				st.setInt(1, mod.getId());
				rs=st.executeQuery();
				if(rs.next())
				{
					if(rs.getBoolean(1))
					{
						conn.setAutoCommit(true);
						result=true;
					}
					else
						throw new SQLException();
				}
				
				
				
			}
			catch(Exception se)
			{
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.writeLog(e.getMessage(), LogLevel.SEVERE);
				}
				Logger.writeLog("MIDefaultModDAO::updateModelInfo: "+se.getMessage(), LogLevel.SEVERE);
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
	public boolean removeModelInfoById(int mid) {
		// TODO Auto-generated method stub
		boolean result=false;
		Connection conn=null;
		PreparedStatement st=null;
		if(mid>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
				conn.setAutoCommit(false);
				st=conn.prepareStatement(SQL_REMOVE_CONTACTS_LINK_BY_MODID);
				st.setInt(1, mid);
				
				if(st.executeUpdate()<=0)
					throw new SQLException();
					
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_MODEL_THEME);
				st.setInt(1, mid);
				if(st.executeUpdate()<=0)
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_MODEL_CATEGORY);
				st.setInt(1, mid);
				if(st.executeUpdate()<=0)
					throw new SQLException();
				st.close();
				
				
				st=conn.prepareStatement(SQL_REMOVE_MODEL_INDEX);
				st.setInt(1, mid);
				if(1!=st.executeUpdate())
					throw new SQLException();
				st.close();
				
				st=conn.prepareStatement(SQL_REMOVE_MODEL);
				st.setInt(1, mid);
				if(1!=st.executeUpdate())
					throw new SQLException();
				st.close();
				
				conn.setAutoCommit(true);
				result=true;
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
		return result;
	}
	
	@Override
	public ArrayList<ModelInfo> getModelInfoByPage(int start, int count,int sortByColIdx,boolean isAsc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> infos=null;
		ModelInfo info=null;
		String sql=null;
		if(start>=0&&count>0&&sortByColIdx>=0&&sortByColIdx<_MODELINFO_BY_PAGE_FIELDS.length)
		{
			try
			{
				if(_MODELINFO_BY_PAGE_FIELDS[sortByColIdx].equals("au.aur_last_name"))
					sql=String.format(SQL_GET_MODELINFO_BY_PAGE_FMT,
							_MODELINFO_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
							"au.aur_first_name"+" "+(isAsc?"asc":"desc"));
				else
				    sql=String.format(SQL_GET_MODELINFO_BY_PAGE_FMT,
						    _MODELINFO_BY_PAGE_FIELDS[sortByColIdx],
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
					info=new ModelInfo();
					info.setId(rs.getInt(1));
					info.setName(rs.getString(2));
					info.setDescription(rs.getString(3));
					info.setLastUpdateDate(rs.getTimestamp(4, this.cal));
					info.setVersionNo(rs.getString(5));
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
	public ArrayList<ModelInfo> getModelInfoByOwnerAndPage(int uid, int start,
			int count, int sortByColIdx, boolean isAsc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> infos=null;
		ModelInfo info=null;
		String sql=null;
		if(uid>0&&start>=0&&count>0&&sortByColIdx>=0&&sortByColIdx<_MODELINFO_BY_PAGE_FIELDS.length)
		{
			try
			{
				if(_MODELINFO_BY_PAGE_FIELDS[sortByColIdx].equals("au.aur_last_name"))
					sql=String.format(SQL_GET_MODELINFO_BY_UID_PAGE_FMT,
							_MODELINFO_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
							"au.aur_first_name"+" "+(isAsc?"asc":"desc"));
				else
				    sql=String.format(SQL_GET_MODELINFO_BY_UID_PAGE_FMT,
						    _MODELINFO_BY_PAGE_FIELDS[sortByColIdx],
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
					info=new ModelInfo();
					info.setId(rs.getInt(1));
					info.setName(rs.getString(2));
					info.setDescription(rs.getString(3));
					info.setLastUpdateDate(rs.getTimestamp(4, this.cal));
					info.setVersionNo(rs.getString(5));
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
	public ModelInfo getModelInfoById(int mid) {
		// TODO Auto-generated method stub
		
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ModelInfo model=null;
		
		if(mid>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(SQL_GET_MODEL_INFO_BY_ID);
				st.setInt(1, mid);
				
				rs=st.executeQuery();
				if(rs.next())
				{
					model=new ModelInfo();
					this.fillModelMain(model, rs);
					rs.close();
					st.close();
					st=conn.prepareStatement(SQL_GET_THEME_BY_ID);
					st.setInt(1, mid);
					rs=st.executeQuery();
					model.setThemeIds(this.fillIntArray(rs));
					rs.close();
					st.close();
					
					st=conn.prepareStatement(SQL_GET_CATEGORY_BY_ID);
					st.setInt(1, mid);
					rs=st.executeQuery();
					model.setCategoryIds(this.fillIntArray(rs));
					rs.close();
					st.close();
					
					st=conn.prepareStatement(SQL_GET_PEOPLE_BY_MODEL_ID);
					st.setInt(1, mid);
					rs=st.executeQuery();
					this.fillPeople(model, rs);
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
		return model;
	}
	private void fillModelMain(ModelInfo model,ResultSet rs) throws SQLException
	{
		if(model!=null&&rs!=null)
		{
			model.setId(rs.getInt(1));
			model.setName(rs.getString(2));
			model.setDescription(rs.getString(3));
			model.setVersionNo(rs.getString(4));
			model.setAttribute(rs.getString(5));
			model.setAvailableId(rs.getInt(6));
			model.setDataRequirement(rs.getString(7));
			model.setSkillLevel(rs.getString(8));
			model.setNote(rs.getString(9));
			model.setLastUpdateDate(rs.getTimestamp(10, this.cal));
			model.setAbbreviation(rs.getString(11));
			model.setStatus(rs.getString(12));
			model.setStrength(rs.getString(13));
			model.setOwnerEmail(rs.getString(14));
			model.setOwnerName(rs.getString(15)+" "+rs.getString(16));
			model.setUrl(rs.getString(17));
		}
	}
	private int[] fillIntArray(ResultSet rs) throws SQLException
	{
		int[] result=null;
		if(rs!=null)
		{
			ArrayList<Integer> lists=null;
			while(rs.next())
			{
				if(lists==null)
					lists=new ArrayList<Integer>();
				lists.add(rs.getInt(1));
			}
			if(lists!=null&&!lists.isEmpty())
			{
				Integer[] tmps=new Integer[lists.size()];
				lists.toArray(tmps);
				if(tmps!=null)
				{
					result=new int[tmps.length];
					for(int i=0;i<tmps.length;++i)
						result[i]=tmps[i].intValue();
				}
			}
		}
		return result;
	}
	private void fillPeople(ModelInfo model,ResultSet rs) throws SQLException
	{
		if(model!=null&&rs!=null)
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
					model.setDeveloper(cnt);
				else if(type==2)
					model.setDistributor(cnt);
				else if(type==3)
					model.setContact(cnt);
			}
		}
	}
	@Override
	public ModelInfo getModelInfoByIdAndUId(int mid, int uid) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ModelInfo model=null;
		
		if(mid>0&&uid>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(SQL_GET_MODEL_INFO_BY_ID_UID);
				st.setInt(1, mid);
				st.setInt(2, uid);
				rs=st.executeQuery();
				if(rs.next())
				{
					model=new ModelInfo();
					this.fillModelMain(model, rs);
					rs.close();
					st.close();
					st=conn.prepareStatement(SQL_GET_THEME_BY_ID);
					st.setInt(1, mid);
					rs=st.executeQuery();
					model.setThemeIds(this.fillIntArray(rs));
					rs.close();
					st.close();
					
					st=conn.prepareStatement(SQL_GET_CATEGORY_BY_ID);
					st.setInt(1, mid);
					rs=st.executeQuery();
					model.setCategoryIds(this.fillIntArray(rs));
					rs.close();
					st.close();
					
					st=conn.prepareStatement(SQL_GET_PEOPLE_BY_MODEL_ID);
					st.setInt(1, mid);
					rs=st.executeQuery();
					this.fillPeople(model, rs);
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
		return model;
	}

	@Override
	public ArrayList<ModelInfo> getModelInfoByUID(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	 
	@Override
	public final String[] getFriendlyPagingFldNames() {
		// TODO Auto-generated method stub
		return MODELINFO_BY_PAGE_FIELDS;
	}

	@Override
	public int getModelCount() {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		int count=0;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.createStatement();
		    
		    rs=st.executeQuery(SQL_GET_MODEL_COUNT);
		    
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
	public int getModelCountByOwner(int uid) {
		// TODO Auto-generated method stub
		if(uid<=0)return 0;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int count=0;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_GET_MODEL_COUNT_BY_OWNER);
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

	@Override
	public boolean isOwnerOfModel(int uid, int mid) {
		// TODO Auto-generated method stub
		
		if(uid<=0||mid<=0)return false;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		boolean result=false;
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    st=conn.prepareStatement(SQL_IS_OWNER);
		    st.setInt(1, mid);
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
	public User isPossibleModelOwner(String email) {
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
	public boolean updateModelOwner(int modid,int uid) {
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
			    st=conn.prepareStatement(SQL_GET_OWNER_ID);
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
			    		st=conn.prepareStatement(SQL_UPDATE_MODEL_FILES_OWNER);
			    		st.setInt(1, uid);
		    			st.setInt(2, oldowner);
		    			st.setInt(3, modid);
		    			st.executeUpdate();
		    			//Logger.writeLog(String.format("%d,%d,%d", uid,oldowner,modid), LogLevel.SEVERE);
		    			st.close();
			    		st=conn.prepareStatement(SQL_UPDATE_MODEL_OWNER);
			    		st.setInt(1, uid);
			    		st.setInt(2, modid);
			    		if(1==st.executeUpdate())
			    		{
			    			conn.setAutoCommit(true);
			    			result=true;
			    		}
			    		else
			    			throw new Exception("MIDefaultModDAO::updateModelOwner: Failed to update model owner");
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
	public boolean updateModelStatus(int modid,boolean isActive) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(modid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    st=conn.prepareStatement(SQL_UPDATE_MODEL_STATUS);
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
