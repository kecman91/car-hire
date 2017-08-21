app.service('VehicleService', ['$http', '$q', function($http, $q) {
	var VehicleService = this;

	VehicleService.baseUrl = 'rest/vehicles';

    VehicleService.list = function() {
        var deferred = $q.defer();
		$http.get(VehicleService.baseUrl).then(function(response) {
		    deferred.resolve(response.data);
        }, function(error) {
		    deferred.reject(error);
        });
		return deferred.promise;
	};

    VehicleService.listFreeVehicles = function(dateFrom, numDays) {
        var deferred = $q.defer();
		$http.get(VehicleService.baseUrl + '/freeVehicles', {
			params: {
				dateFrom: dateFrom.getTime(),
				numDays: numDays
			}
		}).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

    VehicleService.update = function(vehicle) {
        var deferred = $q.defer();
		$http.put(VehicleService.baseUrl, vehicle).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

    VehicleService.save = function(vehicle) {
        var deferred = $q.defer();
		$http.post(VehicleService.baseUrl, vehicle).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

    VehicleService.delete = function(vehicle) {
        var deferred = $q.defer();
		$http.delete(VehicleService.baseUrl, {
			params: {
				vehicleId: vehicle.regNumber
			}
		}).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

    VehicleService.listFilteredVehicles = function(criteria) {
        var deferred = $q.defer();
		$http.post(VehicleService.baseUrl + '/filteredVehicles', criteria).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

    VehicleService.search = function(searchTerm) {
        var deferred = $q.defer();
        $http.get(VehicleService.baseUrl + '/search', {
            params: {
                searchTerm: searchTerm
            }
        }).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
    }

}]);