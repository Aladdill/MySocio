function openMessage(id){
	window.alert(id);
}

function openUrlInDiv(divId, url, functions){
	$.post(url).success(function(data) { $(divId).html(data); })
	    .error(onFailure)
	    .complete(functions);	
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
	$.jstree._themes = "../treeThemes";
    $.post("execute?command=getSources").success(function(data) { $("#sources_tree")
    	.jstree({
    		"json_data" : {"data" : [data]},
			"themes" : {"theme" : "default", "dots" : false, "icons" : true},
    		"plugins" : ["themes","json_data","ui","crrm"],
    		"core" : { "initially_open" : [ "All" ]}
    	})
    	.bind("loaded.jstree", function (event, data) {
    	}).bind("select_node.jstree", function (e, data) { getMessages(jQuery.data(data.rslt.obj[0], "id")); }); })
    .error(onFailure);
}
function login(identifierValue){
	$.post("login",{
		identifier : identifierValue,
		email : "test@test.com"
	}).success(function(data) { window.open(data,"name","height=500,width=500");})
    .error(onFailure);
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
function onFailure(data) {
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
	initMessagesContainer();
	showSources();
	initSources();
}
function initMessagesContainer(){
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