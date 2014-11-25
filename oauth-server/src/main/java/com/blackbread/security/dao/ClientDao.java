package com.blackbread.security.dao;

import com.blackbread.security.entity.Client;

import java.util.List;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-1-28
 * <p>
 * Version: 1.0
 */
public interface ClientDao {

	public Client createClient(Client client);

	public Client updateClient(Client client);

	public void deleteClient(Long clientId);

	public int findUserNameClientID(String userName, String clientID);

	public int inserUserClient(String userName, String clientID);

	Client findOne(Long clientId);

	List<Client> findAll();

	Client findByClientId(String clientId);

	Client findByClientSecret(String clientID,String clientSecret);

}
