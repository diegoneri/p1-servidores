package com.fatecrl.api_pagamentos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.api_pagamentos.model.Pagamento;

@Service
public class PagamentoService {
    private static List<Pagamento> listaDePagamentos = new ArrayList<>();

    private void pagamentoFake(){
        Pagamento pagamentoFake = new Pagamento();
        pagamentoFake.setId(1L);
        pagamentoFake.setIdUsuario(1L);
        pagamentoFake.setValor(15.5);
        pagamentoFake.setFormaPagamento("Cartão de crédito");
        pagamentoFake.setStatus("Aprovado");
        LocalDate data = LocalDate.parse("2024-09-26");
        pagamentoFake.setDataPagamento(data);
        listaDePagamentos.add(pagamentoFake);
    }

    public PagamentoService(){
        pagamentoFake();
    }

    public void setId(Pagamento pagamento, Long id){
        pagamento.setId(id);
    }

    public Pagamento getById(Long id){
        Pagamento _pagamento = new Pagamento(id);

        return listaDePagamentos.stream()
                            .filter(p -> p.equals(_pagamento))
                            .findFirst()
                            .orElse(null);
    }

    public Pagamento get(Pagamento pagamento){
        return this.getById(pagamento.getId());
    }

    public List<Pagamento> getByIdUsuario(Long idUsuario) {
        return listaDePagamentos.stream()
                .filter(p -> p.getIdUsuario().equals(idUsuario))
                .toList();
    }

    public List<Pagamento> getPagamentosByValor(Double valorMinimo, Double valorMaximo) {
        return listaDePagamentos.stream()
                .filter(p -> (valorMinimo == null || p.getValor() >= valorMinimo) &&
                             (valorMaximo == null || p.getValor() <= valorMaximo))
                .toList();
    }

    public boolean delete(Long id){
        Pagamento pagamento = this.getById(id);
        if (pagamento != null){
            listaDePagamentos.remove(pagamento);
            return true;
        }
        return false;
    }

    public Pagamento create(Pagamento pagamento){
        pagamento.setId((long) (listaDePagamentos.size() + 1));
        listaDePagamentos.add(pagamento);
        return pagamento;
    }

    public boolean statusUpdate(Long id, String status){
        Pagamento pagamento = this.getById(id);

        if (!pagamento.getStatus().equals(status)
            && (status.equals("Aprovado") || status.equals("Recusado") || status.equals("Pendente"))
        ){
            pagamento.setStatus(status);
            return true;
        }

        return false;
    }
}
