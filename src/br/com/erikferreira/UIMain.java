package br.com.erikferreira;

import br.com.erikferreira.ui.custom.frame.MainFrame;
import br.com.erikferreira.ui.custom.panel.MainPanel;
import br.com.erikferreira.ui.custom.screen.MainScreen;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {
    public static void main(String[] args) {
        final var gameConfig = Stream.of(args).collect(toMap(k -> k.split(";")[0],
                v -> v.split(";")[1]));
        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }
}
