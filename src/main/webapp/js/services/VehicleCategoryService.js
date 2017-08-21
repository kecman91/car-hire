app.service('VehicleCategoryService', ['$http', '$q', function($http, $q) {
    var VehicleCategoryService = this;

    VehicleCategoryService.baseUrl = 'rest/categories';

    VehicleCategoryService.list = function() {
        var deferred = $q.defer();
		$http.get(VehicleCategoryService.baseUrl).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

}]);