package Coordinatescompany.POI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.services.PoiService;

@RestController
@RequestMapping("/pois")
public class PoiController {

	@Autowired
	private PoiService poiService;

	@PostMapping("/cadastrar")
	public Poi novoPoi(@RequestBody Poi poi) {
		return poiService.salvar(poi);
	}

	@PutMapping("/alterar")
	public Poi alterarPoi(@RequestBody Poi poi) {
		return poiService.alterar(poi);
	}

	@DeleteMapping("/deletar/{id}")
	public void deletarPoi(@PathVariable int id) {
		poiService.deletar(id);
	}

	@GetMapping("/todos")
	public Iterable<Poi> obterTodos() {
		return poiService.obterTodos();
	}

	@GetMapping("/pagina/{paginaAtual}/{qtdePorPagina}")
	public Iterable<Poi> obterProdutosPorPagina(@PathVariable int paginaAtual, @PathVariable int qtdePorPagina) {
		Pageable page = PageRequest.of(paginaAtual, qtdePorPagina);
		return poiService.obterTodos(page);
	}

	@GetMapping("/localizar")
	public Iterable<Poi> localizarNaProximidade(@RequestParam(name = "x", defaultValue = "-1") int referenciaX,
			@RequestParam(name = "y", defaultValue = "-1") int referenciaY,
			@RequestParam(name = "dmax", defaultValue = "-1") int distanciaMax) {

		return poiService.localizarNaProximidade(referenciaX, referenciaY, distanciaMax);

	}
}
