package pcbuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief DAO específico do Processador.
 * Herda a base de arquivos e foca apenas em converter as 10 colunas em um Processador.
 * @author Denis
 */
public class CatalogoProcessador extends LeitorCsvBase<Processador> {

    public CatalogoProcessador() {
        super("catalogo_processadores.csv");
    }

    @Override
    protected void gerarArquivoPadrao(FileWriter fw) throws IOException {
        fw.write("Marca;Modelo;Preco;Consumo;Socket;Cores;Threads;Frequencia;Video;ECC\n");
        fw.write("AMD;Ryzen 5 8600G;1200.00;65;AM5;6;12;4.3;true;false\n");
        fw.write("Intel;Core i5-12400F;850.00;65;LGA1700;6;12;2.5;false;false\n");
    }

    @Override
    protected Processador instanciarVariaveis(String[] colunas) throws NumberFormatException {
        String marca = colunas[0].trim();
        String modelo = colunas[1].trim();
        double preco = Double.parseDouble(colunas[2].trim().replace(",", "."));
        int consumo = Integer.parseInt(colunas[3].trim());
        String socket = colunas[4].trim();
        int cores = Integer.parseInt(colunas[5].trim());
        int threads = Integer.parseInt(colunas[6].trim());
        double freq = Double.parseDouble(colunas[7].trim().replace(",", "."));
        boolean video = Boolean.parseBoolean(colunas[8].trim());
        boolean ecc = Boolean.parseBoolean(colunas[9].trim());
        
        return GenSetup.criarProcessador(marca, modelo, preco, consumo, socket, cores, threads, freq, video, ecc);
    }
}