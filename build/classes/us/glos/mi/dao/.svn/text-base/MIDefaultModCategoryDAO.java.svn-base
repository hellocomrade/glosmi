package us.glos.mi.dao;


public class MIDefaultModCategoryDAO extends MINameValuePairDAO{

	//static
	{
		SQL_GET_ALL_THEMES="select rmc_id,rmc_desc from ref_mod_cat;";
		SQL_GET_THEME_COUNT="select count(*) from ref_mod_cat;";
		SQL_GET_THEME_BY_ID="select rmc_id,rmc_desc from ref_mod_cat where rmc_id=?;";
		SQL_INSERT_THEME="insert into ref_mod_cat (rmc_desc) values(?) returning rmc_id;";
		SQL_UPDATE_VALUE_BY_NAME="update ref_mod_cat set rmc_desc=? where rmc_id=?;";
	}
}
