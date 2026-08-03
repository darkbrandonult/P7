package com.poseidoncapitalsolutions.poseidon.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.poseidoncapitalsolutions.poseidon.dto.BidListDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.BidListMapper;
import com.poseidoncapitalsolutions.poseidon.model.BidList;
import com.poseidoncapitalsolutions.poseidon.service.BidListService;

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListService bidListService;

    @MockitoBean
    private BidListMapper bidListMapper;

    private BidList bidList;
    private BidListDTO bidListDTO;
    private List<BidListDTO> bidListDTOs;

    @BeforeEach
    void setUp() {
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account1");
        bidList.setType("Type1");
        bidList.setBidQuantity(100.0);

        bidListDTO = new BidListDTO();
        bidListDTO.setBidListId(1);
        bidListDTO.setAccount("Account1");
        bidListDTO.setType("Type1");
        bidListDTO.setBidQuantity(100.0);

        bidListDTOs = new ArrayList<>();
        bidListDTOs.add(bidListDTO);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void homeShouldReturnBidListPage() throws Exception {
        when(bidListService.findAll()).thenReturn(bidListDTOs);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attribute("bidLists", bidListDTOs));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addBidFormShouldReturnAddBidPage() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldRedirectToBidListWhenNoErrors() throws Exception {
        when(bidListMapper.toEntity(any(BidListDTO.class))).thenReturn(bidList);
        when(bidListService.save(any(BidListDTO.class))).thenReturn(bidListDTO);

        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "Account1")
                .param("type", "Type1")
                .param("bidQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).save(any(BidListDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void validateShouldReturnToAddPageWhenValidationErrors() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "")
                .param("type", "Type1")
                .param("bidQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldReturnUpdatePage() throws Exception {
        when(bidListService.findById(anyInt())).thenReturn(Optional.of(bidListDTO));

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void showUpdateFormShouldThrowExceptionWhenNotFound() throws Exception {
        when(bidListService.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/bidList/update/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBidShouldRedirectToBidListWhenNoErrors() throws Exception {
        when(bidListService.save(any(BidListDTO.class))).thenReturn(bidListDTO);

        mockMvc.perform(post("/bidList/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "Account1")
                .param("type", "Type1")
                .param("bidQuantity", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).save(any(BidListDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBidShouldReturnToUpdatePageWhenValidationErrors() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "")
                .param("type", "Type1")
                .param("bidQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteBidShouldRedirectToBidList() throws Exception {
        when(bidListService.findById(anyInt())).thenReturn(Optional.of(bidListDTO));
        doNothing().when(bidListService).deleteById(anyInt());

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService, times(1)).deleteById(anyInt());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteBidShouldThrowExceptionWhenNotFound() throws Exception {
        when(bidListService.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/bidList/delete/999"))
                .andExpect(status().isNotFound());
    }
}