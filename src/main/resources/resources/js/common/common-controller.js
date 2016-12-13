'use strict';

CommonModule.controller("CommonController", function ($scope, $http, $location, $window, $mdDialog) {

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


     $scope.showTournamentCreateDialog = function(ev) {
        $mdDialog.show({
          controller: TournamentCreateController,
          templateUrl: 'resources/html/create_tournament_dialog.tmpl.html',
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose:true,
          fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
        .then(function(answer) {
        }, function() {
        });
      };


        function TournamentCreateController($scope, $http, $mdDialog) {
        $scope.tournament = {};
        $scope.maxParticipantsNums = [16, 32, 48, 64];
        $scope.submit = function () {
            console.log($scope.tournament);
            $http.post("/rest/tournament/create", $scope.tournament).then(function successCallback(response) {
                window.location = "tournament_list";
                $scope.hide()
            }, function errorCallback(response) {
            });
        };
        $scope.hide = function() {
          $mdDialog.hide();
        };

        $scope.cancel = function() {
          $mdDialog.cancel();
        };

        $scope.answer = function(answer) {
          $mdDialog.hide(answer);
        };
      }




});


