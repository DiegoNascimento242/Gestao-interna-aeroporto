package dao;

import model.Voo;
import java.time.LocalDate;

// CRUD SEM DAO - demonstra CRUD sem padrão DAO
public class GerenciadorVoos {
    private Voo[] voos; // VETOR
    private int proximoId;
    private int tamanho;

    public GerenciadorVoos() {
        this.voos = new Voo[50]; // VETOR
        this.proximoId = 1;
        this.tamanho = 0;
    }

    // CREATE - CRUD SEM DAO
    public void adicionarVoo(Voo voo) {
        if (this.tamanho < this.voos.length) {
            this.voos[this.tamanho] = voo;
            this.tamanho++;
        }
    }

    // READ - CRUD SEM DAO
    public Voo[] listarVoos() {
        Voo[] resultado = new Voo[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.voos[i];
        }
        return resultado;
    }

    public Voo buscarVooPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getId() == id) {
                return this.voos[i];
            }
        }
        return null;
    }

    // UPDATE - CRUD SEM DAO
    public void atualizarVoo(int id, Voo vooAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getId() == id) {
                this.voos[i] = vooAtualizado;
                break;
            }
        }
    }

    // DELETE - CRUD SEM DAO
    public void removerVoo(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.voos[j] = this.voos[j + 1];
                }
                this.voos[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }

    // Busca personalizada - POLIMORFISMO através de sobrecarga
    public Voo[] buscarVoosPorOrigem(String origem) {
        Voo[] resultado = new Voo[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getOrigem().equalsIgnoreCase(origem)) {
                resultado[count++] = this.voos[i];
            }
        }

        // Retorna vetor com tamanho correto
        Voo[] resultadoFinal = new Voo[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultado[i];
        }
        return resultadoFinal;

    }

    public Voo[] buscarVoosPorDestino(String destino) {
        Voo[] resultado = new Voo[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getDestino().equalsIgnoreCase(destino)) {
                resultado[count++] = this.voos[i];
            }
        }

        // Retorna vetor com tamanho correto
        Voo[] resultadoFinal = new Voo[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultado[i];
        }
        return resultadoFinal;
    }

    public Voo[] buscarVoosPorData(LocalDate data) {
        Voo[] resultado = new Voo[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getData().equals(data)) {
                resultado[count++] = this.voos[i];
            }
        }

        // Retorna vetor com tamanho correto
        Voo[] resultadoFinal = new Voo[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultado[i];
        }
        return resultadoFinal;
    }
}