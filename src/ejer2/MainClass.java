package ejer2;

import javax.swing.JFrame;

public class MainClass {
    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(210, 500);
        ventana.setLocationRelativeTo(null);
    }
}
