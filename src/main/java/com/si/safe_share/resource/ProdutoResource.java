package com.si.safe_share.resource;

import com.si.safe_share.model.Categoria;
import com.si.safe_share.model.Produto;
import com.si.safe_share.repository.CategoriaRepository;
import com.si.safe_share.repository.ProdutoRepository;
import com.si.safe_share.resource.form.ProdutoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api")
public class ProdutoResource {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @PostMapping("/produto")
    @Transactional
    public Produto salva(@RequestBody ProdutoForm produtoForm) {

        Categoria categoria = categoriaRepository.getOne(produtoForm.getCategoria());
        Produto produto = Produto.builder()
                .categoria(categoria)
                .descricao(produtoForm.getDescricao())
                .valor(produtoForm.getValor())
                .build();
        Produto produtoNovo = produtoRepository.save(produto);
        return produtoNovo;
    }

    @GetMapping("/produto/{id}")
    public Optional<Produto> buscaPorId(@PathVariable(value="id") Integer id){
        return produtoRepository.findById(id);
    }

    @DeleteMapping("/produto/{id}")
    public ResponseEntity<?> apagaPorId(@PathVariable(value="id") Integer id){
        return produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/produto/{id}")
    @Transactional
    public ResponseEntity<Produto> atualiza(@PathVariable Integer id,
                                            @RequestBody ProdutoForm produtoform){

        Categoria categoria = categoriaRepository.getOne(produtoform.getCategoria());

        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setCategoria(categoria);
                    produto.setDescricao(produtoform.getDescricao());
                    produto.setValor(produtoform.getValor());
                    Produto atualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok().body(atualizado);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/produtos")
    public List<Produto> lista(){
        return produtoRepository.findAll();
    }
}
