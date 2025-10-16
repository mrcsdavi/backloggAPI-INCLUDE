🎮 BacklogG - API de Gerenciamento de Jogos e Reviews
Uma API RESTful desenvolvida em Spring Boot para gerenciar uma biblioteca de jogos e suas respectivas avaliações.

📋 Índice
Funcionalidades
Tecnologias
Pré-requisitos
Instalação e Execução
Endpoints
Jogos
Reviews
Exemplos de Uso
Tratamento de Erros
Estrutura do Projeto

🚀 Funcionalidades
✅ CRUD de jogos
✅ Sistema de reviews com notas e comentários
✅ Reviews organizadas por jogo
✅ IDs sequenciais automáticos para reviews
✅ Tratamento de exceções personalizado
✅ Validação de recursos existentes

🛠 Tecnologias
Java 17+
Spring Boot 3.x
Maven
Lombok
RESTful API

📋 Pré-requisitos
Java 17 ou superior

Maven 3.6+

A aplicação estará disponível em: http://localhost:8080

INSTALAÇÃO:

# Clone o repositório
git clone https://github.com/seu-usuario/backlogg.git

IDE usada por mim (VScode)
Foi usado Postman para testes
////

////
📚 Endpoints

🎯 Controller dos Jogos
Método	Endpoint	Descrição
GET	/jogos	Lista todos os jogos
GET	/jogos/{id}	Busca jogo por ID
POST	/jogos	Cria um novo jogo
PUT	/jogos/{id}	Atualiza jogo existente
DELETE	/jogos/{id}	Remove jogo

📝 Controller das Reviews
Método	Endpoint	Descrição
GET	/reviews	Lista todas as reviews
GET	/reviews/{id}	Busca review por ID
GET	/reviews/jogo/{jogoId}	Lista reviews de um jogo específico
GET	/reviews/jogo/{jogoId}/review/{reviewId}	Busca review específica de um jogo
POST	/reviews	Cria uma nova review
POST	/reviews/jogo/{jogoId}	Cria review para jogo específico
POST	/reviews/jogo/{jogoId}/review/{reviewId}	Vincula review existente a jogo
DELETE	/reviews/{id}	Remove review
DELETE	/reviews/jogo/{jogoId}/review/{reviewId}	Remove review específica de um jogo

💡 Exemplos de Uso
1. Criando um Jogo

POST http://localhost:8080/jogos
Content-Type: application/json

{
    "titulo": "The Legend of Zelda: Breath of the Wild",
    "genero": "Aventura",
    "plataforma": "Nintendo Switch",
    "dataLancamento": "2017-03-03",
    "imagemUrl": "https://exemplo.com/zelda.jpg"
}

2. Criando Review para Jogo Específico
POST http://localhost:8080/reviews/jogo/1
Content-Type: application/json

{
    "Nota_Usuario": 10,
    "Comentario_Usuario": "Jogo incrível! Revolucionário.",
    "Usuario": "João Silva"
}

3. Buscando Reviews de um Jogo
GET http://localhost:8080/reviews/jogo/1

4. Buscando Review Específica
GET http://localhost:8080/reviews/jogo/1/review/1


⚠️ Tratamento de Erros
A API retorna respostas padronizadas para erros:

Exemplo de Erro 404
{
    "timestamp": "2024-01-15T10:30:00",
    "status": 404,
    "error": "Jogo não encontrado",
    "message": "Jogo com ID 999 não foi encontrado."
}

<img width="401" height="414" alt="image" src="https://github.com/user-attachments/assets/6412b69f-aa89-431f-a441-16424a8dd30c" />


🔧 Modelos de Dados
CONTROLLER JOGO

{
    "id": Long,
    "titulo": String,
    "genero": String,
    "plataforma": String,
    "dataLancamento": String (yyyy-MM-dd),
    "imagemUrl": String
}

CONTROLLER REVIEW

{
    "id": Long,
    "Nota_Usuario": int (0-10),
    "Comentario_Usuario": String,
    "Usuario": String,
    "ID_Jogo": Long
}
