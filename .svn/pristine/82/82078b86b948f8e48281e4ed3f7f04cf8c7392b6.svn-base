var topwindow = window;
var popdocument = topwindow.document;
var successmsg = false;
/*var alert = function(aa) {
	topwindow.$.messager.alert('系统提示', aa, 'info');
}*/

String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.Trim = function() {
    return this.trim();
}
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds(), // millisecond
		"w" : this.getDay()
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}	
var _rooturl = "/" + window.location.href.split("/")[3];

// jquery
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/easyui/jquery.min.js'></script>");
// bootstrap
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/bootstrap/js/bootstrap.min.js'></script>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/bootstrap/css/bootstrap.min.css'/>");
// easyui

document.write("<link type='text/css' id='themes' rel='stylesheet' href='"
		+ _rooturl + "/cssjs/easyui/themes/default/easyui.css'/>");

document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/jsControl/top_form.js'></script>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/css/main.css'/>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/easyui/themes/icon.css'/>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/easyui/portal.css'/>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/css/icons.css'/>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/jsControl/baseConfig.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/easyui/jquery.easyui.min.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/easyui/locale/easyui-lang-zh_CN.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/jsControl/easyuiext.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/jsControl/own_form.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/easyui/jquery.portal.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/jsControl/select.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/jsControl/Cookie.js'></script>");

document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/Highcharts/js/highcharts.js'></script>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/Highcharts/js/modules/exporting.js'></script>");


//编辑器
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/kindeditor/kindeditor.js'></script>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/kindeditor/themes/default/default.css'/>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/kindeditor/plugins/code/prettify.css'/>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/datepicker/skin/WdatePicker.css'/>");
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/datepicker/WdatePicker.js'></script>");
//省市区
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/jsControl/jsAddress.js'></script>");

//拖动
document.write("<script type='text/javascript' src='" + _rooturl
		+ "/cssjs/dragform/jqueryDrag.js'></script>");
document.write("<link type='text/css' rel='stylesheet' href='" + _rooturl
		+ "/cssjs/dragform/drag.css'/>");