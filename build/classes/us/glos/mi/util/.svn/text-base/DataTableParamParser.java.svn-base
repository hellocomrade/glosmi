package us.glos.mi.util;

import javax.servlet.ServletRequest;

import org.glc.utils.Validation;

/*
 *implementation according to http://datatables.net/examples/data_sources/server_side.html  
 */
public class DataTableParamParser {
	public static class DataTableParams
	{
		private int iStartAt;
		private int iDisplayLenth;
		private int totalRecords=0;
		private int totalFiltered=0;
		private int extraCols=0;
		private String sEcho=null;
		private int[] sortedCols=null;
		private String[] sortedOrder=null;
		private DataTableParams(int start,int len,int[] sc,String[] so,String echo)
		{
			this.iStartAt=start;
			this.iDisplayLenth=len;
			this.sortedCols=sc;
			this.sortedOrder=so;
			this.sEcho=echo;
		}
		public static DataTableParams CreateInstance(int start,int len,int[] sCols,String[] sColsOrder,String sEcho)
		{
			DataTableParams param=null;
			if(start>=0&&len>0&&sCols!=null&&sCols.length>0&&sColsOrder!=null&&sColsOrder.length>0&&sCols.length==sColsOrder.length&&sEcho!=null)
			{
				param=new DataTableParams(start,len,sCols,sColsOrder,sEcho);
			}
			return param;
		}
		public static String getClassName()
		{
			return DataTableParams.class.getName();
		}
		public int getiStartAt() {
			return iStartAt;
		}
		public int getiDisplayLenth() {
			return iDisplayLenth;
		}
		public int[] getSortedCols() {
			return sortedCols;
		}
		public String[] getSortedOrder() {
			return sortedOrder;
		}
		public String getsEcho() {
			return sEcho;
		}
		public int getTotalRecords() {
			return totalRecords;
		}
		public void setTotalRecords(int totalRecords) {
			this.totalRecords = totalRecords;
		}
		public int getTotalFiltered() {
			return totalFiltered;
		}
		public void setTotalFiltered(int totalFiltered) {
			this.totalFiltered = totalFiltered;
		}
		public int getExtraCols() {
			return extraCols;
		}
		public void setExtraCols(int extraCols) {
			this.extraCols = extraCols;
		}
		
	}
	public static DataTableParams ParseDataTableParameters(ServletRequest request)
	{
		DataTableParams param=null;
		if(request!=null)
		{
			int start=0;
			int len=10;
			int sortLen=1;
			int[] sCols=null;
			String[] sColsOrder=null;
			String temp=null;
			int index=0;
			String order=null;
			String echo=null;
			if(Validation.basicValidation(request,"iDisplayStart"))
			{
				try
				{
					start=Integer.parseInt(request.getParameter("iDisplayStart"));
				}
				catch(NumberFormatException ne)
				{
					start=0;
				}
				if(start<0)start=0;
				if(Validation.basicValidation(request,"iDisplayLength"))
				{
					try
					{
						len=Integer.parseInt(request.getParameter("iDisplayLength"));
					}
					catch(NumberFormatException ne)
					{
						len=10;
					}
					if(len<0)len=10;
				}
			}
			if(Validation.basicValidation(request,"iSortCol_0"))
			{
				if(Validation.basicValidation(request,"iSortingCols"))
				{
					try
					{
						sortLen=Integer.parseInt(request.getParameter("iSortingCols"));
					}
					catch(NumberFormatException ne)
					{
						sortLen=1;
					}
				}
				sCols=new int[sortLen];
				sColsOrder=new String[sortLen];
				for(int i=0;i<sortLen;++i)
				{
					temp=String.format("iSortCol_%d", i);
					if(Validation.basicValidation(request,temp))
					{
						try
						{
							index=Integer.parseInt(request.getParameter(temp));
						}
						catch(NumberFormatException ne)
						{
							index=0;
						}
						sCols[i]=index;
					}
					temp=String.format("sSortDir_%d", i);
					
					if(Validation.basicValidation(request,temp))
					{
						order=request.getParameter(temp);
						if(order!=null)
						{
							if(order.equalsIgnoreCase("asc"))
								sColsOrder[i]="asc";
							else if(order.equalsIgnoreCase("desc"))
								sColsOrder[i]="desc";
						}
					}
				}
			}
			if(Validation.basicValidation(request,"sEcho"))
				echo=request.getParameter("sEcho");
			param=DataTableParams.CreateInstance(start, len, sCols, sColsOrder,echo);
			if(Validation.basicValidation(request,"extraCol"))
			{
				index=0;
				try
				{
					index=Integer.parseInt(request.getParameter("extraCol"));
				}
				catch(NumberFormatException ne)
				{
					index=0;
				}
				param.setExtraCols(index);
			}
		}
		return param;
	}
}
