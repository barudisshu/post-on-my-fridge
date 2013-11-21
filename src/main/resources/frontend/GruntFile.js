module.exports = function(grunt) {
  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    emberTemplates: {
       compile: {
        options: {
          templateBasePath: /web\/templates\//,
          templateFileExtensions: /\.hbs/
        },
        files: {
          "web/dist/templates.js": ["web/templates/**/*.hbs"]
        }
      }
    },
    concat : {
      libjs : {
        src : [
          "web/bower_components/jquery/jquery.min.js",
          "web/vendor/js/bootstrap.min.js",
          //"web/bower_components/bootstrap/dist/js/bootstrap.min.js,
          "web/bower_components/handlebars/handlebars.runtime.js",
          "web/bower_components/ember/ember.min.js",
          "web/bower_components/momentjs/min/moment.min.js",
          "web/bower_components/store.js/store.min.js",
          "web/bower_components/js-url/url.min.js",
          "web/bower_components/typeahead.js/dist/typeahead.min.js",
          "web/bower_components/nprogress/nprogress.js",
          "web/bower_components/jquery-ui/ui/minified/jquery-ui.min.js",
          "web/bower_components/bacon/dist/Bacon.min.js",
          "web/bower_components/alertify.js/dist/alertify.min.js"
        ],
        dest: 'web/dist/libs.min.js'
      },
      libcss : {
        src : [
          "web/vendor/css/bootstrap.min.css",
          //"web/bower_components/bootstrap/dist/css/bootstrap.min.css",
          "web/bower_components/nprogress/nprogress.css",
          "web/bower_components/alertify.js/dist/themes/alertify.bootstrap.css"
        ],
        dest : 'web/dist/libs.min.css'
      }
    },
    uglify: {
      js: {
        files: {
          'web/dist/pomf.min.js': [
            "web/dist/templates.js",
            "web/js/tools.js",
            "web/js/app.js",
            "web/js/jquery-ui-ember.js",
            "web/js/dao.js",
            "web/js/router.js",
            "web/js/view/customViews.js",
            "web/js/controller/mainHeaderController.js",
            "web/js/view/mainHeaderView.js",
            "web/js/model/indexModel.js",
            "web/js/controller/indexController.js",
            "web/js/view/indexView.js",
            "web/js/model/fridgeModel.js",
            "web/js/controller/fridgeController.js",
            "web/js/view/fridgeView.js",
            "web/js/model/postModel.js",
            "web/js/view/postView.js",
            "web/js/controller/postsController.js",
            "web/js/view/postsView.js",
            "web/js/model/messageModel.js",
            "web/js/view/messageView.js",
            "web/js/view/messagesView.js",
            "web/js/controller/messagesController.js",
            "web/js/view/chatInputView.js",
            "web/js/controller/chatInputController.js",
            "web/js/view/panelView.js",
            "web/js/controller/panelController.js",
          ]
        }
      }
    },
    cssmin : {
      combine: {
        files: {
          "web/dist/pomf.min.css" : [
            "web/css/typeahead-custom.css",
            "web/css/layout.css",
            "web/css/post.css"
            ]
        } 
      }   
    },
    watch: {
      files: ["web/css/**","web/js/**","web/templates/**"],
      tasks: ['default']
    }
  });

  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-ember-templates');
  grunt.loadNpmTasks('grunt-contrib-watch');

  // Default task(s).
  grunt.registerTask('default', ['emberTemplates','concat','uglify','cssmin' ]);

};