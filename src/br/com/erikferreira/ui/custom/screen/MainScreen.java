package br.com.erikferreira.ui.custom.screen;

import br.com.erikferreira.service.BoardService;
import br.com.erikferreira.ui.custom.button.FinishGameButton;
import br.com.erikferreira.ui.custom.button.ResetGameButton;
import br.com.erikferreira.ui.custom.frame.MainFrame;
import br.com.erikferreira.ui.custom.panel.MainPanel;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton finishGameButton;
    private JButton resetGameButton;
    private JButton checkGameButton;

    public MainScreen(final Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addShowGameStatus(mainPanel);
        addFinishGame(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addShowGameStatus(JPanel mainPanel) {
        checkGameButton = new FinishGameButton(e -> {
            var hasErros = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "O jogo ainda não foi iniciado.";
                case INCOMPLETED -> "O jogo ainda não foi finalizado.";
                case COMPLETED -> "O jogo foi finalizado com sucesso!";
            };
            message += hasErros ? " e existem erros no jogo." : " E não existem erros no jogo.";
            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(checkGameButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetGameButton = new ResetGameButton(e -> {
            var diaologResult = JOptionPane.showConfirmDialog(
                    null, "Você tem certeza que deseja reiniciar o jogo?",
                    "Reiniciar Jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if(diaologResult == 0) {
                boardService.resetar();
            }
        });

        mainPanel.add(resetGameButton);
    }

    private void addFinishGame(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if(boardService.gameOver()) {
                JOptionPane.showMessageDialog(null, "O jogo já foi finalizado.");
                return;
            }
            var hasErros = boardService.hasErrors();
            if(hasErros) {
                JOptionPane.showMessageDialog(null, "Existem erros no jogo. Corrija antes de finalizar.");
                return;
            }
            boardService.getStatus();
            JOptionPane.showMessageDialog(null, "O jogo foi finalizado com sucesso!");
            resetGameButton.setEnabled(false);
            checkGameButton.setEnabled(false);
            finishGameButton.setEnabled(false);

        });
        mainPanel.add(finishGameButton);
    }
}
