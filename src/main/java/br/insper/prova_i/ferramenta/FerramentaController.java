package br.insper.prova_i.ferramenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ferramentas")
public class FerramentaController {

        @Autowired
        private FerramentaService ferramentaService;

        @Autowired
        private RestTemplate restTemplate;

        private static final String USER_API_URL = "http://56.124.127.89:8080/api/usuario/";

        @PostMapping
        public Ferramenta createFerramenta(@RequestBody Ferramenta ferramenta,
                        @RequestHeader(name = "email") String email) {
                Map<String, Object> usuario = getUsuarioByEmail(email);

                if (!"ADMIN".equals(usuario.get("papel"))) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                                        "Usuário não tem permissão para criar ferramentas.");
                }

                ferramenta.setUserName((String) usuario.get("nome"));
                ferramenta.setUserEmail((String) usuario.get("email"));
                return ferramentaService.save(ferramenta);
        }

        @DeleteMapping("/{id}")
        public void deleteFerramenta(@PathVariable String id, @RequestHeader(name = "email") String email) {
                Map<String, Object> usuario = getUsuarioByEmail(email);

                if (!"ADMIN".equals(usuario.get("papel"))) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                                        "Usuário não tem permissão para excluir ferramentas.");
                }

                ferramentaService.deleteById(id);
        }

        @GetMapping
        public List<Ferramenta> listFerramentas() {
                return ferramentaService.findAll();
        }

        private Map<String, Object> getUsuarioByEmail(String email) {
                try {
                        return restTemplate.getForObject(USER_API_URL + email, Map.class);
                } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
                }
        }
}
