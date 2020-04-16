package novikova_task_3.operations;

import java.math.BigDecimal;

public class Convertation {
    private static BigDecimal RATE_USD = new BigDecimal(74.26);
    private static BigDecimal RATE_EUR = new BigDecimal(80.55);

    public static BigDecimal СurrencyToRubles(BigDecimal addSum, BigDecimal curRate) {
        return addSum.multiply(curRate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal RublesToCurrency(BigDecimal addSum, BigDecimal curRate) {
        return addSum.divide(curRate,2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal CurrencyToCurrency(BigDecimal addSum, BigDecimal curFrom, BigDecimal curTo) {
        addSum = СurrencyToRubles(addSum, curFrom);
        return RublesToCurrency(addSum, curTo);
    }

    public static BigDecimal getRateEUR() {
        return RATE_EUR;
    }

    public static BigDecimal getRateUSD() {
        return RATE_USD;
    }
}