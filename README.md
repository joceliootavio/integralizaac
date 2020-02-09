IntegralizaAC
==================
O IntegralizaAC é o sistema de gestão e controle de atividades complementares.

Ambiente de desenvolvimento
---------------------------

O projeto trabalha e depende de várias ferramentas para rodar. Entre elas podemos citar:

* Java 8 (JDK);
* Eclipse IDE for Java EE Developers (Kepler ou superior) / IntelliJ IDEA;
* Apache Tomcat 8.5.50;
* Bancos de dados PostgreSQL;
* Maven;
* Git;
* Docker;
* Docker-compose;

O ideal é que o desenvolvimento ocorra em ambiente unix-like. Nós aconselhamos o uso de Ubuntu. Todas as ferramentas e tecnologias serão instaladas em ambiente **Ubuntu**, de preferência a versão Ubuntu Desktop 18.04.3 LTS.

Pacotes do Ubuntu
-----------------

Para ter certeza que você instalará os pacotes mais recentes do Ubuntu, por favor, execute o comando abaixo:

```SHELL
sudo apt-get update -y
```

Instalando Git
-------------- 

Git é um **controle de versão distribuído** e um **sistema de gerenciamento de código fonte** com enfase em velocidade. Inicialmente ele foi desenhado e desenvolvido pelo Linus Torvalds para o desenvolvimento do kernel do Linux, desde então ele tem se popularizado no mundo opensource e é utilizado em milhares de projetos.

Para instalar o Git no Ubuntu basta executar o comando abaixo:

```SHELL
sudo apt-get install git
```

### Configurando sua identidade no Git

A primeira coisa a se fazer logo após a instalação do Git é configurar seu **nome de usuário** e **endereço de e-mail**. Isto é importante pois cada commit no Git usa esta informação e ela é imutável a partir daí.

Se você não fornecer estas informações, o Git utilizará as informações locais da sua máquina - o que normalmente não é o que você quer.

Execute os comandos abaixo para configurar seu usuário e e-mail:

```SHELL
git config --global user.name "Your Name Here"
git config --global user.email "your_email@example.com"
```

### Gerando sua SSH Key

Para se conectar à um servidor Git de forma segura você precisa gerar uma SSH Key (chave pública e privada). Esta chave deverá ser registrada no servidor Git para que seja possível ter acesso aos repositórios dos projetos.

- ATENÇÃO: Caso você já possua uma SSH Key configurada, você não precisa regera-la, mas somente registra-la no servidor Git.

Para registrar sua chave você precisa executar os comandos abaixo

```SHELL
ssh-keygen -t rsa -C "your_email@example.com"
```

* Tecle `<ENTER>` para todas as perguntas do comando acima - são 3 perguntas.

