const Page = require('./page');

/**
 * sub page containing specific selectors and methods for a specific page
 */
class PageHeader extends Page {
    /**
     * define selectors using getter methods
     */
    get btnUserIcon() { 
        $('#menuUserLink').waitForClickable({timeout: 5000});
        return $('#menuUserLink');
    }

    get btnCartIcon(){
        $('#menuCart').waitForClickable({timeout: 5000});
        return $('#menuCart');
    }

    get fieldUsername() {
        return $('[name="username"]');
    }

    get fieldPassword() {
        return $('[name="password"]');
    }

    get checkboxRememberMe() {
        return $('[name="remember_me"]');
    }

    get btnSignIn () {
        return $('#sign_in_btnundefined');
    }

    get divPopUp() {
        return $('div[class="PopUp"]');
    }

    async login(username, password, rememberMe) {
        await this.btnUserIcon.click();
        await this.fieldUsername.waitForClickable({timeout: 5000});

        await this.fieldUsername.setValue(username);
        await this.fieldPassword.setValue(password);
        if(rememberMe){
            await this.checkboxRememberMe.click();
        }
        await this.btnSignIn.click();
    }
}

module.exports = new PageHeader();
