package com.pcs.web.mapper;

import com.pcs.model.RuleName;
import com.pcs.web.dto.RuleNameDTO;
import org.springframework.stereotype.Component;

@Component
public class RuleNameMapper {

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

}
