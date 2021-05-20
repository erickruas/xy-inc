package Coordinatescompany.POI.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.services.PoiService;

@ExtendWith(MockitoExtension.class)
class PoiControllerTest {

	@Mock
	private PoiService poiService;

	@InjectMocks
	private PoiController poiController;

	/*
	 * Teste 1 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .novoPoi(Poi). Foram informados
	 * atributos válidos e é esperado que seja devolvida uma entidade valida, já com
	 * o ID.
	 */
	@Test
	void novoPoiValidoDeveRetornarComID() {
		Poi poiValido = new Poi("Lanchonete", 10, 10);
		Poi poiValidoComId = new Poi("Lanchonete", 10, 10);
		poiValidoComId.setId(1);
		Mockito.when(poiService.salvar(poiValido)).thenReturn(poiValidoComId);
		Poi resposta = poiController.novoPoi(poiValido);
		assertEquals(1, resposta.getId());
		assertEquals(10, resposta.getCoordenadaX());
		assertEquals(10, resposta.getCoordenadaY());
		assertEquals("Lanchonete", resposta.getNome());
	}

	/*
	 * Teste 2 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .novoPoi(Poi). Foram informados
	 * atributos inválidos (Sem nome) e é esperado que seja lançada uma exceção
	 * ResponseStatusException.
	 */
	@Test
	void novoPoiSemNomeDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiSemNome = new Poi("", 10, 10);
		Mockito.when(poiService.salvar(poiSemNome)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.novoPoi(poiSemNome);
		});
	}

	/*
	 * Teste 3 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .novoPoi(Poi). Foram informados
	 * atributos inválidos (CoordenadaX negativa) e é esperado que seja lançada uma
	 * exceção ResponseStatusException.
	 */
	@Test
	void salvarNovoPoiComCoordenadaXNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiComCoordenadaXNegativa = new Poi("Lanchonete", -10, 10);
		Mockito.when(poiService.salvar(poiComCoordenadaXNegativa)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.novoPoi(poiComCoordenadaXNegativa);
		});
	}

	/*
	 * Teste 4 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .novoPoi(Poi). Foram informados
	 * atributos inválidos (CoordenadaY negativa) e é esperado que seja lançada uma
	 * exceção ResponseStatusException.
	 */
	@Test
	void salvarNovoPoiComCoordenadaYNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiCoordenadaYNegativa = new Poi("Lanchonete", 10, -10);
		Mockito.when(poiService.salvar(poiCoordenadaYNegativa)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.novoPoi(poiCoordenadaYNegativa);
		});
	}

	/*
	 * Teste 5 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .novoPoi(Poi). Foram informados
	 * atributos inválidos (CoordenadaY e Coordenada Y negativas) e é esperado que
	 * seja lançada uma exceção ResponseStatusException.
	 */
	@Test
	void salvarNovoPoiComCoordenadaXNegativaECoordenadaYNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiCoordenadaXeYNegativa = new Poi("Lanchonete", -10, -10);
		Mockito.when(poiService.salvar(poiCoordenadaXeYNegativa)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.novoPoi(poiCoordenadaXeYNegativa);
		});
	}

	/*
	 * Teste 6 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .novoPoi(Poi). Foram informados
	 * atributos inválidos (CoordenadaY e Coordenada Y negativas e sem nome ) e é
	 * esperado que seja lançada uma exceção ResponseStatusException.
	 */
	@Test
	void salvarNovoPoiComCoordenadasNegativasESemNomeDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiCoordenadaXeYNegativaeNomeVazio = new Poi("", -10, -10);
		Mockito.when(poiService.salvar(poiCoordenadaXeYNegativaeNomeVazio))
				.thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.novoPoi(poiCoordenadaXeYNegativaeNomeVazio);
		});
	}

	/*
	 * Teste 7 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .alterarPoi(Poi). Foram
	 * informados atributos validos e esperado que a resposta seja um POI os mesmos
	 * atributos do poi informado ao chamar o método.
	 */
	@Test
	void alterarPoiValidoComIdDeveRetornarComID() {

		Poi poiValidoComId = new Poi("Lanchonete", 10, 10);
		poiValidoComId.setId(1);
		Mockito.when(poiService.alterar(poiValidoComId)).thenReturn(poiValidoComId);
		Poi resposta = poiController.alterarPoi(poiValidoComId);
		assertEquals(1, resposta.getId());
		assertEquals(10, resposta.getCoordenadaX());
		assertEquals(10, resposta.getCoordenadaY());
		assertEquals("Lanchonete", resposta.getNome());

	}

	/*
	 * Teste 8 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .alterarPoi(Poi). Foram
	 * informados atributos inválidos (Sem ID/ID não localizado) e é esperado que
	 * seja lançada uma exceção ResponseStatusException.
	 */
	@Test
	void alterarPoiValidoSemIdValidoDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiValidoSemId = new Poi("Lanchonete", 10, 10);
		poiValidoSemId.setId(0);
		Mockito.when(poiService.alterar(poiValidoSemId)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.alterarPoi(poiValidoSemId);
		});

	}

	/*
	 * Teste 9 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .alterarPoi(Poi). Foram
	 * informados atributos inválidos (Sem nome) e é esperado que seja lançada uma
	 * exceção ResponseStatusException.
	 */
	@Test
	void alterarPoiSemNomeDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiSemNomeComId = new Poi("", 10, 10);
		poiSemNomeComId.setId(1);
		Mockito.when(poiService.alterar(poiSemNomeComId)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.alterarPoi(poiSemNomeComId);
		});
	}

	/*
	 * Teste 10 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .alterarPoi(Poi). Foram
	 * informados atributos inválidos (CoordenadaX negativa) e é esperado que seja
	 * lançada uma exceção ResponseStatusException.
	 */
	@Test
	void alterarPoiComCoordenadaXNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiCoordenadaXNegativaComId = new Poi("Lanchonete", -10, 10);
		poiCoordenadaXNegativaComId.setId(1);
		Mockito.when(poiService.alterar(poiCoordenadaXNegativaComId)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.alterarPoi(poiCoordenadaXNegativaComId);
		});
	}

	/*
	 * Teste 11 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .alterarPoi(Poi). Foram
	 * informados atributos inválidos (CoordenadaY negativa) e é esperado que seja
	 * lançada uma exceção ResponseStatusException.
	 */
	@Test
	void alterarPoiComCoordenadaYNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiCoordenadaYNegativaComId = new Poi("Lanchonete", 10, -10);
		poiCoordenadaYNegativaComId.setId(1);
		Mockito.when(poiService.alterar(poiCoordenadaYNegativaComId)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.alterarPoi(poiCoordenadaYNegativaComId);
		});
	}

	/*
	 * Teste 12 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .alterarPoi(Poi). Foram
	 * informados atributos inválidos (CoordenadaX e CoordenadaY negativa) e é
	 * esperado que seja lançada uma exceção ResponseStatusException.
	 */
	@Test
	void alterarPoiComCoordenadaXNegativaECoordenadaYNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiCoordenadaXeYNegativaComId = new Poi("Lanchonete", -10, -10);
		poiCoordenadaXeYNegativaComId.setId(1);
		Mockito.when(poiService.alterar(poiCoordenadaXeYNegativaComId)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.alterarPoi(poiCoordenadaXeYNegativaComId);
		});
	}

	/*
	 * Teste 13 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .alterarPoi(Poi). Foram
	 * informados atributos inválidos (CoordenadaX e CoordenadaY negativa e sem
	 * nome) e é esperado que seja lançada uma exceção ResponseStatusException.
	 */
	@Test
	void alterarPoiComCoordenadasNegativasESemNomeDeveRetornarResponseStatusExceptionBadRequest() {

		Poi poiCoordenadaXeYNegativaSemNomeComId = new Poi("", -10, -10);
		poiCoordenadaXeYNegativaSemNomeComId.setId(1);
		Mockito.when(poiService.alterar(poiCoordenadaXeYNegativaSemNomeComId))
				.thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.alterarPoi(poiCoordenadaXeYNegativaSemNomeComId);
		});
	}

	/*
	 * Teste 14 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .deletar(id). Foi informado um id
	 * e é esperado que o método da camada se serviço seja acionado.
	 */
	@Test
	void deletarInformandoOIdDeveSerAcionado() {

		Poi poiValidoComId = new Poi("Lanchonete", 10, 10);
		poiValidoComId.setId(1);
		Mockito.doNothing().when(poiService).deletar(poiValidoComId.getId());
		poiController.deletarPoi(poiValidoComId.getId());
		Mockito.verify(poiService).deletar(poiValidoComId.getId());

	}

	/*
	 * Teste 15 Classe PoiController Objetivo - Verificar o acionamento da camada de
	 * serviço por meio do método da controller -> .deletar(id). Não foi informado
	 * um id e é esperado que seja lançada uma exceção ResponseStatusException
	 */
	@Test
	void deletarSemInformarOIdDeveRetornarResponseStatusExceptionBadRequest() {

		int poiId = 0;
		Mockito.doThrow(IllegalArgumentException.class).when(poiService).deletar(poiId);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.deletarPoi(poiId);
		});
	}

	/*
	 * Teste 16 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * válidos e é esperado que seja retornada uma lista dos POIs mais próximos, de
	 * acordo com os parâmetros determinados.
	 */
	@Test
	void localizarNaProximidadeComReferenciasEDistanciaMaximaPositivaDeveRetornarLocaisProximos() {
		ArrayList<Poi> proximos = new ArrayList<Poi>();
		Mockito.when(poiService.localizarNaProximidade(10, 10, 10)).thenReturn((Iterable<Poi>) proximos);
		Iterable<Poi> resposta = poiController.localizarNaProximidade(10, 10, 10);
		assertEquals(resposta, proximos);
	}

	/*
	 * Teste 17 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * inválidos (referenciaX negativa) e é esperado que seja lançada uma exceção
	 * ResponseStatusException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaXNegativaEDistanciaMaximaPositivaDeveRetornarResponseStatusExceptionBadRequest() {
		Mockito.when(poiService.localizarNaProximidade(-10, 10, 10)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.localizarNaProximidade(-10, 10, 10);
		});
	}

	/*
	 * Teste 18 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * inválidos (referenciaY negativa) e é esperado que seja lançada uma exceção
	 * ResponseStatusException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaYNegativaEDistanciaMaximaPositivaDeveRetornarResponseStatusExceptionBadRequest() {

		Mockito.when(poiService.localizarNaProximidade(10, -10, 10)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.localizarNaProximidade(10, -10, 10);
		});
	}

	/*
	 * Teste 19 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * inválidos (referenciaX e referenciaY negativa) e é esperado que seja lançada
	 * uma exceção ResponseStatusException.
	 */
	@Test
	void localizarNaProximidadeComReferenciasNegativasEDistanciaMaximaPositivaDeveRetornarResponseStatusExceptionBadRequest() {

		Mockito.when(poiService.localizarNaProximidade(-10, -10, 10)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.localizarNaProximidade(-10, -10, 10);
		});
	}

	/*
	 * Teste 20 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * inválidos (distanciaMax negativa) e é esperado que seja lançada uma exceção
	 * ResponseStatusException.
	 */
	@Test
	void localizarNaProximidadeComReferenciasPositivasEDistanciaMaximaNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Mockito.when(poiService.localizarNaProximidade(10, 10, -10)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.localizarNaProximidade(10, 10, -10);
		});
	}

	/*
	 * Teste 21 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * inválidos (referenciaX e distanciaMax negativas) e é esperado que seja
	 * lançada uma exceção ResponseStatusException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaXNegativaEDistanciaMaximaNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Mockito.when(poiService.localizarNaProximidade(-10, 10, -10)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.localizarNaProximidade(-10, 10, -10);
		});
	}

	/*
	 * Teste 22 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * inválidos (referenciaY e distanciaMax negativas) e é esperado que seja
	 * lançada uma exceção ResponseStatusException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaYNegativaEDistanciaMaximaNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Mockito.when(poiService.localizarNaProximidade(10, -10, -10)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.localizarNaProximidade(10, -10, -10);
		});
	}

	/*
	 * Teste 23 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .localizarNaProximidade(int
	 * referenciaX, int referenciaY, int distanciaMax). Foram informados argumentos
	 * inválidos (referenciaY, referencia X e distanciaMax negativas) e é esperado
	 * que seja lançada uma exceção ResponseStatusException.
	 */
	@Test
	void localizarNaProximidadeComReferenciasNegativasEDistanciaMaximaNegativaDeveRetornarResponseStatusExceptionBadRequest() {

		Mockito.when(poiService.localizarNaProximidade(-10, -10, -10)).thenThrow(IllegalArgumentException.class);
		assertThrows(ResponseStatusException.class, () -> {
			poiController.localizarNaProximidade(-10, -10, -10);
		});
	}

	/*
	 * Teste 24 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .obterTodos(). O método não
	 * requer parâmetros e é esperado que retorne todos os POIs cadastrados.
	 */
	@Test
	void obterTodosDeveRetornarUmOuMaisPOIs() {
		ArrayList<Poi> todos = new ArrayList<Poi>();
		Mockito.when(poiService.obterTodos()).thenReturn((Iterable<Poi>) todos);
		Iterable<Poi> resposta = poiController.obterTodos();
		assertEquals(resposta, todos);

	}

	/*
	 * Teste 25 Classe PoiServiceImpl Objetivo - Verificar o acionamento da camada
	 * de serviço por meio do método da controller -> .obterTodos(Pageable page). O
	 * método não requer parâmetros e é esperado que retorne todos os POIs
	 * cadastrados de forma paginada.
	 * 
	 */
	@Test
	void obterTodosPaginadoDeveRetornarUmOuMaisPOIsPaginados() {
		Page<Poi> todos = new PageImpl<Poi>(new ArrayList<>());
		Pageable page = PageRequest.of(5, 5);
		Mockito.when(poiService.obterTodos(page)).thenReturn(todos);
		Page<Poi> resposta = (Page<Poi>) poiController.obterProdutosPorPagina(5, 5);
		assertEquals(resposta, todos);
	}
}
