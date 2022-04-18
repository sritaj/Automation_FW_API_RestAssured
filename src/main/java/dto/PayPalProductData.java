package dto;

import com.github.javafaker.Faker;
import pojo.PayPalProductPojo;

/**
 * PayPalProductData class to create data for PayPal product
 */
public class PayPalProductData {

    private PayPalProductPojo payPalProductPojo;
    private Faker fs;

    public PayPalProductData() {
        this.payPalProductPojo = new PayPalProductPojo();
        this.fs = new Faker();
    }

    /**
     * Method to create data for Paypal product
     *
     * @return PayPalProductPojo - Returns Object of PayPalProductPojo type
     */
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
