app.controller('CustomerController', ['$location', 'CustomerService', '$uibModal', 'ToasterService', 'DialogService',
        function($location, CustomerService, $uibModal, ToasterService, DialogService) {
    var ctrl = this;

    ctrl.pages = [];
    ctrl.customers = [];

    CustomerService.list().then(function(response) {
        ctrl.customers = response;
    }, function() {
        ToasterService.showError('Greška tokom dobavljanja podataka.');
    });

    ctrl.showAddEditCustomerDialog = function(customer) {
        var modal = $uibModal.open({
            templateUrl: 'views/customers/addEditCustomerModal.html',
			controller: 'AddEditCustomerModalController',
			controllerAs: 'ctrl',
            resolve: {
                customer: function() {
                    return customer;
                }
            }
        });

        modal.result.then(function(customerToSave) {
            CustomerService.save(customerToSave).then(function(savedCustomer) {
                var index = ctrl.customers.indexOf(customer);
                if (index > -1) {
                    ctrl.customers.splice(index, 1, savedCustomer);
                    ToasterService.showSuccess('Uspešno ste ažurirali klijenta ' + savedCustomer.name + ' ' +
                        savedCustomer.lastName + '!');
                } else {
                    ctrl.customers.splice(0, 0, savedCustomer);
                    ToasterService.showSuccess('Uspešno ste sačuvali klijenta ' + savedCustomer.name + ' ' +
                        savedCustomer.lastName + '!');
                }
            }, function() {
                ToasterService.showError('Sačuvanje klijenta nije uspelo!');
            });
        }, function() {})
	};

    ctrl.showDeleteDialog = function(customer, event) {
        event.stopPropagation();
        DialogService
            .showConfirmDialog('Brisanje klijenta', 'Da li želite da obrišete klijenta '
                + customer.name + ' ' + customer.lastName + '?')
            .then(function() {
                CustomerService.delete(customer).then(function() {
                    ToasterService.showSuccess('Uspešno ste obrisali klijenta '
                        + customer.name + ' ' + customer.lastName + '.');
                    ctrl.customers.splice(ctrl.customers.indexOf(customer), 1);
                }, function() {
                    ToasterService.showError('Greška prilikom brisanja klijenta.');
                });
            }, function() {});
    };

    ctrl.showUnpaidBookings = function(customer, event) {
        event.stopPropagation();
        $location.search('customerId', customer.id);
        $location.search('customerName', customer.name + ' ' + customer.lastName);
        $location.path('/bookingMngmnt');
    }
	
}]);