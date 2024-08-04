package com.pcs.beanValidation;

import com.pcs.web.dto.BidListDTO;
import com.pcs.web.dto.CurvePointDTO;
import com.pcs.web.dto.RatingDTO;
import com.pcs.web.dto.TradeDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationAnnotationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void when_curvePoint_values_are_valid_then_validation_succeeds() {
        //given
        CurvePointDTO curvePointDTO = new CurvePointDTO(1, "1", "55", "4");
        //when
        Set<ConstraintViolation<CurvePointDTO>> violations = validator.validate(curvePointDTO);
        //then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_curvePoint_values_are_invalid_then_validation_fails() {
        //given
        CurvePointDTO curvePointDTO = new CurvePointDTO(1, "", "lettersInsteadOfNumbers", "4 4");
        //when
        Set<ConstraintViolation<CurvePointDTO>> violations = validator.validate(curvePointDTO);
        //then
        assertFalse(violations.isEmpty());
        assertThat(violations).hasSize(4);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "CurveId is mandatory",
                        "CurveId must be an integer",
                        "Term must be a numeric value",
                        "Value must be a numeric value");
    }

    @Test
    public void when_bidList_values_are_valid_then_validation_succeeds() {
        //given
        BidListDTO bidListDTO = new BidListDTO(1, "account_1", "type_1", "44");
        //when
        Set<ConstraintViolation<BidListDTO>> violations = validator.validate(bidListDTO);
        //then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_bidList_values_are_invalid_then_validation_fails() {
        //given
        BidListDTO bidListDTO = new BidListDTO(1, "", "", "4j4");
        //when
        Set<ConstraintViolation<BidListDTO>> violations = validator.validate(bidListDTO);
        //then
        assertFalse(violations.isEmpty());
        assertThat(violations).hasSize(3);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Account is mandatory",
                        "Type is mandatory",
                        "BidQuantity must be a numeric value");
    }

    @Test
    public void when_trade_values_are_valid_then_validation_succeeds() {
        //given
        TradeDTO tradeDTO = new TradeDTO(1, "account_1", "type_1", "44");
        //when
        Set<ConstraintViolation<TradeDTO>> violations = validator.validate(tradeDTO);
        //then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_trade_values_are_invalid_then_validation_fails() {
        //given
        TradeDTO tradeDTO = new TradeDTO(1, "", "", "4.4.1");
        //when
        Set<ConstraintViolation<TradeDTO>> violations = validator.validate(tradeDTO);
        //then
        assertFalse(violations.isEmpty());
        assertThat(violations).hasSize(3);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Account is mandatory",
                        "Type is mandatory",
                        "BuyQuantity must be a numeric value");
    }

    @Test
    public void when_rating_values_are_valid_then_validation_succeeds() {
        //given
        RatingDTO ratingDTO = new RatingDTO(1, "MoodysRating_1", "SandPRating_1",
                "FitchRating_1", "11");
        //when
        Set<ConstraintViolation<RatingDTO>> violations = validator.validate(ratingDTO);
        //then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void when_rating_values_are_invalid_then_validation_fails() {
        //given
        RatingDTO ratingDTO = new RatingDTO(1, "", "", "", "1hu78");
        //when
        Set<ConstraintViolation<RatingDTO>> violations = validator.validate(ratingDTO);
        //then
        assertFalse(violations.isEmpty());
        assertThat(violations).hasSize(4);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "MoodysRating is mandatory",
                        "SandPRating is mandatory",
                        "FitchRating is mandatory",
                        "OrderNumber must be an integer");
    }

}
