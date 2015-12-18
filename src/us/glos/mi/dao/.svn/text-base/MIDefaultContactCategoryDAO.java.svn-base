package us.glos.mi.dao;

public class MIDefaultContactCategoryDAO extends MINameValuePairDAO {
	//static
	{ 
		SQL_GET_ALL_THEMES="select rcc_id,rcc_desc from ref_cnt_cat;";
	    SQL_GET_THEME_COUNT="select count(*) from ref_cnt_cat;";
	    SQL_GET_THEME_BY_ID="select rcc_id,rcc_desc from ref_cnt_cat where rcc_id=?;";
	    SQL_INSERT_THEME="insert into ref_cnt_cat (rcc_desc) values(?) returning rcc_id;";
	    SQL_UPDATE_VALUE_BY_NAME="update ref_cnt_cat set rcc_desc=? where rcc_id=?;";	

	}
}
