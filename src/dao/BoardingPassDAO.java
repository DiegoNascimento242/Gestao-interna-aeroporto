package dao;

import model.BoardingPass;

// CRUD COM DAO implementando INTERFACE
public class BoardingPassDAO implements IDAO<BoardingPass> {
    private BoardingPass[] boardingPasses; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 200;

    public BoardingPassDAO() {
        this.boardingPasses = new BoardingPass[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(BoardingPass boardingPass) {
        if (this.tamanho < this.boardingPasses.length) {
            this.boardingPasses[this.tamanho] = boardingPass;
            this.tamanho++;
        }
    }

    public BoardingPass[] listarTodos() {
        BoardingPass[] resultado = new BoardingPass[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.boardingPasses[i];
        }
        return resultado;
    }

    public BoardingPass buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.boardingPasses[i].getId() == id) {
                return this.boardingPasses[i];
            }
        }
        return null;
    }

    public BoardingPass buscarPorTicketId(int ticketId) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.boardingPasses[i].getTicketId() == ticketId) {
                return this.boardingPasses[i];
            }
        }
        return null;
    }

    public BoardingPass buscarPorCodigo(String codigo) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.boardingPasses[i].getCodigo().equals(codigo)) {
                return this.boardingPasses[i];
            }
        }
        return null;
    }

    public void atualizar(int id, BoardingPass boardingPassAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.boardingPasses[i].getId() == id) {
                this.boardingPasses[i] = boardingPassAtualizado;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.boardingPasses[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.boardingPasses[j] = this.boardingPasses[j + 1];
                }
                this.boardingPasses[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}