function expandMessage(id) {
	$("#" + id).children(".MessageText").addClass("Expand");
	$("#" + id).children(".MessageExpand").addClass("Invisible");
	$("#" + id).children(".MessageCollapse").removeClass("Invisible");
	$("#" + id).addClass("Expand");
}
function collapseMessage(id) {
	$("#" + id).children(".MessageText").removeClass("Expand");
	$("#" + id).children(".MessageExpand").removeClass("Invisible");
	$("#" + id).children(".MessageCollapse").addClass("Invisible");
	$("#" + id).removeClass("Expand");
}
function openUrlInDiv(divId, url, functions) {
	$.post(url).success(function(data) {
		$(divId).html(data);
	}).error(onFailure).complete(functions);
}
function resizeTabs() {
	$("#accounts_tab_div").css("height",
			$("#accounts_tab_span").innerWidth() + 10);
	$("#contacts_tab_div").css("height",
			$("#contacts_tab_span").innerWidth() + 10);
	$("#feeds_tab_div").css("height", $("#feeds_tab_span").innerWidth() + 10);
	$("#accounts_tab_span").css("bottom",
			$("#accounts_tab_span").innerWidth() / 2 * -1 + 5);
	$("#contacts_tab_span").css("bottom",
			$("#contacts_tab_span").innerWidth() / 2 * -1 + 5);
	$("#feeds_tab_span").css("bottom",
			$("#feeds_tab_span").innerWidth() / 2 * -1 + 5);
	$("#accounts_tab_span").css("left",
			$("#accounts_tab_span").innerHeight() * -1 - 2);
	$("#contacts_tab_span").css("left",
			$("#contacts_tab_span").innerHeight() * -1);
	$("#feeds_tab_span").css("left",
			$("#feeds_tab_span").innerHeight() * -1 - 3);
}
function centerLoginCircle() {
	$("#login_center_div").css(
			"top",
			$("#login_page_div").innerHeight() / 2
					- $("#login_center_div").innerHeight() / 2);
}
function initSources() {
	$.post("execute?command=getSources", {}, initSourcesData, "json").error(
			onFailure);
}
function initSourcesData(data) {
	$("#sources_tree").jstree(data).bind("loaded.jstree",
			function(event, data) {
			}).bind("select_node.jstree", function(e, data) {
		if (!$("#sources_tree").data("treeRefreshInitiated")) {
			getMessages(data.rslt.obj[0].id);
		} else {
			$("#sources_tree").data("treeRefreshInitiated", false);
		}
	});
}
function openAuthenticationWindow(identifierValue, flowValue) {
	$.post("execute?command=startAuthentication", {
		identifier : identifierValue,
		flow : flowValue
	}).success(function(data) {
		window.open(data, "name", "height=500,width=500");
	}).error(onFailure);
}
function login(identifierValue) {
	openAuthenticationWindow(identifierValue, "login");
}
function addAccount(identifierValue) {
	openAuthenticationWindow(identifierValue, "addAccount");
}
function addRssFeed() {
	$.post("execute?command=addRssFeed", {
		url : $("#rssUrl").attr("value")
	}).success(function(data) {
		$("#data_container").html(data);
	}).error(onFailure);
}
function removeRssFeed(feedId) {
	$.post("execute?command=removeRssFeed", {
		id : feedId
	}).success(function(data) {
		$("#data_container").html(data);
	}).error(onFailure);
}
function hideTabs() {
	hideDiv("tabs");
}
function showTabs() {
	showDiv("tabs");
}
function hideSources() {
	hideDiv("sources_tree");
	hideDiv("subscriptions_underline");
	hideDiv("subscriptions_title");
	//Stop refreshing sources when user in other tab
	$("#sources_tree").stopTime("refreshSources");
}
function showSources() {
	showDiv("sources_tree");
	showDiv("subscriptions_underline");
	showDiv("subscriptions_title");
	initSources();
	//Start refreshing sources when user logs in
	$("#sources_tree").everyTime("60s", "refreshSources", refreshSources, 0);
}
function refreshSources(){
	$("#sources_tree").data("treeRefreshInitiated", true);
	initSources();
}
function hideDiv(id) {
	$("#" + id).css("display", "none");
}
function showDiv(id) {
	$("#" + id).css("display", "block");
}
function onFailure(data) {
	window.alert(data.responseText);
}
function showSettings() {
	hideSources();
	showTabs();
	$("#upper_link").html($("#back_to_main_link").html());
	showAccounts();
}
function showRssFeeds() {
	openUrlInDiv("#data_container", "execute?command=getRssFeeds", []);
}
function showMainPage() {
	hideTabs();
	showSources();
	$("#upper_link").html($("#settings_link").html());
	$("#data_container").html("<div id='filler' class='filler'></div>");
	$("#filler").css("height", $("#data_container").innerHeight() - 10);
}
function openMainPage() {
	resizeTabs();
	hideTabs();
	initMessagesContainer();
	showSources();
}
function initMessagesContainer() {
	var messageContainer = $("#data_container");
	messageContainer.css("height", $("body").innerHeight() - 81);
	$("#filler").css("height", messageContainer.innerHeight() - 10);
	messageContainer.scroll(messageScroll);
}
function messageScroll() {
	var previous = 0;
	$.each($(".Message"), function(index, value) {
		var message = $("#" + value.id);
		var current = message.position().top + 100;
		if ((previous * current <= 0 && value.id != "filler")) {
			message.addClass("SelectedMessage");
			message.children(".MessageTitle").addClass("MessageTitleSelected");
			message.children(".MessageTitle").children(".NetworkIconReaden")
					.addClass("Invisible");
			message.children(".MessageTitle").children(".NetworkIcon")
					.removeClass("Invisible");
			message.children(".MessageText").removeClass("MessageTextReaden");
			message.children(".MessageText").addClass("MessageTextSelected");
			message.children(".MessageButtons").addClass(
					"MessageButtonsSelected");
			message.children(".MessageExpand")
					.addClass("MessageExpandSelected");
			message.children(".MessageCollapse").addClass(
					"MessageCollapseSelected");
			if (message.data("wasSelected") == undefined) {
				markMessageReadden(value.id);
				message.data("wasSelected", "true");
				message.children(".MessageTitle")
						.addClass("MessageTitleReaden");
			}
		} else {
			if (message.hasClass("SelectedMessage")) {
				message.removeClass("SelectedMessage");
				message.children(".MessageTitle").removeClass(
						"MessageTitleSelected");
				message.children(".MessageTitle")
						.children(".NetworkIconReaden")
						.removeClass("Invisible");
				message.children(".MessageTitle").children(".NetworkIcon")
						.addClass("Invisible");
				message.children(".MessageText").addClass("MessageTextReaden");
				message.children(".MessageText").removeClass(
						"MessageTextSelected");
				message.children(".MessageButtons").removeClass(
						"MessageButtonsSelected");
				message.children(".MessageExpand").removeClass(
						"MessageExpandSelected");
				message.children(".MessageCollapse").removeClass(
						"MessageCollapseSelected");
			}
		}
		previous = current;
	});
}
function markMessageReadden(id) {
	$.post("execute?command=markMessagesReaden&messagesIds=" + id).error(onFailure);
}
function initPage() {
	if ($("#login_center_div").size() != 0) {
		centerLoginCircle();
	} else {
		openMainPage();
	}
}
function loadMainPage() {
	openUrlInDiv("#SiteBody", "execute?command=openMainPage", [ initPage ]);
}
function logout() {
	openUrlInDiv("#SiteBody", "execute?command=logout", [ initPage ]);
}
function loadStartPage() {
	openUrlInDiv("#SiteBody", "execute?command=openStartPage", [ initPage ]);
}
function getMessages(id) {
	$(".Message").remove();
	$.post("execute?command=getMessages&sourceId=" + id).success(
			function(data) {
				$(data).sort(sortAlpha).insertBefore("#filler");
			}).error(onFailure);
}
function sortAlpha(a,b){  
    return $(a).find(".LongDate").first().html() < $(b).find(".LongDate").first().html() ? 1 : -1;  
}
function showAccounts() {
	openUrlInDiv("#data_container", "execute?command=getAccounts", []);
}
function showContacts() {
	openUrlInDiv("#data_container", "execute?command=getContacts", []);
}