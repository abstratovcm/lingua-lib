# Estudo de Línguas

Este projeto tem como objetivo criar um site para o estudo de línguas. Até o momento, foi feito por uma única pessoa.

## Versões Usadas

- Java 11
- WildFly 28.0.1.Final
- MySQL 8.0.33

## Configuração e implementação
O projeto foi implementado e testado usando o WildFly 28.0.1.Final, que não está incluído no repositório. Você precisará instalar e configurar o WildFly separadamente. Note que a funcionalidade pode não ser garantida para outras versões do WildFly.

No meu ambiente, o WildFly foi instalado em `/opt/`. O diretório exato pode variar dependendo de sua configuração e preferências pessoais, mas aqui está o caminho do exemplo: `/opt/wildfly-28.0.1.Final`.

Para adicionar um usuário, você pode usar o seguinte comando:

~~~bash
/opt/wildfly-28.0.1.Final/bin/add-user.sh
~~~

Para este projeto, o superusuário é `abstrato` com a senha `s6qJ#9Mx$K2P`. No entanto, você é livre para escolher o nome de usuário e senha de sua preferência.

Acesse o projeto através do link: http://127.0.0.1:8080/

## Configuração do MySQL no WildFly

Para o MySQL, o nome do usuário e a senha também são `abstrato` e `s6qJ#9Mx$K2P` respectivamente. O nome do banco de dados utilizado é `language_study` e a versão do MySQL Connector é 8.0.33.

É necessário criar o banco de dados `language_study` e dar todas as permissões relacionadas ao `language_study` para o usuário `abstrato`. Você pode fazer isso com os seguintes comandos MySQL:

```sql
CREATE DATABASE language_study;
GRANT ALL PRIVILEGES ON language_study.* TO 'abstrato'@'localhost';
FLUSH PRIVILEGES;
```

A integração do WildFly com o MySQL envolveu os seguintes passos:

1. Baixar o `mysql-connector-j-8.0.33.jar`.
2. Criar o diretório `/opt/wildfly-28.0.1.Final/modules/system/layers/base/com/mysql/main` (você terá que criar as pastas mysql e main).
3. Colocar o `mysql-connector-j-8.0.33.jar` no diretório criado no passo anterior.
4. Criar um arquivo `module.xml` em `/opt/wildfly-28.0.1.Final/modules/system/layers/base/com/mysql/main` com o seguinte conteúdo:
    ~~~xml
    <?xml version="1.0" encoding="UTF-8"?>
    <module xmlns="urn:jboss:module:1.1" name="com.mysql">
        <resources>
            <resource-root path="mysql-connector-j-8.0.33.jar"/>
        </resources>
        <dependencies>
            <module name="javax.api"/>
            <module name="javax.transaction.api"/>
        </dependencies>
    </module>
    ~~~
5. Modificar o arquivo `/opt/wildfly-28.0.1.Final/standalone/configuration/standalone.xml`, adicionando as seguintes tags:
    ```xml
    <datasource jndi-name="java:/LanguageStudyDS" pool-name="LanguageStudyDS" enabled="true" use-java-context="true">
            <connection-url>jdbc:mysql://localhost:3306/language_study?useSSL=false</connection-url>
            <driver>mysql</driver>
            <security>
                <user-name>abstrato</user-name>
                <password>s6qJ#9Mx$K2P</password>
            </security>
    </datasource>
    ```
    ```xml
    <driver name="mysql" module="com.mysql">
        <driver-class>com.mysql.cj.jdbc.Driver</driver-class>
    </driver>
    ```

    Após essas modificações, o trecho dentro da tag `datasources` do arquivo standalone.xml deve se parecer com o seguinte:

    ```xml
    <datasources>
        <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
            <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=${wildfly.h2.compatibility.mode:REGULAR}</connection-url>
            <driver>h2</driver>
            <security>
                <user-name>sa</user-name>
                <password>sa</password>
            </security>
        </datasource>
        <datasource jndi-name="java:/LanguageStudyDS" pool-name="LanguageStudyDS" enabled="true" use-java-context="true">
            <connection-url>jdbc:mysql://localhost:3306/language_study?useSSL=false</connection-url>
            <driver>mysql</driver>
            <security>
                <user-name>abstrato</user-name>
                <password>s6qJ#9Mx$K2P</password>
            </security>
        </datasource>
        <drivers>
            <driver name="h2" module="com.h2database.h2">
                <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
            </driver>
            <driver name="mysql" module="com.mysql">
                <driver-class>com.mysql.cj.jdbc.Driver</driver-class>
            </driver>
        </drivers>
    </datasources>
    ```
Para verificar se o driver está funcionando corretamente, você deve seguir os seguintes passos:

1. Acesse o Administration Console do WildFly, na URL http://127.0.0.1:8080/.
2. Navegue até `Configuration` > `Subsystems` > `Datasources & Drivers` > `Datasources`.
3. Localize `LanguageStudyDS`, clique na seta ao lado de `View` e selecione `Test Connection`. 

Se a configuração estiver correta, a seguinte mensagem será exibida:

`Successfully tested connection for data source LanguageStudyDS.`

## Construção do Projeto

Este projeto usa tanto `Makefile` quanto `pom.xml` para gerenciar o ciclo de vida do aplicativo.

### Configuração do Java

O projeto é configurado para usar a versão 11 do Java, mais especificamente a `/usr/lib/jvm/java-1.11.0-openjdk-amd64`. A versão do Java pode ser alterada no arquivo `config.mk` se você estiver usando uma versão diferente.

```
JAVA_HOME := /caminho/para/o/seu/java
```

### Usando Maven com o Makefile

Além de gerenciar o servidor WildFly, o `Makefile` também foi configurado para trabalhar com o Maven. Qualquer comando do Maven pode ser executado através do `Makefile` ao prefixar o comando com `make mvn`. Por exemplo, para limpar o projeto e construir um pacote, você usaria:

~~~
make mvn clean package
~~~

Esta característica é especialmente útil para garantir que todo o projeto e até o servidor WildFly estejam funcionando com a versão correta do Java, pois isso pode variar entre projetos. Ao usar o `Makefile` para executar comandos do Maven, você pode ter certeza de que está usando a versão correta do Java conforme configurado no `config.mk`.

Isso garante uma maior flexibilidade ao desenvolver vários projetos que podem requerer diferentes versões do Java.

### Usando Makefile

Aqui estão as tarefas disponíveis:

- `make start`: Inicia o servidor WildFly
- `make stop`: Encerra o servidor WildFly
- `make mvn clean package`: Limpa o diretório do projeto e constrói um pacote
- `make log`: Monitora e exibe em tempo real o arquivo de log do servidor WildFly

O `Makefile` usa a variável `WILDFLY_DIR` para apontar para o local de instalação do WildFly. Se você instalou o WildFly em um local diferente de `/opt/wildfly-28.0.1.Final`, você precisará atualizar a variável `WILDFLY_DIR` no `config.mk`:

```makefile
WILDFLY_DIR := /caminho/para/o/seu/wildfly
```

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](./LICENSE) para obter mais detalhes.

## Suporte
Este é um projeto pessoal desenvolvido e mantido por um único indivíduo. Embora tenha sido feito o melhor esforço para garantir a funcionalidade, pode haver algumas falhas. Se encontrar problemas, sinta-se à vontade para abrir um issue.

Lembre-se de fornecer detalhes suficientes (como erros, versões usadas e etapas para reproduzir o problema) para facilitar a correção do problema.