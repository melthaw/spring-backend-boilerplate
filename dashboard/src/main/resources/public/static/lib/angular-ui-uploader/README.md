# ui-uploader [![Build Status](https://travis-ci.org/angular-ui/ui-uploader.svg?branch=master)](https://travis-ci.org/angular-ui/ui-uploader) [![npm version](https://badge.fury.io/js/angular-ui-uploader.svg)](http://badge.fury.io/js/angular-ui-uploader) [![Bower version](https://badge.fury.io/bo/angular-ui-uploader.svg)](http://badge.fury.io/bo/angular-ui-uploader) [![Join the chat at https://gitter.im/angular-ui/ui-uploader](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/angular-ui/ui-uploader?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

ui-uploader is a single/multiple and high customizable file uploader and the most important is very easy to implement.

  - Upload multiple or single files
  - Cancel or remove upload when you want.
  - Allows concurrent Upload
  - Totally cutomizable

You can use with html5, jquery or every library or framework:

> The main objective of ui-uploader is
>  to have a user control, clean, simple, customizable,
> and above all very easy to implement.

Try the [demo](http://realtica.org/ng-uploader/demo.html).


Compatibility
-------------

Because this project uses [FormData](http://caniuse.com/#search=formdata), it does **not** work on IE9 or earlier.

## Requirements

- AngularJS

## Usage


You can get it from [Bower](http://bower.io/)

```sh
bower install angular-ui-uploader
```

Load the script files in your application:

```html
<script type="text/javascript" src="bower_components/angular/angular.js"></script>
<script type="text/javascript" src="bower_components/angular-ui-uploader/dist/uploader.js"></script>
```

Add the specific module to your dependencies:

```javascript
angular.module('myApp', ['ui.uploader', ...])
```

Now you can use the ui-uploader methods.

```javascript
$uiUploader.addFiles(files);
$uiUploader.remove(file);
$uiUploader.removeAll();
```

Configure ui-uploader callbacks and start!

```javascript
$uiUploader.startUpload({
                url: 'http://my_domain.com',
                concurrency: 2,
                onProgress: function(file) {
                    // file contains a File object
                    console.log(file);
                },
                onCompleted: function(file, response) {
                    // file contains a File object
                    console.log(file);
                    // response contains the server response
                    console.log(response);
                }
                onCompletedAll: function(files) {
                	// files is an array of File objects
                	console.log(files);
                }
            });
```

Perform CORS AJAX requests by setting the options.withCredentials flag to true!

```javascript
$uiUploader.startUpload({
                url: 'http://my_domain.com/path/to/api-endpoint',
                options: {
                	withCredentials: true
                },
                onProgress: function(file) {
                    //do stuff
                },
                onCompleted: function(file, response) {
                    // do stuff
                }
                onCompletedAll: function(files) {
                	// do stuff
                }
            });
```

Configure custom request headers by options.headers field

```javascript
$uiUploader.startUpload({
                url: 'http://my_domain.com',
                concurrency: 2,
                headers: {
                    'Accept': 'application/json'
                },
                onCompletedAll: function(files) {
                	// files is an array of File objects
                	console.log(files);
                }
            });
```

## Development

We use Karma and jshint to ensure the quality of the code.  The easiest way to run these checks is to use grunt:

```sh
npm install -g gulp-cli
npm install && bower install
gulp
```

The karma task will try to open Firefox and Chrome as browser in which to run the tests.  Make sure this is available or change the configuration in `karma.conf.js`


### Gulp watch

`gulp watch` will automatically test your code and build a release whenever source files change.

### How to release

Use gulp to bump version, build and create a tag. Then push to GitHub:

````sh
gulp release [--patch|--minor|--major]
git push --tags origin master # push everything to GitHub
````

Travis will take care of testing and publishing to npm's registry (bower will pick up the change automatically). Finally [create a release on GitHub](https://github.com/angular-ui/ui-uploader/releases/new) from the tag created by Travis.
