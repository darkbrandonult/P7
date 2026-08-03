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

import com.poseidoncapitalsolutions.poseidon.dto.TradeDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.TradeMapper;
import com.poseidoncapitalsolutions.poseidon.model.Trade;
import com.poseidoncapitalsolutions.poseidon.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeMapper tradeMapper;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;
    private TradeDTO tradeDTO;

    @BeforeEach
    void setUp() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account2");
        trade.setType("Sell");
        trade.setBuyQuantity(100.0);

        tradeDTO = new TradeDTO();
        tradeDTO.setTradeId(1);
        tradeDTO.setAccount("Account2");
        tradeDTO.setType("Sell");
        tradeDTO.setBuyQuantity(100.0);
    }

    @Test
    void findAllShouldReturnListOfTradeDTOs() {
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade));
        when(tradeMapper.toDto(trade)).thenReturn(tradeDTO);

        List<TradeDTO> result = tradeService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Account2", result.get(0).getAccount());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnTradeDTOWhenFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeMapper.toDto(trade)).thenReturn(tradeDTO);

        Optional<TradeDTO> result = tradeService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Account2", result.get().getAccount());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(tradeRepository.findById(999)).thenReturn(Optional.empty());

        Optional<TradeDTO> result = tradeService.findById(999);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveShouldPersistAndReturnTradeDTO() {
        when(tradeMapper.toEntity(tradeDTO)).thenReturn(trade);
        when(tradeRepository.save(trade)).thenReturn(trade);
        when(tradeMapper.toDto(trade)).thenReturn(tradeDTO);

        TradeDTO result = tradeService.save(tradeDTO);

        assertNotNull(result);
        assertEquals("Account2", result.getAccount());
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        doNothing().when(tradeRepository).deleteById(anyInt());

        tradeService.deleteById(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }
}