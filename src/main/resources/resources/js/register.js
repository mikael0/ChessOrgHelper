var registrationModule = angular.module('registration', []);

registrationModule.controller("RegistrationController", function($scope, $http, $location) {
    $scope.user = {};

    $scope.submit = function () {
        $http.post("/rest/user/register", $scope.user).then(function successCallback(response) {
            var link = window.location.href;
            var href = link.substr(0, link.indexOf("/"));
            window.location = href + "/login";
        }, function errorCallback(response) {
        });

    };
});

//exports.RegistrationController = RegistrationController;
