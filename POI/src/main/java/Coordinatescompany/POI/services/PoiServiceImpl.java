package Coordinatescompany.POI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import Coordinatescompany.POI.repository.PoiRepository;
import Coordinatescompany.POI.entities.Poi;

public class PoiServiceImpl implements PoiService{

	@Autowired
	private PoiRepository poiRepository;
	
	@Override
	public Poi salvar(Poi poi) {

		if (poi.getNome( ) == null || poi.getNome().isEmpty()) {
			throw new IllegalArgumentException("Preencha o nome do POI.");
		} else if (poi.getCoordenadaX() < 0) {
			throw new IllegalArgumentException("A coordenadaX deve ser igual ou maior que 0.");
		} else if (poi.getCoordenadaY() < 0) {
			throw new IllegalArgumentException("A coordenadaY deve ser igual ou maior que 0.");
		} else {
			return poiRepository.save(poi);
		}
	}

	@Override
	public Poi alterar(Poi poi) {

		if (poi.getId() == 0) {
			throw new IllegalArgumentException("Deve ser informado o id do POI a ser alterado.");
		} else if (poi.getNome().isEmpty()) {
			throw new IllegalArgumentException("Preencha o nome do POI.");
		} else if (poi.getCoordenadaX() < 0) {
			throw new IllegalArgumentException("A coordenadaX deve ser igual ou maior que 0.");
		} else if (poi.getCoordenadaY() < 0) {
			throw new IllegalArgumentException("A coordenadaY deve ser igual ou maior que 0.");
		} else {
			if (poiRepository.findById(poi.getId()).isPresent()) {
				return poiRepository.save(poi);
			} else {
				throw new IllegalArgumentException("Não foi localizado POI com ID informado");
			}
		}
	}

	@Override
	public Iterable<Poi> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Poi> obterTodos(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Poi> localizarNaProximidade(int referenciaX, int referenciaY, int distanciaMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(int id) {
		// TODO Auto-generated method stub
		
	}

}
