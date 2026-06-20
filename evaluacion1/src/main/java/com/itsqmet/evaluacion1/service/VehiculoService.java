package com.itsqmet.evaluacion1.service;

import com.itsqmet.evaluacion1.model.Vehiculo;
import com.itsqmet.evaluacion1.model.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> obtenerTodo(){
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> buscarporId(Long id){
        return vehiculoRepository.findById(id);
    }

    public Vehiculo crearVehiculo (Vehiculo vehiculo){
        return vehiculoRepository.save(vehiculo);
    }

    public boolean eliminar(Long id){
        if (vehiculoRepository.existsById(id)){
            vehiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Vehiculo> actualizar(Long id, Vehiculo vehiculoActualizado){
        return vehiculoRepository.findById(id).map(vehiculo -> {
            vehiculo.setModelo(vehiculoActualizado.getModelo());
            vehiculo.setCategoria(vehiculoActualizado.getCategoria());
            vehiculo.setDescripcion(vehiculoActualizado.getDescripcion());
            vehiculo.setPrecioPorDia(vehiculoActualizado.getPrecioPorDia());
            vehiculo.setUnidadesDisponibles(vehiculoActualizado.getUnidadesDisponibles());
            return vehiculoRepository.save(vehiculo);
        });
    }

    //

    public List<Vehiculo> buscarPorCategoriaYDisponibilidad(String categoria, Integer unidades){

        return vehiculoRepository.findByCategoriaAndUnidadesDisponiblesGreaterThan(categoria, unidades);
    }



    public List<Vehiculo> buscarPorRangoPrecio(Double minimo, Double maximo){

        return vehiculoRepository.findByPrecioPorDiaBetweenOrderByPrecioPorDiaAsc(minimo, maximo);
    }



    public List<Vehiculo> buscarPorModelo(String modelo){
        return vehiculoRepository.findByModeloContainingIgnoreCase(modelo);}
}
