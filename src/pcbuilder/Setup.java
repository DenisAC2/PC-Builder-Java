package pcbuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Classe agregadora que representa um computador montado.
 * Contém a lógica de negócios principal do simulador, incluindo
 * cálculo financeiro, consumo energético e a validação estrita de 
 * compatibilidade entre as peças escolhidas.
 * @author Denis
 */
public class Setup implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nomeSetup;
    private Processador processador;
    private PlacaMae placaMae;
    private Fonte fonte;
    private List<MemoriaRam> memorias;
    private List<Armazenamento> armazenamentos;
    
    /**
     * @brief Inicializa um novo setup vazio, instanciando as listas de expansão.
     * @param nomeSetup Nome de identificação (ex: "PC Gamer", "Servidor").
     */
    public Setup(String nomeSetup) {
        this.nomeSetup = nomeSetup;
        this.memorias = new ArrayList<>();
        this.armazenamentos = new ArrayList<>();
    }

    public String getNomeSetup() { return nomeSetup; }
    public void setNomeSetup(String nomeSetup) { this.nomeSetup = nomeSetup; }
    
    public Processador getProcessador() { return processador; }
    public PlacaMae getPlacaMae() { return placaMae; }
    public Fonte getFonte() { return fonte; }
    public List<MemoriaRam> getMemorias() { return memorias; }
    public List<Armazenamento> getArmazenamentos() { return armazenamentos; }

    public void setProcessador(Processador processador) { this.processador = processador; }
    public void setPlacaMae(PlacaMae placaMae) { this.placaMae = placaMae; }
    public void setFonte(Fonte fonte) { this.fonte = fonte; }
    
    public void adicionarMemoria(MemoriaRam memoria) { this.memorias.add(memoria); }
    public void limparMemorias() { this.memorias.clear(); }
    
    public void adicionarArmazenamento(Armazenamento armazenamento) { this.armazenamentos.add(armazenamento); }
    public void limparArmazenamentos() { this.armazenamentos.clear(); }

    /**
     * @brief Calcula o custo total do sistema.
     * Varre todos os componentes instanciados e soma os seus preços individuais.
     * @return O valor financeiro total da máquina.
     */
    public double calcularPrecoTotal() {
        double total = 0;
        if (processador != null) total += processador.getPreco();
        if (placaMae != null) total += placaMae.getPreco();
        if (fonte != null) total += fonte.getPreco();
        for (MemoriaRam ram : memorias) total += ram.getPreco();
        for (Armazenamento arm : armazenamentos) total += arm.getPreco();
        return total;
    }
    
    /**
     * @brief Calcula o consumo energético (TDP) total do sistema.
     * Soma o consumo em Watts da placa-mãe, CPU e unidades de expansão.
     * @return A quantidade de energia exigida pelas peças (em Watts).
     */
    public int calcularConsumoTotal() {
        int watts = 0;
        if (processador != null) watts += processador.getConsumoWatts();
        if (placaMae != null) watts += placaMae.getConsumoWatts();
        for (MemoriaRam ram : memorias) watts += ram.getConsumoWatts();
        for (Armazenamento arm : armazenamentos) watts += arm.getConsumoWatts();
        return watts;
    }
    
    /**
     * @brief Motor de validação de regras de montagem.
     * Verifica cruzamentos de dados: compatibilidade de Socket (CPU vs Placa-mãe),
     * padrões de memória (DDRx), limites físicos de slots (RAM e NVMe) e balanço energético.
     * @throws IncompatibilidadeException Se qualquer regra física ou lógica de montagem for violada.
     */
    public void verificarCompatibilidade() throws IncompatibilidadeException {
        List<String> erros = new ArrayList<>();

        if (processador != null && placaMae != null) {
            if (!processador.getSocket().equalsIgnoreCase(placaMae.getSocketSuportado())) {
                erros.add("Incompatibilidade de Socket: Processador (" + processador.getSocket() + ") e Placa-Mãe (" + placaMae.getSocketSuportado() + ").");
            }
        }

        if (placaMae != null) {
            for (MemoriaRam ram : memorias) {
                if (!ram.getTipoRam().equalsIgnoreCase(placaMae.getTipoRamSuportado())) {
                    erros.add("Incompatibilidade de RAM: " + ram.getModelo() + " é " + ram.getTipoRam() + ", mas a placa exige " + placaMae.getTipoRamSuportado() + ".");
                }
            }
            if (memorias.size() > placaMae.getSlotsRam()) {
                erros.add("Limite de RAM excedido: A placa possui apenas " + placaMae.getSlotsRam() + " slots, mas você adicionou " + memorias.size() + " pentes.");
            }
            if (armazenamentos.size() > placaMae.getSlotsNvme()) {
                 erros.add("Limite de Armazenamento excedido: A placa possui apenas " + placaMae.getSlotsNvme() + " slots NVMe.");
            }
        }

        if (fonte != null) {
            int consumoEstimado = calcularConsumoTotal();
            if (consumoEstimado > fonte.getPotenciaWatts()) {
                erros.add("Falta de Energia: O sistema consome " + consumoEstimado + "W, mas a fonte fornece " + fonte.getPotenciaWatts() + "W.");
            }
        }

        if (!erros.isEmpty()) {
            throw new IncompatibilidadeException("Foram encontrados problemas na montagem:\n- " + String.join("\n- ", erros));
        }
    }
    
    /**
     * @brief Gera o relatório final de especificações do sistema.
     * Concatena os detalhes polimórficos de cada peça instalada e exibe o fechamento de preço e consumo.
     * @return String formatada como relatório.
     */
    public String exibirResumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("====== RELATÓRIO: ").append(nomeSetup).append(" ======\n\n");
        if (processador != null) resumo.append(processador.exibirDetalhes()).append("\n");
        if (placaMae != null) resumo.append(placaMae.exibirDetalhes()).append("\n");
        if (fonte != null) resumo.append(fonte.exibirDetalhes()).append("\n");
        for (MemoriaRam ram : memorias) resumo.append(ram.exibirDetalhes()).append("\n");
        for (Armazenamento arm : armazenamentos) resumo.append(arm.exibirDetalhes()).append("\n");
        
        resumo.append("-----------------------------\n");
        resumo.append(String.format("PREÇO TOTAL: R$ %.2f\n", calcularPrecoTotal()));
        resumo.append("CONSUMO MÁXIMO ESTIMADO: ").append(calcularConsumoTotal()).append("W\n");
        resumo.append("=============================\n");
        return resumo.toString();
    }
    
    @Override
    public String toString() {
        return this.nomeSetup + " (R$ " + String.format("%.2f", calcularPrecoTotal()) + ")";
    }
}
