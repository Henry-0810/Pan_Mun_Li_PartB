import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoanTest {
    Loan loan;

    @Test
    @Order(1)
    public void testConstructor(){
        //no argument constructor
        loan = new Loan();
        assertNotNull(loan);

        //multi-argument constructor
        double expectedLoanAmount = 10000;
        int expectedPeriod = 2;

        loan = new Loan(expectedLoanAmount,expectedPeriod);

        assertEquals(expectedLoanAmount,loan.getAmount());
        assertEquals(expectedPeriod,loan.getPeriod());
    }

    @Test
    @Order(2)
    public void testGetters(){
        loan = new Loan(1000,3);
        //expected
        double expectedLoanAmount = 1000;
        int expectedPeriod = 3;
        double expectedRate = 10;
        double expectedMonthlyPayment = 32.27;
        double expectedTotalPayment = 1161.62;

        //test if expected matches actual
        assertEquals(expectedLoanAmount, loan.getAmount());
        assertEquals(expectedPeriod,loan.getPeriod());
        assertEquals(expectedRate,loan.getRate());
        assertEquals(expectedMonthlyPayment,Double.parseDouble(String.format("%.2f",loan.getMonthlyPayment())));
        assertEquals(expectedTotalPayment,Double.parseDouble(String.format("%.2f",loan.getTotalPayment())));
    }


}