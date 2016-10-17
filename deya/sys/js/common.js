/**
 * 本文件内容是对开发中常用的功能的集合
 * author : Chunson
 */

/*
 * 获得随机数
 */
function getRandom(){
	return Math.round(Math.random()*10000000)+1;
}
/**
 * 对象池,用于在页面和弹出页面之间共享对象
 */
ObjectPool={
	//把对象放入对象池中,并返回唯一标识(1到10000000之间的随机数)
	put:function(obj){
		var key=getRandom();
		this[key]=obj;
		return key;
	},
	//在对象池中查找对象,查找顺序为 本窗口-->本窗口的opener-->本窗口的父窗口
	get:function(key){
		if(this[key]){
			return this[key];
		}else if(window.opener.ObjectPool[key]){
			return window.opener.ObjectPool[key];
		}else if(window.parent.ObjectPool[key]){
			return window.parent.ObjectPool[key];
		}
	},
	getFromOpener:function(key){//指定从本窗口的opener窗口的对象池中查找对象
		return window.opener.ObjectPool[key];
	}
};

/**
 * 对页面request对象的实现,可以得到页面地址,参数,读写cookie等
 * @param {Object} name
 */
request = {
	/**
	 * 得到参数	如在aaa.html?id=1中request.getParameter("id")得到1
	 * @param {Object} name	参数名
	 */
	getParameter : function(name) {
		var GetUrl = this.getUrl();
		var Plist = [];
		if (GetUrl.indexOf('?') > 0) {
			Plist = GetUrl.split('?')[1].split('&');
		} else if (GetUrl.indexOf('#') > 0) {
			Plist = GetUrl.split('#')[1].split('&');
		}
		if (GetUrl.length > 0) {
			for (var i = 0; i < Plist.length; i++) {
				var GetValue = Plist[i].split('=');
				if (GetValue[0].toUpperCase() == name.toUpperCase()) {
					return GetValue[1];
					break;
				}
			}
			return;
		}
	},
	/**
	 * 如果有多个值的参数,返回包含全部值的Array对象
	 * @param {Object} name
	 */
	getParameters : function(name) {
		var param=this.getParameter(name);
		if(param){
			return param.split(",");
		}
	},
	/** 获取浏览器URL */
	getUrl : function() {
		return location.href;
	},
	/**得到应用的context*/
	getContextPath : function() {
		return context;
	},
//	getSession : function(){
//		return jsonrpc.jsonUtil.getSession();
//	},
	/*
	 * cookies设置函数 @name Cookies名称 @value 值
	 */
	setCookie : function(name, value) {
		try {
			var longTimes = 0;
			var argv = this.setCookie.arguments;
			var argc = this.setCookie.arguments.length;
			var expires = (argc > 2) ? argv[2] : null;			
			if (expires != null) {
				var str1=expires.substring(1,expires.length)*1; 
				var str2=expires.substring(0,1); 
				if (str2=="s"){
					longTimes = str1*1000;
				}else if (str2=="h"){
					longTimes = str1*60*60*1000;
				}else if (str2=="d"){
					longTimes = str1*24*60*60*1000;
				}
				var LargeExpDate = new Date();
				LargeExpDate.setTime(LargeExpDate.getTime()
						+ (longTimes));
			}
			document.cookie = name
					+ "="
					+ escape(value)
					+ ((expires == null) ? "" : ("; expires=" + LargeExpDate
							.toGMTString()));
			return true;
		} catch (e) {
			alert(e.description);
			return false;
		}
	},
	/*
	 * cookies读取函数 @Name Cookies名称 返回值 Cookies值
	 */
	getCookie : function(Name) {
		var search = Name + "="
		if (document.cookie.length > 0) {
			offset = document.cookie.indexOf(search)
			if (offset != -1) {
				offset += search.length
				end = document.cookie.indexOf(";", offset)
				if (end == -1)
					end = document.cookie.length
				return unescape(document.cookie.substring(offset, end))
			} else {
				return;
			}
		}
	},
	removeCookie: function(name){
		if (this.getCookie(name)) {
			document.cookie = name + "=;expires=Thu, 01-Jan-1970 00:00:01 GMT";
		}
	}
};
/**
 * Json处理工具
 * 
 * @param {Object}
 * 
 */
