# Estudo de Línguas

Este projeto tem como objetivo criar um site para o estudo de línguas. Até o momento, foi feito por uma única pessoa.

## Configuração e implementação
O projeto foi implementado e testado usando o WildFly 28.0.1.Final, que não está incluído no repositório. Você precisará instalar e configurar o WildFly separadamente. Note que a funcionalidade pode não ser garantida para outras versões do WildFly.

No meu ambiente, o WildFly foi instalado em `/opt/`. O diretório exato pode variar dependendo de sua configuração e preferências pessoais, mas aqui está o caminho do exemplo: `/opt/wildfly-28.0.1.Final`.

Para adicionar um usuário, você pode usar o seguinte comando:

~~~bash
/opt/wildfly-28.0.1.Final/bin/add-user.sh
~~~

Para este projeto, o superusuário é `abstrato` com a senha `s6qJ#9Mx$K2P`. No entanto, você é livre para escolher o nome de usuário e senha de sua preferência.

Acesse o projeto através do link: http://127.0.0.1:8080/

## Construção do Projeto

Este projeto usa tanto `Makefile` quanto `pom.xml` para gerenciar o ciclo de vida do aplicativo.

### Usando Maven

Para compilar o projeto, você pode usar o Maven. Execute o seguinte comando:

~~~bash
mvn package
~~~
Este comando irá compilar o código e gerar um arquivo .war na pasta target.

### Usando Makefile

O projeto também inclui um `Makefile` para gerenciar o servidor WildFly. Aqui estão as tarefas disponíveis:

- `make start`: Inicia o servidor WildFly
- `make stop`: Encerra o servidor WildFly

O `Makefile` usa a variável `WILDFLY_DIR` para apontar para o local de instalação do WildFly. Se você instalou o WildFly em um local diferente de `/opt/wildfly-28.0.1.Final`, você precisará atualizar a variável `WILDFLY_DIR` no `config.mk`:

```makefile
WILDFLY_DIR := /caminho/para/o/seu/wildfly
```

## Versões Usadas

- Java 11
- WildFly 28.0.1.Final

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](./LICENSE) para obter mais detalhes.

## Suporte
Este é um projeto pessoal desenvolvido e mantido por um único indivíduo. Embora tenha sido feito o melhor esforço para garantir a funcionalidade, pode haver algumas falhas. Se encontrar problemas, sinta-se à vontade para abrir um issue.

Lembre-se de fornecer detalhes suficientes (como erros, versões usadas e etapas para reproduzir o problema) para facilitar a correção do problema.