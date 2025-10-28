package ecommerce.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ecommerce.entity.CarrinhoDeCompras;
import ecommerce.entity.Cliente;
import ecommerce.entity.Produto;
import ecommerce.entity.Regiao;
import ecommerce.entity.TipoCliente;

public class CompraServiceRobustezTest {

    private CompraService service;
    private Cliente clienteValido;
    private Produto produtoValido;
    private CarrinhoDeCompras carrinhoValido;

    @BeforeEach
    public void setup() {
        service = new CompraService(null, null, null, null);
        clienteValido = TestHelper.criarCliente(TipoCliente.BRONZE, Regiao.SUDESTE);
        produtoValido = TestHelper.criarProduto("Valido", BigDecimal.TEN, BigDecimal.ONE, false);
        
        carrinhoValido = new CarrinhoDeCompras();
        carrinhoValido.setItens(Arrays.asList(TestHelper.criarItem(produtoValido, 1L)));
    }

    @Test
    @DisplayName("Deve lançar exceção quando Carrinho for nulo")
    public void calcularCustoTotal_quandoCarrinhoNulo_entaoLancaIllegalArgumentException() {
        assertThatThrownBy(() -> service.calcularCustoTotal(null, Regiao.SUDESTE, TipoCliente.BRONZE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Carrinho não pode ser nulo ou vazio.");
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando a Lista de Itens for nula")
    public void calcularCustoTotal_quandoItensNulos_entaoLancaIllegalArgumentException() {
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        carrinho.setItens(null); // Lista de itens nula

        assertThatThrownBy(() -> service.calcularCustoTotal(carrinho, Regiao.SUDESTE, TipoCliente.BRONZE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Carrinho não pode ser nulo ou vazio.");
    }

    @Test
    @DisplayName("Deve lançar exceção quando a Lista de Itens for vazia")
    public void calcularCustoTotal_quandoItensVazios_entaoLancaIllegalArgumentException() {
        CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
        carrinho.setItens(Collections.emptyList()); // Lista vazia

        assertThatThrownBy(() -> service.calcularCustoTotal(carrinho, Regiao.SUDESTE, TipoCliente.BRONZE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Carrinho não pode ser nulo ou vazio.");
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando Regiao for nula")
    public void calcularCustoTotal_quandoRegiaoNula_entaoLancaIllegalArgumentException() {
        assertThatThrownBy(() -> service.calcularCustoTotal(carrinhoValido, null, TipoCliente.BRONZE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Região não pode ser nula.");
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando TipoCliente for nulo")
    public void calcularCustoTotal_quandoTipoClienteNulo_entaoLancaIllegalArgumentException() {
        assertThatThrownBy(() -> service.calcularCustoTotal(carrinhoValido, Regiao.SUDESTE, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Tipo de cliente não pode ser nulo.");
    }
}