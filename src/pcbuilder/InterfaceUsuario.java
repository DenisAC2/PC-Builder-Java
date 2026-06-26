package pcbuilder;
import javax.swing.JOptionPane;

/**
 * @brief Responsável pelas interações gráficas com o usuário (View).
 * Abstrai a lógica complexa de conversão de dados (parse) e tratamento de erros
 * das caixas de diálogo do JOptionPane, mantendo as classes de negócio limpas.
 * @author Denis
 */
public class InterfaceUsuario {
    
    /**
     * @brief Lê uma entrada de texto do usuário via caixa de diálogo.
     * Entra em loop e valida para garantir que o usuário não deixe o campo vazio.
     * @param msg A mensagem de instrução que aparecerá na tela.
     * @param padrao O valor padrão preenchido na caixa de texto (útil para edição).
     * @return String com o texto digitado e limpo (trim).
     * @throws CanceladoException Se o usuário fechar a janela ou clicar em Cancelar.
     */
    private static String lerString(String msg, String padrao) throws CanceladoException {
        while (true) {
            Object res = JOptionPane.showInputDialog(null, msg, "Entrada de Dados", JOptionPane.QUESTION_MESSAGE, null, null, padrao != null ? padrao : "");
            if (res == null) throw new CanceladoException();
            String texto = res.toString().trim();
            if (!texto.isEmpty()) return texto;
            JOptionPane.showMessageDialog(null, "O campo não pode ficar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * @brief Lê e valida uma entrada de número inteiro.
     * Captura exceptions de formatação (NumberFormatException) e avisa o utilizador caso introduza letras,
     * impedindo ativamente também a inserção de valores negativos.
     * @param msg A mensagem de instrução.
     * @param padrao Valor numérico padrão para preenchimento.
     * @return O número inteiro formatado, validado e não negativo.
     * @throws CanceladoException Se o utilizador cancelar a operação.
     */
    private static int lerInt(String msg, String padrao) throws CanceladoException {
        while (true) {
            Object res = JOptionPane.showInputDialog(null, msg, "Entrada de Dados", JOptionPane.QUESTION_MESSAGE, null, null, padrao != null ? padrao : "");
            if (res == null) throw new CanceladoException();
            try {
                int valor = Integer.parseInt(res.toString().trim());
                if (valor < 0) {
                    JOptionPane.showMessageDialog(null, "O valor introduzido não pode ser negativo.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                
                return valor;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor inválido. Certifique-se de introduzir apenas números inteiros.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * @brief Lê e valida uma entrada de número decimal (double).
     * Substitui automaticamente vírgulas por pontos para evitar falhas de formatação locais,
     * e impede ativamente a inserção de valores negativos.
     * @param msg A mensagem de instrução.
     * @param padrao Valor decimal padrão para exibição.
     * @return O número decimal validado e não negativo.
     * @throws CanceladoException Se o usuário cancelar a operação.
     */
    private static double lerDouble(String msg, String padrao) throws CanceladoException {
        while (true) {
            Object res = JOptionPane.showInputDialog(null, msg, "Entrada de Dados", JOptionPane.QUESTION_MESSAGE, null, null, padrao != null ? padrao : "");
            if (res == null) throw new CanceladoException();
            try {
                double valor = Double.parseDouble(res.toString().trim().replace(",", "."));
                if (valor < 0) {
                    JOptionPane.showMessageDialog(null, "O valor introduzido não pode ser negativo.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    continue; 
                }
                
                return valor;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor numérico inválido. Certifique-se de introduzir apenas números.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * @brief Captura uma resposta booleana (Sim/Não) do usuário.
     * @param msg A pergunta a ser feita.
     * @param padrao Valor reservado para implementações futuras de pré-seleção.
     * @return true se clicar em Sim, false se clicar em Não.
     * @throws CanceladoException Se fechar a janela do diálogo.
     */
    private static boolean lerBoolean(String msg, boolean padrao) throws CanceladoException {
        int escolha = JOptionPane.showConfirmDialog(null, msg, "Especificação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (escolha == JOptionPane.CLOSED_OPTION) throw new CanceladoException();
        return escolha == JOptionPane.YES_OPTION;
    }
    
    /**
     * @brief Abre o formulário guiado para criação ou edição de um Processador.
     * Tenta primeiramente buscar o modelo em um catálogo pré-escrito (Fast-path).
     * Se encontrado, importa os dados e encerra o fluxo; caso contrário, solicita 
     * a digitação manual. Protegido contra "Edit-Lock".
     * @param atual O objeto Processador existente (usado para autopreencher) ou null.
     * @return Uma nova instância de Processador configurada.
     * @throws CanceladoException Se o fluxo de criação for interrompido.
     */
    public static Processador capturarProcessador(Processador atual) throws CanceladoException {
        String mod = atual != null ? atual.getModelo() : "";
        String modelo = lerString("Digite o Modelo do Processador (ex: Ryzen 5 8600G):", mod);
        
        boolean isEditandoMesmoModelo = (atual != null && atual.getModelo().equalsIgnoreCase(modelo));
        
        if (!isEditandoMesmoModelo) {
            CatalogoProcessador catalogo = new CatalogoProcessador();
            Processador processadorCatalogo = catalogo.buscarPorModelo(modelo, 1);
            
            if (processadorCatalogo != null) {
                JOptionPane.showMessageDialog(null, 
                    "✅ Processador '" + processadorCatalogo.getModelo() + "' encontrado no catálogo!\n"
                  + "Todos os dados arquiteturais e elétricos foram preenchidos automaticamente.", 
                    "Catálogo Localizado", JOptionPane.INFORMATION_MESSAGE);
                
                return processadorCatalogo;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Modelo não encontrado no banco de dados pré-escrito.\n"
                  + "Por favor, insira as demais especificações da peça manualmente.", 
                    "Cadastro Manual Requerido", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        String m = atual != null ? atual.getMarca() : "";
        String p = atual != null ? String.valueOf(atual.getPreco()) : "";
        String w = atual != null ? String.valueOf(atual.getConsumoWatts()) : "";
        String s = atual != null ? atual.getSocket() : "";
        String c = atual != null ? String.valueOf(atual.getQuantidadeCores()) : "";
        String t = atual != null ? String.valueOf(atual.getQuantidadeThreads()) : "";
        String f = atual != null ? String.valueOf(atual.getFrequenciaGhz()) : "";
        boolean vi = atual != null ? atual.isPossuiVideoIntegrado() : false;
        boolean ecc = atual != null ? atual.isSuporteECC() : false;

        String marca = lerString("Marca (ex: AMD, Intel):", m);
        double preco = lerDouble("Preço (R$):", p);
        int consumo = lerInt("Consumo Estimado (Watts):", w);
        String socket = lerString("Socket (ex: AM5, LGA1700):", s);
        int cores = lerInt("Quantidade de Cores:", c);
        int threads = lerInt("Quantidade de Threads:", t);
        double freq = lerDouble("Frequência Base (GHz):", f);
        boolean video = lerBoolean("Possui vídeo integrado?", vi);
        boolean suporteEcc = lerBoolean("Suporta memória ECC?", ecc);

        return GenSetup.criarProcessador(marca, modelo, preco, consumo, socket, cores, threads, freq, video, suporteEcc);
    }
    
    /**
     * @brief Abre o formulário guiado para criação ou edição de uma Placa-Mãe.
     * Busca dados base no CatalogoPlacaMae, impedindo gargalos de digitação manual
     * e mantendo a segurança na edição com o Edit-Lock.
     * @param atual Objeto existente para autopreenchimento, ou null.
     * @return Nova instância de PlacaMae configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static PlacaMae capturarPlacaMae(PlacaMae atual) throws CanceladoException {
        String mod = atual != null ? atual.getModelo() : "";
        String modelo = lerString("Digite o Modelo da Placa-Mãe (ex: B650M-AYW WIFI):", mod);

        boolean isEditandoMesmoModelo = (atual != null && atual.getModelo().equalsIgnoreCase(modelo));

        if (!isEditandoMesmoModelo) {
            CatalogoPlacaMae catalogo = new CatalogoPlacaMae();
            PlacaMae placaCatalogo = catalogo.buscarPorModelo(modelo, 1);

            if (placaCatalogo != null) {
                JOptionPane.showMessageDialog(null, 
                    "✅ Placa-Mãe '" + placaCatalogo.getModelo() + "' encontrada no catálogo!\n"
                  + "Limites de expansão e dados do chipset preenchidos automaticamente.", 
                    "Catálogo Localizado", JOptionPane.INFORMATION_MESSAGE);
                
                return placaCatalogo;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Modelo não encontrado no banco de dados pré-escrito.\n"
                  + "Por favor, insira as demais especificações da peça manualmente.", 
                    "Cadastro Manual Requerido", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        String marca = lerString("Marca da Placa-Mãe:", atual != null ? atual.getMarca() : "");
        double preco = lerDouble("Preço (R$):", atual != null ? String.valueOf(atual.getPreco()) : "");
        int consumo = lerInt("Consumo Próprio (Watts):", atual != null ? String.valueOf(atual.getConsumoWatts()) : "");
        String chipset = lerString("Chipset (ex: B650):", atual != null ? atual.getChipset() : "");
        String socket = lerString("Socket Suportado (ex: AM5):", atual != null ? atual.getSocketSuportado() : "");
        String tipoRam = lerString("Tipo de RAM suportado (ex: DDR5):", atual != null ? atual.getTipoRamSuportado() : "");
        int slotsRam = lerInt("Quantidade de slots de RAM:", atual != null ? String.valueOf(atual.getSlotsRam()) : "");
        int slotsNvme = lerInt("Quantidade de slots M.2 NVMe:", atual != null ? String.valueOf(atual.getSlotsNvme()) : "");
        String formato = lerString("Formato (ex: Micro-ATX):", atual != null ? atual.getFormato() : "");

        return GenSetup.criarPlacaMae(marca, modelo, preco, consumo, chipset, socket, tipoRam, slotsRam, slotsNvme, formato);
    }
    
    /**
     * @brief Abre o formulário guiado para criação ou adição de Memória RAM.
     * Procura prioritariamente no catálogo para permitir adições rápidas e consecutivas.
     * @param atual Objeto existente (último adicionado) para sugerir valores por defeito.
     * @return Nova instância de MemoriaRam configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static MemoriaRam capturarMemoria(MemoriaRam atual) throws CanceladoException {
        String mod = atual != null ? atual.getModelo() : "";
        String modelo = lerString("Digite o Modelo da Memória RAM:", mod);
        
        CatalogoMemoria catalogo = new CatalogoMemoria();
        MemoriaRam memoriaCatalogo = catalogo.buscarPorModelo(modelo, 1);

        if (memoriaCatalogo != null) {
            JOptionPane.showMessageDialog(null, 
                "✅ Memória RAM '" + memoriaCatalogo.getModelo() + "' encontrada no catálogo!\n"
              + "Frequência e capacidade preenchidas automaticamente.", 
                "Catálogo Localizado", JOptionPane.INFORMATION_MESSAGE);
            return memoriaCatalogo;
        } else {
            // Mensagem opcional de aviso removida ou mantida caso queiras alertar a ausência
            System.out.println("[Aviso] Modelo não encontrado no catálogo. Seguindo para fluxo manual.");
        }
        
        boolean usarDadosAtuais = (atual != null && atual.getModelo().equalsIgnoreCase(modelo));
        
        String marca = lerString("Marca da Memória RAM:", usarDadosAtuais ? atual.getMarca() : "");
        double preco = lerDouble("Preço (R$):", usarDadosAtuais ? String.valueOf(atual.getPreco()) : "");
        int consumo = lerInt("Consumo (Watts):", usarDadosAtuais ? String.valueOf(atual.getConsumoWatts()) : "");
        String tipoRam = lerString("Tipo da Memória (ex: DDR5):", usarDadosAtuais ? atual.getTipoRam() : "");
        int capacidade = lerInt("Capacidade (GB):", usarDadosAtuais ? String.valueOf(atual.getCapacidadeGB()) : "");
        int freq = lerInt("Frequência (MHz):", usarDadosAtuais ? String.valueOf(atual.getFrequenciaMhz()) : "");
        boolean ecc = lerBoolean("Possui tecnologia ECC?", usarDadosAtuais ? atual.isSuporteECC() : false);

        return GenSetup.criarMemoria(marca, modelo, preco, consumo, tipoRam, capacidade, freq, ecc);
    }
    
    /**
     * @brief Abre o formulário guiado para criação ou adição de Armazenamento.
     * Procura prioritariamente no catálogo para agilizar a montagem de arranjos com múltiplos discos.
     * @param atual Objeto existente (último adicionado) para sugerir valores por defeito.
     * @return Nova instância de Armazenamento configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static Armazenamento capturarArmazenamento(Armazenamento atual) throws CanceladoException {
        String mod = atual != null ? atual.getModelo() : "";
        String modelo = lerString("Digite o Modelo do Armazenamento:", mod);
        
        CatalogoArmazenamento catalogo = new CatalogoArmazenamento();
        Armazenamento armCatalogo = catalogo.buscarPorModelo(modelo, 1);

        if (armCatalogo != null) {
            JOptionPane.showMessageDialog(null, 
                "✅ Armazenamento '" + armCatalogo.getModelo() + "' encontrado no catálogo!\n"
              + "Velocidade de leitura e tipo preenchidos automaticamente.", 
                "Catálogo Localizado", JOptionPane.INFORMATION_MESSAGE);
            return armCatalogo;
        }
        
        boolean usarDadosAtuais = (atual != null && atual.getModelo().equalsIgnoreCase(modelo));
        
        String marca = lerString("Marca do Armazenamento:", usarDadosAtuais ? atual.getMarca() : "");
        double preco = lerDouble("Preço (R$):", usarDadosAtuais ? String.valueOf(atual.getPreco()) : "");
        int consumo = lerInt("Consumo (Watts):", usarDadosAtuais ? String.valueOf(atual.getConsumoWatts()) : "");
        String tipo = lerString("Tipo (ex: NVMe M.2):", usarDadosAtuais ? atual.getTipo() : "");
        int capacidade = lerInt("Capacidade (GB):", usarDadosAtuais ? String.valueOf(atual.getCapacidadeGB()) : "");
        int velocidade = lerInt("Velocidade (MB/s):", usarDadosAtuais ? String.valueOf(atual.getVelocidade()) : "");
        String pcie = lerString("Interface de Conexão (ex: PCIe 4.0):", usarDadosAtuais ? atual.getConexaoPCIE() : "");

        return GenSetup.criarArmazenamento(marca, modelo, preco, consumo, tipo, capacidade, velocidade, pcie);
    }
    
    /**
     * @brief Abre o formulário guiado para criação ou edição de uma Fonte de Alimentação.
     * Busca dados no catálogo para preenchimento rápido (Fast-path).
     * @param atual Objeto existente para autopreenchimento.
     * @return Nova instância de Fonte configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static Fonte capturarFonte(Fonte atual) throws CanceladoException {
        String mod = atual != null ? atual.getModelo() : "";
        String modelo = lerString("Digite o Modelo da Fonte:", mod);
        
        boolean isEditandoMesmoModelo = (atual != null && atual.getModelo().equalsIgnoreCase(modelo));

        if (!isEditandoMesmoModelo) {
            CatalogoFonte catalogo = new CatalogoFonte();
            Fonte fonteCatalogo = catalogo.buscarPorModelo(modelo, 1);

            if (fonteCatalogo != null) {
                JOptionPane.showMessageDialog(null, 
                    "✅ Fonte '" + fonteCatalogo.getModelo() + "' encontrada no catálogo!\n"
                  + "Potência máxima e certificação preenchidas automaticamente.", 
                    "Catálogo Localizado", JOptionPane.INFORMATION_MESSAGE);
                return fonteCatalogo;
            } else {
                JOptionPane.showMessageDialog(null, "Modelo não encontrado no banco de dados. Insira os dados manualmente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        String marca = lerString("Marca da Fonte:", atual != null ? atual.getMarca() : "");
        double preco = lerDouble("Preço (R$):", atual != null ? String.valueOf(atual.getPreco()) : "");
        int potencia = lerInt("Potência Total (Watts):", atual != null ? String.valueOf(atual.getPotenciaWatts()) : "");
        String certificado = lerString("Certificação (ex: 80 Plus Bronze):", atual != null ? atual.getCertificado80Plus() : "");
        boolean modular = lerBoolean("A fonte é Full Modular?", atual != null ? atual.isFullModular() : false);

        return GenSetup.criarFonte(marca, modelo, preco, potencia, certificado, modular);
    }
    
    /**
     * @brief Abre o formulário guiado para definição ou edição de uma Placa de Vídeo.
     * Procura prioritariamente no catálogo para preenchimento rápido (Fast-path).
     * @param atual Objeto existente para sugerir valores por defeito, ou null.
     * @return Nova instância de PlacaDeVideo configurada.
     * @throws CanceladoException Se o fluxo for interrompido pelo utilizador.
     */
    public static PlacaDeVideo capturarPlacaDeVideo(PlacaDeVideo atual) throws CanceladoException {
        String mod = atual != null ? atual.getModelo() : "";
        String modelo = lerString("Digite o Modelo da Placa de Vídeo (ex: RTX 4060 ou RX 7600):", mod);
        
        CatalogoPlacaDeVideo catalogo = new CatalogoPlacaDeVideo();
        PlacaDeVideo gpuCatalogo = catalogo.buscarPorModelo(modelo, 1);

        if (gpuCatalogo != null) {
            JOptionPane.showMessageDialog(null, 
                "✅ Placa de Vídeo '" + gpuCatalogo.getModelo() + "' encontrada no catálogo!\n"
              + "VRAM, consumo e interface preenchidos automaticamente.", 
                "Catálogo Localizado", JOptionPane.INFORMATION_MESSAGE);
            return gpuCatalogo;
        } else {
            JOptionPane.showMessageDialog(null, 
                "Modelo não encontrado no banco de dados pré-escrito.\n"
              + "Por favor, insira as restantes especificações da peça manualmente.", 
                "Registo Manual Requerido", JOptionPane.WARNING_MESSAGE);
        }
        
        boolean usarDadosAtuais = (atual != null && atual.getModelo().equalsIgnoreCase(modelo));

        String marca = lerString("Marca da Placa de Vídeo (ex: NVIDIA, AMD, Intel):", usarDadosAtuais ? atual.getMarca() : "");
        double preco = lerDouble("Preço (R$):", usarDadosAtuais ? String.valueOf(atual.getPreco()) : "");
        int consumo = lerInt("Consumo Estimado (Watts):", usarDadosAtuais ? String.valueOf(atual.getConsumoWatts()) : "");
        int vram = lerInt("Capacidade de VRAM (GB):", usarDadosAtuais ? String.valueOf(atual.getCapacidadeVramGB()) : "");
        String tipoVram = lerString("Tipo de Memória VRAM (ex: GDDR6):", usarDadosAtuais ? atual.getTipoVram() : "");
        String pcie = lerString("Interface de Conexão (ex: PCIe 4.0 x16):", usarDadosAtuais ? atual.getConexaoPCIE() : "");

        return GenSetup.criarPlacaDeVideo(marca, modelo, preco, consumo, vram, tipoVram, pcie);
    }
}
