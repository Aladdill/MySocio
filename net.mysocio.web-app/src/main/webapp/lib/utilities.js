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
function openUrlInDiv(divId, url, afterfunction) {
	$.post(url).done(function(data) {
		if (isNoContent(data)) {
			return;
		}
		$(divId).html(data);
	}).fail(onFailure).done(afterfunction);
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
	$.post("execute?command=getSources", {}, initSourcesData, "json").fail(
			onFailure);
}
function initSourcesData(data) {
	$("#sources_tree").jstree(data).bind("loaded.jstree",
			function(event, data) {$("#sources_tree_scroll").tinyscrollbar();
			}).bind("select_node.jstree", function(e, data) {
		if (!$("#sources_tree").data("treeRefreshInitiated")) {
			getMessages(data.rslt.obj[0].id);
		}else{
			$("#sources_tree").data("treeRefreshInitiated", false);
		}
	}).bind("after_open.jstree after_close.jstree", function (e) {
		$("#sources_tree_scroll").tinyscrollbar_update();
	});
}
function login(identifierValue) {
	setLoginCookie(identifierValue);
	startAuthentication(identifierValue);
}
function startAuthentication(identifierValue) {
	$.post("execute?command=startAuthentication", {
		identifier : identifierValue,
	}).done(function(data) {
		if (isNoContent(data)) {
			return;
		}
		window.open(data, "name", "height=600,width=900");
	}).fail(onFailure);
}

function startHiddenAuthentication(identifierValue) {
	$.cookie('hidden_login_cookie', "true", { path: '/' });
	$.post("execute?command=startAuthentication", {
		identifier : identifierValue,
	}).done(function(data) {
		if (isNoContent(data)) {
			return;
		}
		$("#hiddenFrame").attr('src', data);
	}).fail(onFailure);
}

function authenticationDone() {
	showWaitDialog("dialog.creating.account.title", "dialog.creating.account.message");
	$.post("execute?command=addAccount")
	.done(function(data) {
		if (isNoContent(data)) {
			return;
		}
		closeWaitDialog();
		showAccounts();
	}).fail(function(data) { $dialog.dialog("close"); onFailure(data);});
}
function executeLogin() {
	showWaitDialog("dialog.login.title", "dialog.login.message");
	$.removeCookie('hidden_login_cookie');
	$.post("execute?command=login")
	.done(function(data) {
		if (isNoContent(data)) {
			return;
		}
		closeWaitDialog();
		loadMainPage();
	}).fail(function(data) { $dialog.dialog("close"); onFailure(data);});
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
				}).done(function(data) {
				}).fail(onFailure)
				.done(function() { $waitDialog.dialog("close"); });
				$(this).dialog("close");
			}
		}
	});
}
function addRssFeed() {
	$.post("execute?command=addRssFeed", {
		url : $("#rssUrl").attr("value")
	}).done(setDataContainer).fail(onFailure);
}
function importOPML() {
	importingOPML();
	$("form#importOPMLform").submit();
}
function removeRssFeed(feedId) {
	showWaitDialog('dialog.removing.feed.title','dialog.removing.feed.message');
	$.post("execute?command=removeRssFeed", {
		id : feedId
	}).done(setDataContainer).fail(onFailure);
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
	$.removeCookie('login_cookie');
	$.removeCookie('hidden_login_cookie');
	showError(data.responseText);
}
function showError(error) {
	showWaitDialog("dialog.error.title", error);
}
function showAuthError(error) {
	$.removeCookie('login_cookie');
	$.removeCookie('hidden_login_cookie');
	showWaitDialog("dialog.error.title", error);
}
function importingOPML() {
	showWaitDialog("dialog.importing.opml.title", "dialog.importing.opml.message");
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
	showRssFeeds();
}
function clearDataContainer(){
	$("#data_container").html("");
}
function showRssFeeds() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv("#data_container", "execute?command=getRssFeeds");
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
	var height = $("body").innerHeight() - 198;
	messageContainer.css("height", height);
	$("#messages_scroll .viewport").first().css("height", height);
	$("#messages_scroll").css("height", height);
	$("#filler").css("height", messageContainer.innerHeight() - 10);
	$("#messages_scroll").bind("tinyscroll", messageScroll);
}
function messageScroll() {
	$.each($(".Message"), function(index, value) {
		var message = $("#" + value.id);
		var messagePosition = message.position().top;
		var viewPosition = message[0].offsetParent.offsetTop;
		var messageRelativeTop = viewPosition + messagePosition;
		var messageRelativeBottom = messageRelativeTop + message[0].offsetHeight;
		if (messageRelativeTop < 0 && messageRelativeBottom > 0 && value.id != "filler") {
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
	});
}
function markMessageReadden(id) {
	postWithTextReturn("execute?command=markMessagesReaden&messagesIds=" + id);
}
function postWithTextReturn(url){
    $.ajax({
    	  type: "POST",
    	  url: url,
    	  dataType: "text",
    	  error: onFailure 
    	}).always(closeWaitDialog);
}
function markAllMessagesReadden() {
	showWaitDialog("dialog.marking.all.messages.read.title", "dialog.marking.all.messages.read.message");
	postWithTextReturn("execute?command=markMessagesReaden&markAll=true");
}
function initPage() {
	if ($("#login_center_div").size() != 0) {
		centerLoginCircle();
	} else {
		openMainPage();
	}
}
function loadMainPage() {
	openUrlInDiv("#SiteBody", "execute?command=openMainPage", initPage);
}
function searchTree(){
	$("#sources_tree").jstree("search",$("#search_field").prop("value"));
}
function logout() {
	openUrlInDiv("#SiteBody", "execute?command=logout", initPage);
}
function loadStartPage() {
	var login_value = $.cookie('login_cookie');
	if (login_value != undefined){
		setLoginCookie(login_value);
		startHiddenAuthentication(login_value);
	}else{
		openUrlInDiv("#SiteBody", "execute?command=openStartPage", initPage);
	}
}
function getMessages(id) {
	$(".Message").remove();
	showWaitDialog("dialog.getting.messages.title", "dialog.getting.messages.message");
	$.post("execute?command=getMessages&sourceId=" + id).done(
			function(data) {
				if (isNoContent(data)) {
					return;
				}
				$(data).insertBefore("#filler");
				$.each($(".MessageDate"), function(index, value) {var millies = new Number($(value).html());
				var date = new Date(millies);
				$(value).html(date.toLocaleString());});
				$("#messages_scroll").tinyscrollbar();
				gapi.plusone.go();
			}).always(closeWaitDialog)
			.fail(onFailure);
}
function showAccounts() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv("#data_container", "execute?command=getAccounts");
}
function showContacts() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv("#data_container", "execute?command=getContacts");
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