import java.util.Scanner;

public class Sudoku {
    private final int SIZE = 9;
    private int[][] board = new int[SIZE][SIZE];
    private boolean[][] fixed = new boolean[SIZE][SIZE];

    public Sudoku(String[] args) {
        for (String arg : args) {
            String[] parts = arg.split(",");
            if (parts.length == 3) {
                int linha = Integer.parseInt(parts[0]);
                int coluna = Integer.parseInt(parts[1]);
                int numero = Integer.parseInt(parts[2]);
                board[linha][coluna] = numero;
                fixed[linha][coluna] = true;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] == 0 ? ". " : board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean placeNumber(int linha, int col, int num) {
        if (fixed[linha][col] || board[linha][col] != 0) return false;
        board[linha][col] = num;
        return true;
    }

    public boolean removeNumber(int linha, int col) {
        if (fixed[linha][col]) return false;
        board[linha][col] = 0;
        return true;
    }

    public void clearUserNumbers() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!fixed[i][j]) board[i][j] = 0;
            }
        }
    }

    public boolean isComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    public boolean hasErrors() {
        for (int i = 0; i < SIZE; i++) {
            boolean[] linhaCheck = new boolean[SIZE+1];
            boolean[] colunaCheck = new boolean[SIZE+1];
            for (int j = 0; j < SIZE; j++) {
                int lNum = board[i][j];
                int cNum = board[j][i];
                if (lNum != 0) {
                    if (linhaCheck[lNum]) return true;
                    linhaCheck[lNum] = true;
                }
                if (cNum != 0) {
                    if (colunaCheck[cNum]) return true;
                    colunaCheck[cNum] = true;
                }
            }
        }


        for (int block = 0; block < SIZE; block++) {
            boolean[] blockCheck = new boolean[SIZE+1];
            int startLinha = (block/3)*3;
            int startColuna = (block%3)*3;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int numero = board[startLinha+i][startColuna+j];
                    if (numero != 0) {
                        if (blockCheck[numero]) return true;
                        blockCheck[numero] = true;
                    }
                }
            }
        }
        return false;
    }

    public String getStatus() {
        boolean started = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != 0) started = true;
            }
        }
        if (!started) return "Não iniciado";
        if (!isComplete()) return hasErrors() ? "Incompleto com erros" : "Incompleto sem erros";
        return hasErrors() ? "Completo com erros" : "Completo sem erros";
    }
}

