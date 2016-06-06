<!doctype html>
<html ng-app="ui.bootstrap.demo">
<head>
    <title>Hello AngularJS</title>
    <head>
        <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.js"></script>
        <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.3.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://rawgit.com/esvit/ng-table/master/dist/ng-table.min.css">
        <script src="https://rawgit.com/esvit/ng-table/master/dist/ng-table.min.js"></script>
    </head>

    <script type="text/javascript">
        angular.module('ui.bootstrap.demo', ['ngAnimate', 'ui.bootstrap', 'ngTable']);
        angular.module('ui.bootstrap.demo').controller('TypeaheadCtrl', function ($scope, $http) {
            $scope.selected = undefined;
            Hello($scope, $http)
        });

        function Hello($scope, $http) {
            $http.get('http://localhost:8080/query/getAllDocumentTypesForSpace.html?envName=Grid-A').success(function (data) {
                $scope.states = data
            });
        }

        angular.module('ui.bootstrap.demo').controller('queryController', function ($scope, $http, $window) {
            $scope.submit = function () {
                var queryFormData = {
                    "gridName": 'Grid-A',
                    "dataType": $scope.selected,
                    "criteria": $scope.queryCriteria
                };
                $http.post('http://localhost:8080/query/getDataFromSpaceForType.html', queryFormData)
                        .success(function (data, status, headers, config) {
                            $scope.documentsData = data;
                        });
            };
        });
    </script>
</head>

<body>
<script type="text/html">
    <ul class="dropdown-menu" role="listbox">
        <li ng-repeat="match in matches track by $index"/>
    </ul>
</script>
<div class='container-fluid typeahead-demo' ng-controller="TypeaheadCtrl">
    <h1>Query Board</h1>

    <form class="form-inline" role="form" ng-controller="queryController" ng-submit="submit()">
        <div class="form-group">
            <input type="text" placeholder="Document / Pojo name" ng-model="selected"
                   uib-typeahead="state for state in states | filter:$viewValue | limitTo:8"
                   class="form-control">
        </div>
        <div class="form-group col-md-offset-1">
            <input type="text" placeholder="search criteria" class="form-control" ng-model="queryCriteria">
        </div>

        <button type="submit" class="btn btn-default col-md-offset-1">Submit</button>
        <h4>You submitted below data through post:{{documentsData}}</h4>
        <table ng-table="usersTable" class="table table-striped">
            <tr ng-repeat="rows in documentsData">
                <td ng-repeat-start="item in rows">{{item.key}}</td>
                <td>:</td>
                <td ng-repeat-end>{{item.value}}</td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>