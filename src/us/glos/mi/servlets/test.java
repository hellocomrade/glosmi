package us.glos.mi.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glc.DBConnFactory;
import org.glc.utils.Encryption;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.dao.MIDataSource;

/**
 * Servlet implementation class test
 */
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
		conn=DBConnFactory.getConnection(MIDataSource.getRWDataSourceName());
	    
	    st=conn.prepareStatement("insert into test(aur_email,aur_passwd,aur_mask) values (?,?,?);");
	    
	    st.setString(1, "test@test.org");
	    byte[] psd= Encryption.MD5("foobar");
	    for(int i=0;i<psd.length;++i)
	    {
	    	short t=(short)psd[i];
	    	if(t<0)t+=256;
	    	psd[i]=(byte)t;
	    }
	    st.setBytes(2, psd);
	    st.setInt(3, 7);
	    st.executeUpdate();
	    response.getWriter().println(new String(psd,"ASCII"));
	    
	    st.close();
	    st=conn.prepareStatement("select aur_passwd from test where aur_email=?");
	    st.setString(1, "test@test.org");
	    rs=st.executeQuery();
	    byte[] psds=null;
	    if(rs.next())
	    {
	        /*InputStream is=rs.getBinaryStream(1);
	        psds=new byte[is.available()];
	        is.read(psds);
	        is.close();*/
	    	psds=rs.getBytes(1);
	    	response.getWriter().println(new String(psds,"ASCII"));
	    }
	    rs.close();st.close();conn.close();
		}
		catch(Exception e){System.err.println(e.getMessage());}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
