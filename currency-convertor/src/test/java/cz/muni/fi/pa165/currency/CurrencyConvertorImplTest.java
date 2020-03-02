package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private final Currency CZK = Currency.getInstance("CZK");
    private final Currency EUR = Currency.getInstance("EUR");

    @Mock
    private ExchangeRateTable exchangeRateTable;

    private CurrencyConvertor currencyConvertor;

    @Before
    public void createCurrencyConvertor(){
        this.currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {

        when(exchangeRateTable.getExchangeRate(CZK,EUR)).thenReturn(new BigDecimal("0.01"));

        assertThat(currencyConvertor.convert(CZK,EUR, new BigDecimal("1.49"))).isEqualTo(new BigDecimal("0.01"));
        assertThat(currencyConvertor.convert(CZK,EUR, new BigDecimal("1.50"))).isEqualTo(new BigDecimal("0.02"));
        assertThat(currencyConvertor.convert(CZK,EUR, new BigDecimal("2.50"))).isEqualTo(new BigDecimal("0.02"));
        assertThat(currencyConvertor.convert(CZK,EUR, new BigDecimal("2.51"))).isEqualTo(new BigDecimal("0.03"));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        try {
            currencyConvertor.convert(null, EUR, new BigDecimal("1.00"));
            fail("Should throw IllegalArgumentException!");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        try {
            currencyConvertor.convert(CZK, null, new BigDecimal("1.00"));
            fail("Should throw IllegalArgumentException!");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        try {
            currencyConvertor.convert(CZK, EUR, null);
            fail("Should throw IllegalArgumentException!");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    @Test
    public void testConvertWithUnknownCurrency() {
        fail("Test is not implemented yet.");
    }

    @Test
    public void testConvertWithExternalServiceFailure() {
        fail("Test is not implemented yet.");
    }

}
