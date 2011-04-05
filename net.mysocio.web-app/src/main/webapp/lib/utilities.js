function openMessage(id){
	window.alert(id);
}

function openUrlInDiv(divId, url){
	YUI().use("io-base", "node", function(Y) {
		function complete(id, o, args) {
			var data = o.responseText;
			var node = Y.one(divId);
			node.set('innerHTML', data);
		};
			Y.on("io:complete", complete, Y, []);
			var request = Y.io(url);
	});
}
function initSources(){
	var tree = new dhtmlXTreeObject("sources_tree","100%","100%",0);
	tree.setImagePath("lib/dhtmlxTree/codebase/imgs/");
	tree.loadXML("login?command=getSources", function(){});
	tree.attachEvent("onSelect", getMessages);
}
function openSettings(){
	openUrlInDiv("#SiteBody", "login?command=openSettings");
}
function openMainPage(){
	openUrlInDiv("#SiteBody", "login?command=openMainPage");
}
function logout(){
	openUrlInDiv("#SiteBody", "login?command=logout");
}
function loadStartPage(){
	openUrlInDiv("#SiteBody", "login?command=openStartPage");
}
function getMessages(id){
	openUrlInDiv("#data_container", "login?command=getMessages&sourceId=" + id);
}