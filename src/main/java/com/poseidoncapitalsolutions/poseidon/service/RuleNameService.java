package com.poseidoncapitalsolutions.poseidon.service;

import com.poseidoncapitalsolutions.poseidon.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.poseidon.model.RuleName;
import com.poseidoncapitalsolutions.poseidon.repository.RuleNameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RuleNameService implements GenericService<RuleNameDTO> {
    
    private final RuleNameRepository ruleNameRepository;
    private final RuleNameMapper ruleNameMapper;
    
    public RuleNameService(RuleNameRepository ruleNameRepository, RuleNameMapper ruleNameMapper) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameMapper = ruleNameMapper;
    }
    
    @Override
    public List<RuleNameDTO> findAll() {
        return ruleNameRepository.findAll().stream()
                .map(ruleNameMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<RuleNameDTO> findById(Integer id) {
        return ruleNameRepository.findById(id)
                .map(ruleNameMapper::toDto);
    }
    
    @Override
    public RuleNameDTO save(RuleNameDTO dto) {
        RuleName ruleName = ruleNameMapper.toEntity(dto);
        RuleName saved = ruleNameRepository.save(ruleName);
        return ruleNameMapper.toDto(saved);
    }
    
    @Override
    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
