package service;

import dao.BoardingPassDAO;
import model.BoardingPass;
import util.GeradorCodigo;

// CRUD COM DAO
public class BoardingPassService {
    private BoardingPassDAO boardingPassDAO;

    public BoardingPassService() {
        this.boardingPassDAO = new BoardingPassDAO();
    }

    public BoardingPass gerarBoardingPass(int ticketId, String assento, String portaoEmbarque) {
        int novoId = this.gerarNovoId();
        String codigo = GeradorCodigo.gerarCodigoBoardingPass();
        BoardingPass boardingPass = new BoardingPass(novoId, ticketId, codigo, assento, portaoEmbarque);
        this.boardingPassDAO.criar(boardingPass);
        return boardingPass;
    }

    public BoardingPass buscarBoardingPassPorTicketId(int ticketId) {
        return this.boardingPassDAO.buscarPorTicketId(ticketId);
    }

    public BoardingPass buscarBoardingPassPorCodigo(String codigo) {
        return this.boardingPassDAO.buscarPorCodigo(codigo);
    }

    public BoardingPass buscarBoardingPassPorId(int id) {
        return this.boardingPassDAO.buscarPorId(id);
    }

    public BoardingPass[] listarBoardingPasses() {
        return this.boardingPassDAO.listarTodos();
    }

    private int gerarNovoId() {
        BoardingPass[] todos = this.listarBoardingPasses();
        int maiorId = 0;
        for (BoardingPass bp : todos) {
            if (bp.getId() > maiorId) {
                maiorId = bp.getId();
            }
        }
        return maiorId + 1;
    }
}