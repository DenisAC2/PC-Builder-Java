package pcbuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief DAO específico para a Placa de Vídeo (GPU).
 * Lê o ficheiro CSV e converte os dados em objetos PlacaDeVideo.
 * @author Denis
 */
public class CatalogoPlacaDeVideo extends LeitorCsvBase<PlacaDeVideo> {

    public CatalogoPlacaDeVideo() {
        super("catalogo_placadevideo.csv");
    }

    @Override
    protected void gerarArquivoPadrao(FileWriter fw) throws IOException {
        fw.write("Marca;Modelo;Preco;Consumo;VRAM;TipoVRAM;ConexaoPCIE\n");
        // Dados de exemplo (separados por ponto e vírgula)
        fw.write("NVIDIA;RTX 4060;1850.00;115;8;GDDR6;PCIe 4.0 x8\n");
        fw.write("AMD;RX 7600;1700.00;165;8;GDDR6;PCIe 4.0 x8\n");
        fw.write("NVIDIA;RTX 4070 Super;4200.00;220;12;GDDR6X;PCIe 4.0 x16\n");
    }

    @Override
    protected PlacaDeVideo instanciarVariaveis(String[] colunas) throws NumberFormatException {
        String marca = colunas[0].trim();
        String modelo = colunas[1].trim();
        double preco = Double.parseDouble(colunas[2].trim().replace(",", "."));
        int consumo = Integer.parseInt(colunas[3].trim());
        int vram = Integer.parseInt(colunas[4].trim());
        String tipoVram = colunas[5].trim();
        String pcie = colunas[6].trim();
        
        return GenSetup.criarPlacaDeVideo(marca, modelo, preco, consumo, vram, tipoVram, pcie);
    }
}