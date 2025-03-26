package br.insper.prova_i.tests.service;

import br.insper.prova_i.ferramenta.Ferramenta;
import br.insper.prova_i.ferramenta.FerramentaRepo;
import br.insper.prova_i.ferramenta.FerramentaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FerramentaServiceTest {

    @InjectMocks
    private FerramentaService ferramentaService;

    @Mock
    private FerramentaRepo ferramentaRepo;

    @Test
    void test_listarTodasQuandoNaoHaFerramentas() {
        Mockito.when(ferramentaRepo.findAll()).thenReturn(new ArrayList<>());

        List<Ferramenta> ferramentas = ferramentaService.findAll();

        Assertions.assertEquals(0, ferramentas.size());
    }

    @Test
    void test_criarFerramentaComSucesso() {
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setId("1");
        ferramenta.setName("Furadeira");
        ferramenta.setDescription("Furadeira elétrica");
        ferramenta.setCategory("Mecânica");
        ferramenta.setUserName("Admin");
        ferramenta.setUserEmail("admin@empresa.com");

        Mockito.when(ferramentaRepo.save(Mockito.any(Ferramenta.class))).thenReturn(ferramenta);

        Ferramenta ferramentaCriada = ferramentaService.save(
                "Furadeira",
                "Furadeira elétrica",
                "Mecânica",
                "Admin",
                "admin@empresa.com"
        );

        Assertions.assertEquals("Furadeira", ferramentaCriada.getName());
        Assertions.assertEquals("Furadeira elétrica", ferramentaCriada.getDescription());
        Assertions.assertEquals("Mecânica", ferramentaCriada.getCategory());
        Assertions.assertEquals("Admin", ferramentaCriada.getUserName());
        Assertions.assertEquals("admin@empresa.com", ferramentaCriada.getUserEmail());
    }

    @Test
    void test_buscarFerramentaPorIdComSucesso() {
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setId("1");
        ferramenta.setName("Furadeira");
        ferramenta.setDescription("Furadeira elétrica");
        ferramenta.setCategory("Mecânica");
        ferramenta.setUserName("Admin");
        ferramenta.setUserEmail("admin@empresa.com");

        Mockito.when(ferramentaRepo.findById("1")).thenReturn(Optional.of(ferramenta));

        Ferramenta ferramentaEncontrada = ferramentaService.findById("1");

        Assertions.assertNotNull(ferramentaEncontrada);
        Assertions.assertEquals("Furadeira", ferramentaEncontrada.getName());
    }

    @Test
    void test_buscarFerramentaPorIdInexistente() {
        Mockito.when(ferramentaRepo.findById("99")).thenReturn(Optional.empty());

        Ferramenta ferramentaEncontrada = ferramentaService.findById("99");

        Assertions.assertNull(ferramentaEncontrada);
    }

    @Test
    void test_excluirFerramentaComSucesso() {
        Mockito.doNothing().when(ferramentaRepo).deleteById("1");

        ferramentaService.deleteById("1");

        Mockito.verify(ferramentaRepo, Mockito.times(1)).deleteById("1");
    }
}