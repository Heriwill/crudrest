package com.belval.crudrest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.belval.crudrest.model.Produto;
import com.belval.crudrest.repository.ProdutoRepository2;

@RestController
public class ProdutoController {

//	private static List<Produto> listaProdutos = new ArrayList<>();
//	private static Integer proxId = 1;
	
//	private static ProdutoRepository repository = 
//			new ProdutoRepository();
	
	@Autowired      //injeta atributos nesse repositorio
	private ProdutoRepository2 repository;

	static {
//		Produto prod = new Produto(1, "Pão", "Pão Francês", 0.5);
//		listaProdutos.add(prod);
	}
	
	@GetMapping("/produtos")
	public ResponseEntity<Iterable<Produto>> obterProdutos() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(repository.findAll());
	}

	//curl POST http://localhost:8080/produtos -H "Content-Type: application/json; Charset=utf-8" -d @produto-mortadela.json
	
	@PostMapping("/produtos")
	public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
		
		repository.save(produto);
		 
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(produto);
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<Object> buscarProdutoPorId(@PathVariable Integer id) {
		
		Optional<Produto> produto = repository.findById(id);
		
		if(!produto.isPresent()) {     // ! é a negação
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado.");
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(produto);
	}
	
	/*
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Object> apagarProduto(
			@PathVariable(value = "id") Integer id){
		repository.removebyId(produtos);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("Produto removido com sucesso!");
					//terminar
			//usar como referencia 
	}
	*/
	
	// curl -X DELETE http://localhost:8080/produtos/id

	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Object> apagar(@PathVariable Integer id){
		
	Optional<Produto> produto = repository.findById(id);
		
		if(!produto.isPresent()) {     // ! é a negação
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado.");
		}
		repository.delete(produto.get());
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto apagado com sucesso!");
		
	
	}
	
	//ResponseEntity é a resposta http, ex: codigos de retorno como 'OK' '404' 'nao encontrado'etc.
	//Put é o metodo de atualizacao
	//  curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json; Charset=utf-8" -d @produto-mortadela.json
	//trocar id depois de /produtos/ 
	@PutMapping("/produtos/{id}")
	public ResponseEntity<Object> atualizarProduto(
			@PathVariable(value = "id") Integer id,
			@RequestBody Produto produto) {
		
		Optional<Produto>produtoEncontrado = repository.findById(id);
		
		//isEmpty é vazio
		if (produtoEncontrado.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado.");
		}
		
		produto.setId(id);
		repository.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto atualizado com sucesso.");
		
	}
	
}

