package pcbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Classe responsável pela persistência permanente de dados do simulador.
 * Gerencia a gravação e leitura da lista global de setups em arquivos binários,
 * garantindo o ciclo de vida dos dados entre diferentes sessões do programa.
 * Aplica o Princípio da Responsabilidade Única (SRP).
 * @author Denis
 */
public class GerenciadorDados {
    
    /** @brief Nome do arquivo binário gerado no diretório raiz do projeto. */
    private static final String NOME_ARQUIVO = "setups_cadastrados.dat";

    /**
     * @brief Grava a lista completa de setups em formato binário estável no disco.
     * Utiliza a técnica de encadeamento de streams de objetos do Java I/O.
     * @param setups Lista contendo os setups atuais da sessão que serão salvos.
     * @throws IOException Lançada caso ocorra falha de hardware ou bloqueio de escrita do SO.
     */
    public static void salvarSetups(List<Setup> setups) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(setups);
        }
    }

    /**
     * @brief Recupera a lista de setups previamente persistida em disco.
     * Caso o arquivo não exista (como na primeira execução do sistema), 
     * inicializa silenciosamente uma nova lista vazia, evitando falhas de inicialização.
     * @return List contendo as instâncias de Setup recriadas ou lista vazia.
     * @throws IOException Caso ocorra erro de leitura ou corrupção do arquivo.
     * @throws ClassNotFoundException Lançada se a JVM não conseguir mapear o tipo do objeto recuperado.
     */
    @SuppressWarnings("unchecked")
    public static List<Setup> carregarSetups() throws IOException, ClassNotFoundException {
        File arquivo = new File(NOME_ARQUIVO);
        
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Setup>) ois.readObject();
        }
    }
}