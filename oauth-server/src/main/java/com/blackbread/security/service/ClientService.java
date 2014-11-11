package com.blackbread.security.service;

import com.blackbread.security.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-1-28
 * <p>
 * Version: 1.0
 */
public interface ClientService {

	public Client createClient(Client client);

	public Client updateClient(Client client);

	public void deleteClient(Long clientId);

	public void inserUserClient(final String userName, final String clientID)
			throws Exception;

	public boolean findUserClient(String userName, String clientID);

	Client findOne(Long clientId);

	List<Client> findAll();

	Client findByClientId(String clientId);

	Client findByClientSecret(String clientSecret);

}
