'use strict';

CommonModule.controller("CommonController", function ($scope, $http, $location, $window) {

    $scope.showSearch = true;
    $scope.toggleSidenav = function (menuId) {
        $mdSidenav(menuId).toggle();
    };
    $scope.modules = [
        {
            link: '',
            title: 'Tournament List',
            role: 'ROLE_SPECTATOR ROLE_ORGANIZER ROLE_PARTICIPANT'
        },
        {
            link: '',
            title: 'Statistics',
            role: 'ROLE_SPECTATOR ROLE_ORGANIZER ROLE_PARTICIPANT'
        },
        {
            link: '',
            title: 'Profile',
            role: 'ROLE_SPECTATOR ROLE_ORGANIZER ROLE_PARTICIPANT'
        },
    ];



    this.logout = function () {
        //console.log($location)
        //$window.location = '../logout';
        $http.post('logout', {}).success(function () {
            var link = window.location.href;
            var href = link.substr(0, link.indexOf("/"));
            window.location = href;
        }).error(function (data) {
            console.log("Logout failed");
        });
    }

});


