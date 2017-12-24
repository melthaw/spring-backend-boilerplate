export default function escape(Constants) {
    return function (input) {
        if (input) {
            return Constants.basicApiUrl + '/files/' + window.encodeURIComponent(window.encodeURIComponent(input));
        }
        return "";
    }
}
