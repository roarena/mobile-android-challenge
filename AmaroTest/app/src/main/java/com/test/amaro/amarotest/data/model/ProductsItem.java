package com.test.amaro.amarotest.data.model;

import com.test.amaro.amarotest.utils.Utils;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class ProductsItem {
    private String codeColor;
    private String regular_price;
    private String image;
    private String color;
    private List<SizesItem> sizes;
    private String actual_price;
    private String installments;
    private String name;
    private String colorSlug;
    private String style;
    private String discount_percentage;


    private boolean on_sale;

    public void setCodeColor(String codeColor) {
        this.codeColor = codeColor;
    }

    public String getCodeColor() {
        return codeColor;
    }

    public void setRegular_price(String regular_price) {
        this.regular_price = regular_price;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setSizes(List<SizesItem> sizes) {
        this.sizes = sizes;
    }

    public List<SizesItem> getSizes() {

        return Utils.clearNotAvailableSizes(sizes);
    }

    public void setActual_price(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getActual_price() {
        return actual_price;
    }

    public void setInstallments(String installments) {
        this.installments = installments;
    }

    public String getInstallments() {
        return installments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setColorSlug(String colorSlug) {
        this.colorSlug = colorSlug;
    }

    public String getColorSlug() {
        return colorSlug;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setDiscount_percentage(String discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }

    public void setOn_sale(boolean on_sale) {
        this.on_sale = on_sale;
    }

    public boolean isOn_sale() {
        return on_sale;
    }

    @Override
    public String toString() {
        return
                "ProductsItem{" +
                        "code_color = '" + codeColor + '\'' +
                        ",regular_price = '" + regular_price + '\'' +
                        ",image = '" + image + '\'' +
                        ",color = '" + color + '\'' +
                        ",sizes = '" + sizes + '\'' +
                        ",actual_price = '" + actual_price + '\'' +
                        ",installments = '" + installments + '\'' +
                        ",name = '" + name + '\'' +
                        ",color_slug = '" + colorSlug + '\'' +
                        ",style = '" + style + '\'' +
                        ",discount_percentage = '" + discount_percentage + '\'' +
                        ",on_sale = '" + on_sale + '\'' +
                        "}";
    }
}