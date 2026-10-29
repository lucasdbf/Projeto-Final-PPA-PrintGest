package utils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

/**
 * Métodos auxiliares de conversão de texto e carregamento do ícone dos diálogos.
 */
public class Utilidade {

    public static final Icon FAVICON = carregarFavicon();

    /**
     * Converte um texto em número inteiro.
     * @param dado texto digitado pelo usuário
     * @return o número ou {@code null} se o texto for vazio ou inválido
     */
    public static Integer converterInteger(String dado) {
        try {
            return Integer.parseInt(dado);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converte um texto em número decimal, aceitando vírgula ou ponto.
     * @param dado texto digitado pelo usuário
     * @return o número ou {@code null} se o texto for vazio ou inválido
     */
    public static Double converterDouble(String dado) {
        try {
            return Double.parseDouble(dado.replace(",", "."));
        } catch (Exception e) {
            return null;
        }
    }

    // Carrega o favicon e redimensiona para 32x32 para não quebrar o layout
    // Se o arquivo não existir, os diálogos são exibidos sem ícone
    /**
     * Carrega o ícone dos diálogos.
     * @return o ícone ou {@code null} se o arquivo não existir
     */
    private static Icon carregarFavicon() {
        File arquivo = new File("resources/PrintGestFavicon.png");
        if (!arquivo.exists()) return null;

        ImageIcon original = new ImageIcon(arquivo.getPath());
        Image redimensionada = original.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        return new ImageIcon(redimensionada);
    }
}
