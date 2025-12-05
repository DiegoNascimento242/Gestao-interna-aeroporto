package model;

/*
  Enum que representa os possíveis estados de um voo.
  ENUM: tipo especial que define constantes

 */
public enum EstadoVoo {
    PROGRAMADO("Programado"),
    EMBARQUE("Embarque"),
    DECOLADO("Decolado"),
    ATRASADO("Atrasado"),
    CANCELADO("Cancelado");

    private final String descricao;

    EstadoVoo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    /*
     Converte string para EstadoVoo.
     */
    public static EstadoVoo fromString(String texto) {
        for (EstadoVoo estado : EstadoVoo.values()) {
            if (estado.descricao.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        return PROGRAMADO; // padrão
    }
}
