package service;

// INTERFACE para serviços - demonstra POLIMORFISMO
public interface IService<T> {
    void cadastrar(T entidade);
    T[] listarTodos();
    T buscarPorId(int id);
    void atualizar(int id, T entidade);
    void excluir(int id);
}