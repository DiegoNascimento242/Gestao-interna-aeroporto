package dao;

import model.Bagagem;

// CRUD SEM DAO - demonstra CRUD sem padrão DAO
public class GerenciadorBagagens {
    private Bagagem[] bagagens; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 200;

    public GerenciadorBagagens() {
        this.bagagens = new Bagagem[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    // CREATE - CRUD SEM DAO
    public void adicionarBagagem(Bagagem bagagem) {
        if (this.tamanho < this.bagagens.length) {
            this.bagagens[this.tamanho] = bagagem;
            this.tamanho++;
        }
    }

    // READ - CRUD SEM DAO
    public Bagagem[] listarBagagens() {
        Bagagem[] resultado = new Bagagem[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.bagagens[i];
        }
        return resultado;
    }

    public Bagagem buscarBagagemPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.bagagens[i].getId() == id) {
                return this.bagagens[i];
            }
        }
        return null;
    }

    public Bagagem[] buscarBagagensPorTicketId(int ticketId) {
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

    // UPDATE - CRUD SEM DAO
    public void atualizarBagagem(int id, Bagagem bagagemAtualizada) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.bagagens[i].getId() == id) {
                this.bagagens[i] = bagagemAtualizada;
                break;
            }
        }
    }

    // DELETE - CRUD SEM DAO
    public void removerBagagem(int id) {
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