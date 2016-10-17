var CollectionDataRPC = jsonrpc.CollectionDataRPC;

function initRuleName(){
	if(rule_id != "" && rule_id != null && rule_id != "null"){
		var rule_name = CollectionDataRPC.getCollRuleBeanByID(rule_id).title;
		$("#ruleName").html(rule_name);
	}else{
		$("#ruleName").html("您还有没有采集任务!");
	}
}

/**
 * 开始采集数据
 * @return
 */
function startCollection()
{
	CollectionDataRPC.startCollectionData(resultCollDataBack,rule_id,"start");   //开始采集调用后台方法
}

var TimerObj;
var timerObj1;    //读取日志文件的定时器
var n=1;          //初始化日志文件行数
var tmpN="";      //记录暂停时读取的日志文件的行数
var num=parseInt(n);
var startTime="";
var endTime="";

function resultCollDataBack(result,e){
	if(e != null){return;}
	$("#collLogInfo").empty();
	$("#collLogInfo").append("<tr><td>任务开始运行</td></tr>");
	$("#collLogInfo").append("<tr><td>正在初始化抓取规则...</td></tr>");
	$("#collLogInfo").append("<tr><td>网络环境也会影响抓取速度!</td></tr>");
	startTime = formatCurrentDate("yyyy-MM-dd hh:mm:ss");
	timerObj = setTimeout("readCollLog("+rule_id+")",10000);  //10秒后执行
}


/**
 * 读取采集日志文件
 * @param ruleName 采集名称
 * @return
 */

function readCollLog(r_id){
	timerObj1 = setInterval("readCollLogByRuleID("+r_id+")",2000); //每隔2秒执行
}


function readCollLogByRuleID(ruleid){
	var str = CollectionDataRPC.readCollLogByRuleID(ruleid,num);
	if(str=="collOver"){ //采集完成
		if(clearTime){
			window.clearTimeout(timerObj); //清除定时器
		}
		endTime = formatCurrentDate("yyyy-MM-dd hh:mm:ss");
		$("#collLogInfo").append("<tr><td><span style='color:red;'>采集任务运行完成!</span></td></tr>");
		$("#collLogInfo").append("<tr><td><span style='color:red;'>共采集信息:"+(num-2)+"条,开始时间:"+startTime+" 结束时间:"+endTime+"</span></td></tr>");
		window.clearInterval(timerObj1); //清除定时器
		$("#btnStart").attr("disabled","true");
		$("#btnParse").attr("disabled","true");
		$("#btnEnd").attr("disabled","true");
	}else if(str==null||str==""){
		window.clearTimeout(timerObj); //清除定时器
		window.clearInterval(timerObj1); //清除定时器
		tmpN = num;  //记录此时读取的日志文件的行数
		num=parseInt(tmpN);
		timerObj = setTimeout("readCollLog("+ruleid+")",5000);  //10秒后执行
	}else if(str != null){
		$("#collLogInfo").append("<tr><td>"+str+"</td></tr>");
	}
	num++;
}

/**
 * 点击开始
 * @return
 */
function startColl(){
	CollectionDataRPC.startCollectionData(startPauseCollDataBack,rule_id,"start_pause"); //暂停之后的恢复
}

function startPauseCollDataBack(result,e){
	if(e!=null){return;}
	num=parseInt(tmpN);
	timerObj1 = setInterval("readCollLogByRuleID("+rule_id+")",2000); //每隔2秒执行
	
	$("#btnStart").attr("disabled","true");
	$("#btnParse").removeAttr("disabled"); 
	$("#btnEnd").removeAttr("disabled");
}

/**
 * 点击暂停
 * @return
 */
function parseColl(){
	CollectionDataRPC.startCollectionData(pauseCollDataBack,rule_id,"pause");
}

function pauseCollDataBack(result,e){
	if(e!=null){return;}
	window.clearInterval(timerObj1); //清除定时器
	tmpN = num;  //记录暂停时读取的日志文件的行数
	
	$("#btnStart").removeAttr("disabled");
	$("#btnParse").attr("disabled","true");
	$("#btnEnd").removeAttr("disabled"); 
}

/**
 * 点击停止
 * @return
 */
function stopColl(){
	CollectionDataRPC.startCollectionData(stopCollDataBack,rule_id,"end");
}

function stopCollDataBack(result,e){
	if(e!=null){return;}
	window.clearTimeout(timerObj); //清除定时器
	window.clearInterval(timerObj1); //清除定时器
	$("#btnStart").attr("disabled","true");
	$("#btnParse").attr("disabled","true");
	$("#btnEnd").attr("disabled","true");
}



