package pcbuilder;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @brief Classe principal e ponto de entrada (Main) do Simulador PC Builder.
 * Gerencia a execução do programa de forma contínua, controlando os menus de 
 * acesso e mantendo o registro de todos os computadores montados.
 * @author Denis
 */
public class PCBuilder {
    
    /** @brief Lista global em memória que armazena os Setups cadastrados. */
    private static List<Setup> setupsCadastrados = new ArrayList<>();
    
    /**
     * @brief Método principal de execução.
     * Inicializa a interface carregando dados do disco e gerencia o loop do menu principal.
     * Registra um Shutdown Hook para garantir a persistência segura no encerramento.
     * @param args Argumentos de linha de comando padrão do Java.
     */
    public static void main(String[] args) {
        try {
            setupsCadastrados = GerenciadorDados.carregarSetups();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Não foi possível carregar os setups salvos anteriormente.\nIniciando base vazia.\nMotivo: " + e.getMessage(),
                    "Aviso de Inicialização", JOptionPane.WARNING_MESSAGE);
            setupsCadastrados = new ArrayList<>();
        }

        //Garante que os dados sejam salvos mesmo se o programa der crash ou for fechado à força
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                GerenciadorDados.salvarSetups(setupsCadastrados);
                // Nota: Não usamos JOptionPane aqui pois a thread gráfica pode já estar morta
                System.out.println("[Sistema] Dados persistidos com sucesso no encerramento.");
            } catch (Exception e) {
                System.err.println("[Erro Crítico] Falha ao salvar no encerramento: " + e.getMessage());
            }
        }));

        boolean sistemaAberto = true;
        String[] menuPrincipal = {
            "1. Criar Novo Setup", 
            "2. Gerenciar/Editar Setup Existente", 
            "3. Sair"
        };

        while (sistemaAberto) {
            String escolha = (String) JOptionPane.showInputDialog(null, "PC Builder - Gerenciador", "Menu Principal",
                    JOptionPane.PLAIN_MESSAGE, null, menuPrincipal, menuPrincipal[0]);

            if (escolha == null || escolha.startsWith("3")) {
                sistemaAberto = false;
                JOptionPane.showMessageDialog(null, "Encerrando sistema. Até logo!");
                break;
            }

            if (escolha.startsWith("1")) {
                String nome = JOptionPane.showInputDialog("Dê um nome para este Setup (ex: PC Gamer, PC Escritório):");
                if (nome != null && !nome.trim().isEmpty()) {
                    Setup novoSetup = new Setup(nome);
                    setupsCadastrados.add(novoSetup);
                    abrirMenuEdicao(novoSetup);
                }
            } else if (escolha.startsWith("2")) {
                if (setupsCadastrados.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum setup cadastrado ainda.");
                } else {
                    Setup selecionado = (Setup) JOptionPane.showInputDialog(null, "Selecione o Setup para gerenciar:",
                            "Meus Setups", JOptionPane.PLAIN_MESSAGE, null, 
                            setupsCadastrados.toArray(), setupsCadastrados.get(0));
                    
                    if (selecionado != null) {
                        abrirMenuEdicao(selecionado);
                    }
                }
            }
        }
        System.exit(0);
    }
    
    /**
     * @brief Método utilitário interno para encapsular o salvamento de dados.
     * Evita duplicação de código e captura exceções de I/O de maneira gráfica para o usuário.
     */
    private static void salvarDadosSessao() {
        try {
            GerenciadorDados.salvarSetups(setupsCadastrados);
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(null, 
                    "Erro crítico ao persistir os dados no disco:\n" + e.getMessage(), 
                    "Erro de Gravação", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * @brief Menu dedicado à edição de um Setup específico.
     * Renderiza as opções de adição e modificação de hardwares (Processador, Placa-Mãe, 
     * Memórias, etc). Também serve de ponte para invocar as validações de compatibilidade
     * da classe Setup, exibindo os resultados gráficos na tela.
     * @param setup A instância de Setup selecionada para edição.
     */
    private static void abrirMenuEdicao(Setup setup) {
        boolean editando = true;
        String[] menuEdicao = {
            "1. Definir/Editar Processador",
            "2. Definir/Editar Placa-Mãe",
            "3. Adicionar Memória RAM",
            "4. Limpar todas as Memórias",
            "5. Adicionar Armazenamento",
            "6. Limpar todos os Armazenamentos",
            "7. Definir/Editar Fonte",
            "8. Definir/Editar Placa de Vídeo",
            "9. Verificar Compatibilidade e Resumo",
            "10. Exportar Relatório em TXT",
            "11. Voltar ao Menu Principal" 
        };

        while (editando) {
            String selecao = (String) JOptionPane.showInputDialog(null, 
                    "Editando: " + setup.getNomeSetup() + "\nO que deseja fazer?",
                    "Editor de Setup", JOptionPane.PLAIN_MESSAGE, null, menuEdicao, menuEdicao[0]);

            if (selecao == null || selecao.startsWith("10")) {
                editando = false;
                break;
            }

            try {
                String opcao = selecao.split("\\.")[0];
                
                switch (opcao) {
                    case "1":
                        setup.setProcessador(InterfaceUsuario.capturarProcessador(setup.getProcessador()));
                        JOptionPane.showMessageDialog(null, "Processador atualizado!");
                        break;
                    case "2":
                        setup.setPlacaMae(InterfaceUsuario.capturarPlacaMae(setup.getPlacaMae()));
                        JOptionPane.showMessageDialog(null, "Placa-Mãe atualizada!");
                        break;
                    case "3":
                        MemoriaRam ultimaRam = setup.getMemorias().isEmpty() ? null : setup.getMemorias().get(setup.getMemorias().size() - 1);
                        setup.adicionarMemoria(InterfaceUsuario.capturarMemoria(ultimaRam));
                        JOptionPane.showMessageDialog(null, "Memória RAM adicionada!");
                        break;
                    case "4":
                        setup.limparMemorias();
                        JOptionPane.showMessageDialog(null, "Lista de memórias esvaziada.");
                        break;
                    case "5":
                        Armazenamento ultimoArm = setup.getArmazenamentos().isEmpty() ? null : setup.getArmazenamentos().get(setup.getArmazenamentos().size() - 1);
                        setup.adicionarArmazenamento(InterfaceUsuario.capturarArmazenamento(ultimoArm));
                        JOptionPane.showMessageDialog(null, "Armazenamento adicionado!");
                        break;
                    case "6":
                        setup.limparArmazenamentos();
                        JOptionPane.showMessageDialog(null, "Lista de armazenamentos esvaziada.");
                        break;
                    case "7":
                        setup.setFonte(InterfaceUsuario.capturarFonte(setup.getFonte()));
                        JOptionPane.showMessageDialog(null, "Fonte atualizada!");
                        break;
                    case "8":
                        setup.setPlacaDeVideo(InterfaceUsuario.capturarPlacaDeVideo(setup.getPlacaDeVideo()));
                        JOptionPane.showMessageDialog(null, "Placa de Vídeo atualizada com sucesso!");
                        break;
                    case "9":
                        try {
                            setup.verificarCompatibilidade();
                            JOptionPane.showMessageDialog(null, "SUCESSO: Peças 100% compatíveis!", "Validação", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IncompatibilidadeException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Alerta de Incompatibilidade", JOptionPane.ERROR_MESSAGE);
                        }
                        JOptionPane.showMessageDialog(null, setup.exibirResumo(), "Resumo do Setup", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "10":
                        try {
                            String caminhoSalvo = ExportadorArquivo.exportarRelatorioTxt(setup);
                            JOptionPane.showMessageDialog(null, 
                                "Relatório salvo com sucesso!\nArquivo: " + caminhoSalvo, 
                                "Exportação Concluída", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } catch (java.io.IOException e) {
                            JOptionPane.showMessageDialog(null, 
                                "Erro ao gravar o arquivo de texto:\n" + e.getMessage(), 
                                "Erro de I/O", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
            } catch (CanceladoException ex){}
        }
    }
}