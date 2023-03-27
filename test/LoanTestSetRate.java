import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoanPrivateTest {

    @Test
    @Order(1)
    public void testAnnualRate() throws Exception{
        System.out.println("Testing private variable annual rate");
        double amount = 6000.0;
        int period = 2;

        Loan loan = new Loan(amount, period);

        Field annualRateField = Loan.class.getDeclaredField("annualRate");
        annualRateField.setAccessible(true);
        double result = annualRateField.getDouble(loan);

        assertEquals(8.0, result);
    }

    @Test
    @Order(2)
    public void testMonthlyInterestRate() throws Exception{
        System.out.println("Testing private variable monthly interest rate");
        double amount = 6000.0;
        int period = 2;

        Loan loan = new Loan(amount, period);

        Field monthlyInterestRateField = Loan.class.getDeclaredField("monthlyInterestRate");
        monthlyInterestRateField.setAccessible(true);
        double result = monthlyInterestRateField.getDouble(loan);

        assertEquals(8.0, result);
    }
}