app.service('Util', [function() {
    var Util = this;

    Util.setToPristine = function(form) {
        angular.forEach(form.$$controls, function(formControl) {
            formControl.$setViewValue(undefined);
            formControl.$render();
            formControl.$commitViewValue();
        });
        form.$setPristine();
    }

}]);