package ejer2;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Ventana extends JFrame implements ActionListener {

    private JButton btnResetear;
    private JTextField telefono;
    private JMenuBar menu;
    JButton btnTecla;
    private JMenu archivo, movil, otros;
    private JMenuItem grabar, leer, reset, salir, acercaDe;

    String[] teclas = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "0", "*" };
    ArrayList<JButton> botonesTeclado = new ArrayList<>();
    String texto = "";

    String archivoTelefonos = "src/numeros.txt";

    public Ventana() {
        super();
        this.setLayout(null);
        
        // Menu=======================================================

        // Menu_Archivos
        grabar = new JMenuItem("Grabar número");
        grabar.addActionListener(this);

        leer = new JMenuItem("Leer números");
        leer.addActionListener(this);

        archivo = new JMenu("Archivo");
        archivo.add(grabar);
        archivo.add(leer);
        archivo.addActionListener(this);

        // Menu Móbil
        reset = new JMenuItem("Reset");
        reset.addActionListener(this);

        salir = new JMenuItem("Salir");
        salir.addActionListener(this);

        movil = new JMenu("Móvil");
        movil.add(reset);
        movil.addSeparator();
        movil.add(salir);
        movil.addActionListener(this);

        // Menu Otros
        acercaDe = new JMenuItem("Acerca de");
        acercaDe.addActionListener(this);

        otros = new JMenu("Otros");
        otros.add(acercaDe);
        otros.addActionListener(this);

        menu = new JMenuBar();
        menu.add(archivo);
        menu.add(movil);
        menu.add(otros);
        this.setJMenuBar(menu);

        // Textfield=====================================================

        telefono = new JTextField();
        telefono.setEnabled(false);
        telefono.setSize(170, 30);
        telefono.setLocation(10, 15);
        this.add(telefono);

        // Botones=======================================================
        int x = 10;
        int y = 60;
        for (int i = 0; i <= 11; i++) {
            btnTecla = new JButton(this.teclas[i]);
            btnTecla.setSize(50, 50);
            btnTecla.setLocation(x, y);
            btnTecla.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String tecla = ((JButton) e.getSource()).getText();
                    telefono.setText(texto + tecla);
                    texto = telefono.getText();
                }
            });
            botonesTeclado.add(btnTecla);
            btnTecla.addMouseListener(new Raton());
            btnTecla.addKeyListener(new TecladoEventos());
            this.add(btnTecla);
            if ((i + 1) % 3 == 0) {
                x = 10;
                y = y + 62;
            } else {
                x = x + 62;
            }

        }

        // Boton reset==================================================

        btnResetear = new JButton("Reset");
        btnResetear.setSize(100, 50);
        btnResetear.setLocation(10, 350);
        btnResetear.addActionListener(this);
        btnResetear.addMouseListener(new Raton());
        btnResetear.addKeyListener(new TecladoEventos());
        this.add(btnResetear);

        // Otros========================================================

        this.addKeyListener(new TecladoEventos());

    }

    // EVENTO
    // COMPONENTES==========================================================================================================================================================

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == btnResetear) {
            telefono.setText("");
            texto = telefono.getText();
        }

        if (e.getSource() == this.grabar) {
            //System.err.println("grabar");
            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoTelefonos,true))) {
                pw.println(telefono.getText());
            } catch (IOException event) {
                System.out.println("Error de acceso al archivo");
            }
        }
        if (e.getSource() == this.leer) {
            String texto="";
            String numerito;
            //System.err.println("leer");
            try (Scanner sc = new Scanner(new File(archivoTelefonos))) {
                while (sc.hasNext()) {
                    //ya entra, ahora guardar los numeros
                    numerito = sc.nextLine();//cogemos el dato te la linea
                    texto = texto + numerito +"\n";//y lo agregamos a numerito para verlos luego todos
                }
                System.err.println("entro aqui");
            } catch (IOException events) {
                System.out.println("Error de acceso al archivo");
            }
            JOptionPane.showMessageDialog(null, texto, "telefonos", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == this.reset) {
            for (int i = 0; i < this.botonesTeclado.size(); i++) {
                if (this.botonesTeclado.get(i).getBackground() == Color.GREEN) {
                    this.botonesTeclado.get(i).setBackground(null);
                }
            }
            telefono.setText("");
            texto = telefono.getText();
        }
        if (e.getSource() == this.salir) {
            //System.err.println("salir");
            int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas salir del programa?", "Confirmar",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch (opcion) {
                case JOptionPane.YES_OPTION:
                    System.exit(0);
                case JOptionPane.NO_OPTION | JOptionPane.CANCEL_OPTION:
                    break;
                case JOptionPane.CLOSED_OPTION:
            }
        }

        
        if (e.getSource() == this.acercaDe) {
            //System.err.println("acercaDe");
            JOptionPane.showMessageDialog(null, "Aplicación que simula un teléfono realizado por Iris ", "App Información", JOptionPane.INFORMATION_MESSAGE);
        } 

    }

    // RATON==============================================================================================================================================

    public class Raton extends MouseAdapter {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

            if (e.getSource() == Ventana.this.btnResetear) {
                for (int i = 0; i < Ventana.this.botonesTeclado.size(); i++) {
                    if (Ventana.this.botonesTeclado.get(i).getBackground() == Color.GREEN) {
                        Ventana.this.botonesTeclado.get(i).setBackground(null);
                    }
                }
            } else {
                ((JButton) e.getSource()).setBackground(Color.GREEN);
            }

        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {

            if (((JButton) e.getSource()).getBackground() != Color.GREEN) {
                ((JButton) e.getSource()).setBackground(Color.orange);
            }

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {

            if (((JButton) e.getSource()).getBackground() != Color.GREEN) {
                ((JButton) e.getSource()).setBackground(null);
            }

        }

    }

    // TECLADO====================================================================================================================================================================

    public class TecladoEventos extends KeyAdapter {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            String teclaTeclado = Character.toString(e.getKeyChar());
            System.err.println(e.getKeyChar());
            for (int i = 0; i < Ventana.this.botonesTeclado.size(); i++) {
                // System.err.println( Ventana.this.botonesTeclado.get(i).getText()+"
                // "+teclaTeclado);
                if (Ventana.this.botonesTeclado.get(i).getText().equals(teclaTeclado)) {
                    // System.err.println("si");
                    Ventana.this.botonesTeclado.get(i).setBackground(Color.GREEN);
                }
            }
        }
    }

}