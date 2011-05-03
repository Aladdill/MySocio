function openMessage(id){
	window.alert(id);
}

function openUrlInDiv(divId, url, functions){
	YUI().use("io-base", "node", function(Y) {
		function complete(id, o, args) {
			onSuccess(id, o, args, Y, divId);
		};
		Y.on('io:success', complete, Y, functions);
		Y.on('io:failure', onFailure, Y, []);
		var request = Y.io(url);
	});
}
function initSources(){
	var tree = new dhtmlXTreeObject("sources_tree","100%","100%",0);
	tree.setImagePath("lib/dhtmlxTree/codebase/imgs/");
	tree.loadXML("login?command=getSources", function(){});
	tree.attachEvent("onSelect", getMessages);
}
function commitForm(form, functions){
	YUI().use("io-form", function(Y) {
		var cfg = {
			method: 'POST',
			form: {
				id: form,
				useDisabled: true
			}
		};
		function complete(id, o, args) {
			onSuccess(id, o, args, Y, "#SiteBody");
		};
		Y.on('io:success', complete, Y, functions);
		Y.on('io:failure', onFailure, Y, []);
		var request = Y.io("login", cfg);
	});
}
function onSuccess(transactionid, response, functions, Y, divId) {
	var data = response.responseText;
	var node = Y.one(divId);
	node.set('innerHTML', data);
	for (var x=0; x < functions.length; x++){
		functions[x].call(this);
	}
}
function onFailure(transactionid, response, arguments) {
	var data = response.responseText;
	window.alert(data);
}
function openSettings(){
	openUrlInDiv("#SiteBody", "login?command=openSettings",[getRssFeeds]);
}
function openMainPage(){
	openUrlInDiv("#SiteBody", "login?command=openMainPage",[getAllMessages,initSources]);
}
function logout(){
	openUrlInDiv("#SiteBody", "login?command=logout",[]);
}
function loadStartPage(){
	openUrlInDiv("#SiteBody", "login?command=openStartPage",[]);
}
function getRssFeeds(){
	openUrlInDiv("#RssList", "login?command=getRssFeeds",[]);
}
function getMessages(id){
	openUrlInDiv("#data_container", "login?command=getMessages&sourceId=" + id,[]);
}
function getAllMessages(){
	openUrlInDiv("#data_container", "login?command=getMessages&sourceId=all",[]);
}