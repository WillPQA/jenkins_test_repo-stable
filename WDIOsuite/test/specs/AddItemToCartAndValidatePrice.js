// const PageHeader = require('../pageobjects/PageHeader');
// const HomePage = require('../pageobjects/HomePage');
// const ProductCategoryPage = require('../pageobjects/ProductCategoryPage');
// const ProductPage = require('../pageobjects/ProductPage');
// const CartPage = require('../pageobjects/CartPage');

// let priceOnProductPage = "";
// let priceOnCartPage = "";

// describe('Add Item to Cart and Validate Prices', () => {
//     it('should click the product category page', async () => {
//         HomePage.open();
//         await HomePage.btnProductCategory.click();
//     });
//     it('should click on a product', async () => {
//         await ProductCategoryPage.btnTargetProduct.click();
//     });
//     it('should save the price displayed and add the product to cart', async () => {
//         priceOnProductPage = await ProductPage.labelPrice;
//         await ProductPage.btnAddToCart.click();
//     });
//     it('should go to the cart and get the total displayed there', async () => {
//         await PageHeader.btnCartIcon.click();
//         priceOnCartPage = await CartPage.labelPrice;
//     });
//     it('should assert that the prices match', async () => {
//         expect(priceOnProductPage).toEqual(priceOnCartPage)

//     });
// });


