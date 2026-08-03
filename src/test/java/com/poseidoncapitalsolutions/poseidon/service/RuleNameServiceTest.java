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

import com.poseidoncapitalsolutions.poseidon.dto.RuleNameDTO;
import com.poseidoncapitalsolutions.poseidon.mapper.RuleNameMapper;
import com.poseidoncapitalsolutions.poseidon.model.RuleName;
import com.poseidoncapitalsolutions.poseidon.repository.RuleNameRepository;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Mock
    private RuleNameMapper ruleNameMapper;

    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName ruleName;
    private RuleNameDTO ruleNameDTO;

    @BeforeEach
    void setUp() {
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Rule1");
        ruleName.setDescription("Test Rule");
        ruleName.setJson("{\"rule\": \"value\"}");
        ruleName.setTemplate("Template1");
        ruleName.setSqlStr("SELECT * FROM table");
        ruleName.setSqlPart("WHERE condition = 1");

        ruleNameDTO = new RuleNameDTO();
        ruleNameDTO.setId(1);
        ruleNameDTO.setName("Rule1");
        ruleNameDTO.setDescription("Test Rule");
        ruleNameDTO.setJson("{\"rule\": \"value\"}");
        ruleNameDTO.setTemplate("Template1");
        ruleNameDTO.setSqlStr("SELECT * FROM table");
        ruleNameDTO.setSqlPart("WHERE condition = 1");
    }

    @Test
    void findAllShouldReturnListOfRuleNameDTOs() {
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName));
        when(ruleNameMapper.toDto(ruleName)).thenReturn(ruleNameDTO);

        List<RuleNameDTO> result = ruleNameService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Rule1", result.get(0).getName());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnRuleNameDTOWhenFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameMapper.toDto(ruleName)).thenReturn(ruleNameDTO);

        Optional<RuleNameDTO> result = ruleNameService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Rule1", result.get().getName());
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(ruleNameRepository.findById(999)).thenReturn(Optional.empty());

        Optional<RuleNameDTO> result = ruleNameService.findById(999);

        assertTrue(result.isEmpty());
    }

    @Test
    void saveShouldPersistAndReturnRuleNameDTO() {
        when(ruleNameMapper.toEntity(ruleNameDTO)).thenReturn(ruleName);
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameMapper.toDto(ruleName)).thenReturn(ruleNameDTO);

        RuleNameDTO result = ruleNameService.save(ruleNameDTO);

        assertNotNull(result);
        assertEquals("Rule1", result.getName());
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        doNothing().when(ruleNameRepository).deleteById(anyInt());

        ruleNameService.deleteById(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}