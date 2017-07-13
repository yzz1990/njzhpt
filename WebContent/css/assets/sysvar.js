/****** 前端参数配置 ******/
//地图服务IP
var SYS_MapServer_IP="58.214.246.30";
//地图服务Port
var SYS_MapServer_Port="23360";
//地图服务URL前缀
var SYS_MapServer_URLPrefix="http://"+SYS_MapServer_IP+":"+SYS_MapServer_Port;

//矢量图URL
var SYS_VECURL = "http://218.2.102.201/njhousenj_house/rest/services/ditu_nj/MapServer";
//矢量图标注URL
var SYS_VECANNO = "";
//地形图URL
var SYS_TERURL = "";
//地形图标注URL
var SYS_TERANNO = "";
//影像图URL
var SYS_IMGURL = "http://218.2.102.201/njhousenj_house/rest/services/image_nj/MapServer";
//影像图标注URL
var SYS_IMGANNO = "";
//地图透明度
var SYS_ALPHA = 1;		
//初始显示地图级别		
var SYS_INITLEVEL = 0;
//初始显示中心点X坐标（经度）
var SYS_INITX = 118.79169361;
//初始显示中心点Y坐标（纬度）
var SYS_INITY = 31.85677465;
//专题图URL
var SYS_TOPICURL = "";
//边界URL
var SYS_BORDERURL = "";
//行政区划图
var SYS_AREAURL = SYS_MapServer_URLPrefix+"/arcgis/rest/services/Nanjing/Nanjing/MapServer";
//地理几何服务
var SYS_GEOSERVER = "http://218.2.102.201/njhousenj_house/rest/services/Utilities/Geometry/GeometryServer";

//打印服务
var SYS_PrintServer = "http://218.2.102.201/njhousenj_house/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task";

//平台IP
var SYS_PT_IP="58.214.246.30";
//平台Port
var SYS_PT_Port="1000";
//平台URL前缀
var SYS_PT_URLPrefix="http://"+SYS_PT_IP+":"+SYS_PT_Port;
