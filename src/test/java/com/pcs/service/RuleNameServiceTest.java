package com.pcs.service;

import com.pcs.model.RuleName;
import com.pcs.repository.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;
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

}
