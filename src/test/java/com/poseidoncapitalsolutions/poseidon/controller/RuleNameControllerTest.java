package com.poseidoncapitalsolutions.poseidon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.poseidoncapitalsolutions.poseidon.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.poseidon.service.RuleNameService;

@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameService ruleNameService;

    private RuleNameDTO ruleNameDTO;
    private List<RuleNameDTO> ruleNameDTOs;

    @BeforeEach
    void setUp() {
        ruleNameDTO = new RuleNameDTO();
        ruleNameDTO.setId(1);
        ruleNameDTO.setName("Rule1");
        ruleNameDTO.setDescription("Test Rule");
        ruleNameDTO.setJson("{\"rule\": \"value\"}");
        ruleNameDTO.setTemplate("Template1");
        ruleNameDTO.setSqlStr("SELECT * FROM table");
        ruleNameDTO.setSqlPart("WHERE condition = 1");

        ruleNameDTOs = new ArrayList<>();
        ruleNameDTOs.add(ruleNameDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnRuleNameListPage() throws Exception {
        when(ruleNameService.findAll()).thenReturn(ruleNameDTOs);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attribute("ruleNames", ruleNameDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addRuleFormShouldReturnAddPage() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToRuleNameListWhenNoErrors() throws Exception {
        when(ruleNameService.save(any(RuleNameDTO.class))).thenReturn(ruleNameDTO);

        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Rule1")
                .param("description", "Test Rule"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleNameDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "")
                .param("description", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        when(ruleNameService.findById(anyInt())).thenReturn(Optional.of(ruleNameDTO));

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateRuleNameShouldRedirectToRuleNameListWhenNoErrors() throws Exception {
        when(ruleNameService.save(any(RuleNameDTO.class))).thenReturn(ruleNameDTO);

        mockMvc.perform(post("/ruleName/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Rule1")
                .param("description", "Test Rule"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleNameDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRuleNameShouldRedirectToRuleNameList() throws Exception {
        when(ruleNameService.findById(anyInt())).thenReturn(Optional.of(ruleNameDTO));
        doNothing().when(ruleNameService).deleteById(anyInt());

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).deleteById(anyInt());
    }
}