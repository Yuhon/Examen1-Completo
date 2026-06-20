package com.itsqmet.evaluacion1.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Ver los vehículos de una categoría específica que además tengan unidades disponibles (mayor a un valor dado).

    List<Vehiculo> findByCategoriaAndUnidadesDisponiblesGreaterThan(String categoria, Integer unidades);


    // Ver los vehículos de una categoría específica que además tengan unidades disponible (mayor a un valor dado).

    List<Vehiculo> findByPrecioPorDiaBetweenOrderByPrecioPorDiaAsc(Double minimo, Double maximo);


    //Buscar vehículos por modelo, permitiendo una búsqueda parcial.

    List<Vehiculo> findByModeloContainingIgnoreCase(String modelo);

}
