class AuthenticationServcie {

    constructor($http, Constants) {
        this.$http = $http;
        this.Constants = Constants;
    }

    authenticate() {
        return this.$http.get(this.Constants.basicApiUrl + '/my/profile');
    }

    getUserGrantedMenus() {
        return this.$http.get(this.Constants.basicApiUrl + '/my/menus');
    }

    getSystemSetting() {
        return this.$http.get('/guest/application/branding');
    }

}

export default AuthenticationServcie;
