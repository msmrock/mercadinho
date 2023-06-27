package com.teste.primeiroexemplo.repository;

import com.teste.primeiroexemplo.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

/* 
public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
  
}*/

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
  
}
