App.FridgesController = Ember.ArrayController.extend({
	sortProperties: ['modificationDate'],
	sortAscending: false,
	page: 1,

	init: function () {	},	

 	actions: {  
 		createFridge : function(name){
			var me = this;
			App.Dao.createFridge(name).then(function (fridge) {
				me.transitionToRoute('fridge', App.Dao.initSessionData(fridge.id));	
			});	
		},

 		nextPage : function() {
 			var currentPage = parseInt(this.get("page"));
 			if (this.get('content').length == App.Dao.get("pageSize")){
 				this.loadFridgesPage(currentPage + 1);
 			}
		},

		previousPage : function() {
			var currentPage = parseInt(this.get("page"));
			if (currentPage != 1){
				this.loadFridgesPage(currentPage - 1);
			}
		}
 	},

 	loadFridgesPage: function (page) {
 		var me = this;
		App.Dao.getFridges(page).then(function (fridges) {
			me.get("content").clear();
		  	me.get("content").pushObjects(fridges);
		   	me.set("page", page);
		});
 	}
});