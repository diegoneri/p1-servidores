package com.fatecrl.api_pagamentos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.api_pagamentos.model.Pagamento;
import com.fatecrl.api_pagamentos.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getById(@PathVariable("id") Long id){
        return ResponseEntity.of(Optional.of(service.getById(id)));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pagamento>> getByIdUsuario(@PathVariable("idUsuario") Long idUsuario){
        return ResponseEntity.of(Optional.of(service.getByIdUsuario(idUsuario)));
    }

    @PostMapping
    public ResponseEntity<Pagamento> create(@RequestBody Pagamento pagamento){

        Pagamento novoPagamento = service.create(pagamento);

        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(novoPagamento.getId())
                        .toUri();

        return ResponseEntity.created(location)
                            .body(novoPagamento);
    }
}
