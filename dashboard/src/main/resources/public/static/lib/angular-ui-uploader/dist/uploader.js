/*!
 * angular-ui-uploader
 * https://github.com/angular-ui/ui-uploader
 * Version: 1.1.3 - 2015-12-01T00:54:49.732Z
 * License: MIT
 */


(function () {
    'use strict';
    /*
     * Author: Remy Alain Ticona Carbajal http://realtica.org
     * Description: The main objective of ng-uploader is to have a user control,
     * clean, simple, customizable, and above all very easy to implement.
     * Licence: MIT
     */

    angular.module('ui.uploader', []).service('uiUploader', uiUploader);

    uiUploader.$inject = ['$log'];

    function uiUploader($log) {

        /*jshint validthis: true */
        var self = this;
        self.files = [];
        self.options = {};
        self.activeUploads = 0;
        self.uploadedFiles = 0;
        $log.info('uiUploader loaded');

        function addFiles(files) {
            for (var i = 0; i < files.length; i++) {
                self.files.push(files[i]);
            }
        }


        function getActiveUploads() {
            return self.activeUploads;
        }


        function getFiles() {
            return self.files;
        }

        function startUpload(options) {
            self.options = options;

            //headers are not shared by requests
            var headers = options.headers || {};

            for (var i = 0; i < self.files.length; i++) {
                if (self.activeUploads == self.options.concurrency) {
                    break;
                }
                if (self.files[i].active)
                    continue;

                // if (self.options.transformRequest) {
                //     var fns = self.options.transformRequest
                //     // if (isFunction(fns))
                //     self.options.data = fns(self.files[i], headers);
                // }

                ajaxUpload(self.files[i], self.options.url, self.options.data, headers);
            }
        }

        function removeFile(file) {
            self.files.splice(self.files.indexOf(file), 1);
        }

        function removeAll() {
            self.files.splice(0, self.files.length);
        }

        return {
            addFiles: addFiles,
            getFiles: getFiles,
            getActiveUploads: getActiveUploads,
            files: self.files,
            startUpload: startUpload,
            removeFile: removeFile,
            removeAll: removeAll
        };

        function getHumanSize(bytes) {
            var sizes = ['n/a', 'bytes', 'KiB', 'MiB', 'GiB', 'TB', 'PB', 'EiB', 'ZiB', 'YiB'];
            var i = (bytes === 0) ? 0 : +Math.floor(Math.log(bytes) / Math.log(1024));
            return (bytes / Math.pow(1024, i)).toFixed(i ? 1 : 0) + ' ' + sizes[isNaN(bytes) ? 0 : i + 1];
        }

        function ajaxUpload(file, url, data, headers) {
            var xhr, formData, prop, key = 'file';
            data = data || {};

            self.activeUploads += 1;
            file.active = true;
            xhr = new window.XMLHttpRequest();

            // To account for sites that may require CORS
            if (data.withCredentials === true) {
                xhr.withCredentials = true;
            }

            formData = new window.FormData();
            angular.forEach(file, function (value, key) {
                formData.append(key, value);
            });
            xhr.open('POST', url);

            if (headers) {
                for (var headerKey in headers) {
                    if (headers.hasOwnProperty(headerKey)) {
                        xhr.setRequestHeader(headerKey, headers[headerKey]);
                    }
                }
            }

            // Triggered when upload starts:
            xhr.upload.onloadstart = function () {
            };

            // Triggered many times during upload:
            xhr.upload.onprogress = function (event) {
                if (!event.lengthComputable) {
                    return;
                }
                // Update file size because it might be bigger than reported by
                // the fileSize:
                //$log.info("progres..");
                //console.info(event.loaded);
                file.loaded = event.loaded;
                file.humanSize = getHumanSize(event.loaded);
                if (angular.isFunction(self.options.onProgress)) {
                    self.options.onProgress(file);
                }
            };

            // Triggered when upload is completed:
            xhr.onload = function () {
                // self.activeUploads -= 1;
                // self.uploadedFiles += 1;
                startUpload(self.options);
                if (xhr.status == 200) {
                    if (angular.isFunction(self.options.onCompleted)) {
                        self.options.onCompleted(file, xhr.responseText, xhr.status);
                    }
                    if (self.uploadedFiles === self.files.length) {
                        self.uploadedFiles = 0;
                        self.activeUploads = 0;
                        $log.info('onCompletedAll');
                        if (angular.isFunction(self.options.onCompletedAll)) {
                            self.options.onCompletedAll(self.files);
                        }
                    }
                }
            };

            // Triggered when upload fails:  not support chrome ,so use 'onreadystatechange' instead
            // xhr.onerror = function (e) {
            //     self.activeUploads -= 1;
            //     if (angular.isFunction(self.options.onError)) {
            //         self.options.onError(e, file);
            //     }
            // };

            xhr.onreadystatechange = function (aEvt) {
                if (xhr.readyState == 4) {
                    // $log.info('self.activeUploads0:' + self.activeUploads);
                    // $log.info('self.uploadedFiles0:' + self.uploadedFiles);
                    self.activeUploads -= 1;
                    self.uploadedFiles += 1;
                    // $log.info('self.activeUploads1:' + self.activeUploads);
                    // $log.info('self.uploadedFiles1:' + self.uploadedFiles);
                    if (xhr.status != 200) {
                        if (angular.isFunction(self.options.onError)) {
                            self.options.onError('error', file);
                        }
                    }
                }
            };


            // Append additional data if provided:
            // if (data) {
            //     for (prop in data) {
            //         if (data.hasOwnProperty(prop)) {
            //             formData.append(prop, data[prop]);
            //         }
            //     }
            // }

            // Append file data:
            // formData.append(key, file, file.name);

            // Initiate upload:
            xhr.send(formData);

            return xhr;
        }

    }

}());
