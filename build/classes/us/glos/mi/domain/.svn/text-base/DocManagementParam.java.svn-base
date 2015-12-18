package us.glos.mi.domain;


public abstract class DocManagementParam {
	public static class DocAdminAction
	{
		public static String AddNew="new";
		public static String Update="update";
		public static String Remove="remove";
		public static String List="list";
		public static String Draft="draft";
		public static String Save="save";
		public static String Submit="submit";
		public static String Approve="approve";
		public static String ChangeOwner="changeowner";
		public static String ChangeStatus="changestatus";
		public static String Audit="audit";
	}
	protected static String NewDoc="0";
	protected static String UpdateDoc="1";
	public static boolean isApprovedAsNew(String val)
	{
		return NewDoc.equalsIgnoreCase(val);
	}
	public static boolean isApprovedAsUpdate(String val)
	{
		return UpdateDoc.equalsIgnoreCase(val);
	}
	
	public String getNewModelFlag()
	{
		return NewDoc;
	}
	public String getUpdateModelFlag()
	{
		return UpdateDoc;
	}
	protected boolean isNew;
	protected boolean isUpdate;
	protected boolean isSave;
	protected boolean isList;
	protected boolean isDraft;
	protected boolean isRemove;
	protected boolean isSubmit;
	protected boolean isApprove;
	protected boolean isChangeOwner;
	protected boolean isChangeStatus;
	protected boolean isAudit;
	
	public DocManagementParam()
	{
		this.isNew=false;
		this.isUpdate=false;
		this.isSave=false;
		this.isList=false;
		this.isDraft=false;
		this.isRemove=false;
		this.isSubmit=false;
		this.isApprove=false;
		this.isChangeOwner=false;
		this.isChangeStatus=false;
	}
	
	public String getAction() {
		if(this.isNew)
			return DocAdminAction.AddNew;
		else if(this.isUpdate)
			return DocAdminAction.Update;
		else if(this.isRemove)
			return DocAdminAction.Remove;
		else if(this.isList)
			return DocAdminAction.List;
		else if(this.isDraft)
			return DocAdminAction.Draft;
		else if(this.isSave)
			return DocAdminAction.Save;
		else if(this.isSubmit)
			return DocAdminAction.Submit;
		else if(this.isApprove)
			return DocAdminAction.Approve;
		else if(this.isChangeOwner)
			return DocAdminAction.ChangeOwner;
		else if(this.isChangeStatus)
			return DocAdminAction.ChangeStatus;
		else if(this.isAudit)
			return DocAdminAction.Audit;
		else
			return null;
	}

	public void setAction(String action) {
		if(action!=null)
		{
			if(action.equalsIgnoreCase(DocAdminAction.AddNew))
				this.isNew=true;
			else if(action.equalsIgnoreCase(DocAdminAction.Update))
				this.isUpdate=true;
			else if(action.equalsIgnoreCase(DocAdminAction.Remove))
				this.isRemove=true;
			else if(action.equalsIgnoreCase(DocAdminAction.Save))
				this.isSave=true;
			else if(action.equalsIgnoreCase(DocAdminAction.List))
				this.isList=true;
			else if(action.equalsIgnoreCase(DocAdminAction.Draft))
				this.isDraft=true;
			else if(action.equalsIgnoreCase(DocAdminAction.Submit))
				this.isSubmit=true;
			else if(action.equalsIgnoreCase(DocAdminAction.Approve))
				this.isApprove=true;
			else if(action.equalsIgnoreCase(DocAdminAction.ChangeOwner))
				this.isChangeOwner=true;
			else if(action.equalsIgnoreCase(DocAdminAction.ChangeStatus))
				this.isChangeStatus=true;
			else if(action.equalsIgnoreCase(DocAdminAction.Audit))
				this.isAudit=true;
		}
	}
	
	public boolean isNew() {
		return isNew;
	}
	public boolean isUpdate() {
		return isUpdate;
	}
	public boolean isSave() {
		return isSave;
	}
	public boolean isList() {
		return isList;
	}
	public boolean isDraft()
	{
		return isDraft;
	}
	public boolean isRemove() {
		return isRemove;
	}
	public boolean isSubmit() {
		return isSubmit;
	}
	public boolean isApprove() {
		return isApprove;
	}
	public boolean isChangeOwner()
	{
		return isChangeOwner;
	}
	public boolean isChangeStatus()
	{
		return isChangeStatus;
	}
	public boolean isAudit()
	{
		return isAudit;
	}
}
