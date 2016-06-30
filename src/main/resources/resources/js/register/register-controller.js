'use strict';

RegisterModule.controller("RegisterController", function($scope, $http, $location) {
    $scope.user = {};
    $scope.submit = function () {
        console.log("register!");
        $http.post("/rest/user/register", $scope.user).then(function successCallback(response) {
            var link = window.location.href;
            var href = link.substr(0, link.indexOf("/"));
            window.location = href + "/login";
        }, function errorCallback(response) {
        });
    };
});

//exports.RegistrationController = RegistrationController;
