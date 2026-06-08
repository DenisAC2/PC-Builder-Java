package pcbuilder;

/**
 * @brief Representa o processador (CPU) do sistema.
 * Extende a classe base Componente e adiciona características específicas
 * cruciais para as validações de compatibilidade da placa-mãe (ex: tipo de Socket).
 * @author Denis
 */
public class Processador extends Componente {
    private final String socket;
    private final int quantidadeCores;
    private final int quantidadeThreads;
    private final double frequenciaGhz;
    private final boolean possuiVideoIntegrado;
    private final boolean suporteECC;
    
    /**
     * @brief Construtor completo do Processador.
     */
    public Processador(String socket, int quantidadeCores, int quantidadeThreads, double frequenciaGhz, boolean possuiVideoIntegrado, boolean suporteECC, String marca, String modelo, double preco, int consumoWatts) {
        super(marca, modelo, preco, consumoWatts);
        this.socket = socket;
        this.quantidadeCores = quantidadeCores;
        this.quantidadeThreads = quantidadeThreads;
        this.frequenciaGhz = frequenciaGhz;
        this.possuiVideoIntegrado = possuiVideoIntegrado;
        this.suporteECC = suporteECC;
    }

    public String getSocket() { return socket; }
    public int getQuantidadeCores() { return quantidadeCores; }
    public int getQuantidadeThreads() { return quantidadeThreads; }
    public double getFrequenciaGhz() { return frequenciaGhz; }
    public boolean isPossuiVideoIntegrado() { return possuiVideoIntegrado; }
    public boolean isSuporteECC() { return suporteECC; }
    
    /**
     * @brief Formata e exibe todos os detalhes técnicos do processador.
     * @return String contendo a ficha técnica pronta para exibição na interface.
     */
    @Override
    public String exibirDetalhes() {
        return "=== Detalhes do Processador ===\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Preço: R$ " + getPreco() + "\n" +
               "Consumo: " + getConsumoWatts() + "W\n" +
               "Quantidade de Cores: " + this.quantidadeCores + "\n" +
               "Quantidade de Threads: " + this.quantidadeThreads + "\n" +
               "Socket: " + this.socket + "\n" +
               "Frequência Base: " + this.frequenciaGhz + " GHz\n" +
               "Vídeo Integrado: " + (this.possuiVideoIntegrado ? "Sim" : "Não") + "\n" +
               "Suporte a ECC: " + (this.suporteECC ? "Sim" : "Não") + "\n" +
               "===============================\n";
    }
}

