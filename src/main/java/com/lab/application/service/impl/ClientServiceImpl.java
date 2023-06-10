package com.lab.application.service.impl;

import com.lab.application.entity.Client;
import com.lab.application.repository.ClientRepository;
import com.lab.application.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public long getClientCount(){
        return clientRepository.findAll().size();
    }

    public void updateClient(Client client){
        clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

}
