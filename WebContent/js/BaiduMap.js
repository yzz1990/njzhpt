var map;
var jsonData;
var idArr=[],codeArr=[],nameArr=[],shortnameArr=[],addressArr=[],typeArr=[],regionArr=[],potArr=[],markerArr=[],potArray=[],remarkArr=[];
var kdsypathArr=[],kdqmpathArr=[],qyzzjgdms=[];
var selectInfoWindow = "";
var selectMarker = "";
// 初始化地图
function initMap(long,lat,name){
	map = new BMap.Map("allmap",{minZoom:10,maxZoom:14});// 创建Map实例

	
			//map = new BMap.Map('map', { mapType: BMAP_HYBRID_MAP }); //创建地图实例  {mapType: BMAP_HYBRID_MAP}为设置默认的为卫星地图  		
			// 创建点坐标
	var point =   new BMap.Point(long , lat); 	    

	// 初始化地图，设置中心点坐标和地图级别。
	map.centerAndZoom(point, 12);
	map.enableScrollWheelZoom();//鼠标滚轮缩放
	map.enableKeyboard();//键盘开启
	map.enableContinuousZoom();// 开启连续缩放效果
	map.enableInertialDragging();// 开启惯性拖拽效果
   /* map.addEventListener("zoomend", function () {
        var jibie = this.getZoom();
       if(10<=jibie<=11){
    	   getBoundary();
    	   initPoint(0);
       }
       if(jibie=="12"){
    	   initPoint(1);
       }
       if(jibie=="13"){
    	   initPoint(2);
       }
    });*/
	// 导航和平移
	var navi_opts = {type: BMAP_NAVIGATION_CONTROL_SMALL}  
//	map.addControl(new BMap.NavigationControl(navi_opts));
//	map.addControl(new BMap.NavigationControl());
	// 比例尺
	var con_opts = {offset: new BMap.Size(150, 315)};
//	map.addControl(new BMap.ScaleControl(con_opts));  
//	map.addControl(new BMap.ScaleControl());
	// 鹰眼图
	map.addControl(new BMap.OverviewMapControl());//缩略地图控件，默认位于地图右下方，是一个可折叠的缩略地图。
	
	// 地图类型   地图 , 卫星
	var map_opts={anchor:BMAP_ANCHOR_TOP_LEFT,offset:new BMap.Size(40,10)};
    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP, BMAP_HYBRID_MAP]},map_opts));
    
    
    window.setTimeout(function(){
        getBoundary(name);
      
    }, 1000);
   
    window.setTimeout(function(){
    	doSearch4GIS();
    	//map.centerAndZoom(new BMap.Point(lont, lat),11,{noAnimation:true});
   	}, 1000);
    
    initPoint();
	
//	map.addEventListener("click", function(e){//地图单击事件  
//	    document.getElementById("lonlat").innerHTML = "经度:"+e.point.lng + ", 纬度:" + e.point.lat;  
//	});
//	getBoundary();


}
function getBoundary(countyName){
    var bdary = new BMap.Boundary();
   // var diqu=new Array("南京溧水","南京六合","南京江宁","南京浦口","南京高淳","南京栖霞","南京鼓楼","南京玄武","南京秦淮","南京建邺","南京雨花台");
     var diqu=new Array("灌南县");
    for(var i=0;i<diqu.length;i++){
    	bdary.get(diqu[i], function(rs){       //获取行政区域
            //map.clearOverlays();        //清除地图覆盖物       
            var count = rs.boundaries.length; //行政区域的点有多少个
            for(var i = 0; i < count; i++){
                var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
                map.addOverlay(ply);  //添加覆盖物
               // map.setViewport(ply.getPath());    //调整视野
            }
        });
    }
    
}


function initPoint(maplevel){
	/*
	$.ajax({
        type: "POST",
        url: "/njzhpt/quxian/findAllQiye",
        data: {maplevel: maplevel},
        dataType: "json",
        success:function(json){
    	jsonData = json;
    		getEnterPriseDate(json);
    		
    	}
    });*/
	
	setTimeout(function () {
		$.ajax({
	        type: "POST",
	        url: "../../quxian/findAllKudian",
	        data: {maplevel: maplevel},
	        dataType: "json",
	        success:function(json){
	        	getEnterPriseDate1(json);
	    	}
	    });
	  }, 1000);
	
	
	
}

