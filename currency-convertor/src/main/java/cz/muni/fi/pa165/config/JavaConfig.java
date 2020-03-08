package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"cz.muni.fi.pa165.currency"})
@EnableAspectJAutoProxy
public class JavaConfig {

//    @Bean
//    public ExchangeRateTable exchangeRateTable(){
//        return new ExchangeRateTableImpl();
//    }
//
//    @Bean
//    public CurrencyConvertor currencyConvertor(ExchangeRateTable exchangeRateTable){
//        return new CurrencyConvertorImpl(exchangeRateTable);
//    }
}
