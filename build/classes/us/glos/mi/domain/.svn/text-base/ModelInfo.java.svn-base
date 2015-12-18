package us.glos.mi.domain;

import java.util.Date;
import java.util.ArrayList;
import java.io.Serializable;
import us.glos.mi.domain.Contact;

public class ModelInfo implements Serializable, IDraft{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2043716303978859387L;
	ArrayList<Contact> developers=null;
	ArrayList<Contact> distributors=null;
	ArrayList<Contact> contacts=null;
	
	transient int serialId;
	int id;
	String abbreviation=null;
	String name=null;
	String description=null;
	String versionNo=null;
	String attribute=null;
	String dataRequirement=null;
	int availableId;
	int[] categoryIds=null;
	int[] themeIds=null;
	String note=null;
	Date lastUpdateDate=null;
	String status=null;
	transient String ownerName=null;
	transient String ownerEmail=null;
	String skillLevel=null;
	String strength=null;
	String approvedFlag=null;
	boolean isActive=false;
	String updateReason=null;
	String url;
	public static String getClassName()
    {
    	return ModelInfo.class.getName();
    }
	public ModelInfo()
	{
		id=-1;
		availableId=-1;
		serialId=-1;
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
			this.distributors = new ArrayList<Contact>();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getAvailableId() {
		return availableId;
	}

	public void setAvailableId(int availableId) {
		this.availableId = availableId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	@Override
	public String getOwnerName() {
		return ownerName;
	}
	@Override
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	@Override
	public String getOwnerEmail() {
		return ownerEmail;
	}
	@Override
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public int[] getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(int[] categoryIds) {
		this.categoryIds = categoryIds;
	}
	public int[] getThemeIds() {
		return themeIds;
	}
	public void setThemeIds(int[] themeIds) {
		this.themeIds = themeIds;
	}
	@Override
	public int getSerialId() {
		return serialId;
	}
	@Override
	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}
	public String getDataRequirement() {
		return dataRequirement;
	}
	public void setDataRequirement(String dataRequirement) {
		this.dataRequirement = dataRequirement;
	}
	public String getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getApprovedFlag() {
		return approvedFlag;
	}
	public void setApprovedFlag(String approvedFlag) {
		this.approvedFlag = approvedFlag;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
