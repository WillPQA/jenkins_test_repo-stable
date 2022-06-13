const Page = require('./page');
const fetch = (...args) => import('node-fetch').then(({default: fetch}) => fetch(...args));
/**
 * sub page containing specific selectors and methods for a specific page
 */
class ContactUs extends Page {
    /**
     * define selectors using getter methods
     */
    
    get #dropdownCategory(){
        return $('select[name="categoryListboxContactUs"]');
    }
    get #optionCategories(){
        return this.#dropdownCategory.$$('option');
    }
    get #dropdownProduct(){
        return $('select[name="productListboxContactUs"]');
    }
    get #optionProducts(){
        return this.#dropdownProduct.$$('option');
    }
    get #fieldEmail(){
        return $('input[name="emailContactUs"]');
    }
    get #fieldSubject(){
        return $('textarea[name="subjectTextareaContactUs"]');
    }
    get btnSubmitContactRequest(){
        return $('#send_btnundefined');
    }

    async sendContactRequest(productId, email, message){
        const response = await fetch('https://www.advantageonlineshopping.com/catalog/api/v1/products/'+productId);
        const json = await response.json();
        const categoryId = json.categoryId;
        const productName = json.productName;

        await this.btnSubmitContactRequest.scrollIntoView();

        await this.#dropdownCategory.waitForClickable({timeout: 2000});
        await this.#dropdownCategory.click();
        await this.#optionCategories[categoryId].click();

        await browser.waitUntil(async () => {
            return(await this.#optionProducts.length) > 1;
        }, {timeout: 1000, timeoutMsg: 'expected field to be populated'});
        
        await this.#dropdownProduct.click();
        let productsInCategory = await this.#optionProducts;
        productsInCategory.forEach(async (product) => {
            let name = await product.getText();
            if(name.toLowerCase() === productName.toLowerCase()){
                await product.click();
                return;
            }
        });

        await this.#fieldEmail.setValue(email);

        await this.#fieldSubject.setValue(message);
    }

}

module.exports = new ContactUs();