function getEnterPriseDate(data){
	var list = data.list;
	if (!list) return;
	var pts = [];
	// clear the overlay on the map
	map.clearOverlays();
	// 监测点编码
	codeArr = new Array();
	// 名称
	nameArr = new Array();
	shortnameArr = new Array();
	potArr = new Array();
	kdsypathArr = new Array();
	kdqmpathArr = new Array();
	//地图标注
	markerArr = new Array();
	typeArr = new Array();
	//注释
	remarkArr = new Array();
	for (var i = 0; i < list.length; i++) {
		var poi = list[i];
		var code = poi.qyzzjgdm;
		var name = poi.qymc;
		var shortname = poi.qymc;
		var remark = poi.remark;
		var type = poi.jyywlxbh;
		var lng = poi.qyjd;
		var lat = poi.qywd;
		var kdsypath = poi.kdsypath;
		var kdqmpath = poi.kdqmpath;
		kdsypathArr.push(kdsypath);
		kdqmpathArr.push(kdqmpath);
		codeArr.push(code);
	  	nameArr.push(name);
	  	shortnameArr.push(shortname);
	  	remarkArr.push(remark);
	  	typeArr.push(type);
	    var point = new BMap.Point(lng,lat);
	    potArr.push(point);
	}
	for(var j=0;j < potArr.length; j++){
		markPoint(j,0);
	}
}

function getEnterPriseDate1(data){

	var list = data.list;
	if (!list) return;
	var pts = [];
	// clear the overlay on the map
	//map.clearOverlays();
	// 监测点编码
	codeArr = new Array();
	// 名称
	nameArr = new Array();
	qyzzjgdms=new Array();
	shortnameArr = new Array();
	potArr = new Array();
	kdsypathArr = new Array();
	kdqmpathArr = new Array();
	//地图标注
	markerArr = new Array();
	typeArr = new Array();
	//注释
	remarkArr = new Array();
	
	for (var i = 0; i < list.length; i++) {
		var poi = list[i];
		var code = poi.kdbm;
		var name = poi.kdmc;
		var shortname = poi.kdmc;
		var qyzzjgdm=poi.qyzzjgdm;
		var remark = poi.remark;
		var kdsypath = poi.kdsypath;
		var kdqmpath = poi.kdqmpath;
		kdsypathArr.push(kdsypath);
		kdqmpathArr.push(kdqmpath);
		var type = poi.kdlxbh;
		var lng = poi.qyjd;
		var lat = poi.qywd;
		codeArr.push(code);
	  	nameArr.push(name);
	  	qyzzjgdms.push(qyzzjgdm);
	  	shortnameArr.push(shortname);
	  	remarkArr.push(remark);
	  	typeArr.push(type);
	    var point = new BMap.Point(lng,lat);
	    potArr.push(point);
	}
	for(var j=0;j < potArr.length; j++){
		markPoint(j,1);
	}
}


