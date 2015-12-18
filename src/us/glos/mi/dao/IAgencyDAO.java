package us.glos.mi.dao;

import java.util.ArrayList;
import org.glc.domain.Orgnization;

public interface IAgencyDAO {
	boolean addAgency(String name,String abbrev,String desc,String url);
	boolean removeAgencyByName(String name);
	boolean removeAgencyByAbbrev(String abbrev);
	boolean removeAgencyById(int id);
	boolean updateAgencyByName(Orgnization org);
	ArrayList<Orgnization> getAllAgencies();
	ArrayList<Orgnization> searchAgencies(String key);
	Orgnization getAgencyById(int id);
	boolean isAgencyNameExist(String name);
	boolean isAgencyAbbrevExist(String abbrev);
	int getAgencyIdByName(String name);
	int getAgencyIdByAbbrev(String abbrev);
}
