package Coordinatescompany.POI.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import Coordinatescompany.POI.entities.Poi;

public interface PoiRepository extends PagingAndSortingRepository<Poi, Integer> {

	@Query("select p from Poi p where abs(p.coordenadaX - :referenciaX) + abs(p.coordenadaY - :referenciaY) <= :distanciaMax")
	public Iterable<Poi> localizaProximos(@Param("referenciaX") int referenciaX, @Param("referenciaY") int referenciaY,
			@Param("distanciaMax") int distanciaMax);

}