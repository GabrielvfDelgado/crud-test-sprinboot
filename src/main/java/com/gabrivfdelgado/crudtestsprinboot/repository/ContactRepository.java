package com.gabrivfdelgado.crudtestsprinboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gabrivfdelgado.crudtestsprinboot.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{
}