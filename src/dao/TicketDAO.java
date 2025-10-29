package dao;

import model.Ticket;

// CRUD COM DAO implementando INTERFACE
public class TicketDAO implements IDAO<Ticket> {
    private Ticket[] tickets; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 200;

    public TicketDAO() {
        this.tickets = new Ticket[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(Ticket ticket) {
        if (this.tamanho < this.tickets.length) {
            this.tickets[this.tamanho] = ticket;
            this.tamanho++;
        }
    }

    public Ticket[] listarTodos() {
        Ticket[] resultado = new Ticket[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.tickets[i];
        }
        return resultado;
    }

    public Ticket buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.tickets[i].getId() == id) {
                return this.tickets[i];
            }
        }
        return null;
    }

    public Ticket buscarPorCodigo(String codigo) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.tickets[i].getCodigo().equals(codigo)) {
                return this.tickets[i];
            }
        }
        return null;
    }

    public Ticket[] buscarPorPassageiroId(int passageiroId) {
        Ticket[] resultadoTemp = new Ticket[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.tickets[i].getPassageiroId() == passageiroId) {
                resultadoTemp[count++] = this.tickets[i];
            }
        }

        Ticket[] resultadoFinal = new Ticket[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public Ticket[] buscarPorVooId(int vooId) {
        Ticket[] resultadoTemp = new Ticket[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.tickets[i].getVooId() == vooId) {
                resultadoTemp[count++] = this.tickets[i];
            }
        }

        Ticket[] resultadoFinal = new Ticket[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public void atualizar(int id, Ticket ticketAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.tickets[i].getId() == id) {
                this.tickets[i] = ticketAtualizado;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.tickets[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.tickets[j] = this.tickets[j + 1];
                }
                this.tickets[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}