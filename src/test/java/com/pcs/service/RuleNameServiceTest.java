package com.pcs.service;

import com.pcs.model.RuleName;
import com.pcs.repository.RuleNameRepository;
import com.pcs.web.dto.RuleNameDTO;
import com.pcs.web.mapper.RuleNameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;
    @Mock
    private RuleNameMapper ruleNameMapper;
    @InjectMocks
    private RuleNameService ruleNameService;


    @Test
    public void should_throw_an_exception_when_getting_ruleName_corresponding_to_id_is_not_found() {
        //given
        Optional<RuleName> emptyRuleName = Optional.empty();
        when(ruleNameRepository.findById(anyInt())).thenReturn(emptyRuleName);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    ruleNameService.getById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid ruleName id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_updating_ruleName_corresponding_to_id_is_not_found() {
        //given
        RuleName testRuleName = new RuleName();
        testRuleName.setId(55);
        Optional<RuleName> emptyRuleName = Optional.empty();
        when(ruleNameRepository.findById(55)).thenReturn(emptyRuleName);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    ruleNameService.update(testRuleName);
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid ruleName id", thrown.getMessage());
    }

    @Test
    public void should_throw_an_exception_when_deleting_ruleName_corresponding_to_id_is_not_found() {
        //given
        Optional<RuleName> emptyRuleName = Optional.empty();
        when(ruleNameRepository.findById(anyInt())).thenReturn(emptyRuleName);
        //when then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> {
                    ruleNameService.deleteById(anyInt());
                }, "IllegalArgumentException was expected");
        assertEquals("Invalid ruleName id", thrown.getMessage());
    }

    @Test
    public void should_get_ruleNameDTOs_successfully() throws Exception {
        //given
        RuleName ruleName1 = new RuleName(1, "name_1", "description_1", "json_1",
                "template_1", "sqlStr_1", "sqlPart_1");
        RuleName ruleName2 = new RuleName(2, "name_2", "description_2", "json_2",
                "template_2", "sqlStr_2", "sqlPart_2");
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName1);
        ruleNames.add(ruleName2);
        RuleNameDTO ruleNameDTO1 = new RuleNameDTO(1, "name_1", "description_1", "json_1",
                "template_1", "sqlStr_1", "sqlPart_1");
        RuleNameDTO ruleNameDTO2 = new RuleNameDTO(2, "name_2", "description_2", "json_2",
                "template_2", "sqlStr_2", "sqlPart_2");
        List<RuleNameDTO> expectedRuleNameDTOs = new ArrayList<>();
        expectedRuleNameDTOs.add(ruleNameDTO1);
        expectedRuleNameDTOs.add(ruleNameDTO2);
        when(ruleNameService.getRuleNames()).thenReturn(ruleNames);
        when(ruleNameMapper.toRuleNameDTO(ruleName1)).thenReturn(ruleNameDTO1);
        when(ruleNameMapper.toRuleNameDTO(ruleName2)).thenReturn(ruleNameDTO2);
        //when
        List<RuleNameDTO> actualRuleNameDTOs = ruleNameService.getRuleNameDTOs();
        //then
        assertEquals(expectedRuleNameDTOs, actualRuleNameDTOs);
    }

}
