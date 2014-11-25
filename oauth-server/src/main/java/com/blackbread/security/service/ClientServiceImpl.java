package com.blackbread.security.service;

import com.blackbread.security.dao.ClientDao;
import com.blackbread.security.entity.Client;

import org.apache.log4j.Logger;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-17
 * <p>
 * Version: 1.0
 */
@Transactional
@Service
public class ClientServiceImpl implements ClientService {
	private final static Logger logger = Logger
			.getLogger(ClientServiceImpl.class);
	@Autowired
	private ClientDao clientDao;

	@Override
	public Client createClient(Client client) {

		client.setClientId(UUID.randomUUID().toString());
		client.setClientSecret(UUID.randomUUID().toString());
		return clientDao.createClient(client);
	}

	@Override
	public Client updateClient(Client client) {
		return clientDao.updateClient(client);
	}

	@Override
	public void deleteClient(Long clientId) {
		clientDao.deleteClient(clientId);
	}

	@Override
	public Client findOne(Long clientId) {
		return clientDao.findOne(clientId);
	}

	@Override
	public List<Client> findAll() {
		return clientDao.findAll();
	}

	@Override
	public Client findByClientId(String clientId) {
		return clientDao.findByClientId(clientId);
	}

	@Override
	public Client findByClientSecret(String clientID,String clientSecret) {
		return clientDao.findByClientSecret(clientID,clientSecret);
	}

	@Override
	public void inserUserClient(String userName, String clientID)
			throws Exception {
		int r=0;
		try {
			r = clientDao.inserUserClient(userName, clientID);
		} catch (Exception e) {
			logger.error(e.getCause().getMessage());
		}
		if (r <= 0)
			throw new Exception("关联异常，请联系管理员");
	}

	public boolean findUserClient(String userName, String clientID) {
		int r;
		try {
			r = clientDao.findUserNameClientID(userName, clientID);
		} catch (Exception e) {
			logger.error(e.getCause().getMessage());
			throw new RuntimeException("查询客户端ID时候异常，请联系管理员");
		}
		if (r == 1)
			return true;
		else
			return false;
	}
}
