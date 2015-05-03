package com.yggdrasil.account.directory;

import java.util.Hashtable;

import javax.naming.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDirectoryFactory {
	
	private static Logger logger = LogManager.getLogger(UserDirectoryFactory.class);
	
	private static Hashtable<String, String> env;
	
	private static boolean initilized = false;
	
	static{
		if(!initilized) {
			logger.debug("initilizing UserDirectoryFactory.");
			env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, Configuration.providerUrl);
			env.put(Context.SECURITY_AUTHENTICATION, Configuration.securityAuthentication);
			env.put(Context.SECURITY_PRINCIPAL, Configuration.securityPrincipal);
			env.put(Context.SECURITY_CREDENTIALS, Configuration.securityCredential);
			
			logger.debug("UserDirectoryFactory is initialized successfully.");
		}
	}

	public static UserDirectory getUserDirectory() throws ConnectionException {
		logger.debug("creating a new UserDirectory instant.");
		UserDirectory ud = new UserDirectory(env);
		logger.debug("a new UserDirectory instant is created.");
		return(ud);
	}
}
