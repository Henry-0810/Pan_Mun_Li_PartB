import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoanTest {
    Loan loan;
    //Order 1 to 2 is to test Constructor and test getters/public methods
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

    //Order 3 to 8 is to test private members and private method such as setters
    //Testing the private members that are used in setRate() method
    @Test
    @Order(3)
    public void testAnnualRate() throws Exception{
        System.out.println("Testing private member annualRate ... \n");
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
    @Order(4)
    public void testMonthlyInterestRate() throws Exception{
        System.out.println("Testing private member monthlyInterestRate ...\n");
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
    @Order(5)
    public void testLoanAmount() throws Exception {
        System.out.println("Testing private member loanAmount ...\n");
        Loan loan = new Loan(2500,3);

        Class<? extends Loan> secretClass = loan.getClass();
        Field loanAmountField = secretClass.getDeclaredField("loanAmount");

        loanAmountField.setAccessible(true);
        double result = loanAmountField.getDouble(loan);

        assertEquals(2500,result);
    }


    @Test
    @Order(6)
    public void testSetRate() throws Exception {
        System.out.println("Test private method setRate() ...\n");
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

        //Testing rate with amount that exceeds 10000 or below 499
        assertThrows(IllegalArgumentException.class,()->{
            Loan loan1 = new Loan(499,1);
            Loan loan2 = new Loan(12000,1);
        });

        //Testing rate with period that exceeds 5 and below 1
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan1 = new Loan(500,0);
            Loan loan2 = new Loan(500,6);
        });
    }

    //setAmount() method is worth testing because it throws an exception in the method
    @Test
    @Order(7)
    public void testSetAmount() throws Exception {
        System.out.println("Testing private method setAmount() ...\n");
        Loan loan = new Loan();

        Class<? extends Loan> secretClass = loan.getClass();
        Method setAmountMethod = secretClass.getDeclaredMethod("setAmount", double.class);

        setAmountMethod.setAccessible(true);

        loan = new Loan(5000,1);
        setAmountMethod.invoke(loan,500);
        assertEquals(500,loan.getAmount());

        assertThrows(IllegalArgumentException.class, () -> {
            Loan l = new Loan(-1,1);
        });
    }

    @Test
    @Order(8)
    public void testSetPeriod() throws Exception{
        System.out.println("Testing private method setPeriod() ...\n");
        Loan loan = new Loan();

        Class<? extends Loan> secretClass = loan.getClass();
        Method setPeriodMethod = secretClass.getDeclaredMethod("setPeriod", int.class);

        setPeriodMethod.setAccessible(true);

        loan = new Loan(5000,1);
        setPeriodMethod.invoke(loan,3);
        assertEquals(3,loan.getPeriod());

        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan1 = new Loan(500,0);
            Loan loan2 = new Loan(500,6);
        });
    }

    //Order 9 to 11 is to test the parameters (positive test and negative test)
    @ParameterizedTest
    @CsvFileSource(resources = "loanParameters/PositiveParamTestFile.csv",numLinesToSkip = 2)
    @Order(9)
    public void positiveTest(double amount, int period,double expected){
        Loan loan = new Loan(amount,period);
        assertEquals(expected,loan.getRate());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "loanParameters/NegativeParamTestAmount.csv",numLinesToSkip = 2)
    @Order(10)
    @DisplayName("Negative Testing for amount")
    public void negativeTestAmount(double amount) {
        assertThrows(IllegalArgumentException.class, () ->{
            Loan loan = new Loan(amount,3);
        });
    }


    @ParameterizedTest
    @CsvFileSource(resources = "loanParameters/NegativeParamTestPeriod.csv",numLinesToSkip = 2)
    @Order(11)
    @DisplayName("Negative Testing for period")
    public void negativeTestPeriod(int period){
        assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan(500,period);
        });
    }



}