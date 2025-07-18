package All;

import java.time.LocalDateTime;

public class DeletedProduct {
    private Product product;
    private LocalDateTime deletedAt;

    public DeletedProduct(Product product, LocalDateTime deletedAt) {
        this.product = product;
        this.deletedAt = deletedAt;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
