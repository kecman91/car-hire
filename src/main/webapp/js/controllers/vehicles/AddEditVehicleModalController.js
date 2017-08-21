app.controller('AddEditVehicleModalController', [
    'vehicle', 'ToasterService', '$uibModalInstance', 'ManufacturerService', 'ModelService', 'VehicleCategoryService',
    function(vehicle, ToasterService, $uibModalInstance, ManufacturerService, ModelService, VehicleCategoryService) {
    var ctrl = this;

    ctrl.vehicle = angular.copy(vehicle);
    ctrl.edit = angular.isDefined(ctrl.vehicle);

    if (!ctrl.edit) {
        ctrl.vehicle = {};
    }

    ManufacturerService.list().then(function(response) {
        ctrl.manufacturers = response;
    }, function() {
        ToasterService.showError('Greška prilikom dobavljanja liste proizvođača!');
    });

    VehicleCategoryService.list().then(function(response) {
        ctrl.categories = response;
    }, function() {
        ToasterService.showError('Greška prilikom dobavljanja liste kategorija!');
    });

    var getModelsByManufacturer = function(manuCode) {
        ModelService.listByManufacturer(manuCode).then(function(response) {
            ctrl.models = response;
        }, function() {
            ToasterService.showError('Greška prilikom dobavljanja liste modela!');
        });
    };

    ctrl.onManufacturerChange = function(manufacturer) {
        ctrl.vehicle.model = undefined;
        getModelsByManufacturer(manufacturer.manufacturerCode);
    };

    ctrl.cancel = function() {
        $uibModalInstance.dismiss();
    };

    ctrl.saveVehicle = function(vehicle, form) {
        if (form.$invalid) return;
        if (angular.isDefined(ctrl.manufacturer))
        vehicle.model.manufacturer = ctrl.manufacturer;
        $uibModalInstance.close(vehicle);
    };

    if (ctrl.edit && angular.isObject(ctrl.vehicle.model)) {
        ctrl.manufacturer = ctrl.vehicle.model.manufacturer;
        getModelsByManufacturer(ctrl.vehicle.model.manufacturer.manufacturerCode);
    }

}]);
