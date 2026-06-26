package pcbuilder;

import java.io.Serializable;

/**
 * @brief Classe abstrata base para todos os hardwares do sistema.
 * Centraliza os atributos comuns como marca, modelo, preço e consumo elétrico,
 * garantindo o reaproveitamento de código através da Herança.
 * Implementa Serializable para permitir a persistência em disco de todas as subclasses.
 * @author Denis
 */
public abstract class Componente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String marca;
    private String modelo;
    private double preco;
    private int consumoWatts;
    
    public Componente(String marca, String modelo, double preco, int consumoWatts) {
        this.marca = marca;
        this.modelo = modelo;
        setPreco(preco);
        setConsumoWatts(consumoWatts);
    }
    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPreco() {
        return preco;
    }
    
    /** @brief Define o preço validando se não é um valor negativo. */
    public final void setPreco(double preco) {
        if(preco>=0){
            this.preco = preco;
        }
    }

    public int getConsumoWatts() {
        return consumoWatts;
    }
    
    /** @brief Define o consumo em Watts validando se não é negativo. */
    public final void setConsumoWatts(int consumoWatts) {
        if(consumoWatts>=0){
            this.consumoWatts = consumoWatts;
        }
    }
    
    /** @brief Método polimórfico para exibir as especificações de cada hardware. */
    abstract String exibirDetalhes();
}
