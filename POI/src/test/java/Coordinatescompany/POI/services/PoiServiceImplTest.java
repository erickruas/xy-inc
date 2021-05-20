package Coordinatescompany.POI.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Optional;

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

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.repository.PoiRepository;

@ExtendWith(MockitoExtension.class)
class PoiServiceImplTest {

	@Mock
	private PoiRepository poiRepository;

	@InjectMocks
	private PoiServiceImpl poiServiceImpl;

	/*
	 * Teste 1 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .salvar(Poi). Foram informados atributos válidos e é
	 * esperado que seja devolvida uma entidade valida, já com o ID.
	 */
	@Test
	void salvarNovoPoiValidoDeveRetornarComID() {

		Poi poiValido = new Poi("Lanchonete", 10, 10);
		Poi poiValidoComId = new Poi("Lanchonete", 10, 10);
		poiValidoComId.setId(1);
		Mockito.when(poiRepository.save(poiValido)).thenReturn(poiValidoComId);
		Poi resposta = poiServiceImpl.salvar(poiValido);
		assertEquals(1, resposta.getId());
		assertEquals(10, resposta.getCoordenadaX());
		assertEquals(10, resposta.getCoordenadaY());
		assertEquals("Lanchonete", resposta.getNome());
	}

	/*
	 * Teste 2 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .salvar(Poi). Foram informados argumentos inválidos
	 * (nome em branco) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void salvarNovoPoiSemNomeDeveLancarIllegalArgumentException() {

		Poi poiSemNome = new Poi("", 10, 10);
		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.salvar(poiSemNome);
		});
	}

	/*
	 * Teste 3 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .salvar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaX negativa) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void salvarNovoPoiComCoordenadaXNegativaDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaXNegativa = new Poi("Lanchonete", -10, 10);
		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.salvar(poiCoordenadaXNegativa);
		});
	}

	/*
	 * Teste 4 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .salvar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaY negativa) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void salvarNovoPoiComCoordenadaYNegativaDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaYNegativa = new Poi("Lanchonete", 10, -10);
		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.salvar(poiCoordenadaYNegativa);
		});
	}

	/*
	 * Teste 5 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .salvar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaX e CoordenadaY negativas) e é esperado que seja lançada uma
	 * exceção IllegalArgumentException.
	 */
	@Test
	void salvarNovoPoiComCoordenadaXNegativaECoordenadaYNegativaDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaXeYNegativa = new Poi("Lanchonete", -10, -10);
		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.salvar(poiCoordenadaXeYNegativa);
		});
	}

	/*
	 * Teste 6 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .salvar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaX e CoordenadaY negativas e nome em branco) e é esperado que seja
	 * lançada uma exceção IllegalArgumentException.
	 */
	@Test
	void salvarNovoPoiComCoordenadasNegativasESemNomeDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaXeYNegativaeNomeVazio = new Poi("", -10, -10);
		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.salvar(poiCoordenadaXeYNegativaeNomeVazio);
		});
	}

	/*
	 * Teste 7 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .alterar(Poi). Foram informados argumentos válidos e
	 * esperado que o retorno da função alterar seja a mesma entidade passada como
	 * parâmetro ao chamar o método.
	 */
	@Test
	void alterarPoiValidoComIdDeveRetornarComID() {

		Poi poiAnteriorValidoComId = new Poi("Lanchonete", 10, 10);
		poiAnteriorValidoComId.setId(1);
		Poi poiAlteradoValidoComId = new Poi("Lanchonete", 10, 15);
		poiAlteradoValidoComId.setId(1);
		Mockito.when(poiRepository.findById(1)).thenReturn(Optional.of(poiAnteriorValidoComId));
		Mockito.when(poiRepository.save(poiAlteradoValidoComId)).thenReturn(poiAlteradoValidoComId);
		Poi resposta = poiServiceImpl.alterar(poiAlteradoValidoComId);
		assertEquals(1, resposta.getId());
		assertEquals(10, resposta.getCoordenadaX());
		assertEquals(15, resposta.getCoordenadaY());
		assertEquals("Lanchonete", resposta.getNome());

	}

	/*
	 * Teste 8 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .alterar(Poi). Foram informados argumentos inválidos
	 * (Falta ID/ID não localizado) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void alterarPoiValidoSemIdValidoDeveLancarIllegalArgumentException() {

		Poi poiValidoSemId = new Poi("Lanchonete", 10, 10);
		poiValidoSemId.setId(0);
		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.alterar(poiValidoSemId);
		});

	}

	/*
	 * Teste 9 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .alterar(Poi). Foram informados argumentos inválidos
	 * (nome em branco) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void alterarPoiSemNomeDeveLancarIllegalArgumentException() {

		Poi poiSemNomeComId = new Poi("", 10, 10);
		poiSemNomeComId.setId(1);

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.alterar(poiSemNomeComId);
		});
	}

	/*
	 * Teste 10 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .alterar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaX negativa) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void alterarPoiComCoordenadaXNegativaDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaXNegativaComId = new Poi("Lanchonete", -10, 10);
		poiCoordenadaXNegativaComId.setId(1);

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.alterar(poiCoordenadaXNegativaComId);
		});
	}

	/*
	 * Teste 11 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .alterar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaY negativa) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void alterarPoiComCoordenadaYNegativaDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaYNegativaComId = new Poi("Lanchonete", 10, -10);
		poiCoordenadaYNegativaComId.setId(1);

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.alterar(poiCoordenadaYNegativaComId);
		});
	}

	/*
	 * Teste 12 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .alterar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaX e CoordenadaY negativas) e é esperado que seja lançada uma
	 * exceção IllegalArgumentException.
	 */
	@Test
	void alterarPoiComCoordenadaXNegativaECoordenadaYNegativaDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaXeYNegativaComId = new Poi("Lanchonete", -10, -10);
		poiCoordenadaXeYNegativaComId.setId(1);

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.alterar(poiCoordenadaXeYNegativaComId);
		});
	}

	/*
	 * Teste 13 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .alterar(Poi). Foram informados argumentos inválidos
	 * (CoordenadaX, CoordenadaY negativas e nome em branco) e é esperado que seja
	 * lançada uma exceção IllegalArgumentException.
	 */
	@Test
	void alterarPoiComCoordenadasNegativasESemNomeDeveLancarIllegalArgumentException() {

		Poi poiCoordenadaXeYNegativaSemNomeComId = new Poi("", -10, -10);
		poiCoordenadaXeYNegativaSemNomeComId.setId(1);

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.alterar(poiCoordenadaXeYNegativaSemNomeComId);
		});
	}

	/*
	 * Teste 14 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .deletar(id). Foram informados argumentos válidos para a
	 * exclusão e é esperado que o método seja acionado.
	 */
	@Test
	void deletarInformandoOIdDeveSerAcionado() {

		Poi poiValidoComId = new Poi("Lanchonete", 10, 10);
		poiValidoComId.setId(1);
		Mockito.doNothing().when(poiRepository).deleteById(poiValidoComId.getId());
		Mockito.when(poiRepository.existsById(1)).thenReturn(true);
		poiServiceImpl.deletar(poiValidoComId.getId());
		Mockito.verify(poiRepository).deleteById(poiValidoComId.getId());

	}

	/*
	 * Teste 15 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .deletar(id). Foram informados argumentos inválidos (Id
	 * = 0) e é esperado que seja lançada uma exceção IllegalArgumentException.
	 */
	@Test
	void deletarSemInformarOIdDeveLancarIllegalArgumentException() {

		int poiId = 0;
		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.deletar(poiId);
		});
	}

	/*
	 * Teste 16 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos válidos e é
	 * esperado que seja retornada uma lista dos POIs mais próximos, de acordo com
	 * os parâmetros determinados.
	 */
	@Test
	void localizarNaProximidadeComReferenciasEDistanciaMaximaPositivaDeveRetornarLocaisProximos() {
		ArrayList<Poi> proximos = new ArrayList<Poi>();
		Mockito.when(poiRepository.localizaProximos(10, 10, 10)).thenReturn((Iterable<Poi>) proximos);
		Iterable<Poi> resposta = poiServiceImpl.localizarNaProximidade(10, 10, 10);
		assertEquals(resposta, proximos);
	}

	/*
	 * Teste 17 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos inválidos
	 * (referenciaX negativa) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaXNegativaEDistanciaMaximaPositivaDeveLancarIllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.localizarNaProximidade(-10, 10, 10);
		});
	}

	/*
	 * Teste 18 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos inválidos
	 * (referenciaY negativa) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaYNegativaEDistanciaMaximaPositivaDeveLancarIllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.localizarNaProximidade(10, -10, 10);
		});
	}

	/*
	 * Teste 19 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos inválidos
	 * (referenciaX e referenciaY negativa) e é esperado que seja lançada uma
	 * exceção IllegalArgumentException.
	 */
	@Test
	void localizarNaProximidadeComReferenciasNegativasEDistanciaMaximaPositivaDeveLancarIllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.localizarNaProximidade(-10, -10, 10);
		});
	}

	/*
	 * Teste 20 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos inválidos
	 * (distanciaMax negativa) e é esperado que seja lançada uma exceção
	 * IllegalArgumentException.
	 */
	@Test
	void localizarNaProximidadeComReferenciasPositivasEDistanciaMaximaNegativaDeveLancarIllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.localizarNaProximidade(10, 10, -10);
		});
	}

	/*
	 * Teste 21 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos inválidos
	 * (referenciaX e distanciaMax negativas) e é esperado que seja lançada uma
	 * exceção IllegalArgumentException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaXNegativaEDistanciaMaximaNegativaDeveLancarIllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.localizarNaProximidade(-10, 10, -10);
		});
	}

	/*
	 * Teste 22 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos inválidos
	 * (referenciaY e distanciaMax negativas) e é esperado que seja lançada uma
	 * exceção IllegalArgumentException.
	 */
	@Test
	void localizarNaProximidadeComReferenciaYNegativaEDistanciaMaximaNegativaDeveLancarIllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.localizarNaProximidade(10, -10, -10);
		});
	}

	/*
	 * Teste 23 Classe PoiServiceImpl Objetivo - Verificar a integridade das
	 * validações do metodo .localizarnaproximidade(int referenciaX, int
	 * referenciaY, int distanciaMax). Foram informados argumentos inválidos
	 * (referenciaX, referenciaY e distanciaMax negativas) e é esperado que seja
	 * lançada uma exceção IllegalArgumentException.
	 */
	@Test
	void localizarNaProximidadeComReferenciasNegativasEDistanciaMaximaNegativaDeveLancarIllegalArgumentException() {

		assertThrows(IllegalArgumentException.class, () -> {
			poiServiceImpl.localizarNaProximidade(-10, -10, -10);
		});
	}

	/*
	 * Teste 24 Classe PoiServiceImpl Objetivo - Verificar a integridade e
	 * validações do metodo .obterTodos(). O método não requer parâmetros e é
	 * esperado que liste todos os POIs cadastrados.
	 */
	@Test
	void obterTodosDeveRetornarUmOuMaisPOIs() {
		ArrayList<Poi> todos = new ArrayList<Poi>();
		Mockito.when(poiRepository.findAll()).thenReturn((Iterable<Poi>) todos);
		Iterable<Poi> resposta = poiServiceImpl.obterTodos();
		assertEquals(resposta, todos);

	}

	/*
	 * Teste 25 Classe PoiServiceImpl Objetivo - Verificar a integridade e
	 * validações do metodo .obterTodos(Pageable page). O método não requer
	 * parâmetros e é esperado que liste todos os POIs cadastrados de forma
	 * paginada.
	 */
	@Test
	void obterTodosPaginadoDeveRetornarUmOuMaisPOIsPaginados() {
		Page<Poi> todos = new PageImpl<Poi>(new ArrayList<>());
		Pageable page = PageRequest.of(5, 5);
		Mockito.when(poiRepository.findAll(page)).thenReturn(todos);
		Page<Poi> resposta = (Page<Poi>) poiServiceImpl.obterTodos(page);
		assertEquals(resposta, todos);

	}

}
