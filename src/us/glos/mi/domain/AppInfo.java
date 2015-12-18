package us.glos.mi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AppInfo implements Serializable,IDraft {

	/**
	 * 
	 */
	private static final long serialVersionUID = -661245941962833023L;
	
	public static String getClassName()
    {
    	return AppInfo.class.getName();
    }
	
	ArrayList<Contact> developers=null;
	ArrayList<Contact> distributors=null;
	ArrayList<Contact> contacts=null;
	
	transient int serialId;
	int id;
	int modId;
	int modDraftId;
	String appName=null;
	String modelName=null;
	String appPurpose=null;
	String appDescription=null;
	String appNote=null;
	String status=null;
	transient String ownerName=null;
	transient String ownerEmail=null;
	String name=null;
	String location=null;
	Date lastUpdateDate=null;
	boolean isActive=false;
	String approvedFlag=null;
	String updateReason=null;
	String url;
	public AppInfo()
	{
		this.serialId=-1;
		this.id=-1;
		this.modId=-1;
		this.modDraftId=-1;
	}
	public ArrayList<Contact> getDevelopers() {
		return developers;
	}
	public void setDeveloper(Contact developer) {
		if(developer==null)return;
		if(this.developers==null)
			this.developers=new ArrayList<Contact>();
		this.developers.add(developer);
	}
	public ArrayList<Contact> getDistributors() {
		return distributors;
	}
	public void setDistributor(Contact distributor) {
		if(distributor==null)return;
		if(this.distributors==null)
			this.distributors=new ArrayList<Contact>();
		this.distributors.add(distributor);
	}
	public ArrayList<Contact> getContacts() {
		return contacts;
	}
	public void setContact(Contact contact) {
		if(contact==null)return;
		if(this.contacts==null)
			this.contacts=new ArrayList<Contact>();
		this.contacts.add(contact);
	}
	@Override
	public int getSerialId() {
		return serialId;
	}
	@Override
	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModId() {
		return modId;
	}
	public void setModId(int modId) {
		this.modId = modId;
	}
	public int getModDraftId() {
		return modDraftId;
	}
	public void setModDraftId(int modDraftId) {
		this.modDraftId = modDraftId;
	}
	public String getAppPurpose() {
		return appPurpose;
	}
	public void setAppPurpose(String appPurpose) {
		this.appPurpose = appPurpose;
	}
	public String getAppDescription() {
		return appDescription;
	}
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}
	public String getAppNote() {
		return appNote;
	}
	public void setAppNote(String appNote) {
		this.appNote = appNote;
	}
	@Override
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	@Override
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}*/
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	@Override
	public String getOwnerEmail() {
		// TODO Auto-generated method stub
		return this.ownerEmail;
	}
	@Override
	public String getOwnerName() {
		// TODO Auto-generated method stub
		return this.ownerName;
	}
	@Override
	public void setOwnerEmail(String ownerEmail) {
		// TODO Auto-generated method stub
		this.ownerEmail=ownerEmail;
	}
	@Override
	public void setOwnerName(String ownerName) {
		// TODO Auto-generated method stub
		this.ownerName=ownerName;
	}
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getApprovedFlag() {
		return approvedFlag;
	}
	public void setApprovedFlag(String approvedFlag) {
		this.approvedFlag = approvedFlag;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
