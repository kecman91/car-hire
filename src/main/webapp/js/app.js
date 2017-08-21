var app = angular.module('CarHire', ['ngRoute', 'ui.bootstrap', 'ngAnimate', 'toaster']);

app.config(function ($routeProvider) {
	$routeProvider
	.when('/', {
		controller: 'ReservationController',
        controllerAs: 'ctrl',
		templateUrl: 'views/reservation/reservation.html'
	})
	.when('/reservation', {
		controller: 'ReservationController',
        controllerAs: 'ctrl',
		templateUrl: 'views/reservation/reservation.html'
	})
	.when('/vehicles', {
		controller: 'VehicleController',
        controllerAs: 'ctrl',
		templateUrl: 'views/vehicles/vehicles.html'
	})
	.when('/customers', {
		controller: 'CustomerController',
		controllerAs: 'ctrl',
		templateUrl: 'views/customers/customers.html'
	})
	.when('/bookingMngmnt', {
		controller: 'BookingManagementController',
		controllerAs: 'ctrl',
		templateUrl: 'views/bookingManagement/bookingManagement.html'
	})
	.otherwise({
		redirectTo: '/'
	});
});