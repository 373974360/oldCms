var AppRPC = jsonrpc.AppRPC;
var title_map = new Map();
function executeSearchSql()
{
	var sql = $("#sql").val();
	if(sql.trim() != "")
	{
		$("#result_tbody").empty();
		$("#result_title_tr").empty();
		if(sql.indexOf("select") == 0)
		{
			getSearchResult(sql);
		}else
		{			
			
			$("#result_title_tr").append("<td>"+AppRPC.executeDynamicSql(sql)+"</td>");
		}
	}	
}

function getSearchResult(sql)
{
	var list = AppRPC.executeSearchSql(sql);//第一个参数为站点ID，暂时默认为空	
	list = List.toJSList(list);//把list转成JS的List对象
	
	if(list != null && list.size() > 0)
	{		
		for(var i=0;i<list.size();i++)
		{
			var m = list.get(i);
			m = Map.toJSMap(m);			
			setResultTitleInMap(m);		
		}		

		setResultTitleInTable();		
		for(var i=0;i<list.size();i++)
		{
			var m = list.get(i);
			m = Map.toJSMap(m);
			setResultInTable(m);
		}

		Init_InfoTable("result_talbe");	
	}
}

//将字段全写到一个map中，如果查询出来的某个字段的值为空，那相对应的字段名也不会出来，为数据一致性，在这里要把字段名全部写入一个map,以免有遗漏
function setResultTitleInMap(m)
{
	var keys = m.keySet();
	for(var i=0;i<keys.length;i++)
	{		
		title_map.put(keys[i]);
	}
}

function setResultTitleInTable()
{
	var keys = title_map.keySet();	
	for(var i=0;i<keys.length;i++)
	{
		$("#result_title_tr").append("<td>"+keys[i].toLowerCase()+"</td>");
	}
}

function setResultInTable(m)
{
	var str = '<tr>';
	var keys = title_map.keySet();	
	for(var i=0;i<keys.length;i++)
	{
		var v = "";
		if(m.containsKey(keys[i]))
		{
			var o = m.get(keys[i]);
			if(typeof o == 'object')
			{
				v = o.value;
			}else
				v = o;
		}
		str += "<td>"+v+"</td>";
	}	
	str += '</tr>';
	$("#result_tbody").append(str);
}