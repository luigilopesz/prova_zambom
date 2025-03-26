package br.insper.prova_i.ferramenta;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ferramenta {
       @Id
       private String id;
       private String name;
        private String description;
        private String category;
        private String userName;
        private String userEmail;   
}
