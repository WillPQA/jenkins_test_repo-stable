const Page = require('./page');
const productPageId = "#tabletsTxt";

/**
 * sub page containing specific selectors and methods for a specific page
 */
class HomePage extends Page {

    get btnProductCategory(){
        $(productPageId).waitForClickable({timeout: 2000, interval: 500, reverse: false, timeoutMsg: "Failed to get product category in a clickable state"});
        return $(productPageId);
    }
    
    open () {
        super.open('');
        $('[class="loader"]').waitForClickable({timeout: 2000, interval: 500, reverse: true, timeoutMsg: "'loader' is obstructing page"});
    }
}

module.exports = new HomePage();