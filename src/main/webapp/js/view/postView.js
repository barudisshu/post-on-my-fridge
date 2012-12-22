App.PostView = Em.View.extend(App.Draggable, {
	tagName: 'article',
	classNames: ['post'],
	uiType: 'draggable',
    templateName: 'post-template',

	relativeDate: function() {
		var date = this.get('content').get('date');
		return moment(date).fromNow();
	}.property('content.date').cacheable(),

	generatedContent: function() {
		return this.generateContent();
	}.property('content.content').cacheable(),

	mouseEnter: function(event) {
		this.$().find(".header").css({
			'position': 'absolute',
			'left': 0,
			'top': '-18px'
		}).fadeIn(300);
	},

	mouseLeave: function(event) {
		var uiElmt = event.currentTarget;
		if (!$(uiElmt).hasClass("header")) {
			this.$().find(".header").fadeOut(300);
		}
	},

	doubleClick: function() {
		alert('Live edit feature coming soon');
	},

	didInsertElement: function() {
		var view = this;

		view.initTrash();
		view.colorize();
		view.updatePhysicalPosition();

		view.$().draggable({
			revert: 'invalid',

			stop: function(event) {
				var fridge = $('#fridge');
				var fullPosition = view.$().offset().left / fridge.width() + ' ' + view.$().offset().top / fridge.height();
				view.get('content').set('fullPosition', fullPosition);
			}
		});
	},

	willDestroyElement: function() {
		this.$().effect("bounce", {
			times: 3
		}, 300).effect("clip", {
			times: 1
		}, 300);

		libnotify = humane.create({
			baseCls: 'humane-libnotify',
			addnCls: 'humane-libnotify-info'
		});
		libnotify.log("Post from " + this.get('content').get('author') + " deleted");
	},

	updatePhysicalPosition: function() {
		var left = this.get('content').get('positionX');
		var top = this.get('content').get('positionY');
		var fridge = $('#fridge');

		xTranslation = (left * fridge.width() - parseInt(this.$().offset().left, 10));
		yTranslation = (top * fridge.height() - parseInt(this.$().offset().top, 10));

		if (xTranslation != 0 || yTranslation != 0){
			this.$().animate({
				'left': "+=" + xTranslation,
				'top': "+=" + yTranslation
			}, 'slow', 'linear');
		}
	}.observes('content.fullPosition'),

	colorize: function() {
		var color = this.get('content').get('color');
		this.$().css("background-color", color);
		this.$().css("color", getTxtColorFromBg(color));
	},

	initTrash: function() {
		var view = this;
		view.$().find(" .ui-icon-trash").click(function() {
			if (confirm("Are you sure you want to delete post?")) {
				App.FridgeController.deletePost(view.get('content').get('id'));
			}
		});
	},

	generateContent: function() {
		content = jQuery.trim(this.get('content').get('content'));
		urlRegexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
		pictureRegexp = /(http|https):\/\/[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(?:\/\S*)?(?:[a-zA-Z0-9_])+\.(?:jpg|JPG|jpeg|gif|png)$/;

		var firstWordUrl = _.find(content.split(' '), function(word){ return isRegExp(urlRegexp, word); });
		if (firstWordUrl == undefined){
			return content
		}else{
			var url = purl(firstWordUrl);
			if (url.attr('host') == "www.youtube.com") {
				return generateYoutubeFrame(url.param('v'));
			} else if (url.attr('host') == "vimeo.com") {
				return generateVimeoFrame(url.segment(1));
			} else if (url.attr('host') == "www.dailymotion.com") {
				return generateDailyMotionLink((url.segment(2)).split('_')[0]);	
			} else if (isRegExp(pictureRegexp, firstWordUrl)) {
				return generatePictureLink(firstWordUrl);
			} else {
				return generateHrefLink(firstWordUrl);
			}
		}	
	}
});