package com.pcs.web.mapper;

import com.pcs.model.RuleName;
import com.pcs.service.RuleNameService;
import com.pcs.web.dto.RuleNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleNameMapper {

    @Autowired
    private RuleNameService ruleNameService;

    public RuleNameDTO toRuleNameDTO(RuleName ruleName) {
        return new RuleNameDTO(
                ruleName.getId(),
                ruleName.getName(),
                ruleName.getDescription(),
                ruleName.getJson(),
                ruleName.getTemplate(),
                ruleName.getSqlStr(),
                ruleName.getSqlPart()
        );
    }

    public RuleName toRuleName(RuleNameDTO ruleNameDTO) {
        return new RuleName(
                ruleNameDTO.getId(),
                ruleNameDTO.getName(),
                ruleNameDTO.getDescription(),
                ruleNameDTO.getJson(),
                ruleNameDTO.getTemplate(),
                ruleNameDTO.getSqlStr(),
                ruleNameDTO.getSqlPart()
        );
    }

    public List<RuleNameDTO> getRuleNameDTOs() {
        List<RuleName> ruleNames = ruleNameService.getRuleNames();
        List<RuleNameDTO> ruleNameDTOs = new ArrayList<>();
        ruleNames.forEach(ruleName -> {
            ruleNameDTOs.add(toRuleNameDTO(ruleName));
        });
        return ruleNameDTOs;
    }

}