JsonUtil = {
	/**
	 * 把对象转成JSON字符串
	 * @param {Object} o
	 */
	toJSON : function(o) {
		var s = [], p = {}, c = "";
		p.isArr = o instanceof Array;
		if (p.isArr) {
			p.l = "[";
			p.r = "]"
		} else {
			p.l = "{";
			p.r = "}"
		};
		for (k in o) {
			if (!p.isArr)
				c = '"' + k + '":';
			else
				c = "";
			switch (typeof(o[k])) {
				case "object" :
					if (o[k] instanceof Object)
						c += arguments.callee(o[k]);
					else
						c += o[k];
					break;
				case "string" :
					c += '"' + o[k].replace("\\", "\\\\").replace('"', '\\"')
							+ '"';
					break;
				case "number" :
				case "boolean" :
				case "null" :
					c += o[k];
					break;
				default :
					continue
			};
			s.push(c)
		};
		return p.l + s.join(",") + p.r
	},
	/**
	 * 把JSON字符串转成对象
	 * @param {Object} str
	 */
	toObject : function(str) {
		var k;
		try {
			k = eval('(' + str + ')');
			return (typeof k == "object") ? k : ''
		} catch (e) {
			return ''
		}
	}
};

/**
 *	类名称: 定时器
 *	功能: 能间隔一段时间执行一个任务(反复或只执行一次,由构造参数isRunOnce决定)
 *	备注: 默认为反复执行,isRunOnce为true,则只执行一次
 **/
function Timer(isRunOnce)
{
	this.interval=null;//执行间隔时间
	this.task=function(){//执行的任务函数,使用时覆盖默认实现
		alert("fnOnTimer回调函数没有设置!");
	}	
	
	this.__oTimer = null;
	this.__fnSet=setInterval;
	this.__fnClear=clearInterval;
	
	if(isRunOnce){
		this.__fnSet=setTimeout;
		this.__fnClear=clearTimeout;		
	}
	
	/**
	 *	启动
	 **/
	this.start = function(interval, task)
	{
		this.interval=interval||this.interval;
		this.task=task||this.task;
		if(this.__oTimer == null){
			this.__oTimer = this.__fnSet(this.task,this.interval);
		}
	}
	
	/**
	 *	停止
	 **/
	this.stop = function()
	{
		if(this.__oTimer!=null){
			this.__fnClear(this.__oTimer);
		}
		this.__oTimer = null;
	}
	
	/**
	 *	重置
	 **/
	this.reset = function()
	{
		this.stop();
		this.start();
	}
}


/**
 * 对翻页的封装
 * @param {Object} name	 该翻页的名称标识,当一个界面有多个翻页时需要加此参数作为标识
 */
