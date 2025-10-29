package util;

import java.util.Random;

// CLASSE UTILITÁRIA para geração de códigos
public class GeradorCodigo {
    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMEROS = "0123456789";
    private static final Random random = new Random();

    // Gera código do ticket (5 letras aleatórias)
    public static String gerarCodigoTicket(int tamanho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            sb.append(LETRAS.charAt(random.nextInt(LETRAS.length())));
        }
        return sb.toString();
    }

    // Gera código do boarding pass (2 letras + 4 números)
    public static String gerarCodigoBoardingPass() {
        StringBuilder sb = new StringBuilder();
        // 2 letras
        for (int i = 0; i < 2; i++) {
            sb.append(LETRAS.charAt(random.nextInt(LETRAS.length())));
        }
        // 4 números
        for (int i = 0; i < 4; i++) {
            sb.append(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        }
        return sb.toString();
    }

    // Gera código do assento (ex: 01A, 02B, etc.)
    public static String gerarCodigoAssento(int fileira, char assento) {
        return String.format("%02d%c", fileira, assento);
    }
}