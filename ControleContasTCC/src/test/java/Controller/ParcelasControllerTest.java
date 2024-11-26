/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Controller;

import DAO.ParcelasDAO;
import Model.Parcelas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Ariel
 */
public class ParcelasControllerTest {

    private ParcelasController parcelasController;
    private ParcelasDAO mockParcelasDAO;

    @BeforeEach
    public void setUp() {

        // Assim como feito no teste do ClientesController, aqui é utilizado o Mock para simular o DAO de forma controlada, sem interação
        // direta com o BD.
        mockParcelasDAO = mock(ParcelasDAO.class);
        parcelasController = new ParcelasController(mockParcelasDAO);
    }

    @Test
    public void testSalvarParcela() {
        Parcelas parcela = new Parcelas(1, 1, 1000.0, LocalDate.now());

        doNothing().when(mockParcelasDAO).save(parcela);

        parcelasController.salvarParcela(parcela);

        verify(mockParcelasDAO, times(1)).save(parcela);
    }

    @Test
    public void testBuscarParcelasPorClienteId() {
        int idCliente = 1;
        List<Parcelas> parcelasMock = new ArrayList<>();
        parcelasMock.add(new Parcelas(1, idCliente, 500.0, LocalDate.now()));

        when(mockParcelasDAO.buscarParcelasPorClienteId(idCliente)).thenReturn(parcelasMock);

        List<Parcelas> resultado = parcelasController.buscarParcelasPorClienteId(idCliente);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(idCliente, resultado.get(0).getIdCliente());
        verify(mockParcelasDAO, times(1)).buscarParcelasPorClienteId(idCliente);
    }

    @Test
    public void testBuscarParcelaPorId() {
        int idParcela = 1;
        Parcelas parcelaEsperada = new Parcelas();
        parcelaEsperada.setIdParcela(idParcela);
        parcelaEsperada.setIdCliente(2);
        parcelaEsperada.setValorTotal(500.0);
        parcelaEsperada.setQuantidadeParcelas(5);
        parcelaEsperada.setDataEmissao(LocalDate.of(2024, 11, 26));

        when(mockParcelasDAO.findById(idParcela)).thenReturn(parcelaEsperada);

        Parcelas resultado = parcelasController.buscarParcelaPorId(idParcela);

        assertNotNull(resultado);
        assertEquals(idParcela, resultado.getIdParcela());
        assertEquals(parcelaEsperada.getValorTotal(), resultado.getValorTotal());
        assertEquals(parcelaEsperada.getQuantidadeParcelas(), resultado.getQuantidadeParcelas());
        assertEquals(parcelaEsperada.getDataEmissao(), resultado.getDataEmissao());

        verify(mockParcelasDAO, times(1)).findById(idParcela);
    }

    @Test
    public void testDeletarParcela() {
        int idParcela = 1;

        doNothing().when(mockParcelasDAO).delete(idParcela);

        parcelasController.deletarParcela(idParcela);

        verify(mockParcelasDAO, times(1)).delete(idParcela);
    }
}
