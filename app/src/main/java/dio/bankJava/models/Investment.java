package dio.bankJava.models;

public record Investment(
    long id,
    long tax,
    long initialFunds
) {
    @Override
    public String toString() {
        return "Investment{" + "id = " + id + ", Tax = " + tax + "%" +
                ", InitialFunds = " + (initialFunds / 100) + "," + (initialFunds % 100) + "}";
    }
}
