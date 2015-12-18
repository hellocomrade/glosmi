package us.glos.mi.db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.util.ArrayList;

import org.glc.DBConnFactory;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;
import org.glc.domain.Address;
import org.glc.domain.Orgnization;

import us.glos.mi.dao.IUserDAO;
import us.glos.mi.dao.MIDataSource;
import us.glos.mi.dao.MIDefaultUserDAO;
import us.glos.mi.domain.UserStats;
import us.glos.mi.domain.Contact;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.UserPrivilegeMask;

public class SqlHelper {
	private static boolean active;
	private static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	private static final String SQL_GET_USER_COUNT_BY_PRI="select count(*) from admin_user where aur_mask&%d=%d;";
	private static final String SQL_GET_MOD_DEV="select c.cnt_last_name,c.cnt_first_name from contacts c inner join model_contacts mc on mc.mc_cnt_no=c.cnt_id inner join model_data m on m.mod_id=mc.mc_mod_no where m.mod_id=? and mc.mc_mod_rcc_no=1;";
	private static final String SQL_GET_APP_CNT="select c.cnt_last_name,c.cnt_first_name from contacts c inner join app_contacts ac on ac.act_cnt_no=c.cnt_id inner join application_data ad on ad.app_id=ac.act_app_no where ad.app_id=? and ac.act_rcc_no=3;";
	private static final String SQL_GET_CONTACTS="select cnt_email,cnt_first_name,cnt_last_name,cnt_org,cnt_street1,cnt_street2,cnt_city,cnt_state,cnt_country,cnt_zip,cnt_phone from contacts where lower(cnt_first_name)=lower(?) and lower(cnt_last_name)=lower(?) and lower(cnt_email)=lower(?);";
	private final static String SQL_GET_MODEL_STATUS="select mod_status from model_data where mod_id=?;";
	private final static String SQL_GET_APP_STATUS="select app_status from application_data where app_id=?;";
	private final static String SQL_GET_ORG="select agn_name,agn_agnid from agencies where lower(agn_agnid) like lower(?) or lower(agn_name) like lower(?);";
	private final static String SQL_GET_ALL_ORG="select agn_name,agn_agnid from agencies order by agn_name asc;";
	private final static String SQL_INSERT_ORG="insert into agencies(agn_name,agn_agnid) values(?,?);";
	private final static String SQL_GET_MOD_PENDING="select count(*) from model_data_pending;";
	private final static String SQL_GET_APP_PENDING="select count(*) from app_data_pending;";
	private final static String SQL_GET_CONTACT_BY_ORG="select cnt_id,cnt_first_name,cnt_last_name from contacts where cnt_agn_no=?";
	private final static String SQL_GET_MODEL_BY_ORG="select distinct(m.mod_id), m.mod_name from model_data m inner join model_contacts mc on mc.mc_mod_no=m.mod_id inner join contacts c on c.cnt_id=mc.mc_cnt_no where c.cnt_agn_no=? order by m.mod_name asc;";
	private final static String SQL_GET_APP_BY_ORG="select distinct(a.app_id),a.app_name from application_data a inner join app_contacts ac on ac.act_app_no=a.app_id inner join contacts c on c.cnt_id=ac.act_cnt_no where c.cnt_agn_no=? order by a.app_name asc;";
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R);
	}
	public static ArrayList<AppInfo> getAppsByOrdId(int id)
	{
		ArrayList<AppInfo> apps=null;
		AppInfo app=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(!SqlHelper.active&&id<=0) return null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);
			st=conn.prepareStatement(SQL_GET_APP_BY_ORG);
			st.setInt(1, id);
			rs=st.executeQuery();
			while(rs.next())
			{
				app=new AppInfo();
				app.setId(rs.getInt(1));
				app.setName(rs.getString(2));
				if(apps==null)
					apps=new ArrayList<AppInfo>();
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
		return apps;
	}
	public static ArrayList<ModelInfo> getModelsByOrgId(int id)
	{
		ArrayList<ModelInfo> models=null;
		ModelInfo model=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(!SqlHelper.active&&id<=0) return null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);
			st=conn.prepareStatement(SQL_GET_MODEL_BY_ORG);
			st.setInt(1, id);
			rs=st.executeQuery();
			while(rs.next())
			{
				model=new ModelInfo();
				model.setId(rs.getInt(1));
				model.setName(rs.getString(2));
				if(models==null)
					models=new ArrayList<ModelInfo>();
				models.add(model);
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
		return models;
	}
	public static ArrayList<Contact> getContactsByOrgId(int id)
	{
		ArrayList<Contact> cnts=null;
		Contact cnt=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(!SqlHelper.active&&id<=0) return null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);
			st=conn.prepareStatement(SQL_GET_CONTACT_BY_ORG);
			st.setInt(1, id);
			rs=st.executeQuery();
			while(rs.next())
			{
				cnt=new Contact();
				cnt.setId(rs.getInt(1));
				cnt.setFirstName(rs.getString(2));
				cnt.setLastName(rs.getString(3));
				if(cnts==null)
					cnts=new ArrayList<Contact>();
				cnts.add(cnt);
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
		return cnts;
	}
	public static int getTotalUserCount()
	{
		IUserDAO dao=new MIDefaultUserDAO();
		return dao.getTotalUserCount();
	}
	public static UserStats getUserStats()
	{
		if(!SqlHelper.active) return null;
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			UserStats us=new UserStats();
			
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.createStatement();
			rs=st.executeQuery(String.format(SQL_GET_USER_COUNT_BY_PRI,UserPrivilegeMask.ADMIN_MASK,UserPrivilegeMask.ADMIN_MASK));

			if(rs.next())
				us.adminUser=rs.getInt(1);
			else
				return null;
			rs.close();
			st.close();
			
			st=conn.createStatement();
			rs=st.executeQuery(String.format(SQL_GET_USER_COUNT_BY_PRI,UserPrivilegeMask.MODELER_MASK,UserPrivilegeMask.MODELER_MASK));
			if(rs.next())
				us.modUser=rs.getInt(1);
			else
				return null;
			rs.close();
			st.close();
			
			st=conn.createStatement();
			rs=st.executeQuery(String.format(SQL_GET_USER_COUNT_BY_PRI,UserPrivilegeMask.APP_MASK,UserPrivilegeMask.APP_MASK));
			if(rs.next())
				us.appUser=rs.getInt(1);
			else
				return null;
			rs.close();
			st.close();
			return us;
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
		return null;
	}
	public static ArrayList<String> getModelDevelopers(int modid)
	{
		if(!SqlHelper.active||modid<=0) return null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<String> devs=null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.prepareStatement(SQL_GET_MOD_DEV);
			st.setInt(1, modid);
			rs=st.executeQuery();
			while(rs.next())
			{
				if(devs==null)
					devs=new ArrayList<String>();
				devs.add(rs.getString(1)+" "+rs.getString(2));
			}
			return devs;
			
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
		return devs;
	}
	public static ArrayList<String> getApplicationDevelopers(int appid)
	{
		if(!SqlHelper.active||appid<=0) return null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<String> devs=null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.prepareStatement(SQL_GET_APP_CNT);
			st.setInt(1, appid);
			rs=st.executeQuery();
			while(rs.next())
			{
				if(devs==null)
					devs=new ArrayList<String>();
				devs.add(rs.getString(1)+" "+rs.getString(2));
			}
			return devs;
			
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
		return devs;
	}
	public static int[] getModelCounts(int uid)
	{
		if(!SqlHelper.active&&uid<0) return null;
		Connection conn=null;
		CallableStatement st=null;
		ResultSet rs=null;
		
		int[] counts={0,0,0,0,0};
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.prepareCall("{call public.get_model_counts(?)}");
			if(uid>0)
				st.setInt(1, uid);
			else
				st.setNull(1, java.sql.Types.INTEGER);
			rs=st.executeQuery();
			
			if(rs.next())
			{
				Array a=rs.getArray(1);
				if(a!=null)
				{
					if(a.getArray() instanceof Object[])
					{
						Object[] objs=(Object[])a.getArray();
						if(objs!=null)
						{
							Integer[] ints=(Integer[])objs;
							if(ints!=null)
								for(int i=0;i<ints.length;++i)
									counts[i]=(int)ints[i];
						}
					}
					else
						counts=(int[])a.getArray();
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
		return counts;
	}
	public static int[] getModelCounts()
	{
		return getModelCounts(0);
	}
	public static int[] getAttachmentCounts(int uid)
	{
		if(!SqlHelper.active&&uid<0) return null;
		Connection conn=null;
		CallableStatement st=null;
		ResultSet rs=null;
		
		int[] counts={0,0,0};
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.prepareCall("{call public.get_file_count(?)}");
			if(uid>0)
				st.setInt(1, uid);
			else
				st.setNull(1, java.sql.Types.INTEGER);
			rs=st.executeQuery();
			
			if(rs.next())
			{
				Array a=rs.getArray(1);
				if(a!=null)
				{
					if(a.getArray() instanceof Object[])
					{
						Object[] objs=(Object[])a.getArray();
						if(objs!=null)
						{
							Integer[] ints=(Integer[])objs;
							if(ints!=null)
								for(int i=0;i<ints.length;++i)
									counts[i]=(int)ints[i];
						}
					}
					else
						counts=(int[])a.getArray();
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
		return counts;
	}
	public static int[] getAttachmentCounts()
	{
		return getAttachmentCounts(0);
	}
	public static int[] getAppCounts(int uid)
	{
		if(!SqlHelper.active&&uid<0) return null;
		Connection conn=null;
		CallableStatement st=null;
		ResultSet rs=null;
		Integer[] it=null;
		int[] counts={0,0,0,0,0};
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.prepareCall("{call public.get_app_counts(?)}");
			if(uid>0)
				st.setInt(1, uid);
			else
				st.setNull(1, java.sql.Types.INTEGER);
			rs=st.executeQuery();
			
			if(rs.next())
			{
				Array a=rs.getArray(1);
				if(a!=null)
				{
					if(a.getArray() instanceof Object[])
					{
						Object[] objs=(Object[])a.getArray();
						if(objs!=null)
						{
							Integer[] ints=(Integer[])objs;
							if(ints!=null)
								for(int i=0;i<ints.length;++i)
									counts[i]=(int)ints[i];
						}
					}
					else
						counts=(int[])a.getArray();
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
		return counts;
	}
	public static int[] getAppCounts()
	{
		return getAppCounts(0);
	}
	public static int[] getPendingCount()
	{
		if(!SqlHelper.active) return null;
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		int[] counts={0,0};
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);

			st=conn.createStatement();
			
			rs=st.executeQuery(SQL_GET_MOD_PENDING);
			
			if(rs.next())
			{
				counts[0]=rs.getInt(1);
			}
			rs.close();
			rs=st.executeQuery(SQL_GET_APP_PENDING);
			if(rs.next())
			{
				counts[1]=rs.getInt(1);
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
		return counts;
	}
	public static ArrayList<Contact> getContactSearch(String fname,String lname,String email)
	{
		ArrayList<Contact> cnts=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		Contact cnt=null;
		Orgnization org=null;
		Address add=null;
		if(SqlHelper.active&&fname!=null&&lname!=null&&email!=null)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				st=conn.prepareStatement(SQL_GET_CONTACTS);
				st.setString(1, fname);
				st.setString(2, lname);
				st.setString(3, email);
				rs=st.executeQuery();
				while(rs.next())
				{
					if(cnts==null)
						cnts=new ArrayList<Contact>();
					cnt=new Contact();
					org=new Orgnization();
					add=new Address();
					cnt.setEmail(rs.getString(1));
					cnt.setFirstName(rs.getString(2));
					cnt.setLastName(rs.getString(3));
					org.setName(rs.getString(4));
					org.setAbbrev(org.getName());
					cnt.setOrgnization(org);
					add.setAddress1(rs.getString(5));
					add.setAddress2(rs.getString(6));
					add.setCity(rs.getString(7));
					add.setState(rs.getString(8));
					add.setCountry(rs.getString(9));
					add.setZipcode(rs.getString(10));
					add.setPhone(rs.getString(11));
					cnt.setAddress(add);
					cnts.add(cnt);
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
		return cnts;
	}
	public static boolean isModelEnabled(int modid) {
		// TODO Auto-generated method stub
		boolean result=false;
		
		if(modid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GET_MODEL_STATUS);
			    st.setInt(1, modid);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	result=(rs.getInt(1)==3);
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
	public static boolean isApplicationEnabled(int apid) {
		// TODO Auto-generated method stub
		boolean result=false;
		
		if(apid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
			    conn=DBConnFactory.getConnection(DB_SOURCE_R);
			    st=conn.prepareStatement(SQL_GET_APP_STATUS);
			    st.setInt(1, apid);
			    rs=st.executeQuery();
			    if(rs.next())
			    {
			    	result=(rs.getInt(1)==3);
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
	public static ArrayList<Orgnization> searchAgencies(String key)
	{
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
			    	org.setName(rs.getString(1));
			    	org.setAbbrev(rs.getString(2));
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
	public static ArrayList<Orgnization> getAgencyList()
	{
		ArrayList<Orgnization> orgs=null;
		Orgnization org=null;
		
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);
			st=conn.prepareStatement(SQL_GET_ALL_ORG);

			rs=st.executeQuery();
			while(rs.next())
			{
				org=new Orgnization();
				org.setName(rs.getString(1));
				org.setAbbrev(rs.getString(2));
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
			
		
		return orgs;
	}
	
	public static boolean addAgency(String name,String abbrev)
	{
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
}
