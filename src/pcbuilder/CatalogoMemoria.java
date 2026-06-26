package pcbuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief DAO específico da Memória RAM.
 * Lê o arquivo CSV e converte os dados em objetos MemoriaRam.
 * @author Denis
 */
public class CatalogoMemoria extends LeitorCsvBase<MemoriaRam> {

    public CatalogoMemoria() {
        super("catalogo_memoria.csv");
    }

    @Override
    protected void gerarArquivoPadrao(FileWriter fw) throws IOException {
        fw.write("Marca;Modelo;Preco;Consumo;TipoRam;Capacidade;Frequencia;ECC\n");
        fw.write("Kingston;Fury Beast 16GB;350.00;5;DDR5;16;5200;false\n");
        fw.write("Corsair;Vengeance 32GB;700.00;6;DDR5;32;6000;false\n");
    }

    @Override
    protected MemoriaRam instanciarVariaveis(String[] colunas) throws NumberFormatException {
        String marca = colunas[0].trim();
        String modelo = colunas[1].trim();
        double preco = Double.parseDouble(colunas[2].trim().replace(",", "."));
        int consumo = Integer.parseInt(colunas[3].trim());
        String tipoRam = colunas[4].trim();
        int capacidade = Integer.parseInt(colunas[5].trim());
        int freq = Integer.parseInt(colunas[6].trim());
        boolean ecc = Boolean.parseBoolean(colunas[7].trim());
        
        return GenSetup.criarMemoria(marca, modelo, preco, consumo, tipoRam, capacidade, freq, ecc);
    }
}