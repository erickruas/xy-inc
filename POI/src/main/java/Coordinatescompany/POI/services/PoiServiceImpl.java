package Coordinatescompany.POI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.repository.PoiRepository;

@Service
public class PoiServiceImpl implements PoiService {

	// Injeção de dependência
	@Autowired
	private PoiRepository poiRepository;

	// O método salvar verifica se os dados do POI são validos, e então chama o
	// respositório para salvar o POI no banco de dados. Caso contrário ele lança
	// uma IllegalArgumentException informando o erro ao validar os dados do POI.
	@Override
	public Poi salvar(Poi poi) {

		if (validaDadosDoPoi(poi)) {
			return poiRepository.save(poi);
		} else {
			throw new IllegalArgumentException("Erro ao validar os dados do POI.");
		}
	}

	// O método alterar verifica se existe um POI igual na base de dados, e depois
	// se os dados do POI são validos, e então chama o
	// respositório para alterar o POI no banco de dados. Caso contrário ele lança
	// uma IllegalArgumentException informando o erro ao validar os dados do POI ou
	// que já existe um POI cadastrado com os mesmos dados.
	@Override
	public Poi alterar(Poi poi) {

		if (verificarSeEIgual(poi)) {
			throw new IllegalArgumentException("Já existe POI com os mesmos dados.");
		} else if (validaDadosDoPoi(poi)) {
			if (poiRepository.findById(poi.getId()).isPresent()) {
				return poiRepository.save(poi);
			} else {
				throw new IllegalArgumentException("Não foi localizado POI com ID informado para ser alterado.");
			}
		} else {
			throw new IllegalArgumentException("Erro ao validar os dados do POI.");
		}
	}

	// O método obterTodos(Pageable) carrega todos os POIs da base de dados
	// utilizando a paginação na exibição.
	@Override
	public Iterable<Poi> obterTodos(Pageable page) {
		return poiRepository.findAll(page);
	}

	// O método obterTodos carrega todos os POIs da base de dados em uma unica
	// página.
	@Override
	public Iterable<Poi> obterTodos() {
		return poiRepository.findAll();
	}

	// O método deletar exclui um POI da base de dados, primeiro ele verifica se
	// existe um POI com aquele ID em caso positivo, o exclui da base de dados.
	// Em caso negativo lança uma IllegalArgumentException informando que não foi
	// localizado POI com ID informado.
	@Override
	public void deletar(int id) {

		if (poiRepository.existsById(id)) {
			poiRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Não foi localizado POI com o ID informado.");
		}

	}

	// O método localizarNaProximidade busca POIs com base em uma referencia X e Y
	// e em um raio máximo de metros, que são informados pelo usuário, porém essa
	// refência não pode ter coordenadas X e Y negativas e também a distância não
	// pode ser negativa. Esses requisitos são verificados e caso atendidos a
	// pesquisa é realizada, caso não atendidos, é lançada uma
	// IllegalArgumentException informando o erro de validação dos requisitos.
	@Override
	public Iterable<Poi> localizarNaProximidade(int referenciaX, int referenciaY, int distanciaMax) {

		if (referenciaX < 0 || referenciaY < 0 || distanciaMax < 0) {
			throw new IllegalArgumentException("As referencias e distancia máxima devem ser igual ou maior que 0.");
		} else {
			return poiRepository.localizaProximos(referenciaX, referenciaY, distanciaMax);
		}
	}

	// Método que verifica se um POI é igual a outro POI, utilizado para verificar
	// se ao alterar um POI, o usuário não insira dados de um POI já cadastrado na
	// base de dados.
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

	// Método privado que valida se um POI tem todos requisitos para ser salvo ou
	// alterado no banco de dados, ou seja, nome não vazio, coordenadas não
	// negativas.
	private boolean validaDadosDoPoi(Poi poi) {

		if (!poi.getNome().isEmpty() && poi.getCoordenadaX() >= 0 && poi.getCoordenadaY() >= 0) {
			return true;
		} else {
			return false;
		}

	}

}
