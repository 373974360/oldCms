
//返回如： 2011年1月27日  星期四 
function getDateAndWeek()
{
	var day="";
	var month="";
	var ampm="";
	var ampmhour="";
	var myweekday="";
	var year="";
	mydate=new Date();
	myweekday=mydate.getDay();
	mymonth=mydate.getMonth()+1;
	myday= mydate.getDate();
	myyear= mydate.getYear();
	year=(myyear > 200) ? myyear : 1900 + myyear;
	if(myweekday == 0)
	weekday="星期日 ";
	else if(myweekday == 1)
	weekday="星期一 ";
	else if(myweekday == 2)
	weekday="星期二 ";
	else if(myweekday == 3)
	weekday="星期三 ";
	else if(myweekday == 4)
	weekday="星期四 ";
	else if(myweekday == 5)
	weekday="星期五 ";
	else if(myweekday == 6)
	weekday="星期六 ";
	var xiqiresults = year+"年"+mymonth+"月"+myday+"日&nbsp;&nbsp;"+weekday;
	
	return xiqiresults;
	//var tmpo = document.getElementById("xiqiresult");
	//tmpo.innerText=xiqiresults;	
}

function formatCurrentDate(format){ 
	var date = new Date();
	
    var paddNum = function(num){ 
     	 num += ""; 
     	 return num.replace(/^(\d)$/,"0$1"); 
    } 

    //指定格式字符 
    var cfg = { 
      yyyy : date.getFullYear(), //年 : 4位 
      yy : date.getFullYear().toString().substring(2),//年 : 2位 
      M  : date.getMonth() + 1,  //月 : 如果1位的时候补0 
      MM : paddNum(date.getMonth() + 1), //月 : 如果1位的时候补0 
      d  : date.getDate(),   //日 : 如果1位的时候不补0 
      dd : paddNum(date.getDate()),//日 : 如果1位的时候补0 
      hh : paddNum(date.getHours()), //时 
      mm : paddNum(date.getMinutes()), //分 
      ss : paddNum(date.getSeconds()) //秒
    } 
    return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];}); 
 } 