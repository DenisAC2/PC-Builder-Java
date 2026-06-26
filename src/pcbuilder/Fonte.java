package pcbuilder;

/**
 * @brief Representa a Fonte de Alimentação (PSU).
 * Fundamental para validar se a potência fornecida é maior que o consumo do Setup.
 * @author Denis
 */
public class Fonte extends Componente {
    /** @brief ID de versão para garantir a estabilidade da serialização (arquivos .dat) */
    private static final long serialVersionUID = 1L;
    private final int potenciaWatts;
    private final String certificado80Plus;
    private final boolean fullModular;

    public Fonte(int potenciaWatts, String certificado80Plus, boolean fullModular, String marca, String modelo, double preco, int consumoWatts) {
        super(marca, modelo, preco, consumoWatts);
        this.potenciaWatts = potenciaWatts;
        this.certificado80Plus = certificado80Plus;
        this.fullModular = fullModular;
    }

    public int getPotenciaWatts() { return potenciaWatts; }
    public String getCertificado80Plus() { return certificado80Plus; }
    public boolean isFullModular() { return fullModular; }

    @Override
    public String exibirDetalhes() {
        return "=== Detalhes da Fonte ===\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Preço: R$ " + getPreco() + "\n" +
               "Potência Máxima: " + this.potenciaWatts + "W\n" +
               "Certificado 80 Plus: " + this.certificado80Plus + "\n" +
               "Full Modular: " + (this.fullModular ? "Sim" : "Não") + "\n" +
               "===============================\n";
    }
}