
const Page = require('./page');
const targetProductIndex = 2;//0 based

/**
 * sub page containing specific selectors and methods for a specific page
 */
class ProductCategoryPage extends Page {
    /**
     * define selectors using getter methods
     */
    get btnTargetProduct() {
        return $('div[class="cell categoryRight"]').$('ul').$$('li')[targetProductIndex];
    }
}

module.exports = new ProductCategoryPage();
