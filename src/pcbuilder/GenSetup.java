package pcbuilder;

/**
 * @brief Classe com padrão Factory simplificado.
 * Centraliza a responsabilidade de instanciar os hardwares, facilitando a manutenção.
 * @author Denis
 */
public class GenSetup {
    
    public static Processador criarProcessador(String marca, String modelo, double preco, int consumo, String socket, int cores, int threads, double freq, boolean video, boolean ecc) {
        return new Processador(socket, cores, threads, freq, video, ecc, marca, modelo, preco, consumo);
    }

    public static PlacaMae criarPlacaMae(String marca, String modelo, double preco, int consumo, String chipset, String socket, String tipoRam, int slotsRam, int slotsNvme, String formato) {
        return new PlacaMae(chipset, socket, tipoRam, slotsRam, slotsNvme, formato, marca, modelo, preco, consumo);
    }

    public static MemoriaRam criarMemoria(String marca, String modelo, double preco, int consumo, String tipoRam, int capacidade, int freq, boolean ecc) {
        return new MemoriaRam(tipoRam, capacidade, freq, ecc, marca, modelo, preco, consumo);
    }

    public static Armazenamento criarArmazenamento(String marca, String modelo, double preco, int consumo, String tipo, int capacidade, int velocidade, String pcie) {
        return new Armazenamento(tipo, capacidade, velocidade, pcie, marca, modelo, preco, consumo);
    }

    public static Fonte criarFonte(String marca, String modelo, double preco, int potencia, String certificado, boolean modular) {
        return new Fonte(potencia, certificado, modular, marca, modelo, preco, 0); 
    }
}
