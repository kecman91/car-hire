app.controller('VehicleController',
    ['$filter', 'VehicleService', 'ManufacturerService', 'VehicleCategoryService', 'ModelService',
        'ToasterService', '$uibModal', 'DialogService', 'Util',
        function($filter, VehicleService, ManufacturerService, VehicleCategoryService, ModelService,
                 ToasterService, $uibModal, DialogService, Util) {
    var ctrl = this;

	ctrl.vehicles = [];
	ctrl.categoryFilter = [];
	ctrl.manufacturersFilter = [];

	ManufacturerService.list().then(function(response) {
		ctrl.manufacturersFilter = response;
	}, function() {
        ToasterService.showError('Greška tokom dobavljanja liste proizvođača.');
	});
	
	VehicleCategoryService.list().then(function(response) {
		ctrl.categoryFilter = response;
	}, function() {
        ToasterService.showError('Greška tokom dobavljanja liste kategorija.');
	});

	VehicleService.list().then(function(response) {
		ctrl.vehicles = response;
	}, function() {
        ToasterService.showError('Greška tokom dobavljanja liste vozila.');
	});

	ctrl.filterVehicles = function() {
		var criteria = {};
		criteria.manufacturers = [];
		criteria.categories = [];
		for (var i = 0; i < ctrl.manufacturersFilter.length; i++) {
			if (ctrl.manufacturersFilter[i].checked) {
				criteria.manufacturers.push(ctrl.manufacturersFilter[i].manufacturerCode);
			}
		}
		for (var j = 0; j < ctrl.categoryFilter.length; j++) {
			if (ctrl.categoryFilter[j].checked) {
				criteria.categories.push(ctrl.categoryFilter[j].id);
			}
		}
		if (ctrl.priceGreaterThan > 0) {
			criteria.priceGT = ctrl.priceGreaterThan;
		}
		if (ctrl.priceLessThan > 0) {
			criteria.priceLT = ctrl.priceLessThan;
		}
		if (criteria.manufacturers.length > 0 || criteria.categories.length > 0 || criteria.priceGT > 0 || criteria.priceLT > 0) {
			VehicleService.listFilteredVehicles(criteria).then(function(response) {
                ctrl.vehicles = response;
			});
		} else {
			VehicleService.list().then(function(response) {
                ctrl.vehicles = response;
            })
		}
	};

	ctrl.resetForm = function(form) {
	    Util.setToPristine(form);
    };
	
	ctrl.showAddEditVehicleDialog = function(vehicle) {
		var vehicleModal = $uibModal.open({
            templateUrl: 'views/vehicles/addEditVehicleModal.html',
            controller: 'AddEditVehicleModalController',
            controllerAs: 'ctrl',
            resolve: {
                vehicle: function () {
                    return vehicle;
                }
            }
        });

        vehicleModal.result.then(function(vehicleToSave) {
            vehicleToSave.regNumber = vehicleToSave.regNumber.toUpperCase();
            VehicleService.save(vehicleToSave).then(
                function(savedVehicle) {
                    var index = ctrl.vehicles.indexOf(vehicle);
                    if (index > -1) {
                        ctrl.vehicles.splice(index, 1, savedVehicle);
                        ToasterService.showSuccess('Uspešno ste ažurirali vozilo '
                            + savedVehicle.regNumber + '!');
                    } else {
                        ctrl.vehicles.splice(0, 0, savedVehicle);
                        ToasterService.showSuccess('Uspešno ste sačuvali vozilo '
                            + savedVehicle.regNumber + '!');
                    }
                }, function() {
                    var message = 'Sačuvanje vozila nije uspelo!';
                    ToasterService.showError(message);
                });
        }, function() {});
	};
	
	ctrl.showDeleteDialog = function(vehicle, event) {
	    event.stopPropagation();
		DialogService
            .showConfirmDialog('Brisanje vozila', 'Da li želite da obrišete vozilo ' + vehicle.regNumber + '?')
            .then(function() {
                VehicleService.delete(vehicle).then(function() {
                    ToasterService.showSuccess('Uspešno ste obrisali vozilo ' + vehicle.regNumber + '.');
                    ctrl.vehicles.splice(ctrl.vehicles.indexOf(vehicle), 1);
                }, function() {
                    ToasterService.showError('Greška prilikom brisanja vozila.');
                });
            }, function() {});
	};

}]);