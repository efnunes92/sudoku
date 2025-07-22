import br.com.erikferreira.model.Board;
import br.com.erikferreira.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {

    private final static Scanner SCANNER = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_SIZE = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var opcao = -1;

        while(true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Iniciar Jogo");
            System.out.println("2 - Colocar um numero");
            System.out.println("3 - Remover um numero");
            System.out.println("4 - Verificar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");

            opcao = SCANNER.nextInt();

            switch (opcao) {
                case 1 -> iniciarJogo(positions);
                case 2 -> colocarNumero();
                case 3 -> removerNumero();
                case 4 -> verificarJogoAtual();
                case 5 -> verificarStatusJogo();
                case 6 -> limparJogo();
                case 7 -> finalizarJogo();
                case 8 -> {
                    System.out.println("Saindo do jogo...");
                    return;
                }
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private static void finalizarJogo() {
    }

    private static void limparJogo() {
    }

    private static void verificarStatusJogo() {
    }

    private static void verificarJogoAtual() {
    }

    private static void removerNumero() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado!");
            return;
        }

        System.out.println("informe a coluna (0-8):");
        var coluna = verificarNumeroValido(0, 8);
        System.out.println("informe a linha (0-8):");
        var linha = verificarNumeroValido(0, 8);
        System.out.printf("informe o numero que vai ser inserido na posicao [%s,%s]:\n", coluna, linha);
        var valor = verificarNumeroValido(1, 9);
        if(!board.clearValue(coluna, linha)){
            System.out.println("Não foi possível remover o número, a posição é fixa ou inválida.");
        } else {
            System.out.println("Número removido com sucesso!");
        }
    }

    private static void colocarNumero() {
        if(isNull(board)){
            System.out.println("O jogo não foi iniciado!");
            return;
        }

        System.out.println("informe a coluna (0-8):");
        var coluna = verificarNumeroValido(0, 8);
        System.out.println("informe a linha (0-8):");
        var linha = verificarNumeroValido(0, 8);
        System.out.printf("informe o numero que vai ser inserido na posicao [%s,%s]:\n", coluna, linha);
        var valor = verificarNumeroValido(1, 9);

        if(!board.changeValue(coluna, linha, valor)){
            System.out.println("Não foi possível inserir o número, a posição é fixa ou inválida.");
        } else {
            System.out.println("Número inserido com sucesso!");
        }
    }

    private static void iniciarJogo(Map<String, String> positions) {
        if(nonNull(board)){
            System.out.println("Jogo já iniciado. Deseja reiniciar? (s/n)");
            var resposta = SCANNER.next();
            if(!resposta.equalsIgnoreCase("s")){
                return;
            }
        }
        List<List<Space>> spaces = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_SIZE; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("Jogo iniciado com sucesso!");
    }

    private static int verificarNumeroValido(final int min, final int max) {
        var current = SCANNER.nextInt();
        while(current < min || current > max){
            System.out.printf("Número inválido, informe um número entre %d e %d: ", min, max);
            current = SCANNER.nextInt();
        }
        return current;
    }

}

