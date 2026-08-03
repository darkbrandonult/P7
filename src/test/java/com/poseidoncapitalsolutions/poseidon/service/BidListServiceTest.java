package com.poseidoncapitalsolutions.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidoncapitalsolutions.poseidon.dto.BidListDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.BidListMapper;
import com.poseidoncapitalsolutions.poseidon.model.BidList;
import com.poseidoncapitalsolutions.poseidon.repository.BidListRepository;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @Mock
    private BidListMapper bidListMapper;

    @InjectMocks
    private BidListService bidListService;

    private BidList bidList;
    private BidListDTO bidListDTO;

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
    }

    @Test
    void findAllShouldReturnListOfBidListDTOs() {
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidList));
        when(bidListMapper.toDto(bidList)).thenReturn(bidListDTO);

        List<BidListDTO> result = bidListService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Account1", result.get(0).getAccount());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnBidListDTOWhenFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        when(bidListMapper.toDto(bidList)).thenReturn(bidListDTO);

        Optional<BidListDTO> result = bidListService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Account1", result.get().getAccount());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(bidListRepository.findById(999)).thenReturn(Optional.empty());

        Optional<BidListDTO> result = bidListService.findById(999);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveShouldPersistAndReturnBidListDTO() {
        when(bidListMapper.toEntity(bidListDTO)).thenReturn(bidList);
        when(bidListRepository.save(bidList)).thenReturn(bidList);
        when(bidListMapper.toDto(bidList)).thenReturn(bidListDTO);

        BidListDTO result = bidListService.save(bidListDTO);

        assertNotNull(result);
        assertEquals("Account1", result.getAccount());
        verify(bidListRepository, times(1)).save(any(BidList.class));
    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        doNothing().when(bidListRepository).deleteById(anyInt());

        bidListService.deleteById(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }
}