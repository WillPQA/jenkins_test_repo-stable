const PageHeader = require('../pageobjects/PageHeader');
const HomePage = require('../pageobjects/HomePage');

describe('Login to advantage and verify that cart items exist', () => {
    it('should go to the homepage', async () => {
        HomePage.open();
    });
    it('should execute the login method', async () => {
        await PageHeader.login("WillPQA", password, true);
    });
    it('should take a screenshot of the cart', async () => {
        await PageHeader.btnCartIcon.click();

        await $('#shoppingCart').waitForEnabled({timeout: 10000});
        await browser.saveScreenshot("./screenshots/cart.png");
    });
});



































































let password = "Hoy8711";