package br.com.bilangieri.cm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import br.com.bilangieri.cm.excecao.ExplosionException;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private int mine;
	private final List<Campo> campos = new ArrayList<Campo>();

	public Tabuleiro(int linhas, int colunas, int mine) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.mine = mine;

		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

	public void abrirCampo(int linha, int coluna) {
		try {
			campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
					.ifPresent(c -> c.abrir());
		} catch (ExplosionException e) {
			campos.forEach(c -> c.setAbertoMinados());
			throw e;
		}

	}

	public void marcarCampo(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacao());
	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));

			}
		}

	}

	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}

	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {

			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < mine);

	}

	public boolean objectiveAlcancado() {
		return campos.stream().allMatch(c -> c.objectiveAlcancado());
	}

	public void resetar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();

	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("   ");
		for (int x = 0; x < colunas; x++) {
			sb.append(" ");
			sb.append(x);
			sb.append(" ");
			//System.out.print(x + "  ");
		}
		sb.append("\n\n");
		for (int l = 0; l < linhas; l++) {
			sb.append(l + "  ");
			for (int c = 0; c < colunas; c++) {
				if (l == 0) {

				}
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
