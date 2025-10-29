package dao;

import model.CompanhiaAerea;

// CRUD COM DAO implementando INTERFACE
public class CompanhiaAereaDAO implements IDAO<CompanhiaAerea> {
    private CompanhiaAerea[] companhias; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 20;

    public CompanhiaAereaDAO() {
        this.companhias = new CompanhiaAerea[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(CompanhiaAerea companhia) {
        if (this.tamanho < this.companhias.length) {
            this.companhias[this.tamanho] = companhia;
            this.tamanho++;
        }
    }

    public CompanhiaAerea[] listarTodos() {
        CompanhiaAerea[] resultado = new CompanhiaAerea[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.companhias[i];
        }
        return resultado;
    }

    public CompanhiaAerea buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.companhias[i].getId() == id) {
                return this.companhias[i];
            }
        }
        return null;
    }

    public CompanhiaAerea buscarPorNome(String nome) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.companhias[i].getNome().equalsIgnoreCase(nome)) {
                return this.companhias[i];
            }
        }
        return null;
    }

    public void atualizar(int id, CompanhiaAerea companhiaAtualizada) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.companhias[i].getId() == id) {
                this.companhias[i] = companhiaAtualizada;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.companhias[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.companhias[j] = this.companhias[j + 1];
                }
                this.companhias[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}