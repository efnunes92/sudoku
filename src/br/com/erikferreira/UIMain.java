package br.com.erikferreira;

import br.com.erikferreira.ui.custom.frame.MainFrame;
import br.com.erikferreira.ui.custom.panel.MainPanel;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class UIMain {
    public static void main(String[] args) {
        var dimension = new Dimension(600, 600);
        JPanel panel = new MainPanel(dimension);
        JFrame frame = new MainFrame(dimension, panel);
        frame.revalidate();
        frame.repaint();
    }
}
