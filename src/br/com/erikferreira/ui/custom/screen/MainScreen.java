package br.com.erikferreira.ui.custom.screen;

import br.com.erikferreira.model.Space;
import br.com.erikferreira.service.BoardService;
import br.com.erikferreira.service.EventEnum;
import br.com.erikferreira.service.NotifierService;
import br.com.erikferreira.ui.custom.button.CheckGameStatusButton;
import br.com.erikferreira.ui.custom.button.FinishGameButton;
import br.com.erikferreira.ui.custom.button.ResetGameButton;
import br.com.erikferreira.ui.custom.frame.MainFrame;
import br.com.erikferreira.ui.custom.input.NumberText;
import br.com.erikferreira.ui.custom.panel.MainPanel;
import br.com.erikferreira.ui.custom.panel.SudokuSector;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton finishGameButton;
    private JButton resetGameButton;
    private JButton checkGameButton;

    public MainScreen(final Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for (int i = 0; i < 9; i+=3) {
            var linhaFinal = i + 2;
            for (int j = 0; j < 9; j+=3) {
                var colunaFinal = j + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), j, colunaFinal, i, linhaFinal);
                mainPanel.add(generateSection(spaces));
            }

        }
        addResetButton(mainPanel);
        addShowGameStatus(mainPanel);
        addFinishGame(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(n -> notifierService.subscribe(EventEnum.CLEAR_SPACE, n));
        return new SudokuSector(fields);
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> sectors,
                                            final int initCol, final int endCol,
                                            final int initRow, final int endRow) {
        List<Space> spaces = new ArrayList<>();
        for (int i = initRow; i <= endRow; i++) {
            for (int j = initCol; j <= endCol; j++) {
                spaces.add(sectors.get(i).get(j));
            }
        }
        return spaces;
    }

    private void addShowGameStatus(JPanel mainPanel) {
        checkGameButton = new CheckGameStatusButton(e -> {
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
                notifierService.notify(EventEnum.CLEAR_SPACE);
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
