package com.lab.application.service;

import com.lab.application.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    long getClientCount();

    void updateClient(Client client);

    Optional<Client> getClientById(Long id);

    List<Client> getAllClients();
}
