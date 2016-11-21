'use strict';

LoginModule.controller("MainController", function ($scope, $http, $mdDialog) {
    $scope.redirect = function () {
        $http.get("/rest/user/details").success(function (data) {
            if (data.authenticated) {
                var link = window.location.href;
                var href = link.substr(0, link.indexOf("/", 8));
                console.log(data);
                window.location = href + "/external";
            }
        }).error(function () {

        });
    }
    $scope.redirect();
    
    $scope.showLoginDialog = function(ev) {
    $mdDialog.show({
      controller: DialogController,
      templateUrl: 'resources/html/login_dialog.tmpl.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true,
      fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
    })
    .then(function(answer) {
      $scope.status = 'You said the information was "' + answer + '".';
    }, function() {
      $scope.status = 'You cancelled the dialog.';
    });
  };

   function DialogController($scope, $http, $mdDialog) {
    $scope.submit = function () {
        var csrf_name = document.getElementById('csrf').name;
        var csrf_value = document.getElementById('csrf').value;
        var data = 'username=' + $scope.user.username + "&password=" + $scope.user.password + "&" + csrf_name + "=" + csrf_value;
        console.log(data);
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
