// const PageHeader = require('../pageobjects/PageHeader');
// const HomePage = require('../pageobjects/HomePage');
// const ProductCategoryPage = require('../pageobjects/ProductCategoryPage');
// const ProductPage = require('../pageobjects/ProductPage');

// const tolerance = 0.5


// describe('first test', async () => {
//     it('should go to a product page', async () => {
//         HomePage.open()
//         await HomePage.btnProductCategory.click()
//         await ProductCategoryPage.btnTargetProduct.click()
//     })
//     it('should save an image of the product', async () => {
//         await browser.checkElement(await ProductPage.imageProductImage, "baselineImage")
//     })
//     it('should expect that the image matches the baseline image', async () => {
//         await browser.setTimeout({'script': 120000})
//         await browser.debug()

//         let margin = await browser.checkElement(await ProductPage.imageProductImage, "baselineImage")

//         try{
//             expect(margin).toBeLessThan(tolerance)
//         } catch(e){
//             await browser.checkElement(await ProductPage.imageProductImage, "displayedImage", {})
//             console.error("Images do not match!!! Margin: " + margin)
//             throw new Error
//         }
        
//     })
// })