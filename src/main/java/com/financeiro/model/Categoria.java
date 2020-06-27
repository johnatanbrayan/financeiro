package com.financeiro.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categoria {    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;
    
    public Categoria() {}

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Categoria)) {
            return false;
        }
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
  
}
