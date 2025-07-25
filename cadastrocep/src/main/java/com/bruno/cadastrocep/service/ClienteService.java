package com.bruno.cadastrocep.service;

import java.util.List;
import java.util.Optional;

import com.bruno.cadastrocep.model.Cliente;

public interface ClienteService {
    Cliente salvarCliente(Cliente cliente);

    List<Cliente> listarClientes();

    Optional<Cliente> buscarClientePorId(Long id);

    Optional<Cliente> atualizarCliente(Long id, Cliente clienteAtualizado);

    void deletarCliente(Long id);

}
