import org.junit.jupiter.api.*;

import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoanPrivateTest {

    @Test
    @Order(1)
    public void testAnnualRate() throws Exception{
        System.out.println("Testing private variable annual rate ... \n");
        double amount = 6000.0;
        int period = 2;

        Loan loan = new Loan(amount, period);

        Class<? extends Loan> secretClass = loan.getClass();
        Field annualRateField = secretClass.getDeclaredField("annualRate");

        annualRateField.setAccessible(true);
        double result = annualRateField.getDouble(loan);

        assertEquals(8.0, result);
    }

    @Test
    @Order(2)
    public void testMonthlyInterestRate() throws Exception{
        System.out.println("Testing private variable monthly interest rate ...\n");
        double amount = 6000.0;
        int period = 2;

        Loan loan = new Loan(amount, period);

        Class<? extends Loan> secretClass = loan.getClass();
        Field monthlyInterestRateField = secretClass.getDeclaredField("monthlyInterestRate");

        monthlyInterestRateField.setAccessible(true);
        double result = monthlyInterestRateField.getDouble(loan);

        assertEquals(0.0067, result,0.001);
    }

    @Test
    @Order(3)
    public void testSetRate() throws Exception {
        System.out.println("Test set rate private method ...");
        Loan loan = new Loan();

        Class<? extends Loan> secretClass = loan.getClass();

        Method setRateMethod = secretClass.getDeclaredMethod("setRate", int.class);
        setRateMethod.setAccessible(true);

        //Testing rate with period 1-3 year, here I chose 2 years
        //amount 500-5000
        loan = new Loan(2500, 2);
        setRateMethod.invoke(loan, 2);
        assertEquals(10, loan.getRate());

        //amount 5001-10000
        loan = new Loan(7500, 2);
        setRateMethod.invoke(loan, 2);
        assertEquals(8, loan.getRate());

        //Testing rate with period 4-5 years
        //amount 500-5000
        loan = new Loan(2500, 4);
        setRateMethod.invoke(loan, 4);
        assertEquals(6, loan.getRate());

        //amount 5001-10000
        loan = new Loan(7500, 4);
        setRateMethod.invoke(loan, 4);
        assertEquals(5, loan.getRate());

        //Testing rate with amount that exceeds 10000
        assertThrows(IllegalArgumentException.class,()->{
            Loan l = new Loan(12000,1);
            setRateMethod.invoke(l,1);
        });
    }
}