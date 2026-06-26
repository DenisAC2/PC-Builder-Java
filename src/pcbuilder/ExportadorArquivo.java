package pcbuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @brief Classe utilitária responsável pela exportação de dados e manipulação de arquivos (I/O).
 * Isola a lógica de gravação no sistema operacional do resto da aplicação, 
 * evitando vazamento de regras de infraestrutura nas classes de domínio.
 * @author Denis
 */
public class ExportadorArquivo {

    /**
     * @brief Salva o resumo técnico do Setup em um arquivo de texto (.txt).
     * Inclui proteção contra caracteres inválidos, limite de nome do SO, 
     * prevenção de sobrescrita e validação automática de compatibilidade.
     * @param setup O objeto Setup contendo as peças que serão exportadas.
     * @return O nome (caminho) do arquivo gerado de forma única.
     * @throws IOException Caso o sistema negue permissão de escrita.
     */
    public static String exportarRelatorioTxt(Setup setup) throws IOException {
        String nomeBase = setup.getNomeSetup().replaceAll("[^a-zA-Z0-9_-]", "_");
        
        if (nomeBase.length() > 30) {
            nomeBase = nomeBase.substring(0, 30);
        }
        
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nomeArquivo = nomeBase + "_" + dataHora + ".txt";
        
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(setup.exibirResumo());
        
        try {
            setup.verificarCompatibilidade();
            conteudo.append("\n[STATUS DE MONTAGEM]: ✅ Sistema 100% Compatível e Aprovado.\n");
        } catch (IncompatibilidadeException e) {
            conteudo.append("\n[STATUS DE MONTAGEM]: ❌ ATENÇÃO! Incompatibilidades detectadas:\n");
            conteudo.append(e.getMessage()).append("\n");
        }
        
        Files.writeString(Path.of(nomeArquivo), conteudo.toString());
        
        return nomeArquivo;
    }
}