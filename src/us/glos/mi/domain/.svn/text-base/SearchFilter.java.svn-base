package us.glos.mi.domain;

import java.util.ArrayList;

public class SearchFilter {
	private ArrayList<Integer> categories=null;
	private boolean isCategoryOR=false;
	private boolean isAvailsOR=false;
	private boolean isThemesOR=false;
	private int avails=0;
	private ArrayList<Integer> themes=null;
	public void addCategory(int i)
	{
		if(i>0)
		{
			if(categories==null)
				this.categories=new ArrayList<Integer>();
			categories.add(i);
		}
	}
	public void addAvails(int i)
	{
		if(i>0)
		{
			this.avails=i;
		}
	}
	public void addThemes(int i)
	{
		if(i>0)
		{
			if(themes==null)
				themes=new ArrayList<Integer>();
			themes.add(i);
		}
	}
	public ArrayList<Integer> getCategories() {
		return categories;
	}
	public int getAvails() {
		return avails;
	}
	public ArrayList<Integer> getThemes() {
		return themes;
	}
	public boolean isCategoryOR() {
		return isCategoryOR;
	}
	public void setCategoryOR(boolean isCategoryOR) {
		this.isCategoryOR = isCategoryOR;
	}
	public boolean isAvailsOR() {
		return isAvailsOR;
	}
	public void setAvailsOR(boolean isAvailsOR) {
		this.isAvailsOR = isAvailsOR;
	}
	public boolean isThemesOR() {
		return isThemesOR;
	}
	public void setThemesOR(boolean isThemesOR) {
		this.isThemesOR = isThemesOR;
	}
	
}
