package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {

    public static final Currency CZK = Currency.getInstance("CZK");
    public static final Currency EUR = Currency.getInstance("EUR");

    public static final BigDecimal CZKEUR = BigDecimal.valueOf(27);



    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency == null) throw new IllegalArgumentException("source currency null");
        if (targetCurrency == null) throw new IllegalArgumentException("target currency null");

        if (sourceCurrency.equals(EUR) && targetCurrency.equals(CZK)) return CZKEUR;

        return null;
    }
}
