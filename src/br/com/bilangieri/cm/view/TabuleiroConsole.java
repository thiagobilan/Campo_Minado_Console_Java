package br.com.bilangieri.cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.bilangieri.cm.excecao.ExplosionException;
import br.com.bilangieri.cm.excecao.SairException;
import br.com.bilangieri.cm.model.Tabuleiro;

public class TabuleiroConsole {
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {

		this.tabuleiro = tabuleiro;
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			while (continuar) {
				cicloDoJogo();

				System.out.println("Deseja Continuar S/n");
				String resposta = entrada.nextLine();
				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.resetar();
				}

			}
		} catch (SairException e) {
			System.out.println("Adeus!");
		} finally {
			entrada.close();
		}

	}

	private void cicloDoJogo() {
		try {
			while (!tabuleiro.objectiveAlcancado()) {
				System.out.println(tabuleiro);
				String digitado = capturarValorDigitado("Digite (x, y)");
				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim()))
						.iterator();

				digitado = capturarValorDigitado("1 - Abrir ou 2 - Marcar e (Des)Marcar");
				if ("1".equals(digitado)) {
					tabuleiro.abrirCampo(xy.next(), xy.next());

				} else if ("2".equals(digitado)) {
					tabuleiro.marcarCampo(xy.next(), xy.next());
				}
			}
			System.out.println(tabuleiro);
			System.out.println("Voce Ganhou!");
		} catch (ExplosionException e) {
			System.out.println(tabuleiro);
			System.out.println("Voce Perdeu!");
		}

	}

	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;

	}

}
