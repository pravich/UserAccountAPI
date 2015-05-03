package com.yggdrasil.account;

import java.util.Hashtable;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.yggdrasil.account.directory.ConnectionException;
import com.yggdrasil.account.directory.InvalidCredentialException;
import com.yggdrasil.account.directory.UserEntry;

//@WebService(name = "UserDirectory", targetNamespace = "http://www.yggdrasil.com")
@WebService
public interface UserAccountInterface {
	
	@WebMethod(action="searchUser", operationName="searchUser")
	public abstract UserEntry searchUser(String username, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException;
	
	@WebMethod
	public abstract List<String> getUserList() throws ConnectionException;
	
	@WebMethod
	public abstract String signon(String username, String password) throws ConnectionException, InvalidCredentialException;
	
	@WebMethod
	public abstract boolean createUser(UserEntry user, String sessionToken)throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException;
	
	@WebMethod
	public abstract boolean deleteUser(String username, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException;
	
	@WebMethod
	public abstract UserEntry modifyUser(String username, Hashtable<String, String> attributes, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException;
	
	@WebMethod
	public abstract boolean deleteUserAttribute(String username, String attribute, String sessionToken) throws ConnectionException, InvalidSessionException, InvalidUserPrivilegeException;

	@WebMethod
	public abstract boolean changePassword(String username, String oldPassword, String newPassword) throws ConnectionException, InvalidCredentialException;

}