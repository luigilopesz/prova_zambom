package br.insper.prova_i.tests.controller;

import br.insper.prova_i.ferramenta.Ferramenta;
import br.insper.prova_i.ferramenta.FerramentaController;
import br.insper.prova_i.ferramenta.FerramentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class FerramentaControllerTest {

        @InjectMocks
        private FerramentaController ferramentaController;

        @Mock
        private FerramentaService ferramentaService;

        @Mock
        private RestTemplate restTemplate;

        private MockMvc mockMvc;

        @BeforeEach
        void setup() {
                this.mockMvc = MockMvcBuilders
                        .standaloneSetup(ferramentaController)
                        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                        .build();

                Mockito.lenient().when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Map.class)))
                        .thenReturn(Map.of("nome", "Admin", "email", "admin@empresa.com", "papel", "ADMIN"));
        }

        @Test
        void testListarFerramentas() throws Exception {
                List<Ferramenta> ferramentas = Arrays.asList(
                                new Ferramenta("1", "Furadeira", "Furadeira elétrica", "Mecânica", "Admin",
                                                "admin@empresa.com"),
                                new Ferramenta("2", "Martelo", "Martelo de aço", "Mecânica", "Admin",
                                                "admin@empresa.com"));

                ObjectMapper objectMapper = new ObjectMapper();

                Mockito.when(ferramentaService.findAll()).thenReturn(ferramentas);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/ferramentas"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content()
                                                .json(objectMapper.writeValueAsString(ferramentas)));
        }

        @Test
        void testCriarFerramenta() throws Exception {
                Ferramenta ferramenta = new Ferramenta(null, "Furadeira", "Furadeira elétrica", "Mecânica", "Admin",
                                "admin@empresa.com");
                Ferramenta ferramentaSalva = new Ferramenta("1", "Furadeira", "Furadeira elétrica", "Mecânica", "Admin",
                                "admin@empresa.com");

                ObjectMapper objectMapper = new ObjectMapper();

                Mockito.when(ferramentaService.save(
                                Mockito.anyString(),
                                Mockito.anyString(),
                                Mockito.anyString(),
                                Mockito.anyString(),
                                Mockito.anyString())).thenReturn(ferramentaSalva);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/ferramentas")
                                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                                .header("email", "admin@empresa.com")
                                .content(objectMapper.writeValueAsString(ferramenta)))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content()
                                                .json(objectMapper.writeValueAsString(ferramentaSalva)));
        }

        @Test
        void testExcluirFerramenta() throws Exception {
                Mockito.doNothing().when(ferramentaService).deleteById("1");

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/ferramentas/1")
                                .header("email", "admin@empresa.com"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }
}