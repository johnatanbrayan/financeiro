package com.financeiro;

import java.util.Arrays;

import com.financeiro.model.Address;
import com.financeiro.model.City;
import com.financeiro.model.Person;
import com.financeiro.model.State;
import com.financeiro.repository.AddressRepository;
import com.financeiro.repository.CityRepository;
import com.financeiro.repository.PersonRepository;
import com.financeiro.repository.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceiroApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
	}

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public void run(String... args) throws Exception {

		State state1 = new State(null, "Distrito Federal", "DF");
		City city1 = new City(null, "Brasília",  state1);
		Person person1 = new Person(null, "Johnatan", false);
		Address address1 = new Address(null, "Qd 802 conj 12", "31", "null", "Recanto das Emas", "72650265", "null", "null", city1, person1);

		state1.getCities().addAll(Arrays.asList(city1));
		person1.getAdresses().addAll(Arrays.asList(address1));
		stateRepository.saveAll(Arrays.asList(state1));
		cityRepository.saveAll(Arrays.asList(city1));
		personRepository.saveAll(Arrays.asList(person1));
		addressRepository.saveAll(Arrays.asList(address1));
		
	}
}
