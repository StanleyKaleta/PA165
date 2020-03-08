package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {
    public static final Currency CZK = Currency.getInstance("CZK");
    public static final Currency EUR = Currency.getInstance("EUR");

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context/appContext.xml");

        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);

        BigDecimal result = currencyConvertor.convert(EUR, CZK, BigDecimal.ONE);

        System.out.println(result);
    }
}
