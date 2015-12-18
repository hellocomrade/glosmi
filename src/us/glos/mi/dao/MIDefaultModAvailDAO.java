package us.glos.mi.dao;

public class MIDefaultModAvailDAO extends MINameValuePairDAO {
	//static
	{
		SQL_GET_ALL_THEMES="select rma_id,rma_desc from ref_mod_avail;";
		SQL_GET_THEME_COUNT="select count(*) from ref_mod_avail;";
		SQL_GET_THEME_BY_ID="select rma_id,rma_desc from ref_mod_avail where rma_id=?;";
		SQL_INSERT_THEME="insert into ref_mod_avail (rma_desc) values(?) returning rma_id;";
		SQL_UPDATE_VALUE_BY_NAME="update ref_mod_avail set rma_desc=? where rma_id=?;";	
	}

}
