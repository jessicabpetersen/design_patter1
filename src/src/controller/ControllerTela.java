/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Branco;
import model.Campo;
import model.CampoNormal;
import model.Log;
import model.Preto;
import model.Refugio;
import model.Rei;
import model.Trono;

/**
 *
 * @author Jéssica Petersen
 */
public class ControllerTela implements Observado {

    private List<Observador> observadores = new ArrayList<>();
    private Campo[][] tabuleiro;
    private Campo[][] refugio;
    private int tamanho;
    private int reiMove;
    private int pecaMove;
    private int colunaClicada;
    private int linhaClicada;
    private boolean mover;
    private Tabuleiro distribuir;
    private Movimento movimento;
    private Log log;
    private Invoker ink;
    private JogadorProxy jogador;

    public ControllerTela() {
    }

    @Override
    public void addObservador(Observador obs) {
        observadores.add(obs);
    }

    public void getFactory() {
        Factory fac = null;
        switch (tamanho) {
            case 11:
                fac = new FactoryHnefatafl();
                break;
            case 7:
                fac = new FactoryBrandubh();
                break;
            case 9:
                fac = new FactoryTablut();
                break;
        }
        this.distribuir = fac.createTabuleiro();
        this.movimento = fac.createMovimento();
        this.tabuleiro = this.distribuir.distribuirCampos(pecaMove, reiMove);
        this.refugio = distribuir.getRefugio();
    }

    @Override
    public void inicializar() {
        jogador = new JogadorProxy();
        log = new Log();
        ink = new Invoker();
        linhaClicada = -1;
        colunaClicada = -1;
        this.tabuleiro = new Campo[tamanho][tamanho];
        this.refugio = new Campo[tamanho][tamanho];
        getFactory();
    }

    @Override
    public void distribuiPecas() {
        tabuleiro = distribuir.distribuiPecas();
        movimento.setTabuleiros(tabuleiro, refugio);
        notificarMudancaTabuleiro();
    }

    private void notificarMudancaTabuleiro() {
        for (Observador obs : observadores) {
            obs.mudouTabuleiro();
        }
    }

    private void notificarLabelJogador(String frase) {
        for (Observador obs : observadores) {
            obs.mudouJogador(frase);
        }
    }

    @Override
    public Icon getPeca(int col, int row) {
        return (this.tabuleiro[col][row] == null ? null : this.tabuleiro[col][row].getImagem());
    }

    @Override
    public int getTamanho() {
        return tamanho;
    }

