package com.bruno.cadastrocep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.cadastrocep.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
