package com.security.FirstSecurityApp.services;

import com.security.FirstSecurityApp.models.Person;
import com.security.FirstSecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }
    @Transactional
    public void register(Person person){
        System.out.println(person);
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        peopleRepository.save(person);

    }
}
