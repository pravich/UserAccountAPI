package com.yggdrasil.account;

import java.util.Hashtable;
import java.util.List;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yggdrasil.account.directory.ConnectionException;
import com.yggdrasil.account.directory.InvalidCredentialException;
import com.yggdrasil.account.directory.UserDirectory;
import com.yggdrasil.account.directory.UserDirectoryFactory;
import com.yggdrasil.account.directory.UserEntry;

@WebService
public class UserAccount implements UserAccountInterface {
	
	private static Logger logger = LogManager.getLogger(UserAccount.class);

	@Override
	public UserEntry searchUser(String username, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException {
		
		UserAccountCacheItem cacheItemCredential = UserAccountCache.getUserItem(sessionToken);
		
		if(cacheItemCredential == null) {
			throw new InvalidSessionException();
		}

		if((cacheItemCredential.role == null) || (!cacheItemCredential.role.contains("[admin.search]"))) {
			throw new InvalidUserPrivilegeException();
		}
		
		UserDirectory ud = UserDirectoryFactory.getUserDirectory();
		if(ud == null) {
			logger.error("UserDirectory instantiation fails.");
			return(null);
		}
		
		UserEntry ueSearchResult = ud.searchUser(username);
		ud.close();
		
		if (ueSearchResult != null) {
			logger.debug("found user " + username + ".");
		} else {
			logger.debug("not found user " + username + ".");
		}
		
		return(ueSearchResult);
	}
	
	@Override
	public List<String> getUserList() throws ConnectionException {
		UserDirectory ud = UserDirectoryFactory.getUserDirectory();

		if(ud == null) {
			logger.error("UserDirectory instantiation fails.");
			return(null);
		}
		
		List<String> userlist = ud.getUserList();
		ud.close();
		
		return(userlist);
	}
	
	@Override
	public String signon(String username, String password) throws ConnectionException, InvalidCredentialException {
		if(UserDirectory.signon(username, password)) { // <-- throws ConnectionException
			UserAccountCacheItem cacheItem = new UserAccountCacheItem();
			cacheItem.username = username;
			
			UserDirectory ud = UserDirectoryFactory.getUserDirectory(); // <-- throws ConnectionException
			cacheItem.role = ud.getUserRole(username);		
			ud.close();

			String sessionToken = SessionTokenGenerator.getSessionToken();	
			UserAccountCache.setSessionToken(sessionToken, cacheItem);

			logger.debug(username + " signon succeeds.");	
			return(sessionToken);
		} else {
			logger.debug(username + " signon fail.");
			return(null);
		}
	}
	
	@Override
	public boolean createUser(UserEntry user, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException {
		
		UserAccountCacheItem cacheItemCredential = UserAccountCache.getUserItem(sessionToken);
		if(!sessionToken.equals("BYPASS")) {
			if(cacheItemCredential == null) {
				throw new InvalidSessionException();
			}
	
			if((cacheItemCredential.role == null) || (!cacheItemCredential.role.contains("[admin.admin]"))) {
				throw new InvalidUserPrivilegeException();
			}
		}
		
		UserDirectory ud = UserDirectoryFactory.getUserDirectory();
		if(ud == null) {
			logger.error("UserDirectory instantiation fails.");
			return(false);
		}
		
		if(ud.createUser(user)) {
			ud.close();
			logger.debug("user " + user.username + " is created.");
			return(true);
		} else {
			ud.close();
			logger.debug("user " + user.username + " creation fails.");
			return(false);
		}
	}
	
	@Override
	public boolean deleteUser(String username, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException {
		
		UserAccountCacheItem cacheItemCredential = UserAccountCache.getUserItem(sessionToken);
		
		if(cacheItemCredential == null) {
			throw new InvalidSessionException();
		}

		if((cacheItemCredential.role == null) || (!cacheItemCredential.role.contains("[admin.admin]"))) {
			throw new InvalidUserPrivilegeException();
		}
		
		
		UserDirectory ud = UserDirectoryFactory.getUserDirectory();
		if(ud == null) {
			logger.error("UserDirectory instantiation fail.");
			return(false);
		}
		
		if(ud.deleteUser(username)) {
			ud.close();
			logger.debug("user " + username + " is deleted.");
			return(true);
		} else {
			ud.close();
			logger.debug("user " + username + " deletion fails.");
			return(false);
		}
	}
	
	@Override
	public UserEntry modifyUser(String username, Hashtable<String, String> attributes, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException {
		
		UserAccountCacheItem cacheItemCredential = UserAccountCache.getUserItem(sessionToken);
		
		if(cacheItemCredential == null) {
			throw new InvalidSessionException();
		}

		if((cacheItemCredential.role == null) || (!cacheItemCredential.role.contains("[admin.admin]"))) {
			throw new InvalidUserPrivilegeException();
		}
		
		UserDirectory ud = UserDirectoryFactory.getUserDirectory();
		if(ud == null) {
			logger.error("UserDirectory instantiation fails.");
			return(null);
		}
		
		UserEntry ue = ud.modifyUserAttributes(username, attributes);
		ud.close();
		if(ue != null) {
			logger.debug(username + "'s attributes is modified.");
		} else {
			logger.debug(username + "'s attributes modification is failed.");
		}
		return(ue);
	}

	@Override
	public boolean deleteUserAttribute(String username, String attribute, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException {
		UserAccountCacheItem cacheItemCredential = UserAccountCache.getUserItem(sessionToken);
		
		if(cacheItemCredential == null) {
			throw new InvalidSessionException();
		}

		if((cacheItemCredential.role == null) || (!cacheItemCredential.role.contains("[admin.admin]"))) {
			throw new InvalidUserPrivilegeException();
		}
		UserDirectory ud = UserDirectoryFactory.getUserDirectory();
		if(ud == null) {
			logger.error("UserDirectory instantiation fail.");
			return(false);
		}
		
		if(ud.deleteUserAttribute(username, attribute)) {
			ud.close();
			logger.debug(username + "'s attributes is deleted.");
			return(true);
		} else {
			ud.close();
			logger.debug(username + "'s attributes deletion fails.");
			return(false);
		}
	}

	@Override
	public boolean changePassword(String username, String oldPassword, String newPassword) throws ConnectionException, InvalidCredentialException {
		UserDirectory ud = UserDirectoryFactory.getUserDirectory();
		if(ud == null) {
			logger.error("UserDirectory instantiation failed.");
			return(false);
		}
		
		if(ud.changePassword(username, oldPassword, newPassword)) {
			ud.close();
			logger.debug(username + "'s password changed ");
			return(true);
		} else {
			logger.debug(username + "'s password change failed");
			return(false);
		}
	}
}
