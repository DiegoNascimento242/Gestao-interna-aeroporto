package dao;

import model.Voo;
import java.time.LocalDate;

// CRUD COM DAO implementando INTERFACE
public class VooDAO implements IDAO<Voo> {
    private Voo[] voos; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 100;

    public VooDAO() {
        this.voos = new Voo[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(Voo voo) {
        if (this.tamanho < this.voos.length) {
            this.voos[this.tamanho] = voo;
            this.tamanho++;
        }
    }

    public Voo[] listarTodos() {
        Voo[] resultado = new Voo[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.voos[i];
        }
        return resultado;
    }

    public Voo buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getId() == id) {
                return this.voos[i];
            }
        }
        return null;
    }

    public Voo[] buscarPorRotaEData(String origem, String destino, LocalDate data) {
        Voo[] resultadoTemp = new Voo[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getOrigem().equalsIgnoreCase(origem) &&
                    this.voos[i].getDestino().equalsIgnoreCase(destino) &&
                    this.voos[i].getData().equals(data)) {
                resultadoTemp[count++] = this.voos[i];
            }
        }

        // Retorna vetor com tamanho correto
        Voo[] resultadoFinal = new Voo[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public Voo[] buscarPorOrigem(String origem) {
        Voo[] resultadoTemp = new Voo[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getOrigem().equalsIgnoreCase(origem)) {
                resultadoTemp[count++] = this.voos[i];
            }
        }

        Voo[] resultadoFinal = new Voo[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public Voo[] buscarPorDestino(String destino) {
        Voo[] resultadoTemp = new Voo[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getDestino().equalsIgnoreCase(destino)) {
                resultadoTemp[count++] = this.voos[i];
            }
        }

        Voo[] resultadoFinal = new Voo[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public void atualizar(int id, Voo vooAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.voos[i].getId() == id) {
                this.voos[i] = vooAtualizado;
                break;
            }
        }
    }

    public void deletar(int id) {
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
}