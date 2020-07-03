package com.financeiro.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String uf;

    @OneToMany(mappedBy = "state")
    private List<City> cities = new ArrayList();

    public State() {}

    public State(Long id, String name, String uf) {
        this.id = id;
        this.name = name;
        this.uf = uf;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getUf() { return this.uf; }
    public void setUf(String uf) { this.uf = uf; }

    public List<City> getCities() { return this.cities; }
    public void setCities(List<City> cities) { this.cities = cities; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof State)) {
            return false;
        }
        State state = (State) o;
        return Objects.equals(id, state.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}