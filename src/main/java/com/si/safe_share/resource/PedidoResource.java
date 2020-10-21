package com.si.safe_share.resource;

import com.si.safe_share.model.Carrinho;
import com.si.safe_share.model.Cliente;
import com.si.safe_share.model.Pedido;
import com.si.safe_share.repository.CarrinhoRepository;
import com.si.safe_share.repository.ClienteRepository;
import com.si.safe_share.repository.PedidoRepository;
import com.si.safe_share.resource.form.PedidoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class PedidoResource {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CarrinhoRepository carrinhoRepository;

    @PostMapping("/pedido")
    public Pedido salva(@RequestBody PedidoForm pedidoForm) {

        Optional<Cliente> cliente = clienteRepository.findById(pedidoForm.getCliente());

        Optional<Carrinho> carrinho = carrinhoRepository.findById(pedidoForm.getCarrinho());

        Pedido pedido = Pedido.builder()
                .cliente(cliente.get())
                .carrinho(carrinho.get())
                .dataDoPedido(LocalDateTime.now())
                .build();
        carrinho.get().setCompraFinalizada(true);
        carrinhoRepository.save(carrinho.get());
        return pedidoRepository.save(pedido);
    }

    @GetMapping("/pedido/{id}")
    public Optional<Pedido> buscaPorId(@PathVariable(value = "id") Integer id) {
        return pedidoRepository.findById(id);
    }

//    @DeleteMapping("/pedido/{id}")
//    public void apagaPorId(@PathVariable(value = "id") Integer id) {
//        Optional<Pedido> pedido = pedidoRepository.findById(id);
//        if (pedido.isPresent()) {
//            pedidoRepository.delete(pedido.get());
//        }
//    }

//    @PutMapping("/pedido/{id}")
//    public Pedido atualiza(@PathVariable(value = "id") Integer id,
//                           @RequestBody PedidoForm pedidoForm) {
//        Optional<Pedido> pedidoAntigoOpt = pedidoRepository.findById(id);
//        Pedido pedidoAntigo = pedidoAntigoOpt.get();
//        Pedido pedidoNovo = pedidoForm.toModel(pedidoForm);
//
//        Pedido pedidoAtualizado = pedidoForm.toModelUpdated(pedidoAntigo, pedidoNovo);
//
//        return pedidoRepository.save(pedidoAtualizado);
//
//    }

    @GetMapping("/pedidos")
    public List<Pedido> lista() {
        return pedidoRepository.findAll();
    }
}
