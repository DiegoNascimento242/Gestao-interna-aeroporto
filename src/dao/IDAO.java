package dao;

// INTERFACE para todos os DAOs
public interface IDAO<T> {
    void criar(T entidade);
    T[] listarTodos(); // VETOR em vez de List
    T buscarPorId(int id);
    void atualizar(int id, T entidade);
    void deletar(int id);
}