package com.financeiro;

import java.time.LocalDate;
import java.util.Arrays;

import com.financeiro.model.Address;
import com.financeiro.model.Category;
import com.financeiro.model.City;
import com.financeiro.model.Launch;
import com.financeiro.model.Person;
import com.financeiro.model.State;
import com.financeiro.model.enums.TypeCategory;
import com.financeiro.repository.AddressRepository;
import com.financeiro.repository.CategoryRepository;
import com.financeiro.repository.CityRepository;
import com.financeiro.repository.LaunchRepository;
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

	@Autowired 
	private CategoryRepository categoryRepository;

	@Autowired
	private LaunchRepository launchRepository;
	
	@Override
	public void run(String... args) throws Exception {

		// State state1 = new State(null, "Distrito Federal", "DF");
		// State state2 = new State(null, "Goiânia", "GO");
		// State state3 = new State(null, "São Paulo", "SP");

		// City city1 = new City(null, "Brasília",  state1);
		// City city2 = new City(null, "Luziânia",  state2);

		Person person1 = new Person(null, "Johnatan", true);
		Person person2 = new Person(null, "Joel", false);


		Category category1 = new Category(null, "Domestico");

		Launch launch1 = new Launch(null, "test", "", 2000.00, LocalDate.of(2020, 07, 10), LocalDate.of(2020, 07, 11), person1, category1, TypeCategory.ESPENSE);

		// Address address1 = new Address(null, "Qd 802 conj 12", "31", "null", "Recanto das Emas", "72650265", "null", "null", city1, person1);

		// state1.getCities().addAll(Arrays.asList(city1));
		// state2.getCities().addAll(Arrays.asList(city2));

		// person1.getAdresses().addAll(Arrays.asList(address1));

		// stateRepository.saveAll(Arrays.asList(state1, state2,state3));
		// cityRepository.saveAll(Arrays.asList(city1, city2));

		personRepository.saveAll(Arrays.asList(person1, person2));
		categoryRepository.saveAll(Arrays.asList(category1));
		launchRepository.saveAll(Arrays.asList(launch1));
		// addressRepository.saveAll(Arrays.asList(address1));
		
	}
}