function markPoint(k,i){
	// 定义信息窗口
    var opts = null;
    var infostr;
    var iconImg;
    // 测站编码
    var kdbm = codeArr[k];
    var type = typeArr[k];
    var qyzzjgdm= qyzzjgdms[k];
    var kdsypath = "/"+kdsypathArr[k].replace(/\\/g,"/");
    var kdqmpath = "/"+kdqmpathArr[k].replace(/\\/g,"/"); 
    var titlestr  =  "<span style='FONT-SIZE: 12pt; FONT-FAMILY: 微软雅黑;' color=#ccc9c9 >&nbsp;&nbsp;&nbsp;&nbsp;"+nameArr[k]+"</span>";
    var infostr1="<div><iframe id='qipao'  name='head' " +
    		" style='border: 0px currentColor; border-image: none; width: 600px; height: 380px;' src='../../quxian/dilixinxi/pictrue.html?kdsypath="+kdsypath+"&kdqmpath="+kdqmpath+"&qyzzjgdm="+qyzzjgdm+"&kdbm="+kdbm+"'></iframe></div>";
    var infostr2="";
    if(remarkArr[k] != null){
    	infostr2="<span style='FONT-SIZE: 9pt; FONT-FAMILY: 微软雅黑;'>&nbsp;&nbsp;&nbsp;&nbsp;"+remarkArr[k]+"</span>";
    }
    
    		var myicon;
    		/*if(i==0&& type=="01"){
    	    	iconImg = "../../image/lsyw/qyhouse1.png";
    	    	infostr = infostr2;
    	    	opts = { width: 120,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==0&& type=="02"){
    	    	iconImg = "../../image/lsyw/qyhouse2.png";
    	    	infostr = infostr1;
    	    	opts = { width: 520,height: 530,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(50, 38), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==0&& type=="03"){
    	    	iconImg = "../../image/lsyw/qyhouse3.png";
    	    	infostr = infostr1;
    	    	opts = { width: 520,height: 530,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(50, 38), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==0&& type=="04"){
    	    	iconImg = "../../image/lsyw/qyhouse4.png";
    	    	infostr = infostr1;
    	    	opts = { width: 520,height: 530,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(50, 38), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==0&& type=="05"){
    	    	iconImg = "../../image/lsyw/qyhouse5.png";
    	    	infostr = infostr1;
    	    	opts = { width: 520,height: 530,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(50, 38), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else{
    	    	iconImg = "../../image/lsyw/qyhouse6.png";
    	    	infostr = infostr1;
    	    	opts = { width: 520,height: 530,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(50, 38), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }*/
    		if(i==1){
    	    	iconImg = "../../images/red_h45_33.png";
    	    	infostr = infostr1;
    	    	opts = { width: 350,height:270, title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }
    		
    		
    		/*if(i==1&& type=="01"){
    	    	iconImg = "../../image/legend/0.1-9.9.png";
    	    	infostr = infostr2;
    	    	opts = { width: 120,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==1&& type=="02"){
    	    	iconImg = "../../image/legend/10-24.9.png";
    	    	infostr = infostr2;
    	    	opts = { width: 120,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==1&& type=="03"){
    	    	iconImg = "../../image/legend/25-49.9.png";
    	    	infostr = infostr2;
    	    	opts = { width: 120,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==1&& type=="04"){
    	    	iconImg = "../../image/legend/55-99.9.png";
    	    	infostr = infostr2;
    	    	opts = { width: 120,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==1&& type=="05"){
    	    	iconImg = "../../image/legend/100-249.9.png";
    	    	infostr = infostr2;
    	    	opts = { width: 120,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }else if(i==1&& type=="06"){
    	    	iconImg = "../../image/legend/250.png";
    	    	infostr = infostr2;
    	    	opts = { width: 120,  title: titlestr};
    	    	myicon = new BMap.Icon(iconImg, new BMap.Size(45, 33), {
    	            infoWindowAnchor: new BMap.Size(20, 15)
    	        });
    	    }*/
    
	var marker = new BMap.Marker(potArr[k], {icon: myicon});
	var infowindow = new BMap.InfoWindow("<br/>"+infostr, opts);
	marker.addEventListener("click", function () { 
		selectMarker = marker;
		selectInfoWindow = infowindow;
        this.openInfoWindow(infowindow);
		window.setTimeout('getOutInoWindow()',500);
	});
	var label = new BMap.Label(shortnameArr[k],{"offset":new BMap.Size(35,10)});
	label.setStyle({
		color:"#000000",              //颜色
		fontSize:"13px",               //字号
		border:"1",                      //边
		height:"18px",                 //高度
//		idth:"125px",                  //宽
		textAlign:"center",            //文字水平居中显示
		lineHeight:"18px",            //行高，文字垂直居中显示
		borderColor:"#FEE274",
		background:"url(../../images/img/ten.png)",    //背景图片
		cursor:"pointer"
	});
    
	marker.setLabel(label);
	markerArr.push(marker);
	//将覆盖物添加到地图中,一个覆盖物实例只能向地图中添加一次 
	map.addOverlay(marker);
}

function getOutInoWindow(){
	 selectMarker.openInfoWindow(selectInfoWindow);	 
}

function centerAt(k){
	// 定义信息窗口
    var opts = null;
    // 粮库编码
    var code = codeArr[k];
    var myicon = new BMap.Icon('../images/point.png', new BMap.Size(32, 32), {
        anchor: new BMap.Size(32, 32),
        infoWindowAnchor: new BMap.Size(5, 0)
    });
    var infostr="<div></div>";
    opts = { width: 390, height: 250, title: nameArr[k]};
    var infowindow = new BMap.InfoWindow(infostr, opts);
	var mk = markerArr[k];
	mk.setIcon(myicon);
	mk.openInfoWindow(infowindow);
    var pt = potArr[k];
    map.centerAndZoom( pt,14);
}

function centerTo(longt,lat){
	var pt = new BMap.Point(longt, lat); 
    map.centerAndZoom( pt,14);
    var myicon = new BMap.Icon('../images/locate.gif', new BMap.Size(100, 100), {
//        anchor: new BMap.Size(32, 32),
        infoWindowAnchor: new BMap.Size(5, 0)
    });
    var marker_short = new BMap.Marker(pt, {icon: myicon});
    map.addOverlay(marker_short);
    window.setTimeout(function(){
    	marker_short.remove();
//    	map.clearOerelay(marker_short);
    }, 2500);
}

function panToPoint(longt,lat){
	var pt = new BMap.Point(longt, lat); 
	map.panTo( pt );
	var myicon = new BMap.Icon('../../images/locate.gif', new BMap.Size(45, 33), {
//      anchor: new BMap.Size(32, 32),
      infoWindowAnchor: new BMap.Size(20, 15)
  });
  var marker_short = new BMap.Marker(pt, {icon: myicon});
  map.addOverlay(marker_short);
  window.setTimeout(function(){
  	marker_short.remove();
//  	map.clearOerelay(marker_short);
  }, 2100);
}