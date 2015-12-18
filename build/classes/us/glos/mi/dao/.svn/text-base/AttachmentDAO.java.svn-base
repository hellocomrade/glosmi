package us.glos.mi.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.glc.DBConnFactory;
import org.glc.DefaultTimeZoneImpl;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;
import org.glc.domain.User;

import us.glos.mi.domain.Attachment;

public class AttachmentDAO extends DefaultTimeZoneImpl implements IFileDAO {

	protected static final String DB_SOURCE_W=MIDataSource.getRWDataSourceName();
	protected static final String DB_SOURCE_R=MIDataSource.getReadonlyDataSourceName();
	protected static boolean active=false;
	private final static String SQL_INSERT_RECORD="insert into files(fpath,ftype,fref_id,fsection_id,fuid,furl,fdesc) values(?,?,?,?,?,?,?) returning fid;";
	private final static String SQL_REMOVE_RECORD="delete from files where fid=?;";
	private final static String SQL_GET_RECORD_BY_SECTION="select fid,furl,fdesc,fupdate_date from files where ftype=? and fref_id=? and fsection_id=? order by fupdate_date asc;";
	private final static String SQL_UPDATE_DESC_BY_ID="update files set fdesc=?,fchk=FALSE where fid=?";
	private final static String SQL_UPDATE_CHK_BY_ID="update files set fchk=? where fid=?;";
	private final static String SQL_IS_OWNER="select count(*) from files where fid=? and fuid=?;";
	private final static String SQL_COUNT_BY_IDS="select count(*) from files where fref_id=? and fsection_id=?";
	private final static String SQL_GET_PATH_BY_ID="select fpath from files where fid=?;";
	private final static String SQL_GET_COUNT_BY_UID="select count(*) from files where fuid=?;";
	private final static String SQL_GET_COUNT="select count(*) from files;";
	private final static String SQL_GET_TYPEID="select ftype from files where fid=?;";
	private final static String SQL_GET_ATTACHMENT_BY_PAGE_FMT="select f.fid,f.ftype,f.fupdate_date,f.fref_id,f.furl,f.fdesc,f.fchk,a.aur_email,a.aur_first_name,a.aur_last_name from files f inner join admin_user a on a.aur_id=f.fuid order by %s %s limit ? offset ?;";
	private final static String SQL_GET_ATTACHMENT_BY_PAGE_AND_UID_FMT="select f.fid,f.ftype,f.fupdate_date,f.fref_id,f.furl,f.fdesc,f.fchk,a.aur_email,a.aur_first_name,a.aur_last_name from files f inner join admin_user a on a.aur_id=f.fuid where fuid=? order by %s %s limit ? offset ?;";
	private String[] _ATTACHMENT_BY_PAGE_FIELDS={"f.fid","","","f.fdesc","","f.fupdate_date","f.fchk","a.aur_email","a.aur_last_name"};
	
	static
	{
		active=DBConnFactory.init(DB_SOURCE_R)&&DBConnFactory.init(DB_SOURCE_W);
		
		
	}
	
	@Override
	public int getTypeId(int id) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(id>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
		        st=conn.prepareStatement(SQL_GET_TYPEID);
		        st.setInt(1, id);
		        rs=st.executeQuery();
		        if(rs.next())
		        	return rs.getInt(1);
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" getTypeId: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" getTypeId: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return 0;
	}

	@Override
	public boolean updateFileStatus(int id,boolean isChecked) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		if(id>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
		        st=conn.prepareStatement(SQL_UPDATE_CHK_BY_ID);
		        st.setBoolean(1, isChecked);
		        st.setInt(2, id);
		        
		        return (1==st.executeUpdate());
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" updateFileStatus: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" updateFileStatus: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return false;
	}

