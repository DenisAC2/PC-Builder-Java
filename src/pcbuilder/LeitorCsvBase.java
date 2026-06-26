package pcbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief Classe abstrata base (Data Access Object) para leitura de catálogos.
 * Aplica os padrões Template Method e Generics. Centraliza a lógica de I/O
 * e repassa a responsabilidade de instanciar dados específicos para as filhas.
 * @param <T> O tipo genérico que representa o Componente (Processador, PlacaMae, etc).
 * @author Denis
 */
public abstract class LeitorCsvBase<T extends Componente> {
    
    private final String caminhoArquivo;
    
    public LeitorCsvBase(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        inicializarCatalogo();
    }
    
    /** @brief Método "Gancho" (Hook) para a filha escrever as peças de exemplo se o arquivo não existir. */
    protected abstract void gerarArquivoPadrao(FileWriter fw) throws IOException;
    
    /** @brief Método abstrato onde cada filha converte as colunas do CSV em seu objeto específico. */
    protected abstract T instanciarVariaveis(String[] colunas) throws NumberFormatException;
    
    private void inicializarCatalogo() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            try (FileWriter fw = new FileWriter(arquivo)) {
                gerarArquivoPadrao(fw);
            } catch (IOException e) {
                System.err.println("[Leitor CSV] Erro ao criar infraestrutura: " + e.getMessage());
            }
        }
    }
    
    /**
     * @brief Varre o banco de dados e busca um componente pelo seu modelo.
     * Trata quebras de linha e protege contra erros de leitura do arquivo.
     * @param modeloBuscado A string do modelo procurado.
     * @param indiceColunaModelo Em qual coluna do CSV o nome do modelo está localizado (geralmente 1).
     * @return O objeto devidamente instanciado e tipado, ou null se não encontrado.
     */
    public T buscarPorModelo(String modeloBuscado, int indiceColunaModelo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine();
            
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                
                String[] dados = linha.split(";");
                
                if (dados.length > indiceColunaModelo) {
                    String modeloNaLinha = dados[indiceColunaModelo].trim();
                    
                    if (modeloNaLinha.equalsIgnoreCase(modeloBuscado.trim())) {
                        try {
                            return instanciarVariaveis(dados);
                        } catch (Exception ex) {
                            System.err.println("[Leitor CSV] Corrupção de dados na linha: " + modeloNaLinha);
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[Leitor CSV] Falha de comunicação com arquivo: " + e.getMessage());
        }
        return null;
    }
}