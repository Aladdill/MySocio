function openMessage(id){
	window.alert(id);
}

function openUrlInDiv(divId, url, functions){
	YUI().use("io-base", "node", function(Y) {
		function complete(id, o, args) {
			onSuccess(id, o, args, divId);
		};
		Y.on('io:success', complete, Y, functions);
		Y.on('io:failure', onFailure, Y, []);
		var request = Y.io(url);
	});
}
function resizeTabs(){
	$("#accounts_tab_div").css("height",$("#accounts_tab_span").innerWidth() + 10);
	$("#contacts_tab_div").css("height",$("#contacts_tab_span").innerWidth() + 10);
	$("#feeds_tab_div").css("height",$("#feeds_tab_span").innerWidth() + 10);
	$("#accounts_tab_span").css("bottom",$("#accounts_tab_span").innerWidth()/2*-1 + 5);
	$("#contacts_tab_span").css("bottom",$("#contacts_tab_span").innerWidth()/2*-1 + 5);
	$("#feeds_tab_span").css("bottom",$("#feeds_tab_span").innerWidth()/2*-1 + 5);
	$("#accounts_tab_span").css("left",$("#accounts_tab_span").innerHeight()*-1-2);
	$("#contacts_tab_span").css("left",$("#contacts_tab_span").innerHeight()*-1);
	$("#feeds_tab_span").css("left",$("#feeds_tab_span").innerHeight()*-1-3);
}
function centerLoginCircle(){
	$("#AuthCircle").css("top",$("#AuthCell").innerHeight()/2 - $("#AuthCircle").innerHeight()/2);
	$("#AuthCircle").css("left",$("#AuthCell").innerWidth()/2 - $("#AuthCircle").innerWidth()/2);
	$("#promo_div").css("left",$("#promo_cell").innerWidth()/2 - $("#promo_div").innerWidth()/2);
}
function initSources(){
	var messageContainer = $("#data_container");
	messageContainer.css("height",$("body").innerHeight() - 81);
	$("#filler").css("height",messageContainer.innerHeight() - 10);
    messageContainer.scroll(function () {
    	var previous = 0;
    	$.each($("#data_container").find("div"), function(index, value) {
    		var message = $("#" + value.id);
    		var current = message.position().top - 48;
    		if ((previous < 0 && current >= 0 && value.id != "filler") || (current == 0 && index ==0)){
    			message.css("border","3px solid #7d4089");
    	}else{message.css("border","none");}
    		previous = message.position().top - 48;
	});
	});
	var tree = new dhtmlXTreeObject("sources_tree","100%","100%",0);
	tree.setImagePath("lib/dhtmlxTree/codebase/imgs/");
	tree.loadXML("execute?command=getSources", function(){});
	tree.attachEvent("onSelect", getMessages);
}
function commitLoginForm(form){
	YUI().use("io-form", function(Y) {
		var cfg = {
			method: "POST",
			form: {
				id: "LoginForm",
				useDisabled: true
			}
		};
		function complete(id, o, args) {
			window.open(o.responseText,"name","height=500,width=500");
		};
		Y.on('io:success', complete, Y, functions);
		Y.on('io:failure', onFailure, Y, []);
		var request = Y.io("login", cfg);
	});
}
function onSuccess(transactionid, response, functions, divId) {
	$(divId).html(response.responseText);
	for (var x=0; x < functions.length; x++){
		functions[x].call(this);
	}
}
function login(identifier){
	$("#identifier").attr("value",identifier);
	commitLoginForm();
}
function hideTabs(){
	hideDiv("tabs");
}
function showTabs(){
	showDiv("tabs");
}
function hideSources(){
	hideDiv("sources_tree");
}
function showSources(){
	showDiv("sources_tree");
}
function hideDiv(id){
	$("#"+id).css("display", "none");
}
function showDiv(id){
	$("#"+id).css("display", "block");
}
function onFailure(transactionid, response, arguments) {
	var data = response.responseText;
	window.alert(data);
}
function showSettings(){
	hideSources();
	showTabs();
	getRssFeeds();
}
function showMainPage(){
	hideTabs();
	resizeTabs();
	showSources();
	initSources();
}
function loadMainPage(){
	openUrlInDiv("#SiteBody", "execute?command=openMainPage",[showMainPage]);
}
function logout(){
	openUrlInDiv("#SiteBody", "execute?command=logout",[centerLoginCircle]);
}
function loadStartPage(){
	openUrlInDiv("#SiteBody", "execute?command=openStartPage",[centerLoginCircle]);
}
function getRssFeeds(){
	openUrlInDiv("#data_container", "execute?command=getRssFeeds",[]);
}
function getMessages(id){
	openUrlInDiv("#data_container", "execute?command=getMessages&sourceId=" + id,[]);
}
function getAllMessages(){
	openUrlInDiv("#data_container", "execute?command=getMessages&sourceId=all",[]);
}