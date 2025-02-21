package br.com.catalogo.produtos.controller.api;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class ProdutoControllerIT {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup(){
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void deveRegistrarProdutosEmLoteComSucesso() {
        //Arrange
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();
        String file = ProdutoHelper.gerarArquivoCsv(produtos);

        //Act & Assert
        given()
                .multiPart("file", "produtos.csv", file.getBytes(StandardCharsets.UTF_8), "text/csv")
        .when()
                .post("/produtos/importar")
        .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("produtosRegistrados", Matchers.is(true));
    }

    @Test
    void deveLancarNenhumProdutoInformadoExceptionAoRegistrarProdutosEmLote() {
        //Arrange
        String file = "";

        //Act & Assert
        given()
                .multiPart("file", "produtos.csv", file.getBytes(StandardCharsets.UTF_8), "text/csv")
        .when()
                .post("/produtos/importar")
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .contentType(ContentType.JSON)
                .body("code", Matchers.is("produtos.nenhumProdutoInformado"))
                .body("message", Matchers.is("Nenhum produto informado."));
    }

    @Test
    void deveConsultarProdutosComSucesso() {
        //Act & Assert
        given().when()
                .get("/produtos/consultar")
        .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("[0].id", org.hamcrest.Matchers.is(1))
                .body("[0].nome", org.hamcrest.Matchers.is("Discipulado Começa com Contemplação"))
                .body("[0].descricao", org.hamcrest.Matchers.is("Livro de Samuel Whitefield. O que você está contemplando? Seja lá o que for, você será transformado à imagem disso..."))
                .body("[0].preco", org.hamcrest.Matchers.is(59.97F))
                .body("[0].quantidadeEmEstoque", org.hamcrest.Matchers.is(10))
                .body("[1].id", org.hamcrest.Matchers.is(2))
                .body("[1].nome", org.hamcrest.Matchers.is("Coração Selvagem"))
                .body("[1].descricao", org.hamcrest.Matchers.is("Livro de John Eldredge. Descobrindo o segredo da alma de um homem. Qual é a vida que você deseja viver..."))
                .body("[1].preco", org.hamcrest.Matchers.is(38.17F))
                .body("[1].quantidadeEmEstoque", org.hamcrest.Matchers.is(10))
                .body("[2].id", org.hamcrest.Matchers.is(3))
                .body("[2].nome", org.hamcrest.Matchers.is("Temperamentos Transformados"))
                .body("[2].descricao", org.hamcrest.Matchers.is("Livro de Tim LaHaye. É difícil mudar seu temperamento, não é? Como seria bom se você pudesse ser calmo como os..."))
                .body("[2].preco", org.hamcrest.Matchers.is(64.90F))
                .body("[2].quantidadeEmEstoque", org.hamcrest.Matchers.is(10));
    }
}