TurnPage =function (){
	this.name=ObjectPool.put(this);//放入对象池,返回唯一标示
	this.showDiv="__turn_page";
	this.showType="";
	/**
	 * 每页显示条数
	 */
	this.pageSize=15;
	this.getPageSize=function(){
		return this.pageSize;
	};
	this.setPageSize=function(pageSize){
		this.pageSize=pageSize;
	};
	/**
	 * 总条数
	 */
	this.total=10;
	this.getTotal=function(){
		return this.total;
	};
	this.setTotal=function(total){
		this.total=total;
	};
	/**
	 * 当前显示页
	 */
	this.curr_page=1;
	this.pageCount = 1;
	/**
	 * 获得总页数
	 */
	this.getPageCount = function(){
		var pageCount=parseInt(this.total/this.pageSize+1);
		if(this.total%this.pageSize==0){
			pageCount=pageCount-1;
		}	
		return pageCount;
	};
	/**
	 * 当前页取数据的开始位置
	 */
	this.getStart=function(){
		return (this.curr_page-1)*this.pageSize;
	};
	/**
	 * 跳转到指定页
	 * @param {Object} page
	 */
	this.goPage=function (page){		
		if(page == 0)
			page = 1;
        var CheckData = /^\d+$/;
        var checkzero = /^0+$/;
      
		page=parseInt(page);		
		if(this.getPageCount()<page){			
			return;
		}		
		this.curr_page=page;
		this.onPageChange();
		this.show();
	};

	this.goPage2=function (page){
		if(page == "" || page == null)
			return;
		this.curr_page=page;
		this.onPageChange();
		this.show();
	};
	this.changePageSize=function (ps){		
		if(ps == "" || ps == null)
			return;
		this.pageSize=ps;
		this.curr_page=1;
		this.onPageChange();
		this.show();
	};
/**
 * 让翻页在指定区域显示
 * @param {Object} div	指定的显示区域的id,默认值为__turn_page
 */
this.show=function(div,showType){
try {
	this.showType = showType||this.showType;
	this.showDiv=div||this.showDiv;
	//加判断,如果在第2页时删除数据,剩于的数据只有1页时,让它跳到上一页
	if(this.curr_page > 1 && this.total == this.pageSize*(this.curr_page-1))
	{
		this.goPage(this.curr_page-1);
		return;
	}

	$("#"+this.showDiv).empty();
	//var page=this.curr_page;
	  var page = parseInt(this.curr_page);//2014-03-27修改了此行
		if(this.showType == "simple")
		{
			var table = '<table class="table_pageing" border="0" cellpadding="0" cellspacing="0">';
				table += '<tr>';
				table += '<td align="left" valign="middle">&nbsp;</td>';
				table += '<td id="pageingArea" align="right" valign="middle">';
				table += '共&nbsp;<span id="infoCount">'+(this.total)+'</span>&nbsp;条记录';				
				table += '</td>';
				table += '</tr>';
				table += '</table>';
		}
		else
		{
			 var pageArr = [10,15,20,25,30,35,40,45,50];
			 var table = '<table class="table_pageing" border="0" cellpadding="0" cellspacing="0">'+
				'<tr>'+
				'<td align="left" valign="middle">&nbsp;</td>'+
				'<td id="pageingArea" align="right" valign="middle">'+
				'共&nbsp;<span id="infoCount">'+(this.total)+'</span>&nbsp;条记录&nbsp;'+
				'<a href="javascript:ObjectPool['+this.name+'].goPage(1);" name="firstPage" id="firstPage">首页</a>&nbsp;'+
				'<a href="javascript:ObjectPool['+this.name+'].goPage('+(page-1)+');" name="previousPage" id="previousPage">上一页</a>&nbsp;'+
				'<a href="javascript:ObjectPool['+this.name+'].goPage('+(page+1)+');" name="nextPage" id="nextPage">下一页</a>&nbsp;'+
				'<a href="javascript:ObjectPool['+this.name+'].goPage('+this.getPageCount()+');" name="lastPage" id="lastPage">尾页</a>&nbsp;'+
				'每页&nbsp;';				
				 if(this.showType == "notShowPageSize")
				{
					table += this.pageSize;
				}
				else
			    {
					table += '<select id="pageSize" name="pageSize" class="input_select" onchange="ObjectPool['+this.name+'].changePageSize($(\'#pageSize\').val())">';
					for(var i=0;i<pageArr.length;i++)
					{
						if(this.pageSize == pageArr[i])
							table += '<option value="'+pageArr[i]+'" selected="selected">'+pageArr[i]+'</option>';
						else
							table += '<option value="'+pageArr[i]+'">'+pageArr[i]+'</option>';
					}
			    }				
				table += '</select>&nbsp;'+
				'条记录&nbsp;'+
				'转到第&nbsp;'+
				'<select id="pageGoNum" name="pageGoNum" class="input_select" onchange="ObjectPool['+this.name+'].goPage2(this.value)">';
					if(this.getPageCount() == 0)
						table += '<option value="0">0/0</option>';
					else
					{
						for(var i=1;i<this.getPageCount()+1;i++)
						{
							if(this.curr_page == i)
								table += '<option value="'+i+'" selected="true">'+i+'/'+this.getPageCount()+'</option>';
							else
								table += '<option value="'+i+'">'+i+'/'+this.getPageCount()+'</option>';

						}
					}
				table += '</select>'+
				'<span id="pageCur" class="hidden">1</span>&nbsp;'+
				'页'+
				'</td>'+
				'</tr>'+
			   '</table>';
		}
		$("#"+this.showDiv).append(table);
	
} catch (e) {
	alert(e.message);
}
}
/**
 * 当页码发生变化后的回调函数,调用者要覆盖该函数,用于在页码变化后取该页的数据
 */
	this.onPageChange=function(){
		alert("没有设置onPageChange回调函数!");
	}
};


