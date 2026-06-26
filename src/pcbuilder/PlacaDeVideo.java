package pcbuilder;

/**
 * @brief Representa a Placa de Vídeo (GPU) dedicada do sistema.
 * Extende a classe base Componente e adiciona especificações de vídeo.
 * @author Denis
 */
public class PlacaDeVideo extends Componente {
    private static final long serialVersionUID = 1L;
    private final int capacidadeVramGB;
    private final String tipoVram;
    private final String conexaoPCIE;

    public PlacaDeVideo(int capacidadeVramGB, String tipoVram, String conexaoPCIE, String marca, String modelo, double preco, int consumoWatts) {
        super(marca, modelo, preco, consumoWatts);
        this.capacidadeVramGB = capacidadeVramGB;
        this.tipoVram = tipoVram;
        this.conexaoPCIE = conexaoPCIE;
    }

    public int getCapacidadeVramGB() { return capacidadeVramGB; }
    public String getTipoVram() { return tipoVram; }
    public String getConexaoPCIE() { return conexaoPCIE; }

    @Override
    public String exibirDetalhes() {
        return "=== Detalhes da Placa de Vídeo ===\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Preço: R$ " + String.format("%.2f", getPreco()) + "\n" +
               "Consumo: " + getConsumoWatts() + "W\n" +
               "VRAM: " + this.capacidadeVramGB + " GB " + this.tipoVram + "\n" +
               "Interface: " + this.conexaoPCIE + "\n" +
               "===============================\n";
    }
}