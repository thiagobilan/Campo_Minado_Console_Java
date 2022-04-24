package br.com.bilangieri.cm.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bilangieri.cm.excecao.ExplosionException;
import br.com.bilangieri.cm.model.Campo;

public class CampoTeste {
	private Campo campo;

	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}

	@Test
	void testeVizinhoCima() {
		Campo vizinho = new Campo(3, 2);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}

	@Test
	void testeVizinhoBaixo() {
		Campo vizinho = new Campo(3, 4);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}

	@Test
	void testeVizinhoDireita() {
		Campo vizinho = new Campo(4, 3);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}

	@Test
	void testeVizinhoEsquerda() {
		Campo vizinho = new Campo(2, 3);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}

	@Test
	void testeVizinhoDiagonal1() {
		Campo vizinho = new Campo(4, 2);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}

	@Test
	void testeVizinhoDiagonal2() {
		Campo vizinho = new Campo(4, 4);
		boolean result = campo.adicionarVizinho(vizinho);
		assertTrue(result);
	}

	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(5, 4);
		boolean result = campo.adicionarVizinho(vizinho);
		assertFalse(result);
	}

	@Test
	void testarMarcacao() {
		assertFalse(campo.isMarcado());
	}

	@Test
	void testAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}

	@Test
	void testAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void testAlternarMarcacaoCampoAberto() {
		campo.abrir();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void testAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}

	@Test
	void testAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}

	@Test
	void testAbrirMinadoNaoMarcado() {
		campo.minar();

		assertThrows(ExplosionException.class, () -> {
			campo.abrir();
		});
	}

	@Test
	void testAbrirComVizinho() {
		Campo vizinho1 = new Campo(2, 2);
		Campo vizinho2 = new Campo(2, 3);
		Campo vizinho3 = new Campo(2, 4);
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		campo.adicionarVizinho(vizinho1);
		campo.adicionarVizinho(vizinho2);
		campo.adicionarVizinho(vizinho3);
		campo.abrir();
		assertTrue(vizinho1.isAberto() && vizinho2.isAberto() && vizinho3.isAberto() && vizinhoDoVizinho1.isAberto());
	}
	@Test
	void testAbrirComVizinhoMinado() {
		Campo vizinho1 = new Campo(2, 2);
		Campo vizinho2 = new Campo(2, 3);
		Campo vizinho3 = new Campo(2, 4);
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		vizinhoDoVizinho1.minar();
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		campo.adicionarVizinho(vizinho1);
		campo.adicionarVizinho(vizinho2);
		campo.adicionarVizinho(vizinho3);
		campo.abrir();
		assertFalse(vizinho1.isAberto() && vizinho2.isAberto() && vizinho3.isAberto() && vizinhoDoVizinho1.isAberto());
	}
}