Mais informações sobre como gerar SSH Keys? Veja em ["Generating SSH Keys"](https://help.github.com/articles/generating-ssh-keys).

Caso precise de mais detalhes sobre o Git, como utiliza-lo e seus comandos básicos, por favor, leia este [guia prático e sem complicação](http://rogerdudler.github.io/git-guide/index.pt_BR.html).

Caso tenha 3h sobrando no seu dia, você pode assistir o [screencast gratuito do AkitaOnRails](http://blip.tv/akitaonrails/screencast-come-ando-com-git-6074964).

**ATENÇÃO**: Após gerar sua chave pública e privada, guarde-a em um lugar seguro para uso posterior - ela funciona como seu CPF no mundo unix. Aconselhamos guarda-la nas nuvens, como Dropbox, por exemplo.

Instalação do Java (Não necessária em produção pois já está contida no container do Tomcat)
------------------

A VM é apenas uma especificação e devemos baixar uma implementação. Há muitas empresas que implementam uma VM, como a própria Oracle, a IBM, a Apache e outros.

No projeto trabalharemos com a **OpenJDK - Java 8**. O ideal é que pudessemos trabalhar com qualquer JVM.

Em teoria, utilizar qualquer uma delas não deveria trazer diferenças para o desenvolvimento nem produção.

### Instalando o JDK Opensource

**IMPORTANTE**: Instale e utilize o **Java 8**.

Para o desenvolvimento em Java usaremos o JDK (Java Development Kit) opensource (openjdk). Precisamos instalá-lo primeiramente. Para isso, execute os seguintes comandos no terminal:

```SHELL
sudo apt-get update
sudo apt-get install openjdk-8-jdk
```

Uma instalação mais braçal, sem usar repositório, pode ser feita baixando o instalador no próprio [site da Oracle](http://www.oracle.com/technetwork/java/). É um `tar.gz` que possui um `.bin` que deve ser executado. Depois, é necessário apontar `JAVA_HOME` para esse diretório e adicionar `JAVA_HOME/bin` no seu `PATH`.

### Mais de uma VM instalada

Se você já tiver outras versões instaladas no seu Ubuntu, você pode utilizar o comando abaixo para escolher entre elas:

```SHELL
sudo update-alternatives --config java
```

Instalação do Eclipse
---------------------

A IDE Eclipse está disponível em vários sabores. Como iremos desenvolver aplicações web com Java EE, nós precisamos de uma versão com suporte a Java EE. Esta versão da IDE, entre vários plugins, possui o WTP (Web Tools Platform) que facilitará o desenvolvimento de aplicações web.

Até esta data a última versão do Eclipse é **Eclipse Kepler (4.3.1)**.

Para baixar e instalar o Eclipse siga os passos abaixo:

1. Acesse a [página de downloads do Eclipse](http://www.eclipse.org/downloads);
2. Baixe a versão **Eclipse IDE for Java EE Developers** de acordo com seu OS (32 ou 64 bits);
3. Extraia o zip e mova o diretório para seu diretório `HOME`, algo como `/home/<usuario>/java/eclipse`;


Instalação do Tomcat (somente para desenvolvimento)
--------------------

O servidor de aplicação Tomcat está disponível em várias realeses, cada versão suporta uma diferente versão da especificação JSP/Servlet. Como iremos trabalhar com Servlet 2.5, nós precisamos baixar a versão **Tomcat 8.5**.

Até esta data a última versão do Tomcat é **Apache Tomcat 8.5.50**.

Para baixar e instalar, basta seguir os passos abaixo:

1. Acesse a [página de downloads do Tomcat 8.5](http://tomcat.apache.org/download-85.cgi);
2. Desça a barra de rolagem até "Binary Distributions > Core" e baixe o `.zip` ou `.tar.gz`;
3. Extraia o zip e mova o diretório para seu diretório `HOME`, algo como `/home/<usuario>/java/apache-tomcat-8.5.50`;


Integrando o Tomcat no Eclipse
------------------------------

Sempre que estamos trabalhando com o desenvolvimento de uma aplicação queremos ser o mais produtivos possível, e não é diferente com uma aplicação web. Uma das formas de aumentar a produtividade do desenvolvedor é utilizar uma ferramenta que auxilie no desenvolvimento e o torne mais ágil, no nosso caso, uma IDE.

Para integrar o Tomcat no Eclipse precisaremos do plugin WTP, na qual já vem por padrão com o Eclipse que baixamos e instalamos anteriormente, o **Eclipse IDE for Java EE Developers**.

O **WTP**, Web Tools Platform, é um conjunto de plugins para o Eclipse que auxilia o desenvolvimento de
aplicações Java EE, em particular, de aplicações Web. Contém desde editores para JSP, CSS, JS e HTML até perspectivas e jeitos de rodar servidores de dentro do Eclipse.

Não esquecer de mudar os metadados do tomcat para a própria instalação do tomcat, caso contrário o eclipse
irá criar uma nova pasta dentro dos metadados do workspace e esta pasta não terá as configurações necessárias. 

### Configurando o Tomcat no WTP

Vamos primeiro configurar no WTP o servidor Tomcat que acabamos de descompactar.

1. Mude a perspectiva do Eclipse para **Java** (e não Java EE, por enquanto). Para isso, vá no canto direito
superior e selecione **Java**;
2. Abra a View de **Servers** na perspectiva atual. Aperte **Ctrl + 3** e digite **Servers**: [imagem]
3. Clique com o botão direito dentro da aba Servers e vá em **New > Server**: [imagem]
4. Selecione o **Apache Tomcat 6.0** e clique em **Next**: [imagem]
5. Na próxima tela, selecione o diretório onde você descompactou o Tomcat e clique em **Finish**: [imagem]

### Instalando o Docker

Executar os seguintes comandos:

```SHELL
sudo apt -f -y install
sudo apt -y install docker.io
sudo update-rc.d docker defaults
```
 
Deve ser instalado também o pacote do docker-compose:

```SHELL
sudo apt install docker-compose
```

Configuração do Projeto
-----------------------

O projeto é configurado através da ferramenta **Maven**. Ela se encarregará de baixar
todas as dependências (libs) e configurar o projeto no Eclipse, tudo através de linha de comando.

### Preparando os bancos de dados

Caso queira utilizar o Banco de Dados rodando em um container Docker, o projeto já possui um script yml para isso. Execute o comando abaixo na raiz do projeto:
```SHELL
sudo docker-compose up --build
```
O comando acima irá construir e iniciar 2 containers docker Postgres e Tomcat, esse último so é necessário para implantação em produção, em desenvolvimento deve se configurar na própria IDE.

_PS: Caso já possua algum desses programas instalados e executando na sua máquina, poderá haver conflito de portas. Edite o arquivo docker-compose.yml para poder resolver._

### Configurando o projeto

Para configurar o projeto siga os passos:

1. Clone o projeto do Git
    ```SHELL
    git clone git@github.com:joceliootavio/integralizaac.git
    ```

2. Importe o projeto no Eclipse

3. Rode o build completo da aplicação. Para isso, faça:

    ```SHELL
    mvn install
    ``` 
