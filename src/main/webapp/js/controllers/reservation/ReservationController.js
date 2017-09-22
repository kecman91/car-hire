app.controller('ReservationController', ['$scope', 'CustomerService', 'VehicleService', 'BookingService', '$timeout',
                                         'ToasterService', 'Util',
                                         function($scope, CustomerService, VehicleService, BookingService, $timeout,
                                                  ToasterService, Util){
	var ctrl = this;
	
	$scope.$parent.location = {
		reservation: true,
		vehicles: false,
		customers: false,
		bookings: false
	};

	ctrl.customers;
	ctrl.vehicles;

	ctrl.customer;
	ctrl.vehicle;
	ctrl.booking;

	ctrl.newClient = false;
	ctrl.formDisabled = false;

    CustomerService.list().then(function(response) {
        ctrl.customers = response;
    }, function() {
        ToasterService.showError('Greška tokom dobavljanja liste klijenata.');
    });

    VehicleService.getLastMonthsMostPopular().then(function(response) {
        ctrl.vehicleOfTheMonth = response;
    }, function() {
        ToasterService.showError('Greška tokom dobavljanja najpopularnijeg vozila.');
    });

    ctrl.resetForm = function(form) {
        Util.setToPristine(form);
        ctrl.formDisabled = false;
    };
	
	ctrl.getFreeVehicles = function(dateFrom, selectNumDays) {
		if (!angular.isDate(dateFrom) || selectNumDays <= 0) return;
        VehicleService.listFreeVehicles(dateFrom, selectNumDays).then(function(response) {
            ctrl.vehicles = response;
        }, function() {
            ToasterService.showError('Greška tokom dobavljanja liste vozila.');
        });
	};
	
	ctrl.reservation = function(customer, vehicle, booking) {
		if (angular.isUndefined(customer) || angular.isUndefined(vehicle) || angular.isUndefined(booking)) return;
		if (!ctrl.newClient) {
            BookingService.save(customer.id, vehicle.regNumber, booking).then(function(response) {
                var reservationSuccessMsg = 'Uspešno ste rezervisali vozilo ' + response.vehicle.regNumber + ' ' +
                    response.vehicle.model.manufacturer.name + ' ' + response.vehicle.model.modelName +
                    ' za klijenta ' + response.customer.name + ' ' + response.customer.lastName + '!';
                ctrl.formDisabled = true;
                ToasterService.showSuccess(reservationSuccessMsg);
            }, function () {
                ToasterService.showError('Neuspešna rezervacija!');
            });
		} else if (ctrl.newClient) {
            BookingService.save(customer, vehicle.regNumber, booking).then(function(response) {
                var reservationSuccessMsg = 'Uspešno ste rezervisali vozilo ' + response.vehicle.regNumber +
                    ' ' + response.vehicle.model.manufacturer.name + ' ' + response.vehicle.model.modelName +
                    ' za klijenta ' + response.customer.name + ' ' + response.customer.lastName + '!';
                ctrl.formDisabled = true;
                ToasterService.showSuccess(reservationSuccessMsg);
            }, function() {
                ToasterService.showError('Neuspešna rezervacija!');
            });
		}
	};
}]);