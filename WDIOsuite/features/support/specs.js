const assert = require('assert')
const { Given, When, Then } = require('@cucumber/cucumber')
const Greeter = require('../../src/Greeter')
const PageHeader = require('../../test/pageobjects/PageHeader');
const HomePage = require('../../test/pageobjects/HomePage');

When('the greeter says hello', function () {
  this.whatIHeard = Greeter.sayHello()
});
Then('I should have heard {string}', function (expectedResponse) {
  assert.equal(this.whatIHeard, expectedResponse)
});


Given('I am on the homepage', async () => {
    HomePage.open()
})
When('I click on the user icon on the homepage', async () => {
    await PageHeader.btnUserIcon.click()
})
When('I enter {string} into the username field on the sign in popup', async (username) => {
    await PageHeader.fieldUsername.waitForClickable({timeout: 5000})
    await PageHeader.fieldUsername.setValue(username)
})
When('I enter {string} into the password field on the sign in popup', async (password) => {
    await PageHeader.fieldPassword.setValue(password)
})
When('I click on sign in', async () => {
    await PageHeader.btnSignIn.click();
})
Then('Sign in success should be valid', async () => {
    await new Promise(r => setTimeout(r, 2000));
    let style = await PageHeader.divPopUp.getAttribute("style");
    console.log(style)
    assert.equal(style, "display: none;")
})