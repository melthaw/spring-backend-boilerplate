'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.attachment', {
                url: '/attachment',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.attachment.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/attachment/list.html',
                controller: 'AttachmentController'
            });
    })
    .factory('formDataObject', function () {
        return function (data) {
            var fd = new FormData();
            angular.forEach(data, function (value, key) {
                fd.append(key, value);
            });
            return fd;
        };
    })
    .factory('sharedAttachmentService', function ($http, $rootScope, $window, webappSettings, pageRequestHelper, formDataObject) {
        return {
            listFileObject: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/files' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getFileStorageMetadata: function () {
                return $http.get(webappSettings.basicApiUrl + '/fileStorageMetadata');
            },
            getFileObjectDetail: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/files/' + id + '/object');
            },
            getFileObjectHistory: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/files/' + id + '/history');
            },
            upload: function (file, options) {
                // return $http({
                //     method: 'POST',
                //     url: webappSettings.basicApiUrl + '/files',
                //     headers: {
                //         'Content-Type': undefined
                //     },
                //     data: file,
                //     transformRequest: formDataObject
                // });
                return $http.post(webappSettings.basicApiUrl + '/files', file, angular.extend({
                    headers: {
                        'Content-Type': undefined
                    },
                    data: file,
                    transformRequest: formDataObject
                }, options));
            },
            reupload: function (id, file, options) {
                return $http.post(webappSettings.basicApiUrl + '/files/' + id, file, angular.extend({
                    headers: {
                        'Content-Type': undefined
                    },
                    data: file,
                    transformRequest: formDataObject
                }, options));
            },
            downloadById: function (id) {
                return $window.open(webappSettings.basicApiUrl + '/files/' + id, 'Download');
                // return $window.location.assign(webappSettings.basicApiUrl + '/files/' + id);
            },
            downloadByFilename: function (fileName) {
                return $window.open(webappSettings.basicApiUrl + '/download/' + fileName);
            },
            deleteById: function (id) {
                return $http.delete(webappSettings.basicApiUrl + '/files/' + id);
            }
        };
    })
    .factory('attachmentService', function ($http, $window, $rootScope, webappSettings, pageRequestHelper) {
        return {
            list: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/attachments' + pageRequestHelper.buildRequest(queryCriteria));
            },
            findById: function (attachmentId) {
                return $http.get(webappSettings.basicApiUrl + '/attachments/' + attachmentId);
            },
            create: function (attachment) {
                return $http.post(webappSettings.basicApiUrl + '/attachments', attachment);
            },
            update: function (id, attachment) {
                return $http.post(webappSettings.basicApiUrl + '/attachments/' + id, attachment);
            },
            delete: function (attachmentId) {
                return $http.delete(webappSettings.basicApiUrl + '/attachments/' + attachmentId);
            },
            publish: function (attachmentId) {
                return $http.post(webappSettings.basicApiUrl + '/attachments/' + attachmentId + '/publish', {});
            },
            unPublish: function (attachmentId) {
                return $http.post(webappSettings.basicApiUrl + '/attachments/' + attachmentId + '/unpublish', {});
            },
            downloadAttachment: function (attachmentId) {
                return $window.open(webappSettings.basicApiUrl + '/attachments/' + attachmentId + '/download', 'Download');
            },
            listDownloadHistory: function (id, queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/attachments/' + id + '/downloadHistory' + pageRequestHelper.buildRequest(queryCriteria));
            }

        };
    })
    .controller('AttachmentController', function ($scope, $log, $http, $state, $window, $timeout, dialogs, webappSettings, notifyService, attachmentService, sharedAttachmentService) {

        $scope.filter = {
            published: '',
            title: '',
            category: '',
            createdAtBegin: '',
            createdAtEnd: '',
            beginDate: '',
            endDate: ''
        };

        $scope.advancedSearch = false;

        $scope.attachments = [];
        $scope.totalElements = 0;
        $scope.currentPage = 1;

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };

        $scope.refreshAttachments = function () {
            attachmentService.list($scope.filter).success(function (result) {
                $scope.attachments = result.content;
                $scope.totalElements = result.totalElements;
            });
        };

        $scope.addAttachment = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/attachment/edit.html', 'AttachmentDetailController', {}, {
                size: 'lg'
            }).result.then(function (data) {
                if (data) {
                    $scope.refreshAttachments();
                }
            });
        };

        $scope.showDetail = function (attachment) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/attachment/detail.html', 'AttachmentDetailController', {id: attachment.id}, {
                size: 'lg'
            });
        };

        $scope.editAttachment = function (attachmentId) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/attachment/edit.html', 'AttachmentDetailController', {id: attachmentId}, {
                size: 'lg'
            }).result.then(function (data) {
                if (data) {
                    $scope.refreshAttachments();
                }
            });
        };

        $scope.deleteAttachment = function (attachmentId) {
            dialogs.confirm('确认', '是否要删除？', {'size': 'sm'}).result.then(function () {
                attachmentService.delete(attachmentId).success(function () {
                    notifyService.success('已删除');
                    $scope.refreshAttachments();
                });
            });
        };

        $scope.publishAttachment = function (attachmentId) {
            dialogs.confirm('确认', '是否要发布？', {'size': 'sm'}).result.then(function () {
                attachmentService.publish(attachmentId).success(function () {
                    notifyService.success('已发布');
                    $scope.refreshAttachments();
                });
            });
        };

        $scope.unPublishAttachment = function (attachmentId) {
            dialogs.confirm('确认', '是否要取消发布？', {'size': 'sm'}).result.then(function () {
                attachmentService.unPublish(attachmentId).success(function () {
                    notifyService.success('已取消发布');
                    $scope.refreshAttachments();
                });
            });
        };

        $scope.downLoadFile = function (attachmentId) {
            attachmentService.downloadAttachment(attachmentId);
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshAttachments();
        };

        $timeout(function () {
            jQuery('.datepicker').datepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true
            });
        }, 500);

        $scope.resetFilters = function () {
            $scope.filter = {
                published: '',
                title: '',
                category: '',
                createdAtBegin: '',
                createdAtEnd: '',
                beginDate: '',
                endDate: ''
            };
        };


        $scope.refreshAttachments();

    })
    .controller('AttachmentDetailController', function ($scope, $rootScope, $http, $log, dialogs, $modalInstance, uiUploader, notifyService, tooltipService, attachmentService, sharedAttachmentService, data) {


        $scope.attachment = {};
        $scope.files = [];

        $scope.deleteFileIds = [];

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.validateForm = function () {
            var validated = true;
            if ($scope.attachment.category && $scope.attachment.category.length > 10) {
                tooltipService.showToolTip('attachment.category', '分类不能超过10个字');
                validated = false;
            }
            if (!$scope.attachment.title) {
                tooltipService.showToolTip('attachment.title', '标题不能为空');
                validated = false;
            } else if ($scope.attachment.title.length > 50) {
                tooltipService.showToolTip('attachment.title', '标题不能超50个字');
                validated = false;
            }

            return {
                then: function (func) {
                    if (validated) {
                        func.apply($scope);
                    }
                }
            }
        };

        $scope.saveAttachment = function () {
            $scope.validateForm().then(function () {
                $scope.syncDeletedFile().then(function () {
                    if (uiUploader.getFiles().length > 0) {
                        angular.forEach(uiUploader.getFiles(), function (file) {
                            if (file.size > 20 * 1024 * 1024) {
                                notifyService.error('文件[' + file.name + ']不能大于20M');
                            } else {
                                var request = {
                                    name: file.name,
                                    category: 'Attachment',
                                    uploadedBy: $rootScope.currentUser.username,
                                    file: file
                                };
                                if (!request.file.id) {
                                    sharedAttachmentService.upload(request).success(function (fileId) {
                                        request.file.loaded = true;
                                        request.file.id = fileId;
                                        $scope.attachment.fileObjectId = fileId;
                                        $scope.saveAttachmentInfo();
                                    });
                                } else {
                                    $scope.saveAttachmentInfo();
                                }
                            }

                        });

                    } else {
                        $scope.saveAttachmentInfo();
                    }
                });
            });

        };

        $scope.syncDeletedFile = function () {
            return {
                then: function (func) {
                    if ($scope.deleteFileIds.length > 0) {
                        angular.forEach($scope.deleteFileIds, function (fileId) {
                            sharedAttachmentService.deleteById(fileId).success(function (fileId) {
                                $scope.attachment.fileObjectId = null;
                                func.apply($scope);
                            });
                        });
                    } else {
                        func.apply($scope);
                    }
                }
            }
        };

        $scope.saveAttachmentInfo = function () {
            if (data.id) {
                attachmentService.update(data.id, $scope.attachment).success(function (result) {
                    $scope.attachment = result;
                    notifyService.success("已保存");
                    $modalInstance.close('refresh');
                });
            } else {
                attachmentService.create($scope.attachment).success(function (result) {
                    $scope.attachment = result;
                    notifyService.success("已保存");
                    $modalInstance.close('refresh');
                });
            }
        };

        // var element = angular.element('#file1')[0];
        // element.addEventListener('change', function (e) {
        //     var files = e.target.files;
        //     uiUploader.addFiles(files);
        //     $scope.files = uiUploader.getFiles();
        //     $scope.$apply();
        // });

        $scope.fileChanged = function (e) {
            var files = e.target.files;
            uiUploader.addFiles(files);
            $scope.files = uiUploader.getFiles();
            $scope.$apply();
        };

        $scope.btn_remove = function (file) {
            $log.info('deleting=' + file);
            dialogs.confirm('确认', '是否要删除附件？', {'size': 'sm'}).result.then(function () {
                if (file.id) {
                    $scope.deleteFileIds.push(file.id);
                }
                $scope.files = [];
                uiUploader.removeFile(file);
            });
        };

        $scope.btn_download = function (file) {
            if (file.id) {
                sharedAttachmentService.downloadById(file.id);
            }
        };

        $scope.histories = [];
        $scope.historiesCount = 0;
        $scope.historiesFilter = {};
        $scope.historiesCurrentPage = 1;
        $scope.refreshDownloadHistories = function () {
            attachmentService.listDownloadHistory($scope.attachment.id, $scope.historiesFilter).success(function (result) {
                $scope.histories = result.content;
                $scope.historiesCount = result.totalElements;
            });
        };

        $scope.pageChanged = function () {
            $scope.historiesFilter.start = $scope.historiesCurrentPage - 1;
            $scope.refreshDownloadHistories();
        };


        $scope.init = function () {
            uiUploader.removeAll();
            if (data.id) {
                attachmentService.findById(data.id).success(function (result) {
                    $scope.attachment = result;
                    if ($scope.attachment.fileObjectId) {
                        sharedAttachmentService.getFileObjectDetail($scope.attachment.fileObjectId).success(function (file) {
                            var buildFile = {
                                id: file.id,
                                name: file.originalFilename,
                                loaded: true
                            };
                            $scope.files.push(buildFile);
                            uiUploader.addFiles($scope.files);
                        });
                    }

                });
            }
        };

        $scope.init();
    });
