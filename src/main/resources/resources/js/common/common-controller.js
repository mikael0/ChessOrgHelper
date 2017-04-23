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
    };

    $scope.updateProfile = function () {
        if ($scope.user === undefined)
            $scope.user = {}
        if ( $scope.newPass === $scope.anotherNewPass)
            $scope.user.password = $scope.newPass
        $http.post("/rest/user/updateCurrent", $scope.user)
             .then(function successCallback(response) {
            window.location = "/profile";
        }, function errorCallback(response) {
        });
    };


    $scope.goToHousings = function(ev, tournamentId) {
        console.log("id: " + tournamentId);
        window.location = "/housing_settings?tournamentId=" + tournamentId;
    };

    $scope.goToArenas = function(ev, tournamentId) {
        console.log("id: " + tournamentId);
        window.location = "/arena_settings?tournamentId=" + tournamentId;
    };

    $scope.goToSchedule = function(tournamentId) {
        console.log("id: " + tournamentId);
        window.location = "/schedule?tournamentId=" + tournamentId;
    };

    $scope.goToParticipants = function(ev, tournamentId) {
        console.log("id: " + tournamentId);
        window.location = "/participants_settings?tournamentId=" + tournamentId;
    };

    $scope.generateSchedule = function (tournamentId) {
        console.log("id: " + tournamentId);
        var data = {"tournamentId": tournamentId};
        $http.post("/rest/tournament/generate_schedule", data)
            .then(function (resp) {
                window.location = "/schedule?tournamentId=" + tournamentId;
            });
    };

    $scope.showGameInfo = function(ev, gameId, tournamentId) {
        console.log("id: " + tournamentId);
        $http.post("/rest/tournament/game_by_id", gameId)
            .then(function (resp) {
                // var resp = { data: {"player1" : {user: {name: "First"}}, "player2": {user: {name: "Second"}}}};
                $mdDialog.show({
                    controller: GameInfoController,
                    templateUrl: 'resources/html/game_settings_dialog.tmpl.html',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true,
                    locals: {
                        game: resp.data,
                        tournamentId: tournamentId
                    },
                    fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
                })
                    .then(function(answer) {

                    }, function() {

                    });
            })
    };

    $scope.approveRequest = function(requestId, tournamentId) {
        console.log("reqId: " + requestId + "tourId: " + tournamentId);
        var data = {"requestId" : requestId, "tournamentId" : tournamentId};
        $http.post("/rest/tournament/approve_request", data)
            .then(function (resp) {
                window.location = "/participants_settings?tournamentId=" + tournamentId;
            });
    };

    $scope.removeParticipant = function(interestedId, tournamentId) {
        console.log("intId: " + interestedId + "tourId: " + tournamentId);
        var data = {"interestedId" : interestedId, "tournamentId" : tournamentId};
        $http.post("/rest/tournament/remove_participant", data)
            .then(function (resp) {
                window.location = "/participants_settings?tournamentId=" + tournamentId;
            });
    };

    $scope.removeArena = function (tournamentId, arenaId) {
        console.log("id: " + tournamentId);
        var data = {"tournamentId" : tournamentId, "arenaId" : arenaId};
        $http.post("/rest/tournament/remove_arena", data)
            .then(function () {
                window.location = "/arena_settings?tournamentId=" + tournamentId;
            });
    }

    $scope.getTournamentById = function(id) {
        console.log(id);
        $http.post("/rest/tournament/getById", id)
            .then(function (resp) {
                $scope.tournament = resp.data;
            });
    };

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


    $scope.showBuyTicketsDialog = function(ev, tournament) {
        console.log(tournament);
        $http.post( "/rest/tournament/getById", tournament).then(function successCallback(response) {
            console.log(response);
            $mdDialog.show({
                controller: BuyTicketsController,
                templateUrl: 'resources/html/buy_tickets_dialog.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true,
                locals: {
                    tournament : response.data
                },
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })
                .then(function(answer) {

                }, function() {

                });
        }, function errorCallback() {

        });

    };

    $scope.showAddHousingDialog = function(ev, tournament) {
        console.log(tournament);
        $http.post( "/rest/tournament/getById", tournament).then(function successCallback(response) {
            console.log(response);
            $mdDialog.show({
                controller: AddHousingController,
                templateUrl: 'resources/html/add_housing_dialog.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true,
                locals: {
                    tournament : response.data
                },
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })
                .then(function(answer) {

                }, function() {

                });
        }, function errorCallback() {

        });

    };

    $scope.showAddArenaDialog = function(ev, tournament) {
        console.log(tournament);
        $http.post( "/rest/tournament/getById", tournament).then(function successCallback(response) {
            console.log(response);
            $mdDialog.show({
                controller: AddArenaController,
                templateUrl: 'resources/html/add_arena_dialog.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true,
                locals: {
                    tournament : response.data
                },
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })
                .then(function(answer) {

                }, function() {

                });
        }, function errorCallback() {

        });

    };


    $scope.showRequestInfo = function(ev, requestId) {
        console.log(requestId);
        $http.post( "/rest/apply/getById", requestId).then(function successCallback(response) {
            console.log(response);
            $mdDialog.show({
                controller: ShowRequestController,
                templateUrl: 'resources/html/show_request_dialog.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true,
                locals: {
                    request : response.data
                },
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })
                .then(function(answer) {

                }, function() {

                });
        }, function errorCallback() {

        });

    };


    $scope.showApplyDialog = function(ev, tournament) {
        console.log(tournament);
        $http.post( "/rest/tournament/getById", tournament).then(function successCallback(response) {
            console.log(response);
            $mdDialog.show({
                controller: ApplyController,
                templateUrl: 'resources/html/apply_dialog.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true,
                locals: {
                    tournament : response.data
                },
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })
                .then(function(answer) {

                }, function() {

                });
        }, function errorCallback() {

        });

    };

    function ShowRequestController($scope, $http, $mdDialog, request) {
        console.log(request);
        $scope.request = request;

        $scope.submit = function () {
            $scope.hide();
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

    function BuyTicketsController($scope, $http, $mdDialog, tournament) {
        console.log(tournament);
        $scope.tournament = tournament;
        $scope.tickets = [{}];

        console.log("tournament: " + $scope.tournament);
        $scope.add = function () {
            $scope.tickets.push({})
        }

        $scope.submit = function () {
            console.log($scope.tickets);
            for (var i = 0; i < $scope.tickets.length; i++) {
                var ticket = $scope.tickets[i];
                console.log(ticket);
                var data = {"tournamentId": tournament.id, "gameId": ticket.gameId, "amount": ticket.amount};
                console.log(data);
                $http.post("/rest/tournament/buy_tickets", data)
                    .then(function (resp) {
                    });
            }
            var housingData = {"roomId": $scope.housing.roomId, "amount": $scope.housing.amount};
            $http.post("/rest/tournament/buy_tickets", housingData)
                .then(function (resp) {
                    window.location = "/tournament_list"
                    $scope.hide();
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

    function AddHousingController($scope, $http, $mdDialog, tournament) {
        console.log(tournament);
        $scope.tournament = tournament;
        // $scope.housing = {"tournament" : tournament};
        $scope.housing = {"tournamentId": tournament.id};

        $scope.housing.rooms= [ {} ];

        $scope.add = function () {
            $scope.housing.rooms.push({})
        }

        $scope.submit = function () {


            console.log($scope.housing);

            $http.post("/rest/tournament/add_housing", $scope.housing)
                 .then(function (resp) {
                     window.location = "/housing_settings?tournamentId=" + tournament.id;
                     $scope.hide();
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

    function AddArenaController($scope, $http, $mdDialog, tournament) {
        console.log(tournament);
        $scope.tournament = tournament;
        $scope.arena = {"tournamentId" : tournament.id};

        $scope.submit = function () {

            console.log($scope.arena);

            $http.post("/rest/tournament/add_arena", $scope.arena)
                .then(function (resp) {
                    window.location = "/arena_settings?tournamentId=" + tournament.id;
                    $scope.hide();
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

    function ApplyController($scope, $http, $mdDialog, tournament) {
        console.log(tournament);
        $scope.tournament = tournament;
        $scope.user = {};

        $scope.request = {tournamentId : tournament.id}

        $scope.submit = function () {
            //TODO: apply controller
            console.log($scope.request)
            $http.post("/rest/apply/request", $scope.request).then(function successCallback(response) {
                var fd = new FormData();
                fd.append('id', response.data);
                fd.append('file', $scope.file);
                console.log("data:" + response.data);
                console.log("file:" + $scope.file);
                console.log(fd);
                $http.post("/rest/apply/confirmation", fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                    }).then(function(resp) {
                        window.location = "tournament_list";
                        $scope.hide();
                    })
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

    function GameInfoController($scope, $http, $mdDialog, game, tournamentId) {

        $scope.winner = {};
        $scope.availableResults = [game["player1"], game["player2"], "Draw"];

        $scope.submit = function () {
            console.log($scope.winner);
            if ($scope.winner === "both"){
                $scope.winner = [game["player1"]["id"], game["player2"]["id"]]
            }
            console.log($scope.winner);
            var data = {"gameId": game["id"],  "winner1": $scope.winner[0], "winner2": $scope.winner[1]}
            $http.post("/rest/game/enter_results", data).then(function successCallback(response) {
                window.location = "/schedule?tournamentId=" + tournamentId;
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




})
    .directive("fileread", [function () {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                var reader = new FileReader();
                reader.onload = function (loadEvent) {
                    scope.$apply(function () {
                        scope.fileread = loadEvent.target.result;
                    });
                }
                reader.readAsDataURL(changeEvent.target.files[0]);
            });
        }
    }
}]);



