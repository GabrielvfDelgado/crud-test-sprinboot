package com.gabrivfdelgado.crudtestsprinboot;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrivfdelgado.crudtestsprinboot.model.Contact;
import com.gabrivfdelgado.crudtestsprinboot.repository.ContactRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContactRepository repository;

    @Test
    public void findAllTest() throws Exception {
        Contact contact = new Contact();
        contact.setName("Nome do Contato");
        contact.setEmail("email@exemplo.com");
        contact.setPhone("1234567890");

        List<Contact> allContacts = Arrays.asList(contact);

        given(repository.findAll()).willReturn(allContacts);

        mvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nome do Contato")));
    }

    @Test
    public void findByIdTest() throws Exception {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Nome do Contato");
        contact.setEmail("email@exemplo.com");
        contact.setPhone("1234567890");

        given(repository.findById(1L)).willReturn(Optional.of(contact));

        mvc.perform(get("/contacts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nome do Contato")));
    }

    @Test
    public void createTest() throws Exception {
        Contact contact = new Contact();
        contact.setName("Nome do Contato");
        contact.setEmail("email@exemplo.com");
        contact.setPhone("1234567890");

        mvc.perform(post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(contact)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nome do Contato")));
    }

    @Test
    public void updateTest() throws Exception{
        Contact contact = new Contact();
        contact.setName("Nome do Contato");
        contact.setEmail("email@exemplo.com");
        contact.setPhone("1234567890");

        Contact updatedContact = new Contact();
        updatedContact.setName("Nome Alterado");
        updatedContact.setEmail(contact.getEmail());
        updatedContact.setPhone(contact.getPhone());

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(contact));
        Mockito.when(repository.save(contact)).thenReturn(updatedContact);

        mvc.perform(put("/contacts/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(updatedContact)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Nome Alterado")));
    }

    @Test
    public void deleteTest() throws Exception{
        Contact contact = new Contact();
        contact.setName("Nome do Contato");
        contact.setEmail("email@exemplo.com");
        contact.setPhone("1234567890");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(contact));

        mvc.perform(delete("/contacts/1"))
                    .andExpect(status().isOk());
    }

}
