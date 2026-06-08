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
     * Captura exceptions de formatação (NumberFormatException) e avisa o usuário caso digite letras.
     * @param msg A mensagem de instrução.
     * @param padrao Valor numérico padrão para preenchimento.
     * @return O número inteiro formatado validado.
     * @throws CanceladoException Se o usuário cancelar a operação.
     */
    private static int lerInt(String msg, String padrao) throws CanceladoException {
        while (true) {
            Object res = JOptionPane.showInputDialog(null, msg, "Entrada de Dados", JOptionPane.QUESTION_MESSAGE, null, null, padrao != null ? padrao : "");
            if (res == null) throw new CanceladoException();
            try {
                return Integer.parseInt(res.toString().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor inválido. Digite apenas números inteiros.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * @brief Lê e valida uma entrada de número decimal (double).
     * Substitui automaticamente vírgulas por pontos para evitar falhas de formatação locais.
     * @param msg A mensagem de instrução.
     * @param padrao Valor decimal padrão para exibição.
     * @return O número decimal validado.
     * @throws CanceladoException Se o usuário cancelar a operação.
     */
    private static double lerDouble(String msg, String padrao) throws CanceladoException {
        while (true) {
            Object res = JOptionPane.showInputDialog(null, msg, "Entrada de Dados", JOptionPane.QUESTION_MESSAGE, null, null, padrao != null ? padrao : "");
            if (res == null) throw new CanceladoException();
            try {
                return Double.parseDouble(res.toString().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor numérico inválido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
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
     * @param atual O objeto Processador existente (usado para autopreencher os campos em caso de edição) ou null para nova criação.
     * @return Uma nova instância de Processador configurada através do GenSetup.
     * @throws CanceladoException Se o fluxo de criação for interrompido.
     */
    public static Processador capturarProcessador(Processador atual) throws CanceladoException {
        String m = atual != null ? atual.getMarca() : "";
        String mod = atual != null ? atual.getModelo() : "";
        String p = atual != null ? String.valueOf(atual.getPreco()) : "";
        String w = atual != null ? String.valueOf(atual.getConsumoWatts()) : "";
        String s = atual != null ? atual.getSocket() : "";
        String c = atual != null ? String.valueOf(atual.getQuantidadeCores()) : "";
        String t = atual != null ? String.valueOf(atual.getQuantidadeThreads()) : "";
        String f = atual != null ? String.valueOf(atual.getFrequenciaGhz()) : "";
        boolean vi = atual != null ? atual.isPossuiVideoIntegrado() : false;
        boolean ecc = atual != null ? atual.isSuporteECC() : false;

        String marca = lerString("Marca (ex: AMD, Intel):", m);
        String modelo = lerString("Modelo (ex: Ryzen 5 8600G):", mod);
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
     * @param atual Objeto existente para autopreenchimento, ou null.
     * @return Nova instância de PlacaMae configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static PlacaMae capturarPlacaMae(PlacaMae atual) throws CanceladoException {
        String marca = lerString("Marca da Placa-Mãe:", atual != null ? atual.getMarca() : "");
        String modelo = lerString("Modelo (ex: B650M-AYW):", atual != null ? atual.getModelo() : "");
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
     * @param atual Objeto existente para autopreenchimento (útil para adicionar pentes idênticos).
     * @return Nova instância de MemoriaRam configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static MemoriaRam capturarMemoria(MemoriaRam atual) throws CanceladoException {
        String marca = lerString("Marca da Memória RAM:", atual != null ? atual.getMarca() : "");
        String modelo = lerString("Modelo:", atual != null ? atual.getModelo() : "");
        double preco = lerDouble("Preço (R$):", atual != null ? String.valueOf(atual.getPreco()) : "");
        int consumo = lerInt("Consumo (Watts):", atual != null ? String.valueOf(atual.getConsumoWatts()) : "");
        String tipoRam = lerString("Tipo da Memória (ex: DDR5):", atual != null ? atual.getTipoRam() : "");
        int capacidade = lerInt("Capacidade (GB):", atual != null ? String.valueOf(atual.getCapacidadeGB()) : "");
        int freq = lerInt("Frequência (MHz):", atual != null ? String.valueOf(atual.getFrequenciaMhz()) : "");
        boolean ecc = lerBoolean("Possui tecnologia ECC?", atual != null ? atual.isSuporteECC() : false);

        return GenSetup.criarMemoria(marca, modelo, preco, consumo, tipoRam, capacidade, freq, ecc);
    }
    
    /**
     * @brief Abre o formulário guiado para criação ou adição de Armazenamento.
     * @param atual Objeto existente para autopreenchimento.
     * @return Nova instância de Armazenamento configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static Armazenamento capturarArmazenamento(Armazenamento atual) throws CanceladoException {
        String marca = lerString("Marca do Armazenamento:", atual != null ? atual.getMarca() : "");
        String modelo = lerString("Modelo:", atual != null ? atual.getModelo() : "");
        double preco = lerDouble("Preço (R$):", atual != null ? String.valueOf(atual.getPreco()) : "");
        int consumo = lerInt("Consumo (Watts):", atual != null ? String.valueOf(atual.getConsumoWatts()) : "");
        String tipo = lerString("Tipo (ex: NVMe M.2):", atual != null ? atual.getTipo() : "");
        int capacidade = lerInt("Capacidade (GB):", atual != null ? String.valueOf(atual.getCapacidadeGB()) : "");
        int velocidade = lerInt("Velocidade (MB/s):", atual != null ? String.valueOf(atual.getVelocidade()) : "");
        String pcie = lerString("Interface de Conexão (ex: PCIe 4.0):", atual != null ? atual.getConexaoPCIE() : "");

        return GenSetup.criarArmazenamento(marca, modelo, preco, consumo, tipo, capacidade, velocidade, pcie);
    }
    
    /**
     * @brief Abre o formulário guiado para criação ou edição de uma Fonte de Alimentação.
     * @param atual Objeto existente para autopreenchimento.
     * @return Nova instância de Fonte configurada.
     * @throws CanceladoException Se o fluxo for interrompido.
     */
    public static Fonte capturarFonte(Fonte atual) throws CanceladoException {
        String marca = lerString("Marca da Fonte:", atual != null ? atual.getMarca() : "");
        String modelo = lerString("Modelo:", atual != null ? atual.getModelo() : "");
        double preco = lerDouble("Preço (R$):", atual != null ? String.valueOf(atual.getPreco()) : "");
        int potencia = lerInt("Potência Total (Watts):", atual != null ? String.valueOf(atual.getPotenciaWatts()) : "");
        String certificado = lerString("Certificação (ex: 80 Plus Bronze):", atual != null ? atual.getCertificado80Plus() : "");
        boolean modular = lerBoolean("A fonte é Full Modular?", atual != null ? atual.isFullModular() : false);

        return GenSetup.criarFonte(marca, modelo, preco, potencia, certificado, modular);
    }
}
