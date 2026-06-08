package pcbuilder;

/**
 * @brief Exceção customizada para tratamento de cancelamentos na interface gráfica.
 * Lançada quando o usuário clica em "Cancelar" ou fecha uma janela do JOptionPane.
 * @author Denis
 */
public class CanceladoException extends Exception {
    public CanceladoException() {
        super("Operação cancelada pelo usuário.");
    }
}
