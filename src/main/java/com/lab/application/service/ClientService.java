package com.lab.application.service;

import com.lab.application.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface ClientService {

    long getClientCount();

    void updateClient(Client client);

    Optional<Client> getClientById(Long id);

    List<Client> getAllClients();
    void deleteClient(Long id);
    
    Page<Client> list(Pageable pageable);

    void saveClient(Client clientObject);

    Client getClient(Long aLong);

    List<Client> findAllClients(String value);

    boolean validateClient(Client client);
}
