package com.yggdrasil.account;

import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAccountCache {
	private static Logger logger = LogManager.getLogger(UserAccountCache.class);

	private static Hashtable<String, String> forwardCache = new Hashtable<String, String> (10000);
	private static Hashtable<String, UserAccountCacheItem> reverseCache = new Hashtable<String, UserAccountCacheItem>(10000);
	
	public static String getSessionToken(String username) {
		String sessionToken = forwardCache.get(username);
		if(sessionToken == null) {
			logger.debug("not found session token of " + username + ".");
		} else {
			logger.debug(username + " gets session token.");
		}
		return(sessionToken);
	}
	
	public static UserAccountCacheItem getUserItem(String sessionToken) {
		UserAccountCacheItem cacheItem = reverseCache.get(sessionToken);
		if(cacheItem == null) {
			logger.debug("not found UserAccountCacheItem of " + sessionToken + ".");
		} else {
			logger.debug("session token read [" + sessionToken + "] ");
		}
		return(cacheItem);
	}

	public static void setSessionToken(String sessionToken, UserAccountCacheItem cacheItem) {
		logger.debug(cacheItem.username + " sets session token [" + sessionToken + "].");
		
		if(forwardCache.containsKey(cacheItem.username)) {
			// removes previous session if exists.
			reverseCache.remove(forwardCache.get(cacheItem.username));
		}
		
		forwardCache.put(cacheItem.username, sessionToken);
		reverseCache.put(sessionToken, cacheItem);
	}
}
