package us.glos.mi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.glc.DBConnFactory;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.SearchFilter;

public class MIDefaultSearchDAO implements ISearchDAO {

	private static boolean active=false;
	private static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	private static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	
	private static final String SQL_SEARCH_MODEL_R_FMT="select distinct(mi.midx_mod_id),md.mod_name,md.mod_desc,md.mod_ver_no, ts_rank_cd(mi.midx_index,query,1|16) as rank from model_index mi inner join model_data md on md.mod_id=mi.midx_mod_id %s ,to_tsquery(?) query where mi.midx_index @@ query and md.mod_status=3 %s order by rank desc limit ? offset ?;";
	private static final String SQL_SEARCH_MODEL_R_C_FMT="select count(distinct mi.midx_mod_id) from model_index mi inner join model_data md on md.mod_id=mi.midx_mod_id %s ,to_tsquery(?) query where mi.midx_index @@ query and md.mod_status=3 %s;";
	private static final String SQL_SEARCH_MODEL_F_FMT="select distinct(mi.midx_mod_id),md.mod_name,md.mod_desc,md.mod_ver_no, ts_rank_cd(mi.midx_index,query,1|16) as rank from model_index mi inner join model_data md on md.mod_id=mi.midx_mod_id %s ,to_tsquery(?) query where mi.midx_index @@ query and md.mod_status=3 %s order by rank desc limit ? offset ?;";
	private static final String SQL_SEARCH_MODEL_F_C_FMT="select count(distinct mi.midx_mod_id) from model_index mi inner join model_data md on md.mod_id=mi.midx_mod_id %s ,to_tsquery(?) query where mi.midx_index @@ query and md.mod_status=3 %s;";
	//private static final String SQL_SEARCH_MODEL_C="select count(*) from model_index mi inner join model_data md on md.mod_id=mi.midx_mod_id ,to_tsquery(?) query where mi.midx_index @@ query and md.mod_status=3;";
	private static final String SQL_SEARCH_OR_ALL="select distinct(mi.midx_mod_id),md.mod_name,md.mod_desc,md.mod_ver_no, ts_rank_cd(mi.midx_index,query,1|16) as rank from model_index mi inner join model_data md on md.mod_id=mi.midx_mod_id ,to_tsquery(?) query where mi.midx_index @@ query and md.mod_status=3 order by rank desc;";
	private static final String SQL_CAT_CONST=" inner join cat_data cd on cd.cat_mod_id=md.mod_id ";
	private static final String SQL_CAT_WHERE_FMT=" cd.cat_no in (%s) ";
	private static final String SQL_THE_CONST=" inner join theme_data td on td.thm_mod_id=md.mod_id ";
	private static final String SQL_THE_WHERE_FMT=" td.thm_no in (%s) ";
	private static final String SQL_AVAIL_WHERE_FMT=" md.mod_avail_no=%d ";
	private static final String SQL_GET_MODELS_BY_CONTACT="select distinct(md.mod_id),md.mod_name,md.mod_desc,md.mod_ver_no from model_data md inner join model_contacts mc on mc.mc_mod_no=md.mod_id where mc.mc_cnt_no=?;";
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
	}
	private String[] createFilterSQL(SearchFilter filter)
	{
		String[] segments=new String[]{"",""};
		if(filter!=null)
		{
			StringBuilder sb1=new StringBuilder();
			StringBuilder sb2=new StringBuilder();
			if(filter.getCategories()!=null&&!filter.getCategories().isEmpty())
			{
				sb1.append(SQL_CAT_CONST);
				StringBuilder temp=new StringBuilder();
				for(Integer i:filter.getCategories())
				{
					temp.append(i);
					temp.append(',');
				}
				if(temp.charAt(temp.length()-1)==',')
					temp.deleteCharAt(temp.length()-1);
				if(filter.isCategoryOR())
					sb2.append(" or ");
				else
					sb2.append(" and ");
				sb2.append(String.format(SQL_CAT_WHERE_FMT, temp.toString()));
			}
			if(filter.getThemes()!=null&&!filter.getThemes().isEmpty())
			{
				sb1.append(SQL_THE_CONST);
				StringBuilder temp=new StringBuilder();
				for(Integer i:filter.getThemes())
				{
					temp.append(i);
					temp.append(',');
				}
				if(temp.charAt(temp.length()-1)==',')
					temp.deleteCharAt(temp.length()-1);
				if(filter.isThemesOR())
					sb2.append(" or ");
				else
					sb2.append(" and ");
				sb2.append(String.format(SQL_THE_WHERE_FMT,temp.toString()));
			}
			if(filter.getAvails()>0)
			{
				if(filter.isAvailsOR())
					sb2.append(" or ");
				else
					sb2.append(" and ");
				sb2.append(String.format(SQL_AVAIL_WHERE_FMT, filter.getAvails()));
			}
			if((sb1.length()>0&&sb2.length()>0)||(sb1.length()==0&&sb2.length()>0&&filter.getAvails()>0))
			{
				segments[0]=sb1.toString();
				segments[1]=sb2.toString();
			}
		}
		return segments;
	}
	@Override
	public ArrayList<ModelInfo> searchModelsRestrict(ArrayList<String> keys,int limit,int offset,boolean bFuzzy,SearchFilter filter) {
		// TODO Auto-generated method stub
		if(!active||keys==null||keys.isEmpty()||limit<=0||offset<0)return null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> list=null;
		String[] filterSQL=null;
		char opt='&';
		if(bFuzzy)
			opt='|';
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    StringBuilder sb=new StringBuilder();
		    for(String k:keys)
		    {
		    	sb.append(k);
		    	sb.append(opt);
		    }
		    if(sb.charAt(sb.length()-1)==opt)
		    	sb.deleteCharAt(sb.length()-1);
		    filterSQL=createFilterSQL(filter);
		    st=conn.prepareStatement(String.format(SQL_SEARCH_MODEL_R_FMT,filterSQL[0],filterSQL[1]));
		    st.setString(1, sb.toString());
		    st.setInt(2, limit);
		    st.setInt(3, offset);
		    rs=st.executeQuery();
		    ModelInfo mod=null;
		    while(rs.next())
		    {
		    	if(list==null)
		    		list=new ArrayList<ModelInfo>();
		    	mod=new ModelInfo();
		    	mod.setId(rs.getInt(1));
		    	mod.setName(rs.getString(2));
		    	mod.setDescription(rs.getString(3));
		    	mod.setVersionNo(rs.getString(4));
		    	list.add(mod);
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
	public ArrayList<ModelInfo> searchModelsFuzzy(ArrayList<String> keys,
			int limit, int offset,SearchFilter filter) {
		// TODO Auto-generated method stub
		if(!active||keys==null||keys.isEmpty()||limit<=0||offset<0)return null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> list=null;
		String[] filterSQL=null;
		char opt='|';
		char opt1='&';
		String fmt="(%s)&(!(%s))";
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    StringBuilder sb=new StringBuilder();
		    StringBuilder sb1=new StringBuilder();
		    
		    for(String k:keys)
		    {
		    	sb.append(k);
		    	sb.append(opt);
		    	sb1.append(k);
		    	sb1.append(opt1);
		    }
		    if(sb.charAt(sb.length()-1)==opt)
		    	sb.deleteCharAt(sb.length()-1);
		    if(sb1.charAt(sb1.length()-1)==opt1)
		    	sb1.deleteCharAt(sb1.length()-1);
		    filterSQL=createFilterSQL(filter);
		    //String tmp=String.format(SQL_SEARCH_MODEL_F_FMT,filterSQL[0],filterSQL[1]);
		    st=conn.prepareStatement(String.format(SQL_SEARCH_MODEL_F_FMT,filterSQL[0],filterSQL[1]));
		    st.setString(1, String.format(fmt, sb.toString(),sb1.toString()));
		    
		    st.setInt(2, limit);
		    st.setInt(3, offset);
		    rs=st.executeQuery();
		    ModelInfo mod=null;
		    while(rs.next())
		    {
		    	if(list==null)
		    		list=new ArrayList<ModelInfo>();
		    	mod=new ModelInfo();
		    	mod.setId(rs.getInt(1));
		    	mod.setName(rs.getString(2));
		    	mod.setDescription(rs.getString(3));
		    	mod.setVersionNo(rs.getString(4));
		    	list.add(mod);
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
	public int searchModelsFuzzyCount(ArrayList<String> keys,SearchFilter filter) 
	{
		// TODO Auto-generated method stub
		if(!active||keys==null||keys.isEmpty())return 0;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int count=0;
		String[] filterSQL=null;
		char opt='|';
		char opt1='&';
		String fmt="(%s)&(!(%s))";
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    StringBuilder sb=new StringBuilder();
		    StringBuilder sb1=new StringBuilder();
		    
		    for(String k:keys)
		    {
		    	sb.append(k);
		    	sb.append(opt);
		    	sb1.append(k);
		    	sb1.append(opt1);
		    }
		    if(sb.charAt(sb.length()-1)==opt)
		    	sb.deleteCharAt(sb.length()-1);
		    if(sb1.charAt(sb1.length()-1)==opt1)
		    	sb1.deleteCharAt(sb1.length()-1);
		    filterSQL=createFilterSQL(filter);
		    st=conn.prepareStatement(String.format(SQL_SEARCH_MODEL_F_C_FMT,filterSQL[0],filterSQL[1]));
		    st.setString(1, String.format(fmt, sb.toString(),sb1.toString()));
		    
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
	public int searchModelsRestrictCount(ArrayList<String> keys, boolean bFuzzy,SearchFilter filter)
	{
		// TODO Auto-generated method stub
		if(!active||keys==null||keys.isEmpty())return 0;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		int count=0;
		String[] filterSQL=null;
		char opt='&';
		if(bFuzzy)
			opt='|';
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    StringBuilder sb=new StringBuilder();
		    for(String k:keys)
		    {
		    	sb.append(k);
		    	sb.append(opt);
		    }
		    if(sb.charAt(sb.length()-1)==opt)
		    	sb.deleteCharAt(sb.length()-1);
		    filterSQL=createFilterSQL(filter);
		    st=conn.prepareStatement(String.format(SQL_SEARCH_MODEL_R_C_FMT,filterSQL[0],filterSQL[1]));
		    st.setString(1, sb.toString());
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
	public ArrayList<ModelInfo> searchModelsANDONLY(ArrayList<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<ModelInfo> searchModelsORONLY(ArrayList<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<ModelInfo> searchModelsOR(ArrayList<String> keys) {
		// TODO Auto-generated method stub
		if(!active||keys==null||keys.isEmpty())return null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> list=null;
		
		char opt='|';
		
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    StringBuilder sb=new StringBuilder();
		    for(String k:keys)
		    {
		    	sb.append(k);
		    	sb.append(opt);
		    }
		    if(sb.charAt(sb.length()-1)==opt)
		    	sb.deleteCharAt(sb.length()-1);
		    
		    st=conn.prepareStatement(SQL_SEARCH_OR_ALL);
		    st.setString(1, sb.toString());
		    
		    rs=st.executeQuery();
		    ModelInfo mod=null;
		    while(rs.next())
		    {
		    	if(list==null)
		    		list=new ArrayList<ModelInfo>();
		    	mod=new ModelInfo();
		    	mod.setId(rs.getInt(1));
		    	mod.setName(rs.getString(2));
		    	mod.setDescription(rs.getString(3));
		    	mod.setVersionNo(rs.getString(4));
		    	list.add(mod);
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
	public ArrayList<ModelInfo> searchModelsByContact(int cid) {
		// TODO Auto-generated method stub
		if(!active||cid<=0)return null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		ArrayList<ModelInfo> list=null;
		
		
		try
		{
		    conn=DBConnFactory.getConnection(DB_SOURCE_R);
		    
		    
		    st=conn.prepareStatement(SQL_GET_MODELS_BY_CONTACT);
		    st.setInt(1, cid);
		    
		    rs=st.executeQuery();
		    ModelInfo mod=null;
		    while(rs.next())
		    {
		    	if(list==null)
		    		list=new ArrayList<ModelInfo>();
		    	mod=new ModelInfo();
		    	mod.setId(rs.getInt(1));
		    	mod.setName(rs.getString(2));
		    	mod.setDescription(rs.getString(3));
		    	mod.setVersionNo(rs.getString(4));
		    	list.add(mod);
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
