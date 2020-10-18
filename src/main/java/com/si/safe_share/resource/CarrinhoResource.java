package com.si.safe_share.resource;

import com.si.safe_share.model.Carrinho;
import com.si.safe_share.model.Cliente;
import com.si.safe_share.repository.CarrinhoRepository;
import com.si.safe_share.repository.ClienteRepository;
import com.si.safe_share.resource.form.CarrinhoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class CarrinhoResource {
    @Autowired
    CarrinhoRepository carrinhoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @PostMapping("/carrinho")
    public Carrinho salva(@RequestBody CarrinhoForm carrinhoForm) {

        Optional<Cliente> cliente = clienteRepository.findById(carrinhoForm.getCliente());

        Carrinho carrinho = Carrinho.builder()
                .cliente(cliente.get())
                .produtos(carrinhoForm.getProdutos())
                .build();

        return carrinhoRepository.save(carrinho);
    }

    @GetMapping("/carrinho/{id}")
    public Optional<Carrinho> buscaPorId(@PathVariable(value = "id") Integer id) {
        return carrinhoRepository.findById(id);
    }

//    @DeleteMapping("/carrinho/{id}")
//    public void apagaPorId(@PathVariable(value = "id") Integer id) {
//        Optional<Carrinho> carrinho = carrinhoRepository.findById(id);
//        if (carrinho.isPresent()) {
//            carrinhoRepository.delete(carrinho.get());
//        }
//    }

//    @PutMapping("/carrinho/{id}")
//    public Carrinho atualiza(@PathVariable(value = "id") Integer id,
//                             @RequestBody CarrinhoForm carrinhoForm) {
//
//        Optional<Carrinho> carrinhoOpt = carrinhoRepository.findById(id);
//
//        Carrinho carrinhoAntigo = carrinhoOpt.get();
//        Carrinho carrinhoNovo = carrinhoForm.toModel(carrinhoForm);
//
//        Carrinho carrinhoAtualizado = carrinhoForm.toModelUpdated(carrinhoAntigo, carrinhoNovo);
//
//        return carrinhoRepository.save(carrinhoAtualizado);
//    }

//    @GetMapping("/carrinhos")
//    public List<Carrinho> lista() {
//        return carrinhoRepository.findAll();
//    }
}
