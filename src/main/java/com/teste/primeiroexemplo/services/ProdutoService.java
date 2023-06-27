package com.teste.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiroexemplo.repository.ProdutoRepository;
import com.teste.primeiroexemplo.shared.ProdutoDTO;


@Service
public class ProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;

    /**
   * Metodo para retornar uma lista de produtos
   * @return Lista de produtos
   */
  public List<ProdutoDTO> obterTodos(){
    List<Produto> produtos =  produtoRepository.findAll();
    
    // Retorna uma lista de produto model.
    return produtos.stream()
    .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
    .collect(Collectors.toList());
  }

    /**
   * Metodo que retorna o produto encontrado pelo seu ID
   * @param id ID do produto que seralocalizao
   * @return Retorna um produco caso tenha encontrado.
   */
  public Optional<ProdutoDTO> obterPorId(Integer id){  
    //obtendo optional de produto pod ID
    Optional<Produto> produto = produtoRepository.findById(id);
    //Se não encontrar vazio (isEmpty()), lança exception
    if(produto.isEmpty()){
      throw new ResourceNotFoundException("Produto com id: " + " não encontrado");
    }
    //Covertendo o meu optional de produto em um produtoDto
    ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
    //criando e retornando um optinal de produto Dto
    return Optional.of(dto);
  }

    /**
   * Metodo para adionar produto a lista
   * @param produto que sera adiconado
   * @return o produto que foi adcionado a lsita
   */
  public ProdutoDTO adicionar(ProdutoDTO produtoDto){
    //Remove o ID para conseguir fazer o cadastro
    produtoDto.setId(null);

    //Criar um objeto de mapeamento
    ModelMapper mapper = new ModelMapper();

    //Converte o novvo ProdutoDTO em um produto
    Produto produto = mapper.map(produtoDto, Produto.class);
    
    //Salvar o produto no banco
    produto = produtoRepository.save(produto);
    produto.setId(produto.getId());

    //Retornar o ProdutoDTO atualizado
    return produtoDto;
  }


    /**
   * Metodo para deletar o porduto por id
   * @param id do produto a ser deletado
   */
  public void deletar(Integer id){

    Optional<Produto> produto = produtoRepository.findById(id);
    if(produto.isEmpty()){
      throw new ResourceNotFoundException("Não foi possivel deletar o produto com o id: " + id + " Produto não existe");
    }
    //Aqui poderia ter alguma logica de validação.  
    produtoRepository.deleteById(id);
  }


      /**
   * Metodo para deletar todos os pordutos
   */
  public void deletarTodos(){
    //Aqui poderia ter alguma logica de validação.  
    produtoRepository.deleteAll();
  }


/**
   * Metodo para atualizar o porduto na lista
   * @param produto produto que sera atualizado
   * @return retorna o produto apos atualizar
   */
  public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto){
      //Passar o id para o produtoDto
       produtoDto.setId(id);
      
       // Criar um objeto no mapeamento
       ModelMapper mapper = new ModelMapper();

       // Converter o ProdutoDTP em um produto
       Produto produto = mapper.map(produtoDto, Produto.class);

              //if(produtoDto.setId(id) ){
        //throw new ResourceNotFoundException("Não foi possivel deletar o produto com o id: " + id + " Produto não existe");
     // }
       
       // Atualizar o Produto no Banco de dados
       produtoRepository.save(produto);
       
       //Retorna o produto atualizado
       return produtoDto;
  }
  
}
