package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import org.glc.utils.ServerUtilities;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;
import org.glc.ILiteralProvider;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.dao.ISearchDAO;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.SearchResultParam;
import us.glos.mi.domain.SearchFilter;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	
    private static final String MAX_SEARCH_STRING_LENGTH="MAX_SEARCH_STRING_LENGTH";
    private static int MAX_SEARCH_KEY_LEN=255;
    private static String SEARCH_PARAM_LIMIT="l";
    private static String SEARCH_PARAM_OFFSET="o";
     
    static final String DAO_NAME="DAO";
    static final String SEARCH_PARAM_LIMIT_NAME="SEARCH_PARAM_LIMIT_NAME";
    static final String SEARCH_PARAM_OFFSET_NAME="SEARCH_PARAM_OFFSET_NAME";
    static final String SEARCH_URL_PARAM_FMT="?key=%s&l=%d&o=%d&f=%d&of=%s";
    private ISearchDAO dao=null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2637144368887929950L;

	static
	{
		String temp=ConfigManager.getAppSetting(MAX_SEARCH_STRING_LENGTH);
		if(temp!=null&&!temp.equals(""))
		{
			try
			{
				MAX_SEARCH_KEY_LEN=Integer.parseInt(temp);
			}
			catch(NumberFormatException e)
			{
				MAX_SEARCH_KEY_LEN=255;
				Logger.writeLog(SearchServlet.class.getName()+" MAX_SEARCH_KEY_LEN is not valid integer", LogLevel.INFO);
			}
		}
		temp=ConfigManager.getAppSetting(SEARCH_PARAM_LIMIT_NAME);
		if(temp!=null&&!temp.trim().equals(""))
			SEARCH_PARAM_LIMIT=temp;
		temp=ConfigManager.getAppSetting(SEARCH_PARAM_OFFSET_NAME);
		if(temp!=null&&!temp.trim().equals(""))
			SEARCH_PARAM_OFFSET=temp;
	}
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String cln=this.getInitParameter(DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			Class<ISearchDAO> iclass=(Class<ISearchDAO>)Class.forName(cln);
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=null;
		int limit=10;
		int offset=0;
		int oldoffset=-1;
		int count=0;
		boolean allowOR=false;
		boolean switch2OR=false;
		boolean hasExtra=false;
		boolean hasMore=false;
		
		String url_prev=null;
		String url_next=null;
		SearchFilter filter=null;
		if(Validation.basicValidation(request,SearchResultParam.getSearchWordsParam()))
		{
			String rawstr=request.getParameter(SearchResultParam.getSearchWordsParam()).trim();
			int len=rawstr.length();
			if(len<=MAX_SEARCH_KEY_LEN)
			{
				int lastindex=0;
				ArrayList<String> keys=new ArrayList<String>();
				char ch=' ';
				
				int i=0;
				for(;i<len;++i)
				{
					ch=rawstr.charAt(i);
					switch(ch){
					case ' ':
					case '\t':
					case ',':
					case ';':
					case '.':
					case '\r':
					case '\n':
						if(lastindex<i)
							keys.add(rawstr.substring(lastindex, i));
						lastindex=i+1;
						break;
					default:
						continue;
					}
				}
				if(lastindex<i)
					keys.add(rawstr.substring(lastindex, i));
				if(Validation.basicValidation(request,SEARCH_PARAM_LIMIT))
				{
					try
					{
						limit=Integer.parseInt(request.getParameter(SEARCH_PARAM_LIMIT).trim());
					}
					catch(NumberFormatException e)
					{
						limit=10;
					}
				}
				if(Validation.basicValidation(request,SEARCH_PARAM_OFFSET))
				{
					try
					{
						offset=Integer.parseInt(request.getParameter(SEARCH_PARAM_OFFSET).trim());
					}
					catch(NumberFormatException e)
					{
						offset=10;
					}
				}
				if(Validation.basicValidation(request,SearchResultParam.getSearchOffsetRistParam()))
				{
					try
					{
						oldoffset=Integer.parseInt(request.getParameter(SearchResultParam.getSearchOffsetRistParam()).trim());
					}
					catch(NumberFormatException e)
					{
						oldoffset=-1;
					}
				}
				if(Validation.basicValidation(request,SearchResultParam.getFuzzyMatchParam()))
				{
					if(request.getParameter(SearchResultParam.getFuzzyMatchParam()).trim().equals("1"))
						allowOR=true;
				}
				if(Validation.basicGroupValidation(request, SearchResultParam.getFilterCategoryParam()))
				{
					String[] temp=request.getParameterValues(SearchResultParam.getFilterCategoryParam());
					if(temp!=null&&temp.length>0)
					{
						if(filter==null)
							filter=new SearchFilter();
						for(String t:temp)
						{
							try
							{
								filter.addCategory(Integer.parseInt(t));
							}
							catch(NumberFormatException e)
							{
								continue;
							}
						}
					}
				}
				if(Validation.basicGroupValidation(request, SearchResultParam.getFilterThemeParam()))
				{
					String[] temp=request.getParameterValues(SearchResultParam.getFilterThemeParam());
					if(temp!=null&&temp.length>0)
					{
						if(filter==null)
							filter=new SearchFilter();
						for(String t:temp)
						{
							try
							{
								filter.addThemes(Integer.parseInt(t));
							}
							catch(NumberFormatException e)
							{
								continue;
							}
						}
					}
				}
				if(Validation.basicValidation(request, SearchResultParam.getFilterAvailParam()))
				{
					String temp=request.getParameter(SearchResultParam.getFilterAvailParam());
					if(temp!=null&&!temp.equals(""))
					{
						if(filter==null)
							filter=new SearchFilter();
						try
						{
							filter.addAvails(Integer.parseInt(temp));
						}
						catch(NumberFormatException e)
						{
							
						}
					}
				}
				ArrayList<ModelInfo> mods=null;
				ArrayList<ModelInfo> mods_extra=null;
				int cur_offset=offset;
				/*
				 * All the funny codes here are for handling the edge between AND and OR search
				 * offset is for the current offset no matter AND or OR
				 * oldoffset is for recording the previous AND search offset
				 */
				boolean hasAndBefore=false;
				if(!allowOR)
				{
					mods=this.dao.searchModelsRestrict(keys,limit+1,offset,false,filter);
					if(mods!=null&&!mods.isEmpty())
					{
						if(mods.size()<limit)
						{
							mods_extra=this.dao.searchModelsFuzzy(keys, limit+1-mods.size(), 0,filter);
							if(mods_extra!=null&&!mods_extra.isEmpty())
							{
								//save oldoffset for AND search
								oldoffset=offset;
								hasAndBefore=true;
								offset=limit-mods.size();
								for(ModelInfo m:mods_extra)
								{
									mods.add(m);
								}
								
								hasExtra=true;
								
								
								
							}
						}
						
						if(mods.size()==limit&&mods_extra==null)
						{
							mods_extra=this.dao.searchModelsFuzzy(keys, 1, 0,filter);
							if(mods_extra!=null&&mods_extra.size()==1)
							{
								hasMore=true;
								
							}
						}
						else if(mods.size()==limit+1)
						{
							hasMore=true;
							mods.remove(mods.size()-1);
						}
						//count=this.dao.searchModelsRestrictCount(keys, true,filter);
						//if(count>limit)//+offset)
						//	hasMore=true;
						
					}
					else//no record match keywords with AND operator,try OR
					{
						oldoffset=offset-limit>0?(offset-limit):0;
						offset=0;
						hasAndBefore=true;
						mods=this.dao.searchModelsFuzzy(keys, limit+1, offset,filter);
						allowOR=true;
						switch2OR=true;
						//count=this.dao.searchModelsRestrictCount(keys, true,filter);//+this.dao.searchModelsFuzzyCount(keys);
						//if(count>limit+offset)
						//	hasMore=true;
						if(mods!=null&&mods.size()==limit+1)
						{
							hasMore=true;
							mods.remove(mods.size()-1);
						}
					}
				}
				else
				{
					if(oldoffset>=0)hasAndBefore=true;
					mods=this.dao.searchModelsFuzzy(keys, limit+1, offset,filter);
					/*count=this.dao.searchModelsRestrictCount(keys, true,filter);//+this.dao.searchModelsFuzzyCount(keys);
					if(mods!=null)
					{
						int tmp=offset+mods.size();
						if(oldoffset>0)
							tmp+=oldoffset;
						if(count>tmp)
							hasMore=true;
					}*/
					if(mods!=null&&mods.size()==limit+1)
					{
						hasMore=true;
						mods.remove(mods.size()-1);
					}
				}
				action="list";
				
				if(mods!=null)
				{
					//get overall count for a search
					count=this.dao.searchModelsRestrictCount(keys, true,filter);
					int o=-1;
					//if(!allowOR&&oldoffset>=0)
					//	o=oldoffset;
					//else
					o=(offset-limit)>0?(offset-limit):0;
					//for previous link only when allowOR happened and current OR offset reaches 0 so go back to assign oldoffset for AND search
					if(allowOR&&o==0&&oldoffset>=0)
						o=oldoffset;
					String encodedkey=java.net.URLEncoder.encode(rawstr, "UTF8");
					if((count>limit)&&((offset>0&&!hasExtra)//handle the case like AND+8 and OR+2 on the first page, the previous link should be empty for this case 
							||(hasAndBefore&&oldoffset>0)))//handle the case like the AND search ends on the !first page
					{
						url_prev=String.format(SEARCH_URL_PARAM_FMT,encodedkey,limit,o,
								(allowOR&&!switch2OR&&(offset-limit)>=0)?1:0,
										oldoffset>=0?String.format("%d",oldoffset):"-1");
					}
					

					if(hasMore&&mods.size()==limit)
					{
						url_next=String.format(SEARCH_URL_PARAM_FMT,encodedkey,limit,
								hasExtra?offset:offset+limit,
											allowOR||hasExtra?1:0,
													oldoffset>=0?String.format("%d",oldoffset):"-1");
					}
					if(url_prev!=null||url_next!=null)
					{
						if(filter!=null)
						{
							StringBuilder sb=new StringBuilder();
							if(filter.getCategories()!=null&&!filter.getCategories().isEmpty())
							{
								for(Integer it:filter.getCategories())
								{
									sb.append(String.format("&%s=%d",SearchResultParam.getFilterCategoryParam(),it));
								}
							}
							if(filter.getThemes()!=null&&!filter.getThemes().isEmpty())
							{
								for(Integer it:filter.getThemes())
								{
									sb.append(String.format("&%s=%d",SearchResultParam.getFilterThemeParam(),it));
								}
							}
							if(filter.getAvails()>0)
							{
								sb.append(String.format("&%s=%d",SearchResultParam.getFilterAvailParam(),filter.getAvails() ));
							}
							if(sb.length()>0)
							{
								if(url_next!=null)
									url_next+=sb.toString();
								if(url_prev!=null)
									url_prev+=sb.toString();
							}
						}
					}
						if(url_prev!=null)
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchPrevURLParam(),url_prev);
						if(url_next!=null)
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchNextURLParam(),url_next);
				}
				
				ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchWordsParam(), rawstr);
				
				ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchRecordPerPageParam(), limit);
				if(mods!=null)
				{
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchResultParam(), mods);
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchCountParam(), count);
				}
				/*if(mods.size()==limit)
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchOffsetNextParam(), offset+limit);
				else
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchOffsetNextParam(), "");//no more
				if(!allowOR&&oldoffset>=0)//go back to restrict search
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchOffsetPrevParam(), oldoffset);
				else
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchOffsetPrevParam(), (offset-limit)>0?(offset-limit):0);
				ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getFuzzyMatchNextParam(), allowOR?"1":"0");
				if(hasExtra)//mix page
				{
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getSearchOffsetRistParam(), oldoffset);
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getFuzzyMatchPrevParam(), "0");
				}
				else
				{
					ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, SearchResultParam.getFuzzyMatchPrevParam(), allowOR?"1":"0");
				}*/
			}
			else
				action="error";
		}
		else
			action="empty";
		
		request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
		ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
