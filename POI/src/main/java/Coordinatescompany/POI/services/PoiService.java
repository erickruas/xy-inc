package Coordinatescompany.POI.services;

import org.springframework.data.domain.Pageable;

import Coordinatescompany.POI.entities.Poi;

public interface PoiService {
	Poi salvar(Poi poi);

	Poi alterar(Poi poi);

	Iterable<Poi> obterTodos();

	Iterable<Poi> obterTodos(Pageable page);

	Iterable<Poi> localizarNaProximidade(int referenciaX, int referenciaY, int distanciaMax);

	void deletar(int id);
}
