/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jéssica Petersen
 */
public class Invoker {

    private List<Command> imediatos = new ArrayList<>();
    private List<Command> todos = new ArrayList<>();

    public void add(Command comm) {
        imediatos.add(comm);
    }

    public void execute() {

        for (Command comm : imediatos) {
            comm.execute();
            todos.add(comm);
        }
        imediatos.clear();
    }

    public String imprimir() {
        String log = "Log: \n";
        int jogada = 1;
        for (Command comm : todos) {
            log += "\n Jogada número: "+jogada+"\n"+comm;
            jogada++;
        }
        return log;
    }
}
