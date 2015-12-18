package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;
import org.glc.domain.Orgnization;
import org.glc.utils.HTMLUtilities;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IAgencyDAO;

/**
 * Servlet implementation class OrgOptServlet
 */
public class OrgOptServlet extends HttpServlet {
	static final String DAO_NAME="DAO";
	private IAgencyDAO dao=null;
    /**
	 * 
	 */
	private static final long serialVersionUID = -4108388167477450282L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public OrgOptServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String cln=this.getInitParameter(DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			Class<IAgencyDAO> iclass=(Class<IAgencyDAO>)Class.forName(cln);
			this.dao=iclass.getConstructor().newInstance(new Object[0]);
			
		}
		catch(ClassNotFoundException cfe)
		{
			throw new ServletException(cfe.getMessage());
		}
		catch(IllegalAccessException iae)
		{
			throw new ServletException(iae.getMessage());
		}
		catch(InvocationTargetException ite)
		{
			throw new ServletException(ite.getMessage());
		}
		catch(InstantiationException ie)
		{
			throw new ServletException(ie.getMessage());
		}
		catch(NoSuchMethodException nse)
		{
			throw new ServletException(nse.getMessage());
		}
	}
    private String _buildOrgJSON(Orgnization org)
    {
    	StringBuilder sb=new StringBuilder();
    	sb.append('{');
		sb.append(String.format("\"%s\":","id"));
		sb.append(String.format("%d", org.getId()));
		sb.append(',');
		sb.append(String.format("\"%s\":","name"));
		sb.append('"');
		sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(String.format("%s", org.getName()))));
		sb.append('"');
		sb.append(',');
		sb.append(String.format("\"%s\":","abbrev"));
		sb.append('"');
		sb.append(HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(String.format("%s", org.getAbbrev()))));
		sb.append('"');
		sb.append(',');
		sb.append(String.format("\"%s\":","desc"));
		sb.append('"');
		sb.append(org.getDescription()==null?"":HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(String.format("%s", org.getDescription()))));
		sb.append('"');
		sb.append(',');
		sb.append(String.format("\"%s\":","url"));
		sb.append('"');
		sb.append(org.getUrl()==null?"":HTMLUtilities.filterDisplay(HTMLUtilities.JSONStringify(String.format("%s", org.getUrl()))));
		sb.append('"');
		sb.append('}');
    	return sb.toString();
    }
	private String buildJSON(ArrayList<Orgnization> orgs)
    {
    	StringBuilder sb=new StringBuilder();
		sb.append("[");
		for(Orgnization org:orgs)
		{
			sb.append(this._buildOrgJSON(org));
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
			{
				if(Validation.basicValidation(request, "action"))
				{
					if(request.getParameter("action").equals("search"))
					{
						if(Validation.basicGroupValidation(request, "key"))
						{
							String key=request.getParameter("key").trim();
							ArrayList<Orgnization> orgs=this.dao.searchAgencies(key);
							if(orgs!=null&&!orgs.isEmpty())
							{
								response.getWriter().print(this.buildJSON(orgs));
								return;
							}
						}
						response.getWriter().print("0");
						return;
					}
					else if(request.getParameter("action").equals("list"))
					{
						ArrayList<Orgnization> orgs=this.dao.getAllAgencies();
						if(orgs!=null&&!orgs.isEmpty())
							response.getWriter().print(this.buildJSON(orgs));
						else
							response.getWriter().print("0");
						return;
					}
					
					else if(request.getParameter("action").equals("get"))
					{
						if(Validation.basicGroupValidation(request, "oid"))
						{
							int oid=0;
							try
							{
								oid=Integer.parseInt(request.getParameter("oid"));
							}
							catch(NumberFormatException ne)
							{
								oid=0;
							}
							if(oid>0)
							{
								Orgnization org=this.dao.getAgencyById(oid);
								if(org!=null)
									response.getWriter().print(this._buildOrgJSON(org));
							}
							return;
						}
					}
					
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
			{
				if(Validation.basicValidation(request, "action"))
				{
					if(request.getParameter("action").equals("add"))
					{
						if(Validation.basicGroupValidation(request, "name")
								&&Validation.basicGroupValidation(request, "abbrev"))
						{
							String name=request.getParameter("name").trim();
							String abbrev=request.getParameter("abbrev").trim();
							String desc=request.getParameter("desc");
							if(desc!=null)desc=desc.trim();
							String url=request.getParameter("url");
							if(url!=null)
								url=url.trim();
							if(this.dao.isAgencyNameExist(name))
							{
								response.getWriter().print("2");
								return;
							}
							else if(this.dao.isAgencyAbbrevExist(abbrev))
							{
								response.getWriter().print("3");
								return;
							}
							if(this.dao.addAgency(name,abbrev,
													desc,url))
								response.getWriter().print("0");
							else
								response.getWriter().print("1");
						}
						return;
					}
					else if(request.getParameter("action").equals("update"))
					{
						if(Validation.basicGroupValidation(request, "oid")
						  &&Validation.basicGroupValidation(request, "name")
						  &&Validation.basicGroupValidation(request, "abbrev"))
						{
							int oid=0;
							try
							{
								oid=Integer.parseInt(request.getParameter("oid").trim());
							}
							catch(NumberFormatException ne)
							{
								oid=0;
							}
							if(oid>0)
							{
								
								Orgnization org=new Orgnization();
								org.setId(oid);
								org.setName(request.getParameter("name").trim());
								org.setAbbrev(request.getParameter("abbrev").trim());
								String temp=null;
								temp=request.getParameter("desc");
								if(temp!=null)
									temp=temp.trim();
								org.setDescription(temp);
								temp=request.getParameter("url");
								if(temp!=null)
									temp=temp.trim();
								org.setUrl(temp);
							    int id=this.dao.getAgencyIdByName(org.getName());
							    if(id!=org.getId())
							    {
							    	response.getWriter().print('2');
							    	return;
							    }
							    id=this.dao.getAgencyIdByAbbrev(org.getAbbrev());
							    if(id!=org.getId())
							    {
							    	response.getWriter().print('3');
							    	return;
							    }
								if(this.dao.updateAgencyByName(org))
									response.getWriter().print('0');
								else
									response.getWriter().print('1');
							}
							return;
						}
					}
				}
				
			}
		}
				
	}
	
}
