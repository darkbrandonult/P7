package com.poseidoncapitalsolutions.poseidon.service;

import com.poseidoncapitalsolutions.poseidon.dto.TradeDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.TradeMapper;
import com.poseidoncapitalsolutions.poseidon.model.Trade;
import com.poseidoncapitalsolutions.poseidon.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TradeService implements GenericService<TradeDTO> {
    
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;
    
    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }
    
    @Override
    public List<TradeDTO> findAll() {
        return tradeRepository.findAll().stream()
                .map(tradeMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<TradeDTO> findById(Integer id) {
        return tradeRepository.findById(id)
                .map(tradeMapper::toDto);
    }
    
    @Override
    public TradeDTO save(TradeDTO dto) {
        Trade trade = tradeMapper.toEntity(dto);
        Trade saved = tradeRepository.save(trade);
        return tradeMapper.toDto(saved);
    }
    
    @Override
    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}
