package prueba.tecnica.PruebaTecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.tecnica.PruebaTecnica.model.Cliente;
import prueba.tecnica.PruebaTecnica.service.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    @GetMapping("/{id}")
    public Optional<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteDetalles) {
        return clienteService.obtenerClientePorId(id).map(cliente -> {
            cliente.setNombre(clienteDetalles.getNombre());
            cliente.setGenero(clienteDetalles.getGenero());
            cliente.setEdad(clienteDetalles.getEdad());
            cliente.setIdentificacion(clienteDetalles.getIdentificacion());
            cliente.setDireccion(clienteDetalles.getDireccion());
            cliente.setTelefono(clienteDetalles.getTelefono());
            cliente.setContrasena(clienteDetalles.getContrasena());
            cliente.setEstado(clienteDetalles.isEstado());

            Cliente clienteActualizado = clienteService.guardarCliente(cliente);
            return ResponseEntity.ok(clienteActualizado);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
