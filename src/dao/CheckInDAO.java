package dao;

import model.CheckIn;

// CRUD COM DAO implementando INTERFACE
public class CheckInDAO implements IDAO<CheckIn> {
    private CheckIn[] checkins; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 200;

    public CheckInDAO() {
        this.checkins = new CheckIn[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(CheckIn checkin) {
        if (this.tamanho < this.checkins.length) {
            this.checkins[this.tamanho] = checkin;
            this.tamanho++;
        }
    }

    public CheckIn[] listarTodos() {
        CheckIn[] resultado = new CheckIn[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.checkins[i];
        }
        return resultado;
    }

    public CheckIn buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkins[i].getId() == id) {
                return this.checkins[i];
            }
        }
        return null;
    }

    public CheckIn buscarPorTicketId(int ticketId) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkins[i].getTicketId() == ticketId) {
                return this.checkins[i];
            }
        }
        return null;
    }

    public CheckIn buscarPorCodigoBoardingPass(String codigoBoardingPass) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkins[i].getCodigoBoardingPass().equals(codigoBoardingPass)) {
                return this.checkins[i];
            }
        }
        return null;
    }

    public void atualizar(int id, CheckIn checkinAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkins[i].getId() == id) {
                this.checkins[i] = checkinAtualizado;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.checkins[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.checkins[j] = this.checkins[j + 1];
                }
                this.checkins[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}