/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Jéssica Petersen
 */
public class Log  {
    private String jogador;
    private String posicaoInicial;
    private String posicaoFinal;
    
    public void add(String jogador, String posicaoInicial, String posicaoFinal){
        this.jogador = jogador;
        this.posicaoFinal = posicaoFinal;
        this.posicaoInicial = posicaoInicial;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public String getPosicaoInicial() {
        return posicaoInicial;
    }

    public void setPosicaoInicial(String posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }

    public String getPosicaoFinal() {
        return posicaoFinal;
    }

    public void setPosicaoFinal(String posicaoFinal) {
        this.posicaoFinal = posicaoFinal;
    }
    @Override
    public String toString(){
        return "Jogador: "+jogador+"\nposição inicial: "+ posicaoInicial+"\n posição final:"+posicaoFinal+"\n\n";
    }
}