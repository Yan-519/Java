package Lab3;

public class Customer {

    private String name;
    private String creditCard;
    private String id;

    public Customer(String name, String creditCard, String id) {
        this.name = name;
        this.creditCard = creditCard;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " " + id;
    }
}