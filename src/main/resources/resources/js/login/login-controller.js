'use strict';

LoginModule.controller("LoginController", function ($scope, $http) {
    $scope.redirect = function () {
        $http.get("/rest/user/details").success(function (data) {
            if (data.authenticated) {
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
        var csrf_name = document.getElementById('csrf').name;
        var csrf_value = document.getElementById('csrf').value;
        var data = 'username=' + $scope.user.username + "&password=" + $scope.user.password + "&" + csrf_name + "=" + csrf_value;

        $http({
            method: 'POST',
            url: "/rest/user/formlogin",
            data: data,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data) {
            var link = window.location.href;
            var href = link.substr(0, link.indexOf("/", 8));
            console.log(data);
            window.location = href + "/dashboard";
        }).error(function () {
        });
    }

});