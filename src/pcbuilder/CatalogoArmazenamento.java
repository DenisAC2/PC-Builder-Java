package pcbuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief DAO específico de Armazenamento (SSD/NVMe/HDD).
 * Lê o arquivo CSV e converte os dados em objetos Armazenamento.
 * @author Denis
 */
public class CatalogoArmazenamento extends LeitorCsvBase<Armazenamento> {

    public CatalogoArmazenamento() {
        super("catalogo_armazenamento.csv");
    }

    @Override
    protected void gerarArquivoPadrao(FileWriter fw) throws IOException {
        fw.write("Marca;Modelo;Preco;Consumo;Tipo;Capacidade;Velocidade;ConexaoPCIE\n");
        fw.write("Kingston;NV2 1TB;400.00;4;NVMe M.2;1000;3500;PCIe 4.0\n");
        fw.write("Samsung;990 PRO 2TB;1200.00;6;NVMe M.2;2000;7450;PCIe 4.0\n");
    }

    @Override
    protected Armazenamento instanciarVariaveis(String[] colunas) throws NumberFormatException {
        String marca = colunas[0].trim();
        String modelo = colunas[1].trim();
        double preco = Double.parseDouble(colunas[2].trim().replace(",", "."));
        int consumo = Integer.parseInt(colunas[3].trim());
        String tipo = colunas[4].trim();
        int capacidade = Integer.parseInt(colunas[5].trim());
        int velocidade = Integer.parseInt(colunas[6].trim());
        String pcie = colunas[7].trim();
        
        return GenSetup.criarArmazenamento(marca, modelo, preco, consumo, tipo, capacidade, velocidade, pcie);
    }
}