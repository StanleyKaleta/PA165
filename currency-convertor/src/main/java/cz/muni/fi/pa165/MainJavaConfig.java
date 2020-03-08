package cz.muni.fi.pa165;

import cz.muni.fi.pa165.config.JavaConfig;
import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainJavaConfig {
    public static final Currency CZK = Currency.getInstance("CZK");
    public static final Currency EUR = Currency.getInstance("EUR");

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);

        BigDecimal result = currencyConvertor.convert(EUR, CZK, BigDecimal.ONE);

        System.out.println(result);
    }
}
