package dao;

import model.Passageiro;

// CRUD COM DAO implementando INTERFACE
public class PassageiroDAO implements IDAO<Passageiro> {
    private Passageiro[] passageiros; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 100;

    public PassageiroDAO() {
        this.passageiros = new Passageiro[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(Passageiro passageiro) {
        if (this.tamanho < this.passageiros.length) {
            this.passageiros[this.tamanho] = passageiro;
            this.tamanho++;
        }
    }

    public Passageiro[] listarTodos() {
        Passageiro[] resultado = new Passageiro[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.passageiros[i];
        }
        return resultado;
    }

    public Passageiro buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.passageiros[i].getId() == id) {
                return this.passageiros[i];
            }
        }
        return null;
    }

    public Passageiro buscarPorDocumento(String documento) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.passageiros[i].getDocumento().equals(documento)) {
                return this.passageiros[i];
            }
        }
        return null;
    }

    public void atualizar(int id, Passageiro passageiroAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.passageiros[i].getId() == id) {
                this.passageiros[i] = passageiroAtualizado;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.passageiros[i].getId() == id) {
                // Remove deslocando elementos no VETOR
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.passageiros[j] = this.passageiros[j + 1];
                }
                this.passageiros[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}