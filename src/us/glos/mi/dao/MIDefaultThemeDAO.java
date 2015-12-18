package us.glos.mi.dao;

public class MIDefaultThemeDAO extends MINameValuePairDAO {

	
	//static
	{ 
		SQL_GET_ALL_THEMES="select rgt_id,rgt_desc from ref_glos_theme;";
		SQL_GET_THEME_COUNT="select count(*) from ref_glos_them;";
		SQL_GET_THEME_BY_ID="select rgt_id,rgt_desc from ref_glos_theme where rgt_id=?;";
		SQL_INSERT_THEME="insert into ref_glos_theme (rgt_desc) values(?) returning rgt_id;";
		SQL_UPDATE_VALUE_BY_NAME="update ref_glos_theme set rgt_desc=? where rgt_id=?;";
	}
	
	
}
