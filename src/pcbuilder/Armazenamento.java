package pcbuilder;

/**
 * @brief Representa as unidades de armazenamento (SSD, NVMe, HDD).
 * @author Denis
 */
public class Armazenamento extends Componente {
    /** @brief ID de versão para garantir a estabilidade da serialização (arquivos .dat) */
    private static final long serialVersionUID = 1L;
    private final String tipo;
    private final int capacidadeGB;
    private final int velocidade;
    private final String conexaoPCIE;

    public Armazenamento(String tipo, int capacidadeGB, int velocidade, String conexaoPCIE, String marca, String modelo, double preco, int consumoWatts) {
        super(marca, modelo, preco, consumoWatts);
        this.tipo = tipo;
        this.capacidadeGB = capacidadeGB;
        this.velocidade = velocidade;
        this.conexaoPCIE = conexaoPCIE;
    }

    public String getTipo() { return tipo; }
    public int getCapacidadeGB() { return capacidadeGB; }
    public int getVelocidade() { return velocidade; }
    public String getConexaoPCIE() { return conexaoPCIE; }

    @Override
    public String exibirDetalhes() {
        return "=== Detalhes do Armazenamento ===\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Preço: R$ " + getPreco() + "\n" +
               "Consumo: " + getConsumoWatts() + "W\n" +
               "Tipo de Armazenamento: " + this.tipo + "\n" +
               "Capacidade: " + this.capacidadeGB + " GB\n" +
               "Velocidade: " + this.velocidade + " MB/s\n" +
               "Conexão PCIe: " + this.conexaoPCIE + "\n" +
               "===============================\n";
    }
}
