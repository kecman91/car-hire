app.controller('BookingManagementController', ['$location', 'BookingService', 'CustomerService',
    'VehicleService', 'DialogService', 'ToasterService',
    function($location, BookingService, CustomerService, VehicleService, DialogService, ToasterService) {
    var ctrl = this;

    ctrl.bookings = [];
    ctrl.selectedCustomer = undefined;

    ctrl.searchCustomers = function(searchTerm) {
        return CustomerService.search(searchTerm);
    };

    ctrl.searchVehicles = function(searchTerm) {
        return VehicleService.search(searchTerm);
    };

    ctrl.search = function(customerId, vehicleRegNum, unpaidOnly) {
        if (customerId <= 0) return;
        BookingService.search(customerId, vehicleRegNum, unpaidOnly).then(function(response) {
            ctrl.bookings = response;
        }, function () {
            ctrl.bookings = [];
        });
    };

    ctrl.showDeleteDialog = function(booking) {
        DialogService
            .showConfirmDialog('Brisanje rezervacije', 'Da li želite da obrišete izabranu rezervaciju?')
            .then(function() {
                BookingService.delete(booking).then(function() {
                    ToasterService.showSuccess('Uspešno ste obrisali rezervaciju.');
                    ctrl.bookings.splice(ctrl.bookings.indexOf(booking), 1);
                }, function() {
                    ToasterService.showError('Greška prilikom brisanja rezervacije.');
                });
            }, function() {});
    };

    ctrl.showSetPaidDialog = function(booking) {
        DialogService
            .showConfirmDialog('Naplata rezervacije', 'Da li je izabrana rezervacija plaćena?')
            .then(function() {
                BookingService.setPaid(booking).then(function() {
                    ToasterService.showSuccess('Rezervacija je označena kao plaćena.');
                    booking.paymentReceived = true;
                }, function() {
                    ToasterService.showError('Greška prilikom obrade.');
                });
            }, function() {});
    };

    var searchParams = $location.search();
    if (angular.isDefined(searchParams.customerId) && angular.isDefined(searchParams.customerName)) {
        ctrl.selectedCustomer = searchParams.customerName;
        ctrl.search(searchParams.customerId, undefined, true);
    }
}]);