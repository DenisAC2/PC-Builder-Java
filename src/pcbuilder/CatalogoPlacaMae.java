package pcbuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief DAO específico da Placa-Mãe.
 * Herda a base de arquivos e implementa a conversão de colunas como Chipset e Slots NVMe.
 * @author Denis
 */
public class CatalogoPlacaMae extends LeitorCsvBase<PlacaMae> {

    public CatalogoPlacaMae() {
        super("catalogo_placamae.csv");
    }

    @Override
    protected void gerarArquivoPadrao(FileWriter fw) throws IOException {
        fw.write("Marca;Modelo;Preco;Consumo;Chipset;Socket;TipoRam;SlotsRam;SlotsNvme;Formato\n");
        fw.write("ASUS;B650M-AYW WIFI;1100.00;25;B650;AM5;DDR5;2;2;Micro-ATX\n");
    }

    @Override
    protected PlacaMae instanciarVariaveis(String[] colunas) throws NumberFormatException {
        String marca = colunas[0].trim();
        String modelo = colunas[1].trim();
        double preco = Double.parseDouble(colunas[2].trim().replace(",", "."));
        int consumo = Integer.parseInt(colunas[3].trim());
        String chipset = colunas[4].trim();
        String socket = colunas[5].trim();
        String tipoRam = colunas[6].trim();
        int slotsRam = Integer.parseInt(colunas[7].trim());
        int slotsNvme = Integer.parseInt(colunas[8].trim());
        String formato = colunas[9].trim();
        
        return GenSetup.criarPlacaMae(marca, modelo, preco, consumo, chipset, socket, tipoRam, slotsRam, slotsNvme, formato);
    }
}