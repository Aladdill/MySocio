function openMessage(id){
	window.alert(id);
}

function openUrlInDiv(divId, url, functions){
	YUI().use("io-base", "node", function(Y) {
		function complete(id, o, args) {
			var data = o.responseText;
			var node = Y.one(divId);
			node.set('innerHTML', data);
			for (var x=0; x < functions.length; x++){
				functions[x].call(this);
			}
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
function commitForm(form){
	openUrlInDiv("#SiteBody", "login?"+buildQuery(form),[]);
}
function buildQuery(form)
{
	var query = "";
	for(var i=0; i<form.elements.length; i++)
	{
		var key = form.elements[i].name;
		var value = getElementValue(form.elements[i]);
		if(key && value)
		{
			query += key +"="+ value +"&";
		}
	}
	return query;
}
function getElementValue(formElement)
{
	if(formElement.length != null) var type = formElement[0].type;
	if((typeof(type) == 'undefined') || (type == 0)) var type = formElement.type;

	switch(type)
	{
		case 'undefined': return;

		case 'radio':
			for(var x=0; x < formElement.length; x++) 
				if(formElement[x].checked == true)
			return formElement[x].value;

		case 'select-multiple':
			var myArray = new Array();
			for(var x=0; x < formElement.length; x++) 
				if(formElement[x].selected == true)
					myArray[myArray.length] = formElement[x].value;
			return myArray;

		case 'checkbox': return formElement.checked;
	
		default: return formElement.value;
	}
}
function openSettings(){
	openUrlInDiv("#SiteBody", "login?command=openSettings",[]);
}
function openMainPage(){
	openUrlInDiv("#SiteBody", "login?command=openMainPage",[]);
}
function logout(){
	openUrlInDiv("#SiteBody", "login?command=logout",[]);
}
function loadStartPage(){
	openUrlInDiv("#SiteBody", "login?command=openStartPage",[getAllMessages,initSources]);
}
function getMessages(id){
	openUrlInDiv("#data_container", "login?command=getMessages&sourceId=" + id,[]);
}
function getAllMessages(){
	openUrlInDiv("#data_container", "login?command=getMessages&sourceId=all",[]);
}