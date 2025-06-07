package com.api.diversity.controller;

import com.api.diversity.model.Cliente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @GetMapping
    public String getAllClientes() {
        return "Lista de clientess";
    }

    @GetMapping("/{id}")
    public String getClienteById(@PathVariable Integer id) {
        return "" + id;
    }

    @PostMapping
    public String createCliente(@RequestBody Cliente cliente) {
        return "";
    }

    @PutMapping("/{id}")
    public String updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return "" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable Integer id) {
        return "" + id;
    }
}