	@Override
	public ArrayList<Attachment> getAttachmentByOwnerAndPage(int uid,
			int start, int count, int sortByColIdx, boolean isAsc) {
		// TODO Auto-generated method stub
		ArrayList<Attachment> atts=null;
		Attachment att=null;
		User usr=null;
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		String sql=null;
		if(start>=0&&count>0&&sortByColIdx>=0&&sortByColIdx<_ATTACHMENT_BY_PAGE_FIELDS.length)
		{
			try
			{
				
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
				
				if(uid>0)
				{
					if(_ATTACHMENT_BY_PAGE_FIELDS[sortByColIdx].equals("a.aur_last_name"))
						sql=String.format(SQL_GET_ATTACHMENT_BY_PAGE_AND_UID_FMT,
								_ATTACHMENT_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
								"a.aur_first_name"+" "+(isAsc?"asc":"desc"));
					else
					    sql=String.format(SQL_GET_ATTACHMENT_BY_PAGE_AND_UID_FMT,
					    		_ATTACHMENT_BY_PAGE_FIELDS[sortByColIdx],
							    isAsc?"asc":"desc");
					
					st=conn.prepareStatement(sql);
					st.setInt(1, uid);
					st.setInt(2, count);
					st.setInt(3, start);
				}
				else
				{
					if(_ATTACHMENT_BY_PAGE_FIELDS[sortByColIdx].equals("a.aur_last_name"))
						sql=String.format(SQL_GET_ATTACHMENT_BY_PAGE_FMT,
								_ATTACHMENT_BY_PAGE_FIELDS[sortByColIdx]+" "+(isAsc?"asc,":"desc,"),
								"a.aur_first_name"+" "+(isAsc?"asc":"desc"));
					else
					    sql=String.format(SQL_GET_ATTACHMENT_BY_PAGE_FMT,
					    		_ATTACHMENT_BY_PAGE_FIELDS[sortByColIdx],
							    isAsc?"asc":"desc");
					
					st=conn.prepareStatement(sql);
					st.setInt(1, count);
					st.setInt(2, start);
				}
				rs=st.executeQuery();
				while(rs.next())
				{
					if(atts==null)
						atts=new ArrayList<Attachment>();
			    	att=new Attachment();
			    	usr=new User();
			    	att.setId(rs.getInt(1));
			    	att.setTypeId(rs.getInt(2));
			    	att.setLastUpdateDate(rs.getTimestamp(3, cal));
			    	att.setReferenceId(rs.getInt(4));
			    	att.setPath(rs.getString(5));
			    	att.setDescription(rs.getString(6));
			    	att.setChecked(rs.getBoolean(7));
			    	usr.setEmail(rs.getString(8));
			    	usr.setFirstName(rs.getString(9));
			    	usr.setLastName(rs.getString(10));
			    	att.setUser(usr);
			    	
			    	atts.add(att);
				}
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" getAttachmentByOwnerAndPage: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" getAttachmentByOwnerAndPage: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return atts;
	}

	@Override
	public int getCountByUID(int uid) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_R);
			if(uid>0)
			{
				st=conn.prepareStatement(SQL_GET_COUNT_BY_UID);
	        	st.setInt(1, uid);
			}
			else
				st=conn.prepareStatement(SQL_GET_COUNT);
	        rs=st.executeQuery();
	        if(rs.next())
	        	return rs.getInt(1);
		}
		catch(SQLException se)
		{
			Logger.writeLog(this.getClass().getName()+" getCountByUID: "+se.getMessage(), LogLevel.SEVERE);
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
				Logger.writeLog(this.getClass().getName()+" getCountByUID: "+e.getMessage(), LogLevel.SEVERE);
			}
		}
		return 0;
	}

	@Override
	public String getFilePathById(int id) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(id>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
		        st=conn.prepareStatement(SQL_GET_PATH_BY_ID);
		        st.setInt(1, id);
		        rs=st.executeQuery();
		        if(rs.next())
		        	return rs.getString(1);
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" getFilePathById: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" getFilePathById: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return null;
	}

	@Override
	public boolean removeFileRecordById(int id) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		if(id>0)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
		        st=conn.prepareStatement(SQL_REMOVE_RECORD);
		        st.setInt(1, id);
		        return (1==st.executeUpdate());
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" removeFileRecordById: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" removeFileRecordById: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return false;
	}

	@Override
	public int saveFileRecord(int rid, int sid, int uid, int tid, String path, String url,String desc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		if(rid<=0||sid<=0||uid<=0||tid<=0||path==null||path.equals(""))return -1;
		try
		{
			conn=DBConnFactory.getConnection(DB_SOURCE_W);
		    
		    st=conn.prepareStatement(SQL_INSERT_RECORD);
		    
		    st.setString(1, path);
		    st.setInt(2, tid);
		    st.setInt(3, rid);
		    st.setInt(4, sid);
		    st.setInt(5, uid);
		    st.setString(6, url);
		    st.setString(7, desc);
		    rs=st.executeQuery();
		    
		    if(rs.next())
		    {
		    	return rs.getInt(1);
		    }
		    
		}
		catch(SQLException se)
		{
			Logger.writeLog(this.getClass().getName()+" saveFileRecord: "+se.getMessage(), LogLevel.SEVERE);
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
				Logger.writeLog(this.getClass().getName()+" saveFileRecord: "+e.getMessage(), LogLevel.SEVERE);
			}
		}
		return 0;
	}

	@Override
	public ArrayList<Attachment> getFilePathBySection(int sid, int rid,int tid) {
		// TODO Auto-generated method stub
		ArrayList<Attachment> paths=null;
		Attachment att=null;
		if(sid>0&&rid>0&&tid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
			    
			    st=conn.prepareStatement(SQL_GET_RECORD_BY_SECTION);
			    
			    st.setInt(1, tid);
			    st.setInt(2, rid);
			    st.setInt(3, sid);
			    
			    rs=st.executeQuery();
			    
			    while(rs.next())
			    {
			    	if(paths==null)
			    		paths=new ArrayList<Attachment>();
			    	att=new Attachment();
			    	att.setId(rs.getInt(1));
			    	att.setPath(rs.getString(2));
			    	att.setDescription(rs.getString(3));
			    	att.setLastUpdateDate(rs.getTimestamp(4, cal));
			    	att.setSectionId(sid);
			    	att.setTypeId(tid);
			    	att.setReferenceId(rid);
			    	paths.add(att);
			    }
			    
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" getFilePathBySection: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" getFilePathBySection: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return paths;
	}

	@Override
	public boolean updateFileDescription(int rid, String desc) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		if(rid>0&&desc!=null)
		{
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_W);
		        st=conn.prepareStatement(SQL_UPDATE_DESC_BY_ID);
		        st.setString(1, desc);
		        st.setInt(2, rid);
		        return (1==st.executeUpdate());
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" updateFileDescription: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" updateFileDescription: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return false;
		
	}

	@Override
	public boolean isOwnerOfFile(int id, int uid) {
		// TODO Auto-generated method stub
		if(id>0&&uid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
		        st=conn.prepareStatement(SQL_IS_OWNER);
		        st.setInt(1, id);
		        st.setInt(2, uid);
		        rs=st.executeQuery();
		        if(rs.next())
		        	return rs.getInt(1)==1;
		        		
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" isOwnerOfFile: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" isOwnerOfFile: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return false;
	}

	@Override
	public int countByIds(int rid,int sid) {
		// TODO Auto-generated method stub
		if(rid>0&&sid>0)
		{
			Connection conn=null;
			PreparedStatement st=null;
			ResultSet rs=null;
			try
			{
				conn=DBConnFactory.getConnection(DB_SOURCE_R);
		        st=conn.prepareStatement(SQL_COUNT_BY_IDS);
		        st.setInt(1, rid);
		        st.setInt(2, sid);
		        rs=st.executeQuery();
		        if(rs.next())
		        	return rs.getInt(1);
		        		
			}
			catch(SQLException se)
			{
				Logger.writeLog(this.getClass().getName()+" countByIds: "+se.getMessage(), LogLevel.SEVERE);
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
					Logger.writeLog(this.getClass().getName()+" countByIds: "+e.getMessage(), LogLevel.SEVERE);
				}
			}
		}
		return -1;
	}

	

}
