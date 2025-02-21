package br.com.catalogo.produtos.bdd;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@Transactional
@SpringBootTest
public class DefinicaoPassos {
    private Response response;
    private final String endpoint = "http://localhost:8080/produtos";
    private String file = "";

    @Dado("que um arquivo CSV com produtos válidos é enviado")
    public void que_um_arquivo_csv_com_produtos_validos_e_enviado() {
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();
        file = ProdutoHelper.gerarArquivoCsv(produtos);
    }

    @Quando("o endpoint de importação é chamado")
    public void o_endpoint_de_importacao_e_chamado() {
        response = given()
                .multiPart("file", "produtos.csv", file.getBytes(StandardCharsets.UTF_8), "text/csv")
                .when()
                .post(endpoint + "/importar");
    }

    @Entao("deve retornar a resposta de sucesso")
    public void deve_retornar_a_resposta_de_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("produtosRegistrados", Matchers.is(true));
    }

    @Dado("que o endpoint de consulta é chamado com produtos cadastrados")
    public void que_o_endpoint_de_consulta_e_chamado_com_produtos_cadastrados() {
        que_um_arquivo_csv_com_produtos_validos_e_enviado();
        o_endpoint_de_importacao_e_chamado();

        response = given()
                .when()
                .get(endpoint + "/consultar");
    }

    @Quando("retornar a resposta de sucesso ao consultar")
    public void retornar_a_resposta_de_sucesso_ao_consultar() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Entao("deve retornar os produtos cadastrados")
    public void deve_retornar_os_produtos_cadastrados(){
        response.then()
                .contentType(ContentType.JSON)
                .body("$.size()", greaterThanOrEqualTo(2));
    }
}
