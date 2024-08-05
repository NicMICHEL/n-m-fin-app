package com.pcs.service;

import com.pcs.model.RuleName;
import com.pcs.repository.RuleNameRepository;
import com.pcs.web.mapper.RuleNameMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    private static final Logger logger = LogManager.getLogger(RuleNameService.class);

    public List<RuleName> getRuleNames() {
        return ruleNameRepository.findAll();
    }


    public RuleName getById(Integer id) throws IllegalArgumentException {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (ruleName.isPresent()) {
            return ruleName.get();
        } else {
            logger.error("Unable to find ruleName corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid ruleName id");
        }
    }

    @Transactional
    public void save(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    @Transactional
    public void update(RuleName ruleName) throws IllegalArgumentException {
        Optional<RuleName> testRuleName = ruleNameRepository.findById(ruleName.getId());
        if (testRuleName.isPresent()) {
            ruleNameRepository.save(ruleName);
        } else {
            logger.error("Unable to find and update ruleName corresponding to id {}", ruleName.getId());
            throw new IllegalArgumentException("Invalid ruleName id");
        }
    }

    public void deleteById(Integer id) throws IllegalArgumentException {
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (ruleName.isPresent()) {
            ruleNameRepository.deleteById(id);
        } else {
            logger.error("Unable to find and delete RuleName corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid ruleName id");
        }
    }

}
