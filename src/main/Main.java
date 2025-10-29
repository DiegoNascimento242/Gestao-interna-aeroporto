package main;
import controller.SistemaController;
import javax.swing.JOptionPane;


public class  Main {
    public static void main(String[] args) {
        // Tela inicial simples com JOptionPane
        String mensagemInicial =
                        "TRABALHO 1 POO - DIEGO CARDOSO DO NASCIMENTO\n\n" +
                        "Bem-vindo ao sistema completo de Gestao-interna-aeroporto!\n\n" +
                        "Este sistema inclui:\n" +
                        "- CRUDs completos\n" +
                        "- Compra de passagens\n" +
                        "- Check-in online\n" +
                        "- Relatorios gerenciais\n" +
                        "Clique em OK para continuar...";

        JOptionPane.showMessageDialog(null, mensagemInicial);

        // Inicializar e executar o sistema
        SistemaController sistema = new SistemaController();
        sistema.inicializarDados();
        sistema.menuPrincipal();

        JOptionPane.showMessageDialog(null, "Obrigado por usar o sistema!");
    }
}

