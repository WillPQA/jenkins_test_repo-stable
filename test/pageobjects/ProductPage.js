const Page = require('./page');

/**
 * sub page containing specific selectors and methods for a specific page
 */
class ProductPage extends Page {
    /**
     * define selectors using getter methods
     */
    get btnAddToCart(){
        return $('button[name="save_to_cart"]');
    }
    get imageProductImage(){
        return $('#product-section').$('figure').$('img');
    }
    get labelPrice(){
        return $('#Description').$('h2').getText();
    }
}

module.exports = new ProductPage();
