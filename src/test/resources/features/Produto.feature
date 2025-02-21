# language: pt
Funcionalidade: API - Catalogo de Produtos

  Cenário: Importar produtos com sucesso
    Dado que um arquivo CSV com produtos válidos é enviado
    Quando o endpoint de importação é chamado
    Então deve retornar a resposta de sucesso

  Cenário: Consultar produtos com sucesso
    Dado que o endpoint de consulta é chamado com produtos cadastrados
    Quando retornar a resposta de sucesso ao consultar
    Então deve retornar os produtos cadastrados