function popdiv() {
	this.winheight = 400;
	this.winwidth = 400;
	this.wintitle = "";
	this.winurl = "弹窗地址";
	this.action = "提交的地址";
	this.buttons = [];
	this.onLoad = function() {
	}
	this.onClose = function() {
	}
}
function popupdiv(obj) {
	var time = new Date().getTime();
	var id = "dialog_" + time;
	$("body").append('<div id="' + id + '" ></div>');
	$('#' + id).dialog({
		title : obj.wintitle,
		iconCls : "icon-add",
		width : obj.winwidth,
		height : obj.winheight,
		zIndex : 1000,
		cache : true,
		modal : true,
		buttons : obj.buttons,
		href : obj.winurl,
		onLoad : function() {
			obj.onLoad(this);
		},
		onClose : function() {
			obj.onClose();
			$(this).dialog("destroy", false);
		}
	});
}
function popupframe(obj) {
	var time = new Date().getTime();
	$("body").append('<div id="' + time + '" ></div>');
	$('#' + time).dialog(
			{
				title : obj.wintitle,
				iconCls : "icon-add",
				width : obj.winwidth,
				height : obj.winheight,
				cache : true,
				buttons : obj.buttons,
				href : '',
				onClose : function() {
					obj.onClose(time);
				},
				content : '<iframe frameborder="0"  src="' + obj.winurl
						+ '" style="width:100%;height:100%;"></iframe>'

			});
}
//进度条代码
function showProcess(isShow, title, msg) {
    if (!isShow) {
        $.messager.progress('close');
       return;
   }
   var win = $.messager.progress({
      title: title,
      msg: msg
   });
}
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	} 
	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
		format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		}
	}
	return format; 
}
function noNumbers(e) {
	var keynum;
	var keychar;
	var numcheck;

	if (window.event) // IE
	{
		keynum = e.keyCode
	} else if (e.which) // Netscape/Firefox/Opera
	{
		keynum = e.which
	}
	keychar = String.fromCharCode(keynum);
	numcheck = /['"<>$&^￥-]/g;

	return !numcheck.test(keychar);
}

function fixWidthTable(tabelePercent, percent, cows) {
	return (getTableWidth(tabelePercent) - 26 - 1 * (cows + 2)) * percent;
}

// 获取当前浏览器的宽度
function getWidth(percent) {
	return $(window).width() * percent;
}

// 获取当前浏览器的高度
function getHeight(percent) {
	return $(window).height() * percent;
}

// 设置table的宽度，是以业务模块的内容为依据，如果是内容的100% percent=1
function getTableWidth(percent) {
	return ($(window).width() - 75) * percent;
}

// 设置table的高度，是以业务模块的内容为依据，如果是内容的100% percent=1
function getTableHeight(percent) {
	return ($(window).height() - 155) * percent;
}
function cjkEncode(text) {
    if (text == null) {
        return "";
    }
    var newText = "";
    for (var i = 0; i < text.length; i++) {
        var code = text.charCodeAt (i);
        if (code >= 128 || code == 91 || code == 93) {
    	    newText += "[" + code.toString(16) + "]";
        } else {
            newText += text.charAt(i);
        }
    }
    return newText;
}
$.ajaxSetup({
	cache : false,
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XHR, textStatus) {
		var resText = XHR.responseText;
		var s = resText.split("@");
		if (s[0] == 'ajaxSessionTimeOut') {
			sessionTimeOut(s[1]);
		} else if (s[0] == 'ajaxNoLimit') {
			noLimit(s[1]);
		}
	}
});
function sessionTimeOut(msg) {
	$.messager
			.confirm(
					'操作提示',
					'用户登录会话已过期，请重新登录！',
					function(r) {
						if (r) {
							window.location.href = "http://10.32.226.54:9080/wujin_fangxunfanghan/login_bak.jsp";
						}
					});
}

function noLimit(msg) {
	$.messager.alert('操作提示', '无相应操作权限，请联系系统管理员！(' + msg + ')', 'warning');
}