app.service('ManufacturerService', ['$http', '$q', function($http, $q) {
	var ManufacturerService = this;

	ManufacturerService.baseUrl = 'rest/manufacturers';

    ManufacturerService.list = function() {
        var deferred = $q.defer();
        $http.get(ManufacturerService.baseUrl).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
    }

}]);