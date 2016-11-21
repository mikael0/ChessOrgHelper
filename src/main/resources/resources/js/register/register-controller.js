'use strict';

RegisterModule.controller("RegisterController", function ($scope, $http, $location) {
    $scope.user = {};
    $scope.submit = function () {
        console.log("register!");
        if ($scope.user.password != $scope.confirm.password) {
            alert('Passwords do not match');
            return;
        }
        $http.post("/rest/user/register", $scope.user).then(function successCallback(response) {
            var link = window.location.href;
            var href = link.substr(0, link.lastIndexOf("/"));
            alert("Activate your account with the email");
            window.location = href;
        }, function errorCallback(response) {
            if (response.status == 409) {
                alert('User already exists');
            }
        });
    };

});

//exports.RegistrationController = RegistrationController;
