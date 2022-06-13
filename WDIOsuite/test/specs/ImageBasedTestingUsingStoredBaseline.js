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
//         await browser.setTimeout({'script': 120000})
//     })
//     it('should expect that the image matches the baseline image', async () => {
//         let margin = await browser.checkElement(await ProductPage.imageProductImage, "savedBaselineImage2")

//         try{
//             expect(margin).toBeLessThan(tolerance)
//         } catch(e){
//             await browser.checkElement(await ProductPage.imageProductImage, "displayedImage", {})
//             console.error("Images do not match!!! Margin: " + margin)
//             throw new Error
//         }
        
//     })
// })