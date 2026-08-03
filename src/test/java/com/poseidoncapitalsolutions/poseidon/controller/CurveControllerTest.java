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

import com.poseidoncapitalsolutions.poseidon.dto.CurvePointDTO;
import com.poseidoncapitalsolutions.poseidon.service.CurvePointService;

@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointService curvePointService;

    private CurvePointDTO curvePointDTO;
    private List<CurvePointDTO> curvePointDTOs;

    @BeforeEach
    void setUp() {
        curvePointDTO = new CurvePointDTO();
        curvePointDTO.setId(1);
        curvePointDTO.setCurveId(10);
        curvePointDTO.setTerm(5.0);
        curvePointDTO.setValue(99.5);

        curvePointDTOs = new ArrayList<>();
        curvePointDTOs.add(curvePointDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnCurvePointListPage() throws Exception {
        when(curvePointService.findAll()).thenReturn(curvePointDTOs);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attribute("curvePoints", curvePointDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addCurvePointFormShouldReturnAddPage() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToCurvePointListWhenNoErrors() throws Exception {
        when(curvePointService.save(any(CurvePointDTO.class))).thenReturn(curvePointDTO);

        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", "10")
                .param("term", "5.0")
                .param("value", "99.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).save(any(CurvePointDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", "0")
                .param("term", "")
                .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        when(curvePointService.findById(anyInt())).thenReturn(Optional.of(curvePointDTO));

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldThrowExceptionWhenNotFound() throws Exception {
        when(curvePointService.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/curvePoint/update/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateCurvePointShouldRedirectToCurvePointListWhenNoErrors() throws Exception {
        when(curvePointService.save(any(CurvePointDTO.class))).thenReturn(curvePointDTO);

        mockMvc.perform(post("/curvePoint/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("curveId", "10")
                .param("term", "5.0")
                .param("value", "99.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).save(any(CurvePointDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteCurvePointShouldRedirectToCurvePointList() throws Exception {
        when(curvePointService.findById(anyInt())).thenReturn(Optional.of(curvePointDTO));
        doNothing().when(curvePointService).deleteById(anyInt());

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).deleteById(anyInt());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteCurvePointShouldThrowExceptionWhenNotFound() throws Exception {
        when(curvePointService.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/curvePoint/delete/999"))
                .andExpect(status().isNotFound());
    }
}