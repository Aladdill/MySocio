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
function openUrlInDiv(div, url, afterfunction) {
	$.post(url).done(function(data) {
		if (isNoContent(data)) {
			return;
		}
		div.html(data);
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
	$.post("execute?command=getTags", {}, initSourcesData, "json").fail(
			onFailure);
}
function initSourcesData(data) {
	$("#sources_tree").jstree(data).bind("loaded.jstree",
			function(event, data) {$("#sources_tree_scroll").tinyscrollbar();
			}).bind("select_node.jstree", function(e, data) {
			getMessages(data.rslt.obj[0].attributes.uniqueId.value, true);
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
	$dialog.append($input);
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
		url : $("#rssUrl").prop("value")
	}).done(showRssFeeds)
	.always(function(){$("#rssUrl").prop("value", "");})
	.fail(onFailure);
}
function importOPML() {
	importingOPML();
	$("form#importOPMLform").submit();
}
function removeRssFeed(feedId) {
	showWaitDialog('dialog.removing.feed.title','dialog.removing.feed.message');
	$.post("execute?command=removeRssFeed", {
		id : feedId
	}).done(showRssFeeds)
	.done(closeWaitDialog)
	.fail(onFailure);
}
function setDataContainer(data) {
	if (isNoContent(data)) {
		return;
	}
	$("#data_container .jspPane").html(data);
	closeWaitDialog();
}
function isNoContent(data) {
	return (data.status == 204);
}
function isEmpty(str) {
    return (!str || 0 === str.length);
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
function showSources(isInit) {
	showDiv("sources_tree");
	showDiv("subscriptions_underline");
	showDiv("subscriptions_title");
	if (isInit){ 
		initSources();
		// Start refreshing sources when user logs in
		$("#sources_tree").everyTime("60s", "refreshTags", refreshTags, 0);
	}
}
function refreshTags(selected) {
	if ($("#sources_tree").data("treeRefreshInitiated")) {
		return;
	}
	$("#sources_tree").data("treeRefreshInitiated", true);
	$.post("execute?command=refreshTags&selected=" + selected, {}, refreshTagsData, "json").fail(
			onFailure).always(function(){$("#sources_tree").data("treeRefreshInitiated", false);});
}
function refreshTagsData(data) {
	$.each(data,function(index,item){
		var node = $("#" + item.id);
		$("#" + item.id + " > a").text(item.name);
		node.attr("style",item.style);
	});
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
	if (isEmpty(error)){
		return;
	}
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
	$("#data_container .jspPane").html("");
}
function showRssFeeds() {
	$("#post_container").addClass("Invisible");
	$(".PostBoxTriangle").addClass("Invisible");
	$("#rss_content_menu").removeClass("Invisible");
	$("#main_content_menu").addClass("Invisible");
	$("#data_container").unbind();
	$.post("execute?command=getRssFeeds").done(
			function(data) {
				if (isNoContent(data)) {
					return;
				}
				clearDataContainer();
				var leftColumn = $("<div />").addClass("RssColumn");
				var rightColumn = $("<div />").addClass("RssColumn");
				$("#data_container .jspPane").append(leftColumn);
				$("#data_container .jspPane").append(rightColumn);
				$.each($(data), function(index, value) {
					var rssLine = $(value);
					if ((index + 2) % 4 == 0 || (index +1) % 4 == 0){
						rssLine.children(".RSSType").addClass("GrayLine");
					}
					if (index % 2 == 0){
						leftColumn.append(rssLine);
					}else{
						rightColumn.append(rssLine);
					}
				}
				);
				refreshDataContainerScroll();
			}).fail(onFailure);
}
function orderBy(order){
	showWaitDialog("dialog.general.wait.title", "dialog.general.wait.message");
	 $.ajax({
   	  type: "POST",
   	  url: "execute?command=setOrder&order="+order,
   	  dataType: "text",
   	  error: onFailure 
   	}).always(closeWaitDialog)
   	.done(function(){getMessages('currentSelection', true);});
}
function showMainPage() {
	hideTabs();
	clearDataContainer();
	showSources(true);
	$("#post_container").removeClass("Invisible");
	$(".PostBoxTriangle").removeClass("Invisible");
	$("#rss_content_menu").addClass("Invisible");
	$("#main_content_menu").removeClass("Invisible");
	$("#upper_link").html($("#settings_link").html());
	$("#data_container .jspPane").html("<div id='filler' class='filler'></div>");
	$("#filler").css("height", $("#data_container").innerHeight() - 10);
	$("#data_container").bind("jsp-scroll-y", messageScroll);
	refreshDataContainerScroll();
}
function openMainPage() {
	resizeTabs();
	hideTabs();
	initMessagesContainer();
	showSources(true);
}
function initMessagesContainer() {
	var messageContainer = $("#data_container");
	$("#import_opml").change(function (){$("#shown_import_opml").prop("value", $("#import_opml").prop("value").substring(12));});
	var height = $("body").innerHeight() - 226;
	messageContainer.css("height", height);
	$("#filler").css("height", messageContainer.innerHeight() - 10);
	messageContainer.jScrollPane();
	messageContainer.bind("jsp-scroll-y", messageScroll);
}
function messageScroll(event, scrollPositionY, isAtTop, isAtBottom) {
	if (isAtBottom == true){
		refreshDataContainerScroll();
	}
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
			message.find(".RssMessageFrom").removeClass("MessageTextReaden");
			message.find(".RssMessageFrom").addClass("MessageTextSelected");
			message.find(".MessageButtons").addClass(
					"MessageButtonsSelected");
			message.find(".MessageExpand")
					.addClass("MessageExpandSelected");
			message.find(".MessageCollapse").addClass(
					"MessageCollapseSelected");
			if (message.data("wasSelected") == undefined) {
				message.data("wasSelected", "true");
				message.find(".MessageTitle").addClass("MessageTitleReaden");
				if ($(this).next().attr("id") == "filler"){
					markMessageReaddenAndGetMore(value.id);
				}else{
					markMessageReadden(value.id);
				}
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
				message.find(".MessageText").removeClass("MessageTextSelected");
				message.find(".RssMessageFrom").addClass("MessageTextReaden");
				message.find(".RssMessageFrom").removeClass("MessageTextSelected");
				message.find(".MessageButtons").removeClass("MessageButtonsSelected");
				message.find(".MessageExpand").removeClass("MessageExpandSelected");
				message.find(".MessageCollapse").removeClass("MessageCollapseSelected");
			}
		}
	});
}
function markMessageReadden(id) {
	$.ajax({
  	  type: "POST",
  	  url: "execute?command=markMessagesReaden&messagesIds=" + id,
  	  dataType: "text",
  	  error: onFailure 
  	}).done(refreshTags);
}
function markMessageReaddenAndGetMore(id) {
	$.ajax({
  	  type: "POST",
  	  url: "execute?command=markMessagesReaden&messagesIds=" + id,
  	  dataType: "text",
  	  error: onFailure 
  	}).done(function(){refreshTags(); getMessages('currentSelection', false);});
}
function markAllMessagesReadden() {
	showWaitDialog("dialog.marking.all.messages.read.title", "dialog.marking.all.messages.read.message");
	$.ajax({
	  	  type: "POST",
	  	  url: "execute?command=markMessagesReaden&markAll=true",
	  	  dataType: "text",
	  	  error: onFailure 
	  	}).always(closeWaitDialog)
	  	.done(function(){refreshTags(false);});
}
function initPage() {
	if ($("#login_center_div").size() != 0) {
		centerLoginCircle();
	} else {
		openMainPage();
	}
}
function loadMainPage() {
	openUrlInDiv($("#SiteBody"), "execute?command=openMainPage", initPage);
}
function searchTree(){
	$("#sources_tree").jstree("search",$("#search_field").prop("value"));
}
function logout() {
	openUrlInDiv($("#SiteBody"), "execute?command=logout", initPage);
}
function loadStartPage() {
	var login_value = $.cookie('login_cookie');
	if (login_value != undefined){
		setLoginCookie(login_value);
		startHiddenAuthentication(login_value);
	}else{
		openUrlInDiv($("#SiteBody"), "execute?command=openStartPage", initPage);
	}
}
function getMessages(id, resetContainer) {
	if ($("#data_container").data("gettingMessages")){
		return;
	}
	showWaitDialog("dialog.getting.messages.title", "dialog.getting.messages.message");
	$("#data_container").data("gettingMessages", true);
	$.post("execute?command=getMessages&sourceId=" + id).done(
			function(data) {
				if (isNoContent(data)) {
					return;
				}
				if (resetContainer == true){
					$("#data_container").data('jsp').scrollTo(0, 0);
					$(".Message").remove();
				}
				$(data).insertBefore("#filler");
				$.each($(".MessageDate"), function(index, value) {var millies = new Number($(value).html());
					var date = new Date(millies);
					var dateString = date.toLocaleString();
					if (dateString != "Invalid Date"){	
						$(value).html(dateString);
					}
				});				
				refreshDataContainerScroll();
				gapi.plusone.go();
			}).always(closeWaitDialog).always(function (){$("#data_container").data("gettingMessages", false);})
			.fail(onFailure);
}
function refreshDataContainerScroll(){
	$("#data_container").data('jsp').reinitialise();
}
function showAccounts() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv($("#data_container .jspPane"), "execute?command=getAccounts");
}
function showContacts() {
	$("#post_container").addClass("Invisible");
	openUrlInDiv($("#data_container .jspPane"), "execute?command=getContacts");
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
function postMessage(){
	var message = $("#post_text").prop("value");
	if (isEmpty(message)){
		showError("dialog.message.empty");
	}
	$.post("execute?command=postMessage&message=" + message).done(
			function(data) {showWaitDialog("dialog.message.posted.title", "dialog.message.posted.message");})
			.always(function(data) {$("#post_text").prop("value", "");})
			.fail(onFailure);
}
function likeMessage(id, like){
	$.post("execute?command=likeMessage&messageId=" + id).fail(onFailure);
}