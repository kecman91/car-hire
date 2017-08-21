/**
 * Created by IMI on 2017. 06. 11..
 */
app.service('ToasterService', ['toaster', function(toaster) {
    var ToasterService = this;

    ToasterService.showSuccess = function(message) {
        toaster.pop({
            type: 'success',
            body: message
        });
    };

    ToasterService.showWarning = function(message) {
        toaster.pop({
            type: 'warning',
            body: message
        });
    };

    ToasterService.showError = function(message) {
        toaster.pop({
            type: 'error',
            title: 'Greška!',
            body: message
        });
    };

}]);