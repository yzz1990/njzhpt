// *****YZPANI*****
// 目前该JS有两个功能：一个是根据一组PNG图片生成动画；另一个是完全由JS创建水流动画，需要HTML5支持。

window.onload=function(){
	setInterval(showani,60); //控制流速
}


/********************* PNG动画开始***********************/

function aniobj(boxid,op,tp){ //动画对象
	
	var obj=null; //装着一组PNG图像的帧序列
	var pngindex=0; //当前处理着哪一帧
	var opstep=op; //透明度步进，值范围0~1，如果为1就是完全的帧切换动画，帧与帧之间没有过渡
	var timestep=tp;  //时间步进，单位为毫秒
	
	obj=document.getElementById(boxid).getElementsByTagName("img"); //取得内部图像元素
	
	obj[pngindex].style.opacity="1"; //透明度为1也要设一下，不然取不到值
	
	for(i=1; i<obj.length; i++){ //把除第一个帧以外的帧全部透明度为0
		obj[i].style.opacity="0";
	}
	
	this.pngani=function(){
		
		function getopn(opn){ //取得透明度的小函数
			return parseInt(parseFloat(obj[opn].style.opacity)*100)/100;
		}

		obj[pngindex].style.opacity=getopn(pngindex)-opstep; //减少当前帧的透明度
	
		if(pngindex<obj.length-1){ //如果当前帧不是最后一帧
			obj[pngindex+1].style.opacity=getopn(pngindex+1)+opstep; //增加下一帧的透明度
		}else{ //如果是最后一帧
			obj[0].style.opacity=getopn(0)+opstep; //那么增加第一帧的透明度
		}
	
		if(getopn(pngindex)<=0){ //如果当前帧已经完全透明了
			pngindex+=1; //处理下一帧	
			if(pngindex==obj.length){ //超过序列长度则归零
				pngindex=0;
			}
		}
	}
	
	setInterval(this.pngani,timestep);
	
}

/********************* PNG动画结束***********************/

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/********************* 水流动画开始***********************/

var mfs=new Array;

function crflow(x1,y1,x2,y2,tw,th,dt,cc){
	var mff= new flows(x1,y1,x2,y2,tw,th,dt,cc);
	mfs.push(mff);
}

var flows=function(x1,y1,x2,y2,cc,tw,th,dt){ //x1,y1起点坐标，x2,y2终点坐标，tw是箭头的宽度，th是箭头的高度(底边到顶点的距离)，dt是箭头间的间隔距离，cc是箭头的颜色。这些参数中起终点坐标是必须的，其他都是可选的。
	if(cc==undefined) cc="#349dff"; //默认三角形颜色
	if(tw==undefined) tw=5; //默认三角形宽度
	if(th==undefined) th=7; //默认三角形高度
	if(dt==undefined) dt=5; //默认三角形间隔
		
	md=1; //移动距离
	
	angt=(y2-y1)/(x2-x1); 
	if(x1<=x2) ang=360*(Math.atan(angt)/(Math.PI*2));
	else ang=180+360*(Math.atan(angt)/(Math.PI*2));
	//计算箭头线要旋转的角度
	
	w=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)); //求两点间距离
	tc=Math.ceil(w/(th+dt)); 
	//计算两点间要生成多少个三角形
	
	var newcvs= document.createElement('canvas'); //生成一个画布
	newcvs.style.position="absolute";
	newcvs.setAttribute("width",Math.abs(x2-x1)+tw); //即使水平或垂直也不能没有高度或宽度，需要加个tw
	newcvs.setAttribute("height",Math.abs(y2-y1)+tw);
	
	var cxt=newcvs.getContext("2d"); //生成一串箭头的容器
			
	if((x1<=x2)&&(y1<=y2)){ //终点在起点的左下
		newcvs.style.left=x1+"px";
		newcvs.style.top=y1+"px";
		cxt.translate(tw,0); 
	}else if((x1>x2)&&(y1<=y2)){ //终点在起点的右下
		newcvs.style.left=x2+"px";
		newcvs.style.top=y1+"px";
		xdd=x1-x2+tw; 
		cxt.translate(xdd,tw); 
	}else if((x1>x2)&&(y1>y2)){  //终点在起点的右上
		newcvs.style.left=x2+"px";
		newcvs.style.top=y2+"px";
		xdd=x1-x2;
		ydd=y1-y2+tw;
		cxt.translate(xdd,ydd); 
	}else if((x1<=x2)&&(y1>y2)){  //终点在起点的左上
		newcvs.style.left=x1+"px";
		newcvs.style.top=y2+"px";
		ydd=y1-y2;
		cxt.translate(0,ydd); 
	}
	
	cxt.rotate(ang*(Math.PI/180));
	
	document.getElementById("layoutwarp").appendChild(newcvs);
	
	this.stp=-th; //箭头初始位置
	
	this.flowani=function(){
		cxt.clearRect(-th,0,3000,3000);
		cxt.fillStyle=cc;
		for(i=0; i<tc+50; i++){ //这个30是个容错数量
			cxt.beginPath();
			cxt.moveTo(this.stp+i*(th+dt),0);
			cxt.lineTo(this.stp+i*(th+dt)+th,tw/2);
			cxt.lineTo(this.stp+i*(th+dt),tw);
			cxt.closePath();
			cxt.fill();		
		}
		this.stp+=md;
		if(this.stp>=dt) this.stp=-th;
	}
}

function showani(){
	for(j=0; j<mfs.length; j++){
		mfs[j].flowani();
	}
}

/********************* 水流动画结束***********************/
