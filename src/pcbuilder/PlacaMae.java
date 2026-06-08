package pcbuilder;

/**
 * @brief Representa a placa-mãe do computador.
 * Guarda as regras estruturais como Socket e Tipo de RAM que ditarão 
 * a compatibilidade do restante do sistema.
 * @author Denis
 */
public class PlacaMae extends Componente {
    private final String chipset;
    private final String socketSuportado;
    private final String tipoRamSuportado;
    private final int slotsRam;
    private final int slotsNvme;
    private final String formato;

    public PlacaMae(String chipset, String socketSuportado, String tipoRamSuportado, int slotsRam, int slotsNvme, String formato, String marca, String modelo, double preco, int consumoWatts) {
        super(marca, modelo, preco, consumoWatts);
        this.chipset = chipset;
        this.socketSuportado = socketSuportado;
        this.tipoRamSuportado = tipoRamSuportado;
        this.slotsRam = slotsRam;
        this.slotsNvme = slotsNvme;
        this.formato = formato;
    }

    public String getChipset() { return chipset; }
    public String getSocketSuportado() { return socketSuportado; }
    public String getTipoRamSuportado() { return tipoRamSuportado; }
    public int getSlotsRam() { return slotsRam; }
    public int getSlotsNvme() { return slotsNvme; }
    public String getFormato() { return formato; }

    @Override
    public String exibirDetalhes() {
        return "=== Detalhes da Placa-Mãe ===\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Preço: R$ " + getPreco() + "\n" +
               "Consumo Próprio: " + getConsumoWatts() + "W\n" +
               "Chipset: " + this.chipset + "\n" +
               "Socket: " + this.socketSuportado + "\n" +
               "RAM Suportada: " + this.tipoRamSuportado + "\n" +
               "Slots de RAM: " + this.slotsRam + "\n" +
               "Slots M.2 NVMe: " + this.slotsNvme + "\n" +
               "Formato: " + this.formato + "\n" +
               "===============================\n";
    }
}
