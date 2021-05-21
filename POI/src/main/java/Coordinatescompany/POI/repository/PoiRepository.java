package Coordinatescompany.POI.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import Coordinatescompany.POI.entities.Poi;

public interface PoiRepository extends PagingAndSortingRepository<Poi, Integer> {

	// Query personalizada JPQL para a consulta de POIs próximos ao ponto de
	// referência informado, com intuito maximizar a possibilidade de expansão do
	// aplicativo, evitando que seja carregado todos os POIs da base de dados para
	// somente depois fazer um loop em cima da base e selecionar os POIs que entram
	// no requisito de pesquisa.
	@Query("select p from Poi p where abs(p.coordenadaX - :referenciaX) + abs(p.coordenadaY - :referenciaY) <= :distanciaMax")
	public Iterable<Poi> localizaProximos(@Param("referenciaX") int referenciaX, @Param("referenciaY") int referenciaY,
			@Param("distanciaMax") int distanciaMax);

}