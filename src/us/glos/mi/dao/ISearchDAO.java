package us.glos.mi.dao;

import java.util.ArrayList;

import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.SearchFilter;
import us.glos.mi.domain.AppInfo;

public interface ISearchDAO {
	ArrayList<ModelInfo> searchModelsRestrict(ArrayList<String> keys,int limit,int offset,boolean bFuzzy,SearchFilter filter);
	ArrayList<ModelInfo> searchModelsANDONLY(ArrayList<String> keys);
	int searchModelsRestrictCount(ArrayList<String> keys,boolean bFuzzy,SearchFilter filter);
	ArrayList<ModelInfo> searchModelsFuzzy(ArrayList<String> keys,int limit,int offset,SearchFilter filter);
	ArrayList<ModelInfo> searchModelsORONLY(ArrayList<String> keys);
	ArrayList<ModelInfo> searchModelsByContact(int cid);
	int searchModelsFuzzyCount(ArrayList<String> keys,SearchFilter filter);
	ArrayList<ModelInfo> searchModelsOR(ArrayList<String> keys);
	
}
