package pcbuilder;

/**
 * @brief Exceção customizada para falhas de compatibilidade de hardware.
 * Lançada quando o sistema detecta gargalos (ex: Socket errado, RAM incompatível).
 * @author Denis
 */
public class IncompatibilidadeException extends Exception {
    public IncompatibilidadeException(String mensagem) {
        super(mensagem);
    }
}
