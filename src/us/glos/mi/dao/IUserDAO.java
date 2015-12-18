package us.glos.mi.dao;

import org.glc.domain.User;
import org.glc.ITimeZoneAware;
import java.util.ArrayList;

public interface IUserDAO extends ITimeZoneAware{
    boolean addUser(User usr);
    boolean resetPassword(User usr,byte[] passwd);
    boolean updateUserInfo(User usr,int oid);
    boolean removeUser(User usr);
    public boolean isValidUser(String email,byte[] passwd);
    boolean isValidUser(User usr);
    boolean setPrivilege(User usr);
    boolean isUserExist(String email);
    boolean updateUserStatus(User usr,boolean active);
    User getUserByEmail(String email);
    User getUserById(int id);
    ArrayList<User> searchUsers(String fname,String lname,String email);
    int getTotalUserCount();
    int getOrgnizationId(String abbrev,String name);
    
}
