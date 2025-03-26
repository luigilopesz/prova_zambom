package br.insper.prova_i.ferramenta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FerramentaService {

        @Autowired
        private FerramentaRepo ferramentaRepo;
        
        
        public Ferramenta save(Ferramenta ferramenta) {
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
