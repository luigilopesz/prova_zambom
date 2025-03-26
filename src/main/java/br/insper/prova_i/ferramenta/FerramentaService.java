package br.insper.prova_i.ferramenta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FerramentaService {

        @Autowired
        private FerramentaRepo ferramentaRepo;
        
        
        public Ferramenta save(String nome, String descricao, String categoria, String nomeUsuario, String emailUsuario) {
                Ferramenta ferramenta = new Ferramenta();
                ferramenta.setName(nome);
                ferramenta.setDescription(descricao);
                ferramenta.setCategory(categoria);
                ferramenta.setUserName(nomeUsuario);
                ferramenta.setUserEmail(emailUsuario);

                return ferramentaRepo.save(ferramenta);
        }
        
        public Ferramenta findById(String id) {
                return ferramentaRepo.findById(id).orElse(null);
        }

        public List<Ferramenta> findAll() {
                return ferramentaRepo.findAll();
        }
        
        public void deleteById(String id) {
                ferramentaRepo.deleteById(id);
        }
}
