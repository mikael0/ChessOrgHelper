'use strict';

LoginModule.controller("LoginController", function($scope, $http) {
    var self = this;
    $http.get("/rest/user/details").success(function(data) {
        if (data.authenticated) {
            var link = window.location.href;
            var href = link.substr(0, link.indexOf("/"));
            window.location = href + "/dashboard";
        }
    }).error(function() {
    });
});