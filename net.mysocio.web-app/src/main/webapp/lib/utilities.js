function expandMessage(id) {
	$("#" + id).find(".MessageText").addClass("Expand");
	$("#" + id).find(".MessageExpand").addClass("Invisible");
	$("#" + id).find(".MessageCollapse").removeClass("Invisible");
	$("#" + id).addClass("Expand");
}
function collapseMessage(id) {
	$("#" + id).find(".MessageText").removeClass("Expand");
	$("#" + id).find(".MessageExpand").removeClass("Invisible");
	$("#" + id).find(".MessageCollapse").addClass("Invisible");
	$("#" + id).removeClass("Expand");
}
function openUrlInDiv(divId, url, functions) {
	$.post(url).success(function(data) {
		if (isNoContent(data)) {
			return;
		}
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
		}else{
			$("#sources_tree").data("treeRefreshInitiated", false);
		}
	});
}
function login(identifierValue) {
	setLoginCookie(identifierValue);
	startAuthentication(identifierValue);
}
function startAuthentication(identifierValue) {
	$.post("execute?command=startAuthentication", {
		identifier : identifierValue,
	}).success(function(data) {
		if (isNoContent(data)) {
			return;
		}
		window.open(data, "name", "height=600,width=900");
	}).error(onFailure);
}

function startHiddenAuthentication(identifierValue) {
	$.cookie('hidden_login_cookie', "true", { path: '/' });
	$.post("execute?command=startAuthentication", {
		identifier : identifierValue,
	}).success(function(data) {
		if (isNoContent(data)) {
			return;
		}
		$("#hiddenFrame").attr('src', data);
	}).error(onFailure);
}

