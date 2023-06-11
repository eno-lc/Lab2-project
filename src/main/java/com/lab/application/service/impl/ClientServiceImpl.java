package com.lab.application.service.impl;

import com.lab.application.entity.Client;
import com.lab.application.repository.ClientRepository;
import com.lab.application.response.FraudCheckResponse;
import com.lab.application.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;

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

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Page<Client> list(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public void saveClient(Client clientObject) {
        clientRepository.save(clientObject);
    }

    @Override
    public Client getClient(Long aLong) {
        return clientRepository.findById(aLong).get();
    }

    @Override
    public List<Client> findAllClients(String value) {

        List<Client> clients = clientRepository.findAll();

        if(value == null || value.isEmpty()){
            return clients;
        } else {
            return clientRepository.search(value);
        }
    }

    public boolean validateClient(Client client){
        FraudCheckResponse response = restTemplate.postForObject("http://localhost:8888/api/v1/fraud-check-client", client, FraudCheckResponse.class);
        return response.isFraudster();
    }
}