    @Override
    public void onMouseClicado(int linha, int coluna) {
        // verificar qual jogador é
        if (jogador.verificarJogador1()) { //BRANCO
            //verificar se qual açao: 
            //1 - selecionar (linha -1)
            //2 - deselecionar (linha tem gente e é igual a anterior)
            //3 - mover ( linha tem gente e é diferente da anterior)
            if (linhaClicada == -1) { // selecionar                
                ///verifica se foi clinado no branco
                if (this.tabuleiro[coluna][linha].getClass() == Branco.class) {
                    this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/brancoclicado.png"));
                    this.linhaClicada = linha;
                    this.colunaClicada = coluna;
                } else {
                    //verifica se foi clinado no rei
                    if (this.tabuleiro[coluna][linha].getClass() == Rei.class) {
                        if (verificarRefugio(linha, coluna)) {
                            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/reibrancoregufioclicado.png"));
                        } else {
                            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/reibrancoclicado.png"));
                        }
                        this.linhaClicada = linha;
                        this.colunaClicada = coluna;
                    }
                }
            } else {// deselecionar ou mover
                if (linha == linhaClicada && coluna == colunaClicada) {// deselecionar
                    if (this.tabuleiro[coluna][linha].getClass() == Branco.class) {
                        this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/branco.png"));
                    } else {
                        if (verificarRefugio(linha, coluna)) {
                            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/reibrancoregufio.png"));
                        } else {
                            this.tabuleiro[coluna][linha].setImagem(new ImageIcon("img/reibranco.png"));
                        }
                    }
                    resetClique();
                } else { // mover
                    //verificar se é rei ou peça normal
                    if (this.tabuleiro[colunaClicada][linhaClicada].getClass() == Rei.class) { //rei
                        //verificar se moveu em linha ou em coluna e na casas corretas permitidas
                        if (moveuEmLinhaOuColuna(coluna, linha, reiMove)) {
                            //moveu em linha ou coluna
                            if (moveuEmLinha(linha)) { //moveu em linha -- coluna estatica
                                //verifica p ql lado
                                if (coluna > colunaClicada) {
                                    //verifica se tem peças entre as casas
                                    if (!verificaColunaMovel(colunaClicada, coluna, false, linhaClicada, true)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, true);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor - Rei");
                                        verificarProximaRodada(linha, coluna);
                                        resetClique();
                                    }
                                } else {
                                    if (!verificaColunaMovel(coluna, colunaClicada, true, linhaClicada, true)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, true);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor - Rei");
                                        verificarProximaRodada(linha, coluna);
                                        resetClique();

                                    }
                                }
                            } else { //moveu em coluna -- linha estatica
                                //verifica p ql lado andou
                                if (linha > linhaClicada) {
                                    //verifica se tem peças entre as casas
                                    if (!verificaLinhaMovel(linhaClicada, linha, false, colunaClicada, true)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, true);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor - Rei");
                                        verificarProximaRodada(linha, coluna);
                                        resetClique();
                                    }
                                } else {
                                    if (!verificaLinhaMovel(linha, linhaClicada, true, colunaClicada, true)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, true);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor - Rei");
                                        verificarProximaRodada(linha, coluna);
                                        resetClique();
                                    }
                                }

                            }
                        }

                    } else {//peça normal
                        if (moveuEmLinhaOuColuna(coluna, linha, pecaMove)) {
                            if (moveuEmLinha(linha)) { // moveu em linha - linha estatica- mudou de coluna
                                if (coluna > colunaClicada) { // direita
                                    if (!verificaColunaMovel(colunaClicada, coluna, false, linhaClicada, false)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, false);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor");
                                        resetClique();
                                    }
                                } else { // esquerda
                                    if (!verificaColunaMovel(coluna, colunaClicada, true, linhaClicada, false)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, false);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor");
                                        resetClique();

                                    }
                                }
                            } else { // moveu em coluna -- coluna estatica -mudou de linha
                                if (linha > linhaClicada) {
                                    //p baixo
                                    if (!verificaLinhaMovel(linhaClicada, linha, false, colunaClicada, false)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, false);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor");
                                        resetClique();
                                    }

                                } else {
                                    // p cima
                                    if (!verificaLinhaMovel(linha, linhaClicada, true, colunaClicada, false)) {
                                        this.tabuleiro = movimento.moverBranco(coluna, linha, linhaClicada, colunaClicada, false);
                                        mover = true;
                                        addLog(linha, coluna, "Defensor");
                                        resetClique();
                                    }

                                }
                            }

                        }
                    }

                }

            }

        } else { // PRETO
            //verificar se qual açao: 
            //1 - selecionar (linha -1)
            //2 - deselecionar (linha tem gente e é igual a anterior)
            //3 - mover ( linha tem gente e é diferente da anterior)

            if (linhaClicada == -1) { // selecionar
                //verificar se foi clicado no preto
                if (this.tabuleiro[coluna][linha].getClass() == Preto.class) {
                    this.tabuleiro = this.movimento.selecionarPreto(coluna, linha);
                    linhaClicada = linha;
                    colunaClicada = coluna;
                }
            } else {
                if (linha == linhaClicada && coluna == colunaClicada) { // deselecionar
                    if (this.tabuleiro[coluna][linha].getClass() == Preto.class) {
                        this.tabuleiro = this.movimento.deselecionarPreto(coluna, linha);
                        resetClique();
                    }
                } else {//mover
                    if (moveuEmLinhaOuColuna(coluna, linha, pecaMove)) {
                        if (moveuEmLinha(linha)) { // linha igual - moveu em coluna
                            if (coluna > colunaClicada) {
                                if (!this.movimento.verificaLColunaMovel(colunaClicada, coluna, false, linhaClicada, colunaClicada)) {
                                    this.tabuleiro = this.movimento.moverPreto(coluna, linha, colunaClicada, linhaClicada);
                                    mover = true;
                                    addLog(linha, coluna, "Mercenario");
                                    resetClique();
                                }
                            } else {
                                if (!this.movimento.verificaLColunaMovel(coluna, colunaClicada, true, linhaClicada, colunaClicada)) {
                                    this.tabuleiro = this.movimento.moverPreto(coluna, linha, colunaClicada, linhaClicada);
                                    mover = true;
                                    addLog(linha, coluna, "Mercenario");
                                    resetClique();
                                }
                            }

                        } else { //coluna estatica - mudou em linha
                            if (linha > linhaClicada) {
                                if (!this.movimento.verificaLinhaMovel(linhaClicada, linha, false, colunaClicada, linhaClicada)) {
                                    this.tabuleiro = this.movimento.moverPreto(coluna, linha, colunaClicada, linhaClicada);
                                    mover = true;
                                    addLog(linha, coluna, "Mercenario");
                                    resetClique();
                                }
                            } else {
                                if (!this.movimento.verificaLinhaMovel(linha, linhaClicada, true, colunaClicada, linhaClicada)) {
                                    this.tabuleiro = this.movimento.moverPreto(coluna, linha, colunaClicada, linhaClicada);
                                    mover = true;
                                    addLog(linha, coluna, "Mercenario");
                                    resetClique();
                                }
                            }
                        }

                    }
                }

            }

        }

        if (mover) {
            jogador.inverter();
            if (jogador.verificarJogador1()) {
                notificarLabelJogador("É a vez dos Defensores");
            } else {
                notificarLabelJogador("É a vez dos Mercenários");
            }
            mover = false;
        }
        notificarMudancaTabuleiro();
    }

    public void addLog(int linha, int coluna, String jogador) {
        int ColunaAnterior = colunaClicada + 1;
        int LinhaAnterior = linhaClicada + 1;
        ink.add(new AddCommand(log, jogador, "coluna: " + ColunaAnterior + " linha: " + LinhaAnterior, "coluna: " + (coluna + 1) + " linha: " + (linha + 1)));
        ink.execute();
    }

    /**
     * Verifica se moveu certo - em linha ou em coluna
     *
     * @param coluna - coluna clicada para o movimento
     * @param linha - linha clicada para o movimento
     * @param casas - quantidade de casas permitidas
     * @return boolean - true:moveu certo, false:movimento em diagonal
     */
    public boolean moveuEmLinhaOuColuna(int coluna, int linha, int casas) {
        if (casas == 1) {
            if ((linha == linhaClicada && colunaClicada - casas <= coluna && colunaClicada + casas >= coluna)
                    || (coluna == colunaClicada && linhaClicada + casas >= linha && linhaClicada - casas <= linha)) {
                return true;
            }
        } else {
            if (casas == 4) { // rei pode mover 4 casas
                if ((linhaClicada + 4 == linha || linhaClicada - 4 == linha) || (colunaClicada + 4 == coluna || colunaClicada - 4 == coluna)) {
                    return true;
                }
            } else {
                if (linha == linhaClicada || coluna == colunaClicada) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Verifica se moveu em linha
     *
     * @param linha
     * @return boolean
     */
    public boolean moveuEmLinha(int linha) {
        if (linha == linhaClicada) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se possui alguma peça entre tais casas na coluna movel -- linha
     * estatica
     *
     * @param menor
     * @param maior
     * @param pEsquerda - clicou em uma casa para mover à esquerda da clicada
     * @param linhaClicada
     * @param rei - a carta clicada é rei
     * @return boolean - true: achou uma casa no meio do movimento
     */
    public boolean verificaColunaMovel(int menor, int maior, boolean pEsquerda, int linhaClicada, boolean rei) {
        boolean achou = false;
        if (pEsquerda) {
            for (int i = menor; i < maior; i++) {
                if (rei) {
                    if (this.tabuleiro[i][linhaClicada].getClass() == Branco.class || this.tabuleiro[i][linhaClicada].getClass() == Preto.class) {
                        achou = true;
                        break;
                    }
                } else {
                    if (this.tabuleiro[i][linhaClicada].getClass() != CampoNormal.class) {
                        achou = true;
                        break;
                    }
                }
            }
        } else {
            for (int i = menor + 1; i <= maior; i++) {
                if (rei) {
                    if (this.tabuleiro[i][linhaClicada].getClass() == Branco.class || this.tabuleiro[i][linhaClicada].getClass() == Preto.class) {
                        achou = true;
                        break;
                    }
                } else {
                    if (this.tabuleiro[i][linhaClicada].getClass() != CampoNormal.class) {
                        achou = true;
                        break;
                    }
                }
            }
        }
        return achou;
    }

    /**
     * Verifica se possui alguma peça entre tais casas na linha movel -- coluna
     * estatica
     *
     * @param menor
     * @param maior
     * @param pCima - clicou em uma casa para mover à cima da clicada
     * @param colunaClicada
     * @param rei
     * @return boolean
     */
    public boolean verificaLinhaMovel(int menor, int maior, boolean pCima, int colunaClicada, boolean rei) {
        boolean achou = false;
        if (pCima) {
            for (int i = menor; i < maior; i++) {
                if (rei) {
                    if (this.tabuleiro[colunaClicada][i].getClass() == Branco.class || this.tabuleiro[colunaClicada][i].getClass() == Preto.class) {
                        achou = true;
                        break;
                    }
                } else {
                    if (this.tabuleiro[colunaClicada][i].getClass() != CampoNormal.class) {
                        achou = true;
                        break;
                    }
                }
            }
        } else {
            for (int i = menor + 1; i <= maior; i++) {
                if (rei) {
                    if (this.tabuleiro[colunaClicada][i].getClass() == Branco.class || this.tabuleiro[colunaClicada][i].getClass() == Preto.class) {
                        achou = true;
                        break;
                    }
                } else {
                    if (this.tabuleiro[colunaClicada][i].getClass() != CampoNormal.class) {
                        achou = true;
                        break;
                    }
                }
            }
        }
        return achou;
    }

    @Override
    public void novoJogo() {
        this.tabuleiro = null;
        inicializar();
        distribuiPecas();
        notificarMudancaTabuleiro();
    }

    @Override
    public void escolhaJogo(int tabuleiro, int movimentoNormal, int movimentoRei) {
        this.pecaMove = movimentoNormal;
        this.reiMove = movimentoRei;
        switch (tabuleiro) {
            case 1:
                tamanho = 11;
                break;
            case 2:
                tamanho = 7;
                break;
            case 3:
                tamanho = 9;
                break;
        }
        for (Observador obs : observadores) {
            obs.tamanhoTabela(tamanho);
        }
        inicializar();
    }

    /**
     * Verificar se naquela posição era refugo
     *
     * @param row
     * @param col
     * @return
     */
    public boolean verificarRefugio(int row, int col) {
        if (this.refugio[col][row].getClass() == Refugio.class || this.refugio[col][row].getClass() == Trono.class) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resetar as linhas clicadas
     */
    public void resetClique() {
        this.linhaClicada = -1;
        this.colunaClicada = -1;
    }

    @Override
    public String logMovimento() {
        return ink.imprimir();
    }

    public void verificarProximaRodada(int linha, int coluna) {
        if (reiMove == 2) {
            if (verificarRaichi(linha, coluna)) {
                JOptionPane.showMessageDialog(null, "Raichi!");
            }
        }

    }

    public boolean verificarRaichi(int linha, int coluna) {
        boolean verificacao[][] = {{false, false}, {false, false}, {false, false}, {false, false}};
        boolean raichi = false;
        boolean peca = false;
        for (int i = 0; i < this.tabuleiro.length; i++) {
            //linha
            if (i < linha) {// linhas acima
                if (this.tabuleiro[coluna][i].getClass() == Refugio.class) {
                    verificacao[0][0] = true;
                }
                if (this.tabuleiro[coluna][i].getClass() != CampoNormal.class && this.tabuleiro[coluna][i].getClass() != Refugio.class) {
                    verificacao[0][1] = true;
                }
            } else {
                if (i > linha) {
                    if (this.tabuleiro[coluna][i].getClass() == Refugio.class) {
                        verificacao[1][0] = true;
                    }
                    if (this.tabuleiro[coluna][i].getClass() != CampoNormal.class && this.tabuleiro[coluna][i].getClass() != Refugio.class) {
                        verificacao[1][1] = true;
                    }
                }
            }
            //coluna
            if (i < coluna) {
                if (this.tabuleiro[i][linha].getClass() == Refugio.class) {
                    verificacao[2][0] = true;
                }
                if (this.tabuleiro[i][linha].getClass() != CampoNormal.class && this.tabuleiro[i][linha].getClass() != Refugio.class) {
                    verificacao[2][1] = true;
                }
            } else {
                if (i > coluna) {
                    if (this.tabuleiro[i][linha].getClass() == Refugio.class) {
                        verificacao[3][0] = true;
                    }
                    if (this.tabuleiro[i][linha].getClass() != CampoNormal.class && this.tabuleiro[i][linha].getClass() != Refugio.class) {
                        verificacao[3][1] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            if (verificacao[i][0] == true && verificacao[i][1] == false) {
                return true;
            }
        }
        return false;
    }

}
