package Coordinatescompany.POI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.repository.PoiRepository;

@Service
public class PoiServiceImpl implements PoiService {

	@Autowired
	private PoiRepository poiRepository;

	@Override
	public Poi salvar(Poi poi) {

		if (validaDadosDoPoi(poi)){
			return poiRepository.save(poi);
		} else {
			throw new IllegalArgumentException("Erro ao validar os dados do POI.");
		} 
	}

	@Override
	public Poi alterar(Poi poi) {

		if (verificarSeEIgual(poi)) {
			throw new IllegalArgumentException("Já existe POI com os mesmos dados.");
		} else if (validaDadosDoPoi(poi)){
			if (poiRepository.findById(poi.getId()).isPresent()) {
				return poiRepository.save(poi);
			} else {
				throw new IllegalArgumentException("Não foi localizado POI com ID informado para ser alterado.");
			}
		} else {
			throw new IllegalArgumentException("Erro ao validar os dados do POI.");
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

		if (poiRepository.existsById(id)) {
			poiRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Não foi localizado POI com o ID informado.");
		}

	}

	@Override
	public Iterable<Poi> localizarNaProximidade(int referenciaX, int referenciaY, int distanciaMax) {

		if (referenciaX < 0 || referenciaY < 0 || distanciaMax < 0) {
			throw new IllegalArgumentException("As referencias e distancia máxima devem ser igual ou maior que 0.");
		} else {
			return poiRepository.localizaProximos(referenciaX, referenciaY, distanciaMax);
		}
	}

	private boolean verificarSeEIgual(Poi poi) {

		if (poiRepository.findById(poi.getId()).isPresent()) {
			if (poiRepository.findById(poi.getId()).get().equals(poi)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean validaDadosDoPoi(Poi poi) {

		if (!poi.getNome().isEmpty() && poi.getCoordenadaX() >= 0 && poi.getCoordenadaY() >= 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
