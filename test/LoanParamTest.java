import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoanParamTest {

    @ParameterizedTest
    @CsvFileSource(resources = "loanParameters/PositiveParamTestFile.csv",numLinesToSkip = 2)
    @Order(1)
    public void positiveTest(double amount, int period,double expected){
        Loan loan = new Loan(amount,period);
        assertEquals(expected,loan.getRate());
    }

        @ParameterizedTest
        @CsvFileSource(resources = "loanParameters/NegativeParamTestFileAmount.csv",numLinesToSkip = 2)
        @Order(2)
        public void negativeTestAmount(double amount) {
            if(amount == 0 || amount < 500 || amount > 10000){
                assertThrows(IllegalArgumentException.class, () -> {
                    Loan loan = new Loan(amount,3);
                });
            }
            else if(Double.isNaN(amount)){
                assertThrows(NumberFormatException.class, () -> {
                    Loan loan = new Loan(amount,3);
                });
            }
            else{
                assertThrows(NullPointerException.class, () -> {
                    Loan loan = new Loan(amount,3);
                });
            }
        }

        @ParameterizedTest
        @CsvFileSource(resources = "loanParameters/NegativeParamTestPeriod.csv",numLinesToSkip = 2)
        @Order(3)
        public void negativeTestPeriod(String periodString){
            try{
                int period = Integer.parseInt(periodString);
                if(period == 0 || period < 1 || period > 5){
                    assertThrows(IllegalArgumentException.class, () -> {
                        Loan loan = new Loan(500,period);
                    });
                }
                else{
                    assertThrows(NullPointerException.class, () -> {
                        Loan loan = new Loan(500,period);
                    });
                }
            }
            catch(NumberFormatException e){
                assertThrows(NumberFormatException.class, () -> {
                    Loan loan = new Loan(500,Integer.parseInt(periodString));
                });
            }
        }

}