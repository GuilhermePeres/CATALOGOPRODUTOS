package br.com.catalogo.produtos.gateway.queue;

import br.com.catalogo.produtos.gateway.PedidoGateway;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PedidoQueueGateway implements PedidoGateway {

    private final StreamBridge streamBridge;
    public static final String ESTOQUE_RESPOSTA_OUT_0 = "estoqueResposta-out-0";

    @Override
    public void enviarRespostaEstoque(EstoqueRespostaJson estoqueRespostaJson) {
        streamBridge.send(ESTOQUE_RESPOSTA_OUT_0, estoqueRespostaJson);
    }
}
