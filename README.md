# Torcedores App
#### Autenticação basic
* Usuário: *admin*
* Senha: *admin*

#### Ferramentas
    OpenJDK 11
    NodeJS 12.18.3
    NPM 6.14.8
    MVN 3.6.3
    Angular 11.0.1

#### Criar Builds
* Pasta **backend**

    mvn clean install -DskipTests=true 

* Pasta **frontend**

    npm install

    npm run build --prod

#### Gerar as Imagens

* Pasta **backend**

    docker build --tag torcedor-app:1.0.7 .

* Pasta **frontend**

    docker build --tag torcedores-site:1.0.0 .

#### Subir os Containers

* Pasta *raiz*

    sudo docker-compose up

#### Aplicação

Login
* http://localhost:4200

Logado
* http://localhost:4200/torcedor

Limpar sessão
Application > storage > Local Storage > auth
