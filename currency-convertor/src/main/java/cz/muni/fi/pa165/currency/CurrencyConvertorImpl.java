package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        if (sourceCurrency == null){
            throw new IllegalArgumentException("sourceCurrency is null!");
        }
        if (targetCurrency == null){
            throw new IllegalArgumentException("targetCurrency is null!");
        }
        if (sourceAmount == null){
            throw new IllegalArgumentException("sourceAmount is null!");
        }
        try {
            return sourceAmount.multiply(exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        } catch (ExternalServiceFailureException e) {
            throw new UnknownExchangeRateException(sourceAmount + " and "+targetCurrency+" pair not defined!");
        }
    }

}
