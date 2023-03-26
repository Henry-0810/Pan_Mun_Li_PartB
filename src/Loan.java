
public class
Loan {

//----------------------------------
//    Data Members
//----------------------------------
    // Constant for the number of months in a year
    private final int MONTHS_IN_YEAR = 12;
    // The amount of the loan
    private double loanAmount;
    // Yearly interest rate
    private double annualRate;
    //The monthly interest rate
    private double monthlyInterestRate;
    // The number of monthly payments
    private int numberOfPayments;

    //----------------------------------
//    Constructor
//----------------------------------
    public Loan() {
    }

    //Creates a new Loan object with passed values.
    public Loan(double amount, int period) {
        setAmount(amount);
        setPeriod(period);
        setRate(period);
    }


    //Returns the loan amount.
    public double getAmount() {
        return loanAmount;
    }

    //Returns the loan period in the number of years.
    public int getPeriod()
    {
        return numberOfPayments / MONTHS_IN_YEAR;
    }

    //Returns the annual interest rate.
    public double getRate() { return annualRate; }

    //Returns the monthly payment
    public double getMonthlyPayment() {
        double monthlyPayment;
        monthlyPayment = (loanAmount * monthlyInterestRate)
                / (1 - Math.pow(1 / (1 + monthlyInterestRate),
                numberOfPayments));
        return monthlyPayment;
    }

    //Returns the total payment
    public double getTotalPayment() {
        double totalPayment;
        totalPayment = getMonthlyPayment() * numberOfPayments;
        return totalPayment;
    }

    //Sets the loan amount of this loan.
    private void setAmount(double amount) throws IllegalArgumentException {

        if (amount <= 0.0) {
            throw new IllegalArgumentException();
        }

        loanAmount = amount;

    }

    //Sets the interest rate of this loan.
    private void setRate(int period) {

        if((loanAmount >=500) && (loanAmount <=5000)) {
            if (period >= 1 && period <= 3) {
                annualRate = 10;

            } else {

                annualRate = 6;
            }
        }
        else if ((loanAmount >=5001) && (loanAmount <=10000))
        {
            if(period >=1 && period <= 3 ){
                annualRate = 8;

            }

            else
            {
                annualRate = 5;
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }

            monthlyInterestRate = annualRate / 100.0 / MONTHS_IN_YEAR;
        }

        //Sets the loan period of this loan.
        private void setPeriod (int periodInYears)
        {
            if (periodInYears < 1 || periodInYears > 5)
                throw new IllegalArgumentException();
            else
                numberOfPayments = periodInYears * MONTHS_IN_YEAR;
        }

    }


