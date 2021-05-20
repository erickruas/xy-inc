package Coordinatescompany.POI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Coordinatescompany.POI.repository.PoiRepository;
import Coordinatescompany.POI.entities.Poi;

@Service
public class PoiServiceImpl implements PoiService {

	@Autowired
	private PoiRepository poiRepository;

	@Override
	public Poi salvar(Poi poi) {

		if (poi.getNome().isEmpty()) {
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
	public Iterable<Poi> obterTodos(Pageable page) {
		return poiRepository.findAll(page);
	}
	
	@Override
	public Iterable<Poi> obterTodos() {
		return poiRepository.findAll();
	}



	@Override
	public void deletar(int id) {

		if (id > 0) {
			poiRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Deve ser informado um ID positivo para a exclusão de um POI.");
		}

	}

	@Override
	public Iterable<Poi> localizarNaProximidade(int referenciaX, int referenciaY, int distanciaMax) {

		if (referenciaX < 0) {
			throw new IllegalArgumentException("A referenciaX deve ser igual ou maior que 0.");
		} else if (referenciaY < 0) {
			throw new IllegalArgumentException("A referenciaY deve ser igual ou maior que 0.");
		} else if (distanciaMax < 0) {
			throw new IllegalArgumentException("A distanciaMax deve ser igual ou maior que 0.");
		} else {
			return poiRepository.localizaProximos(referenciaX, referenciaY, distanciaMax);
		}
	}

}
