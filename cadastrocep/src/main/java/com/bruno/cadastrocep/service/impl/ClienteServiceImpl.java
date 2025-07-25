package com.bruno.cadastrocep.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bruno.cadastrocep.exception.CepConsultaException;
import com.bruno.cadastrocep.exception.ClienteNotFoundException;
import com.bruno.cadastrocep.model.Cliente;
import com.bruno.cadastrocep.model.LogConsulta;
import com.bruno.cadastrocep.repository.ClienteRepository;
import com.bruno.cadastrocep.repository.LogConsultaRepository;
import com.bruno.cadastrocep.service.ClienteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final LogConsultaRepository logConsultaRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ClienteServiceImpl(ClienteRepository clienteRepository, LogConsultaRepository logConsultaRepository) {
        this.clienteRepository = clienteRepository;
        this.logConsultaRepository = logConsultaRepository;
    }

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        String cep = cliente.getCep();
        String url = "http://localhost:8081/cep/" + cep;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        LogConsulta log = new LogConsulta();
        log.setCepConsultado(cep);
        log.setRespostaApi(response.getBody());
        log.setHorarioConsulta(LocalDateTime.now());
        logConsultaRepository.save(log);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());

            cliente.setLogradouro(node.get("logradouro").asText());
            cliente.setBairro(node.get("bairro").asText());
            cliente.setCidade(node.get("cidade").asText());
            cliente.setEstado(node.get("estado").asText());

        } catch (Exception e) {
            throw new CepConsultaException("Erro ao processar a resposta do CEP", e);
        }

        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Optional<Cliente> atualizarCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            clienteExistente.setNome(clienteAtualizado.getNome());
            clienteExistente.setEmail(clienteAtualizado.getEmail());
            clienteExistente.setCep(clienteAtualizado.getCep());

            // Atualiza dados do endereço consultando o CEP
            String cep = clienteAtualizado.getCep();
            String url = "http://localhost:8081/cep/" + cep;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            LogConsulta log = new LogConsulta();
            log.setCepConsultado(cep);
            log.setRespostaApi(response.getBody());
            log.setHorarioConsulta(LocalDateTime.now());
            logConsultaRepository.save(log);

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(response.getBody());

                clienteExistente.setLogradouro(node.get("logradouro").asText());
                clienteExistente.setBairro(node.get("bairro").asText());
                clienteExistente.setCidade(node.get("cidade").asText());
                clienteExistente.setEstado(node.get("estado").asText());

            } catch (Exception e) {
                throw new CepConsultaException("Erro ao processar a resposta do CEP", e);
            }

            return clienteRepository.save(clienteExistente);
        });
    }

    @Override
    public void deletarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com id: " + id + " não existe"));

        clienteRepository.delete(cliente);
    }

}
