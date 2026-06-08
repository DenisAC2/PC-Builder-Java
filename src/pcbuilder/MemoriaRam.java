package pcbuilder;

/**
 * @brief Representa um módulo de Memória RAM.
 * @author Denis
 */
public class MemoriaRam extends Componente {
    private final String tipoRam;
    private final int capacidadeGB;
    private final int frequenciaMhz;
    private final boolean suporteECC;

    public MemoriaRam(String tipoRam, int capacidadeGB, int frequenciaMhz, boolean suporteECC, String marca, String modelo, double preco, int consumoWatts) {
        super(marca, modelo, preco, consumoWatts);
        this.tipoRam = tipoRam;
        this.capacidadeGB = capacidadeGB;
        this.frequenciaMhz = frequenciaMhz;
        this.suporteECC = suporteECC;
    }

    public String getTipoRam() { return tipoRam; }
    public int getCapacidadeGB() { return capacidadeGB; }
    public int getFrequenciaMhz() { return frequenciaMhz; }
    public boolean isSuporteECC() { return suporteECC; }

    @Override
    public String exibirDetalhes() {
        return "=== Detalhes da Memória RAM ===\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Preço: R$ " + getPreco() + "\n" +
               "Consumo: " + getConsumoWatts() + "W\n" +
               "Tipo: " + this.tipoRam + "\n" +
               "Capacidade: " + this.capacidadeGB + " GB\n" +
               "Frequência: " + this.frequenciaMhz + " MHz\n" +
               "Suporte a ECC: " + (this.suporteECC ? "Sim" : "Não") + "\n" +
               "===============================\n";
    }
}
