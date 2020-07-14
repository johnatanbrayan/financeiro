package com.financeiro.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.financeiro.model.enums.TypeCategory;

@Entity
public class Launch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;
    private String observation;

    @NotNull
    private Double price;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate paymentDate;


    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dueDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeCategory typeCategory;

    public Launch() {}

    public Launch(Long id, String description, String observation, Double price, LocalDate paymentDate, LocalDate dueDate, Person person, Category category, TypeCategory typeCategory) {
        this.id = id;
        this.description = description;
        this.observation = observation;
        this.price = price;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.person = person;
        this.category = category;
        this.typeCategory = typeCategory;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getObservation() { return this.observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public Double getPrice() { return this.price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDate getPaymentDate() { return this.paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public LocalDate getDueDate() { return this.dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Person getPerson() { return this.person; }
    public void setPerson(Person person) { this.person = person; }

    public Category getCategory() { return this.category; }
    public void setCategory(Category category) { this.category = category; }

    public TypeCategory getTypeCategory() { return this.typeCategory; }
    public void setTypeCategory(TypeCategory typeCategory) { this.typeCategory = typeCategory; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Launch)) {
            return false;
        }
        Launch launch = (Launch) o;
        return Objects.equals(id, launch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}