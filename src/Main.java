import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sudoku sudoku = new Sudoku(args);
        boolean running = true;
        while (running) {
            System.out.println("\nMenu Sudoku:");
            System.out.println("1. Iniciar novo jogo");
            System.out.println("2. Colocar novo número");
            System.out.println("3. Remover número");
            System.out.println("4. Visualizar jogo");
            System.out.println("5. Verificar status do jogo");
            System.out.println("6. Limpar números do usuário");
            System.out.println("7. Finalizar jogo");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    sudoku = new Sudoku(args);
                    System.out.println("Novo jogo iniciado!");
                    sudoku.printBoard();
                    break;
                case 2:
                    System.out.print("Informe o número (1-9): ");
                    int numero = scanner.nextInt();
                    System.out.print("Índice linha (0-8): ");
                    int linha = scanner.nextInt();
                    System.out.print("Índice coluna (0-8): ");
                    int coluna = scanner.nextInt();
                    if (sudoku.placeNumber(linha, coluna, numero)) {
                        System.out.println("Número colocado!");
                    } else {
                        System.out.println("Não é possível colocar número nesta posição!");
                    }
                    break;
                case 3:
                    System.out.print("Índice linha (0-8): ");
                    linha = scanner.nextInt();
                    System.out.print("Índice coluna (0-8): ");
                    coluna = scanner.nextInt();
                    if (sudoku.removeNumber(linha, coluna)) {
                        System.out.println("Número removido!");
                    } else {
                        System.out.println("Não é possível remover número fixo!");
                    }
                    break;
                case 4:
                    sudoku.printBoard();
                    break;
                case 5:
                    System.out.println("Status: " + sudoku.getStatus());
                    break;
                case 6:
                    sudoku.clearUserNumbers();
                    System.out.println("Números do usuário removidos!");
                    break;
                case 7:
                    if (sudoku.isComplete() && !sudoku.hasErrors()) {
                        System.out.println("Jogo finalizado com sucesso!");
                        running = false;
                    } else {
                        System.out.println("O jogo não está completo ou contém erros!");
                    }
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}