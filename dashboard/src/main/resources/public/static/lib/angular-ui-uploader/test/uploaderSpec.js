describe('uiUploader', function() {
    'use strict';

    var uiUploader;

    beforeEach(module('ui.uploader'));
    beforeEach(inject(function(_uiUploader_) {
        uiUploader = _uiUploader_;
    }));

    describe('#addFiles', function() {
        it('adds files to the file array', function() {
            var file = new File([''], 'testFile');
            uiUploader.addFiles([file]);

            expect(uiUploader.files).toEqual([file]);
        });
    });

    describe('#removeFile', function() {
        it('removes the specified file from the file array', function() {
            var fileToDelete = new File([''], 'fileToDelete');
            var fileToKeep = new File([''], 'fileToKeep');
            uiUploader.addFiles([fileToKeep, fileToDelete]);

            uiUploader.removeFile(fileToDelete);

            expect(uiUploader.files).toEqual([fileToKeep]);
        });
    });

    describe('#removeAll', function() {
        it('removes all files from the file array', function() {
            var files = [1,2,3,4,5].map(function() {
                return new File([''], 'testFile');
            });
            uiUploader.addFiles(files);

            uiUploader.removeAll();

            expect(uiUploader.files).toEqual([]);
        });
    });

    describe('#files / #getFiles', function() {
        it('returns the added files', function() {
            var files = [1,2,3,4,5].map(function() {
                return new File([''], 'testFile');
            });
            uiUploader.addFiles(files);

            expect(uiUploader.getFiles()).toEqual(files);
            expect(uiUploader.files).toEqual(files);
        });
    });
});
