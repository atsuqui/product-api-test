package org.test.product;



import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class ProductDetailDTO {

    private String id;

    private String name;

    private BigDecimal price;

    private Boolean availability;




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDetailDTO productDetail = (ProductDetailDTO) o;
        return Objects.equals(this.id, productDetail.id) &&
                Objects.equals(this.name, productDetail.name) &&
                Objects.equals(this.price, productDetail.price) &&
                Objects.equals(this.availability, productDetail.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, availability);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductDetailDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", availability=").append(availability);
        sb.append('}');
        return sb.toString();
    }
}

