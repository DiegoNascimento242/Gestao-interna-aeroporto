package dao;

import model.Assento;

// CRUD COM DAO implementando INTERFACE
public class AssentoDAO implements IDAO<Assento> {
    private Assento[] assentos; // VETOR
    private int tamanho;
    private static final int CAPACIDADE_MAXIMA = 500;

    public AssentoDAO() {
        this.assentos = new Assento[CAPACIDADE_MAXIMA];
        this.tamanho = 0;
    }

    public void criar(Assento assento) {
        if (this.tamanho < this.assentos.length) {
            this.assentos[this.tamanho] = assento;
            this.tamanho++;
        }
    }

    public Assento[] listarTodos() {
        Assento[] resultado = new Assento[this.tamanho];
        for (int i = 0; i < this.tamanho; i++) {
            resultado[i] = this.assentos[i];
        }
        return resultado;
    }

    public Assento buscarPorId(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.assentos[i].getId() == id) {
                return this.assentos[i];
            }
        }
        return null;
    }

    public Assento[] buscarPorVooId(int vooId) {
        Assento[] resultadoTemp = new Assento[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.assentos[i].getVooId() == vooId) {
                resultadoTemp[count++] = this.assentos[i];
            }
        }

        Assento[] resultadoFinal = new Assento[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public Assento[] buscarAssentosLivresPorVooId(int vooId) {
        Assento[] resultadoTemp = new Assento[this.tamanho];
        int count = 0;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.assentos[i].getVooId() == vooId && !this.assentos[i].estaOcupado()) {
                resultadoTemp[count++] = this.assentos[i];
            }
        }

        Assento[] resultadoFinal = new Assento[count];
        for (int i = 0; i < count; i++) {
            resultadoFinal[i] = resultadoTemp[i];
        }
        return resultadoFinal;
    }

    public Assento buscarPorVooIdECodigo(int vooId, String codigoAssento) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.assentos[i].getVooId() == vooId &&
                    this.assentos[i].getCodigoAssento().equals(codigoAssento)) {
                return this.assentos[i];
            }
        }
        return null;
    }

    public void atualizar(int id, Assento assentoAtualizado) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.assentos[i].getId() == id) {
                this.assentos[i] = assentoAtualizado;
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < this.tamanho; i++) {
            if (this.assentos[i].getId() == id) {
                for (int j = i; j < this.tamanho - 1; j++) {
                    this.assentos[j] = this.assentos[j + 1];
                }
                this.assentos[this.tamanho - 1] = null;
                this.tamanho--;
                break;
            }
        }
    }
}