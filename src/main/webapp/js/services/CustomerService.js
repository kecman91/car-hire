app.service('CustomerService', ['$http', '$q', function ($http, $q) {
    var CustomerService = this;

    CustomerService.baseUrl = 'rest/customers';

    CustomerService.list = function() {
        var deferred = $q.defer();
        $http.get(CustomerService.baseUrl).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
    };

    CustomerService.save = function(customer) {
        var deferred = $q.defer();
        $http.post(CustomerService.baseUrl, customer).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
    };

    CustomerService.delete = function(customer) {
        var deferred = $q.defer();
        $http.delete(CustomerService.baseUrl, {
            params: {
                customerId: customer.id
            }
        }).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
    };

    CustomerService.search = function(searchTerm) {
        var deferred = $q.defer();
        $http.get(CustomerService.baseUrl + '/search', {
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