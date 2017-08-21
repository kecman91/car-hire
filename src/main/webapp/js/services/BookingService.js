app.service('BookingService', ['$http', '$q', function($http, $q) {
	var BookingService = this;

	BookingService.baseUrl = 'rest/bookings';

	BookingService.save = function(customer, vehicle, booking) {
		var data = {
            paymentReceived: booking.payed,
            dateFrom: booking.dateFrom,
            numberOfDays: booking.numDays,
            vehicleRegNumber: vehicle
        };

		if (angular.isObject(customer)) {
		    data.customer = customer;
        } else {
		    data.customerId = customer;
        }

		var deferred = $q.defer();
		$http.post(BookingService.baseUrl + '/booking', data).then(function(response) {
			deferred.resolve(response.data);
		}, function(error) {
			deferred.reject(error);
		});
		return deferred.promise;
	};

	BookingService.search = function(customerId, vehicleRegNum, unpaidOnly) {
        var deferred = $q.defer();
        $http.get(BookingService.baseUrl + '/search', {
        	params: {
        		customerId: customerId,
                vehicleRegNum: vehicleRegNum,
				unpaidOnly: unpaidOnly
			}
		}).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

	BookingService.delete = function(booking) {
        var deferred = $q.defer();
        $http.delete(BookingService.baseUrl, {
            params: {
                bookingId: booking.id
            }
        }).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	};

	BookingService.setPaid = function(booking) {
        var deferred = $q.defer();
        $http.get(BookingService.baseUrl + '/payment',{
            params: {
                bookingId: booking.id
            }
        }).then(function(response) {
            deferred.resolve(response.data);
        }, function(error) {
            deferred.reject(error);
        });
        return deferred.promise;
	}
}]);