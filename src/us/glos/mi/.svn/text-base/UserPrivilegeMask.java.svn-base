package us.glos.mi;

public final class UserPrivilegeMask {
	public static final byte ADMIN_MASK=0x4;
	public static final byte MODELER_MASK=0x2;
	public static final byte APP_MASK=0x1;
	
	public static int CreatPrivilegeMask(boolean isAdmin,boolean isMod,boolean isApp)
	{
		int i=0;
		if(isAdmin)
			i=i|ADMIN_MASK;
		if(isMod)
			i=i|MODELER_MASK;
		if(isApp)
			i=i|APP_MASK;
		return i;
	}
	public static boolean IsAdmin(int pri)
	{
		return (pri&ADMIN_MASK)>0;
	}
	
	public static boolean IsModeler(int pri)
	{
		return (pri&MODELER_MASK)>0;
	}
	
	public static boolean IsApplication(int pri)
	{
		return (pri&APP_MASK)>0;
	}
	
	public static boolean IsNonPrivilege(int pri)
	{
		return pri==0;
	}
	
	public static String getAdminSubPath()
	{
		return "adm";
	}
	public static String getModelerSubPath()
	{
		return "mod";
	}
	public static String getApplicationSubPath()
	{
		return "app";
	}
	
}
