package cz.fi.muni.pa165.rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.fi.muni.pa165.dto.CategoryDTO;
import cz.fi.muni.pa165.dto.Color;
import cz.fi.muni.pa165.dto.PriceDTO;
import cz.fi.muni.pa165.dto.ProductDTO;

import java.util.*;

//@JsonIgnoreProperties
public class ProductDTOMixin {
    private Long id;

    @JsonIgnore
    private byte[] image;

    private String imageMimeType;

    private String name;

    private String description;

    private Color color;

    private Date addedDate;

    private Set<CategoryDTO> categories = new HashSet<>();

    private List<PriceDTO> priceHistory = new ArrayList<>();

    private PriceDTO currentPrice;



    public byte[] getImage() {
        return image;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }
    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }


    public void setCurrentPrice(PriceDTO currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }



    public PriceDTO getCurrentPrice() {
        return currentPrice;
    }



    public List<PriceDTO> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory( List<PriceDTO> priceHistory) {
        this.priceHistory=priceHistory;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTOMixin that = (ProductDTOMixin) o;
        return Objects.equals(id, that.id) &&
                Arrays.equals(image, that.image) &&
                Objects.equals(imageMimeType, that.imageMimeType) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                color == that.color &&
                Objects.equals(addedDate, that.addedDate) &&
                Objects.equals(categories, that.categories) &&
                Objects.equals(priceHistory, that.priceHistory) &&
                Objects.equals(currentPrice, that.currentPrice);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, imageMimeType, name, description, color, addedDate, categories, priceHistory, currentPrice);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", imageMimeType='" + imageMimeType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", color=" + color +
                ", addedDate=" + addedDate +
                ", categories=" + categories +
                ", priceHistory=" + priceHistory +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
