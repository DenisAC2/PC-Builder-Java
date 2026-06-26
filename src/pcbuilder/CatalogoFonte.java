package pcbuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief DAO específico para Fonte de Alimentação (PSU).
 * Lê o arquivo CSV e converte os dados em objetos Fonte.
 * @author Denis
 */
public class CatalogoFonte extends LeitorCsvBase<Fonte> {

    public CatalogoFonte() {
        super("catalogo_fonte.csv");
    }

    @Override
    protected void gerarArquivoPadrao(FileWriter fw) throws IOException {
        fw.write("Marca;Modelo;Preco;Potencia;Certificado;Modular\n");
        fw.write("Corsair;CX650;450.00;650;80 Plus Bronze;false\n");
        fw.write("XPG;Core Reactor 850W;750.00;850;80 Plus Gold;true\n");
    }

    @Override
    protected Fonte instanciarVariaveis(String[] colunas) throws NumberFormatException {
        String marca = colunas[0].trim();
        String modelo = colunas[1].trim();
        double preco = Double.parseDouble(colunas[2].trim().replace(",", "."));
        int potencia = Integer.parseInt(colunas[3].trim());
        String certificado = colunas[4].trim();
        boolean modular = Boolean.parseBoolean(colunas[5].trim());
        
        return GenSetup.criarFonte(marca, modelo, preco, potencia, certificado, modular);
    }
}