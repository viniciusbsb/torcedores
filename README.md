# Torcedores App
#### Autenticação basic
* Usuário: *admin*
* Senha: *admin*

#### Ferramentas
    OpenJDK 11
    NodeJS 12.18.3
    NPM 6.14.8
    MVN 3.6.3

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

    docker-compose up
