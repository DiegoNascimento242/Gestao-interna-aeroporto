package dao;

import model.Bagagem;

// CRUD COM DAO implementando INTERFACE
public class BagagemDAO implements IDAO<Bagagem> {
    private Bagagem[] bagagens; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 200;

    public BagagemDAO() {
        this.bagagens = new Bagagem[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(Bagagem bagagem) {
        if (this.tamanho < this.bagagens.length) {
            this.bagagens[this.tamanho] = bagagem;
            this.tamanho++;
        }
    }

    public Bagagem[] listarTodos() {
        Bagagem[] resultado = new Bagagem[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.bagagens[i];
        }
        return resultado;
    }

    public Bagagem buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.bagagens[i].getId() == id) {
                return this.bagagens[i];
            }
        }
        return null;
    }

    public Bagagem[] buscarPorTicketId(int ticketId) {
        Bagagem[] resultadoTemp = new Bagagem[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.bagagens[i].getTicketId() == ticketId) {
                resultadoTemp[count++] = this.bagagens[i];
            }
        }

        Bagagem[] resultadoFinal = new Bagagem[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public void atualizar(int id, Bagagem bagagemAtualizada) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.bagagens[i].getId() == id) {
                this.bagagens[i] = bagagemAtualizada;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.bagagens[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.bagagens[j] = this.bagagens[j + 1];
                }
                this.bagagens[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}