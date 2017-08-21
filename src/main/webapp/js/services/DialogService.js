app.service('DialogService', ['$uibModal', '$q', function ($uibModal, $q) {
    var DialogService = this;

    DialogService.showConfirmDialog = function(title, message) {
        var modal = $uibModal.open({
            size: 'sm',
            templateUrl: 'views/commonModals/confirmModal.html',
            bindToController: true,
            controller: [function() {}],
            controllerAs: 'ctrl',
            resolve: {
                title: function() {
                    return title;
                },
                message: function() {
                    return message;
                }
            }
        });

        var deferred = $q.defer();
        modal.result.then(function() {
            deferred.resolve();
        }, function() {
            deferred.reject();
        });
        return deferred.promise;
    }
}]);

