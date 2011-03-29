function openMessage(id){
	window.alert(id);
}
function initSources(){
	
}
function getMessages(id){
	YUI().use("io-base", "node", function(Y) {
		var uri = "login?command=getMessages";
		if (id != all){
			uri += "&sourceId=" + id;
		}else{
			uri += "\";";
		}
		function complete(id, o, args) {
		var data = o.responseText;
		var node = Y.one("#data_container");
		node.set('innerHTML', data);};
		Y.on("io:complete", complete, Y, []);
		var request = Y.io(uri);});
	}
}
function initSources(){
	var tree = new dhtmlXTreeObject("sources_tree","100%","100%",0);
	tree.setImagePath("lib/dhtmlxTree/codebase/imgs/");
	tree.loadXML("login?command=getSources", function(){});
	tree.attachEvent("onSelect", getMessages);
}
