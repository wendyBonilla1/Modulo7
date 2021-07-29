package com.mycompany.tareamodulo7wendy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.*;

public class Main extends JFrame {

    private JLabel lblResultado = new JLabel(" ");
    private JPanel pnlBotones = new JPanel(new GridLayout(4, 4));
    private JPanel pnlIgual = new JPanel(new GridLayout(1, 1));
    private JButton[] botones = {
        new JButton("1"), new JButton("2"), new JButton("3"), new JButton("+"),
        new JButton("4"), new JButton("5"), new JButton("6"), new JButton("-"),
        new JButton("7"), new JButton("8"), new JButton("9"), new JButton("*"),
        new JButton("C"), new JButton("0"), new JButton(","), new JButton("/"),
        new JButton("=")
    };

    private Dimension dmVentana = new Dimension(300, 440);

    private double resultado = 0;
    private double numero;
    private static final int SUMA = 1;
    private static final int RESTA = 2;
    private static final int MULTIPLICACION = 3;
    private static final int DIVISION = 4;
    private static final int NINGUNO = 0;
    private int operador = Main.NINGUNO;
    private boolean hayPunto = false;
    private boolean nuevoNumero = true;
    private NumberFormat nf = NumberFormat.getInstance();

    public Main() {
        Dimension dmPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dmPantalla.width - dmVentana.width) / 2;
        int y = (dmPantalla.height - dmVentana.height) / 2;
        this.setLocation(x, y);
        this.setSize(dmVentana);
        this.setTitle("Calculadora");

        lblResultado.setBackground(Color.white);
        lblResultado.setOpaque(true);
        lblResultado.setFont(new Font("Arial", Font.PLAIN, 32));
        PulsaRaton pr = new PulsaRaton();
        for (int i = 0; i < botones.length - 1; i++) {
            pnlBotones.add(botones[i]);
            botones[i].addActionListener(pr);
        }

        pnlIgual.add(botones[botones.length - 1]);
        botones[botones.length - 1].addActionListener(pr);

        pnlIgual.setPreferredSize(new Dimension(0, 50));
        this.add(lblResultado, BorderLayout.NORTH);
        this.add(pnlBotones);
        this.add(pnlIgual, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        lblResultado.setText("0,0");
        lblResultado.setHorizontalAlignment(JLabel.RIGHT);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main();
    }

    class PulsaRaton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton origen = (JButton) e.getSource();
            String texto = origen.getText();
            switch (texto) {
                case "+":
                    operar(Main.SUMA);
                    break;
                case "-":
                    operar(Main.RESTA);
                    break;
                case "*":
                    operar(Main.MULTIPLICACION);
                    break;
                case "/":
                    operar(Main.DIVISION);
                    break;
                case ",":
                    if (!nuevoNumero) {
                        if (!hayPunto) {
                            String rdo = lblResultado.getText();
                            lblResultado.setText(rdo + ",");
                        }
                    } else {
                        lblResultado.setText("0,");
                        nuevoNumero = false;
                    }
                    hayPunto = true;
                    break;
                case "C":
                    lblResultado.setText("0,0");
                    nuevoNumero = true;
                    hayPunto = false;
                    break;
                case "=":
                    if (operador != Main.NINGUNO) {
                        String rdo = lblResultado.getText();
                        if (!rdo.isEmpty()) {
                            Number n = null;
                            try {
                                n = (Number) nf.parse(rdo);
                                numero = n.doubleValue();
                            } catch (ParseException ex) {
                                numero = 0;
                            }
                            switch (operador) {
                                case Main.SUMA:
                                    resultado += numero;
                                    break;
                                case Main.RESTA:
                                    resultado -= numero;
                                    break;
                                case Main.MULTIPLICACION:
                                    resultado *= numero;
                                    break;
                                case Main.DIVISION:
                                    resultado /= numero;
                                    break;
                                default:
                                    resultado = numero;
                                    break;
                            }
                            operador = Main.NINGUNO;
                            lblResultado.setText(nf.format(resultado));
                        }
                    }
                    break;
                default:
                    String rdo = lblResultado.getText();
                    if (nuevoNumero) {
                        lblResultado.setText(texto);
                    } else {
                        lblResultado.setText(rdo + texto);
                    }
                    nuevoNumero = false;
                    break;
            }
        }
    }

    public void operar(int operacion) {
        if (!nuevoNumero) {
            String rdo = lblResultado.getText();
            if (!rdo.isEmpty()) {
                Number n = null;
                try {
                    n = nf.parse(rdo);
                    numero = n.doubleValue();
                } catch (ParseException ex) {
                }
                switch (operador) {
                    case Main.SUMA:
                        resultado += numero;
                        break;
                    case Main.RESTA:
                        resultado -= numero;
                        break;
                    case Main.MULTIPLICACION:
                        resultado *= numero;
                        break;
                    case Main.DIVISION:
                        resultado /= numero;
                        break;
                    default:
                        resultado = numero;
                }
                operador = operacion;
                lblResultado.setText(nf.format(resultado));
                nuevoNumero = true;
                hayPunto = false;
            }
        }
    }

}
