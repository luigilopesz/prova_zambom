package br.insper.prova_i.ferramenta;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FerramentaRepo extends MongoRepository<Ferramenta, String> {
        
}
