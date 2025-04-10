import java.util.List;

public class LoyalSystem {
    private DiscountManager discountManager;

    public LoyalSystem(DiscountManager discountManager) {
        this.discountManager = discountManager;
    }

    private Integer getDiscount(Long buyerId) {
        try {
            Integer discount = discountManager.getDiscount(buyerId); // TODO: retries
            if (discount == null) {
                return 0;
            }
            if (discount < 0 || discount > 100) {
                throw new RuntimeException("Wrong discount value");
                // TODO: notify discount team about wrong discount value for buyerId
            }
            return discount;
        } catch (Exception e) {
            throw new RuntimeException("Cannot do at this time");
        }
    }

    // buyerId and basket should not be null
    public Basket applyDiscounts(Long buyerId, Basket basket) {
        Integer discount = getDiscount(buyerId);
        for (Purchase purchase : basket.getPurchases()) {
            purchase.applyDiscount(discount);
        }
        return basket;
    }

}
