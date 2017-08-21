app.service('ModelService', ['$http', '$q', function($http, $q) {
	var ModelService = this;

	ModelService.baseUrl = 'rest/models';

    ModelService.list = function() {
        var deferred = $q.defer();
		$http.get(ModelService.baseUrl).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

    ModelService.listByManufacturer = function(manufacturerCode) {
        var deferred = $q.defer();
		$http.get(ModelService.baseUrl + '/modelsByManufacturer', {
			params: {
				manufacturerCode: manufacturerCode
			}
		}).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

}]);