/**
 * 数据Table(信息列表)的封装
 * @param {Object} name	名称标识,当一个页面有多个列表时需要指定名字
 */
Table = function(){
	this.name=ObjectPool.put(this);//放入对象池,返回唯一标示
	this.table_name;
	this.enableSort=true;
	this.showDiv="__table";
	this.beanList=new List();	
	this.listStyle="";//表格列表内容样式名称
	/**
	 * 设置Table的数据对象List
	 * @param {Object} list	List或Array类型的对象
	 */
	this.setBeanList = function(list,className){
		this.listStyle = className;		
		if (list) {
			this.beanList.clear();
			this.beanList.addAll(list);
		}
	}
	this.checkBox=true;
	this.colsMap=new Map();
	this.colsList=new List();
	/**
	 * 设置Table的列映射信息
	 * @param {Object} map	列信息,列名和列显示名的键值对
	 */
	this.setColsMap=function(map){		
		if(map){
			//this.colsMap.clear().putAll(map);
			this.colsMap = map;
		}
	};

	this.setColsList=function(list){		
		if(list){
			//this.colsMap.clear().putAll(map);
			this.colsList = list;			
		}
	};
	this.allColsMap=new Map();
	this.allColsList=new List();
	/**
	 * 设置Table的列映射信息
	 * @param {Object} map	列信息,列名和列显示名的键值对
	 */
	this.setAllColsMap=function(map){
		if(map){
			//this.allColsMap.clear().putAll(map);
			this.allColsMap = map;			
		}
	};

	this.setAllColsList=function(list){
		if(list){
			//this.allColsMap.clear().putAll(map);
			this.allColsList = list;			
		}
	};
	/**
	 * 得到选定的对象List集合
	 */
	this.getSelecteBeans=function(){ 
		var checks=$("#"+this.name).find(".inputHeadCol:checkbox").getCheckValues();
		var beans=new List();
		for (var i=0; i<checks.length; i++) {
			if(this.beanList.get(checks[i]) != null)
				beans.add(this.beanList.get(checks[i]));
		}
		return beans;
	};
	this.getSelecteCheckboxValue=function(itemName){ 
		var checks=$("#"+this.name).find(".inputHeadCol:checkbox").getCheckValues();
		var ids = [];
		for (var i=0; i<checks.length; i++) {
			if(this.beanList.get(checks[i]) != null)
				ids.push(eval("this.beanList.get(checks[i])."+itemName));
		}
		return ids.toString();
	};
	//反选
	this.unChekcbox=function(){
		$(".inputHeadCol:checked").parent().parent().click();
	};
	this.getAllCheckboxValue=function(itemName){ 
		var checks=$("#"+this.name).find(".inputHeadCol:checkbox").getAllCheckValues();
		var ids = [];
		for (var i=0; i<checks.length; i++) {
			if(this.beanList.get(checks[i]) != null)
				ids.push(eval("this.beanList.get(checks[i])."+itemName));
		}
		return ids.toString();
	};
	this.getSelecteCount=function(){ 		
		return $("#"+this.name).find(":checkbox[id!='checkAll']").getCheckValues().length;
	};	
	this.getTable = function(i){
		return $("#"+this.name).find(".table");
	}
	/**
	 * 得到指定行号(从0开始)的行的页面元素对象
	 * @param {Object} i
	 */
	this.getRow=function(i){
			return $("#"+this.name).find("#row_"+i);
	};
	this.getCol=function(colName){
		colName=colName.replace(".","\\.");
			return $("#"+this.name).find("[id $='_"+colName+"']");//.data_cell
	};
	/**
	 * 得到指定行号(从0开始)的行的指定列的页面元素对象
	 * @param {Object} i
	 * @param {Object} name
	 */
	this.getCell=function(i,name){
		name=name.replace(".","\\.");
			return $("#"+this.name).find("#cell_"+i+"_"+name);
	};
	this.getTitleCell=function(name){
		name=name.replace(".","\\.");
		return $("#"+this.name).find("#cell_title_"+name);
	};
	this.con_name="";//查询条件名称
	this.con_value="";//查询条件值
	this.sortCol="";
	this.sortType="asc";
	this.__changeSort=function(name){
		if(!name)return;
		if(!this.enableSort){
			return;
		}
		if(name == "actionCol") return;
		if(name===this.sortCol){
			this.sortType=="asc"?this.sortType="desc":this.sortType="asc";
		}else{
			this.sortCol=name;
			this.sortType="asc";
		}
		//alert(this.sortCol);
		//alert(this.sortType);
		this.onSortChange();
	}
	this.onSortChange=function(){
		alert("没有设置onSortChange回调函数!");
	}
	/**
	 * 在指定区域中显示列表内容
	 * @param {Object} div	指定区域的id
	 */
	this.show=function(div){
	
		this.showDiv=div||this.showDiv;	
		if(!this.colsMap){
			alert("没有设置显示列信息");
			return false;
		}
		if(!this.beanList){
			alert("没有设置数据信息");
			return false;
		}
		$("#"+this.showDiv).empty();
		var foot_td_str = "";
		var table = '<div id="'+this.name+'">';
			table += '<table id="'+this.table_name+'" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0">';
			table += '<thead>';
			 table += '<tr>';
			  if(this.checkBox){
				table+='<td width="25" align="center"><input id="selectAll" name="selectAll" type="checkbox" onclick="" /></td>';
			  }
			  for (var i=0; i<this.colsList.size(); i++) {
				//var class_name = this.colsList.get(i).class_name;
				var name = this.colsList.get(i).name;
				var show_name = this.colsList.get(i).show_name;
				var width = this.colsList.get(i).width;
				var height = this.colsList.get(i).height;
				var clicks = this.colsList.get(i).clicks;
						
				var bmp="";			
				
				if(i==0)
					table+='<td  width="'+width+'" height="'+height+'" name="'+name+'" id="cell_title_'+name+'" >'+show_name+bmp+'&#160;</td>';
				else
					table+='<td  width="'+width+'" height="'+height+'" align="center"  name="'+name+'" id="cell_title_'+name+'" style="word-wrap:break-word; word-break:break-all">'+show_name+bmp+'&#160;</td>';
			 };
			 table += '</tr>';
			table += '</thead>';
			table += '<tbody>';

			if(this.beanList.size() == 0)
			{
				if(this.checkBox)
					table += '<tr><td colspan="'+(this.colsList.size()+1)+'">暂无数据...</td></tr>';
				else
					table += '<tr><td colspan="'+(this.colsList.size())+'">暂无数据...</td></tr>';
			}		

			 for (var n = 0; n < this.beanList.size(); n++) {
				var bean=this.beanList.get(n);
				if(!bean){
					continue;
				}
				table+='<tr height="25px"  id="row_'+n+'">';
				if(this.checkBox){
					table+='<td align="center"><INPUT TYPE="checkbox" style="cursor:pointer" class="inputHeadCol" name="checkBox" value="'+n+'" id="'+n+'"></td>';					
				}
				for (var i=0; i<this.colsList.size(); i++) {
					var tempS = eval("bean."+this.colsList.get(i).name)+"";
					var title_tempS = tempS;				
					if(tempS == "" || tempS == null || tempS == "undefined")
					{
						tempS = "&#160;";						
					}
					if(i==0)
						table+='<td id="cell_'+n+'_'+this.colsList.get(i).name+'" width="'+this.colsList.get(i).width+'" >'+tempS+'</td>';
					else
						table+='<td id="cell_'+n+'_'+this.colsList.get(i).name+'" width="'+this.colsList.get(i).width+'" align="center" >'+tempS+'</td>';
				};			
				table+='</tr>';
			}
			
			foot_td_str += '<td colspan="'+this.colsList.size()+'"></td>';
			
			if(this.checkBox){
				foot_td_str += "<td></td>";					
			}
			table += '</tbody>';
			table += '<tfoot><tr>'+foot_td_str+'</tr></tfoot>';
			table += '</table>';	
			table += '</div>';	

		$("#"+this.showDiv).append(table);
//		if ("page" == this.sortMode) {
//			$("#" + this.showDiv).find(".table").tableSorter({
//				//sortColumn: values[0],			// Integer or String of the name of the column to sort by.  
//				sortClassAsc: 'headerSortUp', // class name for ascending sorting action to header
//				sortClassDesc: 'headerSortDown', // class name for descending sorting action to header
//				headerClass: 'header' // class name for headers (th's)
//			//disableHeader: 'ID' 	// DISABLE Sorting on ID
//			});
//		}
	}	
	this.customCols=function(){
		window.open("tableCols.html?table="+this.name, "customCols_"+this.name, 
		"height=250,width=350,left=300,top=170,channelmode=0,directories=0,fullscreen=0,"+
		"location=0,menubar=0,Resizable=1,scrollbars=1,status=0,titlebar=0,toolbar=0");
	}
}

