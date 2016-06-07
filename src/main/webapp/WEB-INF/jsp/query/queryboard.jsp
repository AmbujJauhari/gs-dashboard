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

        angular.module('ui.bootstrap.demo').controller('queryController', function ($scope, $http, $filter, ngTableParams, $uibModal) {
            $scope.submit = function () {
                var queryFormData = {
                    "gridName": 'Grid-A',
                    "dataType": $scope.selected,
                    "criteria": $scope.queryCriteria
                };
                $http.post('http://localhost:8080/query/getDataFromSpaceForType.html', queryFormData)
                        .success(function (data, status, headers, config) {
                            $scope.columns = data.headerColumns;
                            $scope.documentDetailedData = data.tableData;
                            $scope.tableParams = new ngTableParams({
                                page: 1,            // show first page
                                count: 10,          // count per page
                                filter: {
                                    name: 'M'       // initial filter
                                }
                            }, {
                                total: $scope.documentDetailedData.length, // length of data
                                getData: function ($defer, params) {
                                    $scope.paginatedDocumentDetailedData =
                                            $scope.documentDetailedData.slice((params.page() - 1) * params.count(), params.page() * params.count());
                                    $defer.resolve($scope.paginatedDocumentDetailedData);
                                }
                            });
                        });
            };

            $scope.items = ['item1', 'item2', 'item3'];

            $scope.open = function () {

                var modalInstance = $uibModal.open({
                    templateUrl: 'myModalContent.html',
                    controller: ModalInstanceCtrl,
                    resolve: {
                        items: function () {
                            return $scope.items;
                        }
                    }
                });

                modalInstance.result.then(function (selectedItem) {
                    $scope.selected = selectedItem;
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };

            var ModalInstanceCtrl = function ($scope, $uibModalInstance, items) {

                $scope.items = items;
                $scope.selected = {
                    item: $scope.items[0]
                };

                $scope.ok = function () {
                    $uibModalInstance.close($scope.selected.item);
                };

                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
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
        <div>

            Columns:

            <table ng-table="tableParams" show-filter="true" class="table">
                <thead>
                <tr>
                    <th ng-repeat="column in columns"
                        class="text-center sortable" ng-class="{
                    'sort-asc': tableParams.isSortBy(column, 'asc'),
                    'sort-desc': tableParams.isSortBy(column, 'desc')
                  }"
                        ng-click="tableParams.sorting(column, tableParams.isSortBy(column, 'asc') ? 'desc' : 'asc')">
                        {{column}}
                    </th>
                </tr>
                </thead>
                <tbody>
                <a href="#">
                    <tr ng-repeat="user in paginatedDocumentDetailedData">
                        <td>
                            <button class="btn" ng-click="open()">Open me!</button>
                        </td>
                        <td ng-repeat="column in columns">
                            {{user[column]}}
                        </td>
                        <script type="text/ng-template" id="myModalContent.html">
                            <div class="modal-header">
                                <h3>I'm a modal!</h3>
                            </div>
                            <div class="modal-body">
                                <ul>
                                    <li ng-repeat="item in items">
                                        <a ng-click="selected.item = item">{{ item }}</a>
                                    </li>
                                </ul>
                                Selected: <b>{{ selected.item }}</b>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-primary" ng-click="ok()">OK</button>
                                <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
                            </div>
                        </script>
                    </tr>
                </a>
                </tbody>
            </table>
        </div>
    </form>
</div>
</body>
</html>