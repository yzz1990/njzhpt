/**
 * Created by Administrator on 2016/10/8 0008.
 */

/******主界面******/

//var urlPre='http://192.168.1.175:8080/y_szlk/app/';//跨域请求的url前缀
var urlPre = 'http://192.168.50.101:8080/y_szlk1/app/'
var datas;
//显示一个正在加载的提示及关闭(weUI)
function loadDataStart(){
	$.showLoading();
}
function loadDataEnd(){
	$.hideLoading();
}



//截取时间字符串
function substringTimeGetyyyyMMdd(timeVal){
	return timeVal.substring(0,10);
}

//格式化时间
Date.prototype.Format = function(formatStr){   
    var str = formatStr;   
    var Week = ['日','一','二','三','四','五','六'];  
  
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
  
    str=str.replace(/MM/,(this.getMonth()+1)>9?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));   
    str=str.replace(/M/g,this.getMonth()+1);   
  
    str=str.replace(/w|W/g,Week[this.getDay()]);   
  
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());   
  
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());   
  
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());   
  
    return str;   
} ;  

var swiper = new Swiper('.swiper-container', {
    pagination: '.swiper-pagination',
    paginationClickable: true,
    centeredSlides: true,
    autoplay: 3000,
    autoplayDisableOnInteraction: false
});
function clock() {
    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var week = date.getDay();
    var weeks = ["日", "一", "二", "三", "四", "五", "六"];
    var hour = date.getHours();
    var minute = date.getMinutes();
    if (hour < 10) {
        hour = '0' + hour;
    }
    if (minute < 10) {
        minute = '0' + minute;
    }
    $('.time').html(hour + ':' + minute);
    $('.date').html(month + '月' + day + '日' + ' 星期' + weeks[week]);
}
$(function(){
    clock();
});
setInterval(function () {
    clock()
}, 1000);

//主页面按钮绑定,调用android后台java代码
$('#jkBtn').click(//监控管理
		function(){
			var str = window.jsObj.HtmlcallJava2('监控管理');
	    }
);
$('#xgBtn').click(//巡更管理
	    function(){
	    	var str = window.jsObj.HtmlcallJava2('巡更管理');
	    }
);
$('#clBtn').click(//储粮管理
	    function(){
	    	var str = window.jsObj.HtmlcallJava2('储粮管理');
	    }
);
$('#a_tel').click(//联系人
	    function(){
	    	var str = window.jsObj.HtmlcallJava2('联系人');
	    }
); 
$('#a_home').click(//主页,不进行跳转
	    function(){
	    	return;
//	    	var str = window.jsObj.HtmlcallJava2('主页');
	    }
); 
$('#a_setting').click(//储粮管理
	    function(){
	    	var str = window.jsObj.HtmlcallJava2('系统设置');
	    }
); 

$('body').css('height',$(window).height());

$('.datePick').calendar();
$('.outLibDate').calendar();
//$('#date-cl').calendar();

$("#pickerMonth").picker({
    title: "选择日期",
    cols: [
        {
            textAlign: 'center',
            values: ['2015-10', '2015-11', '2015-12', '2016-01', '2016-02', '2016-03', '2016-04', '2016-05', '2016-06', '2016-07', '2016-08', '2016-09', '2016-10']
        }
    ]
});
$("#ioType").picker({
    title: "选择类型",
    cols: [
        {
            textAlign: 'center',
            values: ['入库', '出库']
        }
    ]
});
$("#ioLib").picker({
    title: "选择类型",
    cols: [
        {
            textAlign: 'center',
            values: ['粮食入库统计表', '粮食出库统计表']
        }
    ]
});
/*function getLibNUm(){
	var postUrl='queryCanghao.do';
	$.getJSON('/njlkApp/getHttpClient.action',{ url:postUrl},function(data){
	datas=data;
	var tt=[];
	var ff=[];
	for(var i=0;i<datas.length;i++){
		//alert(datas[i]);
		tt.push(code=datas[i].vWareHouseCode);
		ff.push(datas[i].vWareHouseName);
	}
	$("#libNum").picker({
	    title: "选择仓号",
	    cols: [
	        {
	            textAlign: 'center',
	            values:ff
	            
	        }
	    ]
	});
	});
}

getLibNUm();*/

/*$("#libNum").change(function(event){
		var vc;
		for (var i = 0; i < datas.length; i++) {
			if(datas[i].vWareHouseName==$("#libNum").val()){
				vc=datas[i].vWareHouseCode;
				break;
			}
		}
		var postUrl='queryDate.do?vWareHouseCode='+vc;
		$.getJSON('/njlkApp/getHttpClient.action',{ url:postUrl},function(data){
			var dd=[];
			for(var i=0;i<data.datetime.length;i++){
				dd.push(data.datetime[i].datetime)
			}
			
			//清空数据
			$("#date-cl").removeData("picker");
			$("#date-cl").val("");
			$("#date-cl").picker({
				
				title:"选择日期",
				cols:[
				      {
				    	  textAlign: 'center',
				    	  values:dd
				      }
				]		
			});
			//$("#date-cl").reload(true);
		});
	});*/





$("#xgMember").picker({
    title: "选择人员",
    cols: [
        {
            textAlign: 'center',
            values: ['张三', '李四']
        }
    ]
});
$('#xgDate').calendar();

window.onload = function(){
    $('#xcUncompleted').addClass('weui_bar_item_on');
    $('.xcUncompleted').show();
};
$('#xcUncompleted').click(
    function(){
        $(this).addClass('weui_bar_item_on').siblings().removeClass('weui_bar_item_on');
        $('.xcUncompleted').show().siblings().hide();
    }
);
$('#xcCompleted').click(
    function(){
        $(this).addClass('weui_bar_item_on').siblings().removeClass('weui_bar_item_on');
        $('.xcCompleted').show().siblings().hide();
    }
);
$("#xgMember1").picker({
    title: "选择人员",
    cols: [
        {
            textAlign: 'center',
            values: ['张三丰', '李四']
        }
    ]
});
