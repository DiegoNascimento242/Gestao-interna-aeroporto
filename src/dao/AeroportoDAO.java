package dao;

import model.Aeroporto;

// CRUD COM DAO implementando INTERFACE
public class AeroportoDAO implements IDAO<Aeroporto> {
    private Aeroporto[] aeroportos; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 50;

    public AeroportoDAO() {
        this.aeroportos = new Aeroporto[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(Aeroporto aeroporto) {
        if (this.tamanho < this.aeroportos.length) {
            this.aeroportos[this.tamanho] = aeroporto;
            this.tamanho++;
        }
    }

    public Aeroporto[] listarTodos() {
        Aeroporto[] resultado = new Aeroporto[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.aeroportos[i];
        }
        return resultado;
    }

    public Aeroporto buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.aeroportos[i].getId() == id) {
                return this.aeroportos[i];
            }
        }
        return null;
    }

    public Aeroporto buscarPorAbreviacao(String abreviacao) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.aeroportos[i].getAbreviacao().equalsIgnoreCase(abreviacao)) {
                return this.aeroportos[i];
            }
        }
        return null;
    }

    public void atualizar(int id, Aeroporto aeroportoAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.aeroportos[i].getId() == id) {
                this.aeroportos[i] = aeroportoAtualizado;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.aeroportos[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.aeroportos[j] = this.aeroportos[j + 1];
                }
                this.aeroportos[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}