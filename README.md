# PC Builder Java 💻⚙️

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/Status-Concluido-brightgreen?style=for-the-badge)

Um sistema simulador de montagem de computadores focado em validar a compatibilidade de hardware. O projeto aplica fortemente os pilares da Programação Orientada a Objetos (POO), incluindo Herança, Polimorfismo e Tratamento de Exceções Customizadas.

## 🚀 Funcionalidades

- **Gerenciamento de Setups:** Criação e edição de múltiplos computadores simultaneamente.
- **Catálogo de Componentes:** Suporte para Processadores, Placas-Mãe, Memória RAM, Armazenamento e Fontes.
- **Validação de Compatibilidade:** O sistema impede gargalos e montagens irreais (ex: tentar colocar RAM DDR4 em uma placa-mãe DDR5, ou processadores em sockets incompatíveis).
- **Cálculo de Consumo:** Validação automática se a Fonte de Alimentação possui potência suficiente para o setup.

## 🛠️ Arquitetura do Sistema

- **Herança:** Classe abstrata `Componente` centralizando atributos comuns (marca, modelo, preço, consumo), estendida por peças específicas.
- **Exceptions Customizadas:** Uso de `IncompatibilidadeException` para barrar montagens erradas e `CanceladoException` para controle de fluxo da interface.
- **Factory Pattern (Simplificado):** A classe `GenSetup` atua como fábrica para instanciar os componentes.
- **Interface Gráfica:** Interações guiadas através da biblioteca `javax.swing.JOptionPane`.

## ⚙️ Como executar

1. Clone este repositório.
2. Compile e execute o arquivo principal `PCBuilder.java` através da sua IDE de preferência ou via terminal.
