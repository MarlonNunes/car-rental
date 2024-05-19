# CAR RENTAL APPLICATION

### REQUISITOS
 * JDK 17
 * DOCKER

### Tecnologias utilizadas
* Spring Boot 3
* Spring Security
* Spring Email
* Spring JPA
* Spring Validation
* Keycloak para autenticação e autorização
* Localstack para simular serviços AWS

### INICIALIZANDO O PROJETO
1. **Configuração de E-mail:** É necessário gerar uma senha de aplicativo no GMAIL para o envio de e-mails pelo Spring Email. Para instruções detalhadas, [consulte este artigo](https://medium.com/@seonggil/send-email-with-spring-boot-and-gmail-27c14fc3d859)
2. **Variáveis de Ambiente:** Na raiz do projeto, crie um arquivo **.env** para armazenar as variáveis de ambiente necessárias. Este arquivo será utilizado pelo script **create-secret.sh** para configurar o ambiente. Inclua as seguintes variáveis:
``` linux
MYSQL_ROOT_PASSWORD=
KEYCLOAK_ADMIN=
KEYCLOAK_ADMIN_PASSWORD=
EMAIL_KEY=
EMAIL_USER=
```
