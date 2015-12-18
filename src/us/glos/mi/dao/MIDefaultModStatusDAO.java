package us.glos.mi.dao;

public class MIDefaultModStatusDAO extends MINameValuePairDAO {
	//static
	{
		SQL_GET_ALL_THEMES="select rms_id,rms_desc from ref_mod_status;";
		SQL_GET_THEME_COUNT="select count(*) from ref_mod_status;";
		SQL_GET_THEME_BY_ID="select rms_id,rms_desc from ref_mod_status where rms_id=?;";
		
	}
	
}
