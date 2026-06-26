# PC Builder Java 💻⚙️

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/Status-Concluido-brightgreen?style=for-the-badge)
![Documentação](https://img.shields.io/badge/Docs-Doxygen-blue)

Um sistema simulador de montagem de computadores focado em validar a compatibilidade de hardware. O projeto aplica fortemente os pilares da Engenharia de Software e Programação Orientada a Objetos (POO), incluindo Padrões de Projeto, Herança, Polimorfismo e Tratamento de Exceções Customizadas.

## 🚀 Funcionalidades

- **Gerenciamento de Setups:** Criação, edição e exclusão de múltiplos computadores simultaneamente.
- **Catálogo Dinâmico (CSV):** O sistema consome dados de arquivos `.csv` para popular os catálogos de Processadores, Placas-Mãe, Memória RAM, Armazenamento e Fontes.
- **Validação de Compatibilidade Estrita:** Impede gargalos e montagens irreais (ex: tentar colocar RAM DDR4 em uma placa-mãe DDR5, limites físicos de slots, ou processadores em sockets incompatíveis).
- **Cálculo de Consumo Elétrico:** Validação automática se a Fonte de Alimentação possui potência suficiente para o setup montado (TDP).
- **Exportação de Relatórios:** Geração de arquivos `.txt` com o resumo e o status de compatibilidade da máquina.
- **Persistência de Dados Segura:** Salvamento automático do estado da aplicação em arquivos binários (`.dat`), garantindo que os setups criados não sejam perdidos ao fechar o programa.

## 🛠️ Arquitetura e Padrões de Projeto

- **Herança e Polimorfismo:** Classe abstrata `Componente` centralizando atributos comuns, estendida por peças específicas. O método `exibirDetalhes()` opera de forma polimórfica.
- **Template Method & Generics:** A classe `LeitorCsvBase<T>` dita o esqueleto do algoritmo de leitura de arquivos (DAO), delegando a instanciação dos objetos específicos para as classes filhas.
- **Factory Pattern (Simplificado):** A classe `GenSetup` atua como fábrica para instanciar os componentes.
- **Single Responsibility Principle (SRP):** Classes como `GerenciadorDados` (I/O Binário), `ExportadorArquivo` (I/O Texto) e `InterfaceUsuario` (View) isolam as responsabilidades de infraestrutura e interface das regras de negócio.
- **Exceptions Customizadas:** Uso de `IncompatibilidadeException` para barrar montagens erradas e `CanceladoException` para controle de fluxo da interface gráfica (`JOptionPane`).

## 📚 Documentação (Doxygen)

O projeto possui documentação automatizada gerada via **Doxygen**. O pipeline de CI/CD está configurado com GitHub Actions para compilar a documentação a cada *push* na branch `main` e publicá-la automaticamente no GitHub Pages.

## ⚙️ Como executar

1. Clone este repositório.
2. Certifique-se de que os arquivos `catalogo_*.csv` estão no mesmo diretório de execução (eles serão gerados automaticamente na primeira execução caso não existam).
3. Compile e execute o arquivo principal `PCBuilder.java` através da sua IDE de preferência ou via terminal.
