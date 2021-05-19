package Coordinatescompany.POI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.services.PoiServiceImpl;

@RestController
@RequestMapping("/pois")
public class PoiController {

	@Autowired
	private PoiServiceImpl poiServiceImpl;

	@PostMapping("/cadastrar")
	public Poi novoPoi(Poi poi) {
		return poiServiceImpl.salvar(poi);
	}

	@PutMapping("/alterar")
	public Poi alterarPoi(Poi poi) {
		return poiServiceImpl.alterar(poi);
	}

	@DeleteMapping("/deletar/{id}")
	public void deletarPoi(@PathVariable int id) {
		poiServiceImpl.deletar(id);
	}

	@GetMapping("/todos")
	public Iterable<Poi> obterTodos() {
		return poiServiceImpl.obterTodos();
	}

	@GetMapping("/pagina/{numeroPagina}/{qtdePagina}")
	public Iterable<Poi> obterProdutosPorPagina(@PathVariable int paginaAtual, @PathVariable int qtdePorPagina) {
		Pageable page = PageRequest.of(paginaAtual, qtdePorPagina);
		return poiServiceImpl.obterTodos(page);
	}

	@GetMapping("/localizar")
	public Iterable<Poi> localizarNaProximidade(@RequestParam(name = "x", defaultValue = "-1") int referenciaX,
			@RequestParam(name = "y", defaultValue = "-1") int referenciaY,
			@RequestParam(name = "dmax", defaultValue = "-1") int distanciaMax) {

		return poiServiceImpl.localizarNaProximidade(referenciaX, referenciaY, distanciaMax);

	}
}
