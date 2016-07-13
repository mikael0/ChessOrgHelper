'use strict';

LoginModule.controller("LoginController", function ($scope, $http) {
    $scope.redirect = function () {
        $http.get("/rest/user/details").success(function (data) {
            if (data.authenticated) {
                // localStorage.setItem('myCat', JSON.stringify(data));
                var link = window.location.href;
                var href = link.substr(0, link.indexOf("/", 8));
                console.log(data);
                window.location = href + "/dashboard";
            }
        }).error(function () {
        });
    }
    $scope.redirect();
    $scope.submit = function () {
        $scope.redirect();
    }

});