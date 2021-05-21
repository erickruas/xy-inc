package Coordinatescompany.POI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.services.PoiService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pois")
public class PoiController {

	//Injeção de dependência
	@Autowired
	private PoiService poiService;

	@PostMapping("/cadastrar")
	@ApiOperation(value = "Cadastrar novo POI (Ponto de interesse)", notes = "Informe o nome e localização do ponto de interesse"
			+ "para fazer o cadastro.", response = Poi.class)
	public Poi novoPoi(@RequestBody Poi poi) {
		try {
			return poiService.salvar(poi);
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}

	@PutMapping("/alterar")
	@ApiOperation(value = "Altera um POI (Ponto de interesse)", notes = "Com base no ID, informe o nome e localização do ponto de interesse"
			+ "para alterar os dados de um POI.", response = Poi.class)
	public Poi alterarPoi(@RequestBody Poi poi) {
		try {
			return poiService.alterar(poi);
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}

	@DeleteMapping("/deletar/{id}")
	@ApiOperation(value = "Excluir um POI (Ponto de interesse)", notes = "Informe o ID de um POI para exclui-lo.")
	public void deletarPoi(@PathVariable int id) {
		try {
			poiService.deletar(id);
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}

	@GetMapping("/todos")
	@ApiOperation(value = "Listar todos os POIs (Pontos de interesse)", notes = "Tenha acesso a todos os POIs do sistema.", response = Poi.class)
	public Iterable<Poi> obterTodos() {
		return poiService.obterTodos();
	}

	@GetMapping("/pagina/{paginaAtual}/{qtdePorPagina}")
	@ApiOperation(value = "Listar todos os POIs (Pontos de interesse) com paginação", notes = "Tenha acesso a todos os POIs do sistema de forma paginada, fornecendo a página atual e quantos itens por pagina.", response = Poi.class)
	public Iterable<Poi> obterProdutosPorPagina(@PathVariable int paginaAtual, @PathVariable int qtdePorPagina) {
		Pageable page = PageRequest.of(paginaAtual, qtdePorPagina);
		return poiService.obterTodos(page);
	}

	@GetMapping("/localizar")
	@ApiOperation(value = "Buscar POIs próximos", notes = "Informe uma localização e distância máxima para localizar todos POIs próximos.", response = Poi.class)
	public Iterable<Poi> localizarNaProximidade(@RequestParam(name = "x", defaultValue = "-1") int referenciaX,
			@RequestParam(name = "y", defaultValue = "-1") int referenciaY,
			@RequestParam(name = "dmax", defaultValue = "-1") int distanciaMax) {

		try {
			return poiService.localizarNaProximidade(referenciaX, referenciaY, distanciaMax);
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}

	}
}
