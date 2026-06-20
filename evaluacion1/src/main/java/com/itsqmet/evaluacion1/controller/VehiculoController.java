package com.itsqmet.evaluacion1.controller;

import com.itsqmet.evaluacion1.model.Vehiculo;
import com.itsqmet.evaluacion1.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin("*")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodos(){
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodo();
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return vehiculoService.buscarporId(id)
                .map(vehiculo -> ResponseEntity.ok((Object) vehiculo))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vehiculo con id " + id + " no encontrado")));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Vehiculo vehiculo, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        Vehiculo nuevo = vehiculoService.crearVehiculo(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (vehiculoService.eliminar(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Vehiculo eliminado correctamente")); // 200
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Vehiculo con id " + id + " no encontrado")); // 404
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Vehiculo vehiculo,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        return vehiculoService.actualizar(id, vehiculo)
                .map(actualizado -> ResponseEntity.ok((Object) actualizado))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vehiculo con id " + id + " no encontrado")));
    }

    //

    @GetMapping("/categoria/{categoria}/{unidades}") public List<Vehiculo> buscarPorCategoriaYDisponibilidad(
            @PathVariable String categoria,
            @PathVariable Integer unidades
    ){
        return vehiculoService.buscarPorCategoriaYDisponibilidad(categoria, unidades);
    }



    @GetMapping("/precio/{minimo}/{maximo}")
    public List<Vehiculo> buscarPorPrecio(@PathVariable Double minimo, @PathVariable Double maximo){
        return vehiculoService.buscarPorRangoPrecio(minimo, maximo);
    }




    @GetMapping("/modelo/{modelo}")
    public List<Vehiculo> buscarPorModelo(@PathVariable String modelo){

        return vehiculoService.buscarPorModelo(modelo);
    }


}
