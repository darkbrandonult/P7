package com.poseidoncapitalsolutions.poseidon.service;

import com.poseidoncapitalsolutions.poseidon.dto.BidListDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.BidListMapper;
import com.poseidoncapitalsolutions.poseidon.model.BidList;
import com.poseidoncapitalsolutions.poseidon.repository.BidListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for BidList entity operations.
 */
@Service
@Transactional
public class BidListService implements GenericService<BidListDTO> {
    
    private final BidListRepository bidListRepository;
    private final BidListMapper bidListMapper;
    
    public BidListService(BidListRepository bidListRepository, BidListMapper bidListMapper) {
        this.bidListRepository = bidListRepository;
        this.bidListMapper = bidListMapper;
    }
    
    @Override
    public List<BidListDTO> findAll() {
        return bidListRepository.findAll().stream()
                .map(bidListMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<BidListDTO> findById(Integer id) {
        return bidListRepository.findById(id)
                .map(bidListMapper::toDto);
    }
    
    @Override
    public BidListDTO save(BidListDTO dto) {
        BidList bidList = bidListMapper.toEntity(dto);
        BidList saved = bidListRepository.save(bidList);
        return bidListMapper.toDto(saved);
    }
    
    @Override
    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
