package com.pcs.web.mapper;

import com.pcs.model.RuleName;
import com.pcs.repository.RuleNameRepository;
import com.pcs.service.RuleNameService;
import com.pcs.web.dto.RuleNameDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RuleNameMapperTest {

    @Mock
    private RuleNameRepository ruleNameRepository;
    @InjectMocks
    private RuleNameMapper ruleNameMapper;
    @Mock
    private RuleNameService ruleNameService;


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
        //when
        List<RuleNameDTO> actualRuleNameDTOs = ruleNameMapper.getRuleNameDTOs();
        //then
        assertEquals(expectedRuleNameDTOs, actualRuleNameDTOs);
    }

}