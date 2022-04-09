package dto;

import com.github.javafaker.Faker;
import pojo.PayPalProductPojo;

public class PayPalProductData {

    private PayPalProductPojo payPalProductPojo;
    private Faker fs;

    public PayPalProductData() {
        this.payPalProductPojo = new PayPalProductPojo();
        this.fs = new Faker();
    }

    public PayPalProductPojo createPayPalProduct() {
        payPalProductPojo.setName(fs.pokemon().name());
        payPalProductPojo.setDescription(fs.pokemon().name());
        payPalProductPojo.setType("SERVICE");
        payPalProductPojo.setCategory("SOFTWARE");
        payPalProductPojo.setImage_url("https://example.com/streaming.jpg");
        payPalProductPojo.setHome_url("https://example.com/home");

        return payPalProductPojo;
    }
}