function authenticationDone() {
	showWaitDialog("creating.account.title", "creating.account.message");
	$.post("execute?command=addAccount")
	.success(function(data) {
		if (isNoContent(data)) {
			return;
		}
		closeWaitDialog();
		showAccounts();
	}).error(function(data) { $dialog.dialog("close"); onFailure(data);});
}
function executeLogin() {
	showWaitDialog("login.dialog.title", "login.dialog.message");
	$.removeCookie('hidden_login_cookie');
	$.post("execute?command=login")
	.success(function(data) {
		if (isNoContent(data)) {
			return;
		}
		closeWaitDialog();
		loadMainPage();
	}).error(function(data) { $dialog.dialog("close"); onFailure(data);});
}
function addAccount(identifierValue) {
	if (identifierValue == "lj") {
		openLjAuthentication();
	} else {
		startAuthentication(identifierValue);
	}
}
function openLjAuthentication() {
	var $dialog = $('<div>Username</div>');
	var $input = $('<input type="text">');
	$dialog.append($input)
	$dialog.dialog({
		title : 'Add Livejournal Account',
		buttons : {
			"Ok" : function() {
				var $waitDialog = creatingAccount();
				$.post("execute?command=addAccount", {
					identifier : 'lj',
					username : $input.val()
				}).success(function(data) {
				}).error(onFailure)
				.complete(function() { $waitDialog.dialog("close"); });
				$(this).dialog("close");
			}
		}
	});
}
function addRssFeed() {
	$.post("execute?command=addRssFeed", {
		url : $("#rssUrl").attr("value")
	}).success(setDataContainer).error(onFailure);
}
function importOPML() {
	importingOPML();
	$("form#importOPMLform").submit();
}
function removeRssFeed(feedId) {
	$.post("execute?command=removeRssFeed", {
		id : feedId
	}).success(setDataContainer).error(onFailure);
}
function setDataContainer(data) {
	if (isNoContent(data)) {
		return;
	}
	$("#data_container").html(data);
	closeWaitDialog();
}
function isNoContent(data) {
	return (data.status == 204);
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
	// Stop refreshing sources when user in other tab
	$("#sources_tree").stopTime("refreshSources");
}
function showSources() {
	showDiv("sources_tree");
	showDiv("subscriptions_underline");
	showDiv("subscriptions_title");
	initSources();
	// Start refreshing sources when user logs in
	$("#sources_tree").everyTime("60s", "refreshSources", refreshSources, 0);
}
function refreshSources() {
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
	if (data.responseText == "restart"){
		loadStartPage();
		return;
	}
	showError(data.responseText);
}
function showError(error) {
	showWaitDialog("Error", error);
}
function importingOPML() {
	showWaitDialog("importing.opml.title", "importing.opml.message");
}
function showWaitDialog(titleString, message) {
	$dialog = $("<div id='waitDialog'></div>").html(jQuery.i18n.prop(message)).dialog({
		title : jQuery.i18n.prop(titleString),
		modal : true
	});
}
function closeWaitDialog(){
	$dialog.dialog("close");
}
function showSettings() {
	hideSources();
	showTabs();
	$("#upper_link").html($("#back_to_main_link").html());
	clearDataContainer();
	showAccounts();
}
function clearDataContainer(){
	$("#data_container").html("");
}
function showRssFeeds() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv("#data_container", "execute?command=getRssFeeds", []);
}
function showMainPage() {
	hideTabs();
	clearDataContainer();
	showSources();
	$("#post_container").removeClass("Invisible");
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
	messageContainer.css("height", $("body").innerHeight() - 158);
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
			message.find(".MessageTitle").addClass("MessageTitleSelected");
			message.find(".MessageTitle").find(".NetworkIconReaden")
					.addClass("Invisible");
			message.find(".MessageTitle").find(".NetworkIcon")
					.removeClass("Invisible");
			message.find(".MessageText").removeClass("MessageTextReaden");
			message.find(".MessageText").addClass("MessageTextSelected");
			message.find(".MessageButtons").addClass(
					"MessageButtonsSelected");
			message.find(".MessageExpand")
					.addClass("MessageExpandSelected");
			message.find(".MessageCollapse").addClass(
					"MessageCollapseSelected");
			if (message.data("wasSelected") == undefined) {
				markMessageReadden(value.id);
				message.data("wasSelected", "true");
				message.find(".MessageTitle")
						.addClass("MessageTitleReaden");
			}
		} else {
			if (message.hasClass("SelectedMessage")) {
				message.removeClass("SelectedMessage");
				message.find(".MessageTitle").removeClass(
						"MessageTitleSelected");
				message.find(".MessageTitle")
						.find(".NetworkIconReaden")
						.removeClass("Invisible");
				message.find(".MessageTitle").find(".NetworkIcon")
						.addClass("Invisible");
				message.find(".MessageText").addClass("MessageTextReaden");
				message.find(".MessageText").removeClass(
						"MessageTextSelected");
				message.find(".MessageButtons").removeClass(
						"MessageButtonsSelected");
				message.find(".MessageExpand").removeClass(
						"MessageExpandSelected");
				message.find(".MessageCollapse").removeClass(
						"MessageCollapseSelected");
			}
		}
		previous = current;
	});
}
function markMessageReadden(id) {
	$.post("execute?command=markMessagesReaden&messagesIds=" + id).error(
			onFailure);
}
function markAllMessagesReadden() {
	$.post("execute?command=markMessagesReaden&markAll=true").error(
			onFailure);
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
function searchTree(){
	$("#sources_tree").jstree("search",$("#search_field").attr("value"));
}
function logout() {
	openUrlInDiv("#SiteBody", "execute?command=logout", [ initPage ]);
}
function loadStartPage() {
	var login_value = $.cookie('login_cookie');
	if (login_value != undefined){
		setLoginCookie(login_value);
		startHiddenAuthentication(login_value);
	}else{
		openUrlInDiv("#SiteBody", "execute?command=openStartPage", [ initPage ]);
	}
}
function getMessages(id) {
	$(".Message").remove();
	$.post("execute?command=getMessages&sourceId=" + id).success(
			function(data) {
				if (isNoContent(data)) {
					return;
				}
				$(data).insertBefore("#filler");
				$.each($(".MessageDate"), function(index, value) {var millies = new Number($(value).html());
				var date = new Date(millies);
				$(value).html(date.toLocaleString());});
				gapi.plusone.go();
			}).error(onFailure);
}
function showAccounts() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv("#data_container", "execute?command=getAccounts", []);
}
function showContacts() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv("#data_container", "execute?command=getContacts", []);
}
function loadBundles(lang) {
	jQuery.i18n.properties({
	    name:'textResources', 
	    path:'properties/', 
	    mode:'map',
	    language:lang
	});
}
function setLoginCookie(login_value){
	$.cookie('login_cookie', login_value, { expires: 30, path: '/' });
}
function removeLoginCookie(){
	$.removeCookie('login_cookie');
}
function refreshLoginCookie(){
	var login_value = $.cookie('login_cookie');
	if (login_value != undefined){
		removeLoginCookie();
		setLoginCookie(login_value);
	}
}