app.controller('AddEditCustomerModalController', ['CustomerService', 'customer', '$uibModalInstance',
    function(CustomerService, customer, $uibModalInstance) {
        var ctrl = this;

        ctrl.customer = angular.copy(customer);
        ctrl.edit = angular.isDefined(ctrl.customer);

        ctrl.cancel = function() {
            $uibModalInstance.dismiss();
        };

        ctrl.saveCustomer = function(customer, form) {
            if (form.$invalid) return;
            $uibModalInstance.close(customer)
        };

    }
]);