TitleClos = function(){		
		this.name;
		this.show_name;		
		this.width;
		this.height;
		this.class_name;
		this.clicks;	
		
}

function setTitleClos(name,show_name,width,height,class_name,clicks)
{
	var ct = new TitleClos();
	ct.name = name;
	ct.show_name = show_name;		
	ct.width = width;
	ct.height = height;
	ct.class_name = class_name;
	ct.clicks = clicks;
	return ct;	
}

function CustomSearch(){
	this.cols=new Map();
	this.setCols=function(cols){
		if(cols){
			this.cols=cols;
		}
	}
	this.condition="";
	this.getCondition=function(){
		return this.condition;
	}
	this.onSetReturn=function(){
		alert("您没有设置通用查询回调函数");
	}
	this.show=function(){
		var key=ObjectPool.put(this);
			window.open("tableSearch.html?key="+key, key, 
			"height=350,width=450,left=300,top=170,channelmode=0,directories=0,fullscreen=0,"+
			"location=0,menubar=0,Resizable=1,scrollbars=1,status=0,titlebar=0,toolbar=0");	
	}
}


var goto_top_type = -1;

var goto_top_itv = 0;

function goto_top_timer() {

      var y = goto_top_type == 1 ? document.documentElement.scrollTop : document.body.scrollTop;

         var moveby = 15;

          

         y -= Math.ceil(y * moveby / 100);

         if (y < 0) {

         y = 0;

         }

          

         if (goto_top_type == 1) {

         document.documentElement.scrollTop = y;

         }

         else {

         document.body.scrollTop = y;

         }

          

         if (y == 0) {

         clearInterval(goto_top_itv);

         goto_top_itv = 0;

         }

}

 

function goto_top() {

         if (goto_top_itv == 0) {

                  if (document.documentElement && document.documentElement.scrollTop) {

                  goto_top_type = 1;

                  }

                  else if (document.body && document.body.scrollTop) {

                  goto_top_type = 2;

                  }

                  else {

                  goto_top_type = 0;

                  }

                   

                  if (goto_top_type > 0) {

                  goto_top_itv = setInterval('goto_top_timer()', 1);

                  }

         }

}