/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerTela;
import controller.Observador;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import controller.Observado;
import javax.swing.SwingConstants;

/**
 *
 * @author Jéssica Petersen
 */
public class Jogo extends JFrame implements Observador {

    private int coluna;
    private int linha;
    private static final long serialVersionUID = 1L;
    private Observado controller;
    private JTable tabuleiro;

    public Jogo(int tamanho, int casaNormal, int casaRei) {
        this.controller = new ControllerTela();
        controller.addObservador(this);
        controller.escolhaJogo(tamanho, casaNormal, casaRei);
        setTitle("Jéssica Bernardi Petersen");
        setSize(305, 370);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        pack();
    }

    public Jogo() { }

    @Override
    public void tamanhoTabela(int tamanho) {
        this.coluna = tamanho;
        this.linha = tamanho;
    }

    @Override
    public void mudouJogador(String frase) {
        jogadorTitutlo.setText(frase);
    }
    
    @Override
    public void mudouTabuleiro() {
        tabuleiro.repaint();
    }

    class TableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return coluna;
        }

        @Override
        public int getRowCount() {
            return linha;
        }

        @Override
        public Object getValueAt(int row, int col) {
            try {
                return controller.getPeca(col, row);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }

        }

    }

    class Renderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }
    private JLabel jogadorTitutlo;

    private void initComponents() {
        
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        JLabel titulo = new JLabel();
        if (this.coluna == 9) {
            titulo.setText("Tabuleiro Tablut");
        } else {
            if (this.coluna == 11) {
                titulo.setText("Tabuleiro Hnefatafl");
            } else {
                titulo.setText("Tabuleiro Brandubh");
            }
        }
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 26));
        jp.add(titulo, BorderLayout.CENTER);

        jogadorTitutlo = new JLabel("É a vez dos Defensores");
        jogadorTitutlo.setFont(new Font("Tahoma", Font.ITALIC, 18));
        jogadorTitutlo.setAlignmentX(100);
        jogadorTitutlo.setAlignmentY(100);
        jogadorTitutlo.setHorizontalAlignment(SwingConstants.CENTER);
        jp.add(jogadorTitutlo, BorderLayout.SOUTH);

        add(jp, BorderLayout.NORTH);

        JPanel jpa1 = new JPanel();
        // criar o tabuleiro e seus componentes
        tabuleiro = new JTable();
        tabuleiro.setModel(new TableModel());
        for (int x = 0; x < tabuleiro.getColumnModel().getColumnCount(); x++) {
            tabuleiro.getColumnModel().getColumn(x).setWidth(50);
            tabuleiro.getColumnModel().getColumn(x).setMinWidth(70);
            tabuleiro.getColumnModel().getColumn(x).setMaxWidth(70);
        }
        tabuleiro.setRowHeight(50);
        tabuleiro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabuleiro.setShowGrid(false);
        tabuleiro.setIntercellSpacing(new Dimension(0, 0));
        tabuleiro.setDefaultRenderer(Object.class, new Renderer());

        tabuleiro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.onMouseClicado(tabuleiro.rowAtPoint(e.getPoint()), tabuleiro.columnAtPoint(e.getPoint()));
            }
        });

        jpa1.add(tabuleiro);

        add(jpa1, BorderLayout.CENTER);

        JPanel jp2 = new JPanel();
        // jp2.setLayout(new BorderLayout());
        JButton jbVoltar = new JButton("Voltar");
        // jbVoltar.setHorizontalAlignment(SwingConstants.LEFT);
        jbVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Inicio tela = new Inicio();
                tela.setVisible(true);
            }
        });
        jp2.add(jbVoltar);

        JButton jbIniciar = new JButton("Iniciar Partida");
        JButton jbLogMovimento = new JButton("Log de Movimentos");
        jbLogMovimento.setEnabled(false);

        jbIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.distribuiPecas();
                jbIniciar.setEnabled(false);
                jbLogMovimento.setEnabled(true);
            }
        });
        jp2.add(jbIniciar);

        jbLogMovimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, controller.logMovimento());
            }
        });
        jp2.add(jbLogMovimento);

        add(jp2, BorderLayout.SOUTH);
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

}
