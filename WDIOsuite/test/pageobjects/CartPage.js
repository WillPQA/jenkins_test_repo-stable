const Page = require('./page');

/**
 * sub page containing specific selectors and methods for a specific page
 */
class CartPage extends Page {
    /**
     * define selectors using getter methods
     */
    get labelPrice(){
        return $('#shoppingCart').$('table').$('tfoot').$$('tr')[0].$$('td')[1].$$('span')[1].getText();
    }
}

module.exports = new CartPage();
