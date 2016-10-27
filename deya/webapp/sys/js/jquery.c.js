/**
 * 本文件是对Jquery的扩展,以减轻工作中的重复劳动, 其中的利用JS反射自动填写表单和填充对象的功能相当有用
 *
 * @author Chunson
 */
/**
 * 操作checkBox的扩展 用法: $("#aa").check("on"); $("input").check("on");
 *
 * @param {}
 *            mode on=选择 off=取消 fan=反选 默认值为on
 * @return {}
 */
$.fn.check = function(mode){
    var mode = mode || 'on'; // if mode is undefined, use 'on' as default
    return this.each(function(){
        switch (mode) {
            case 'on':
                this.checked = true;
                break;
            case 'off':
                this.checked = false;
                break;
            case 'fan':
                this.checked = !this.checked;
                break;
        }
    });
};
/**
 * checkBox全选,全取消的函数,参数为true全选,false全取消
 * @param {Object} mode
 */
$.fn.checkAll = function(mode){
    return this.each(function(){
        if (mode) {
            this.checked = true;
        }
        else {
            this.checked = false;
        }
    });
};

function unCheckAll(obj)
{
	if(obj.checked == false)
		$("#checkAll").attr("checked",false);	
}

/**
 * 得到所选的checkBox的值列表
 */
$.fn.getCheckValues = function(){
    var values = [];
    this.each(function(){
        if (this.checked == true) {			
            values.push(this.value);
        }
    });
    return values;
};

$.fn.getAllCheckValues = function(){
    var values = [];
    this.each(function(){       
            values.push(this.value);       
    });
    return values;
};

/**
 * 得到select中所有选项的值 
 *
 */ 
$.fn.getAllOptionsValue = function(){	
	var vl = "";
	$(this).find("option").each(function(){
		vl += ","+$(this).val();
	});
	if(vl != "" && vl != null)
		vl = vl.substring(1);
	return vl;	
};

$.fn.getAllOptionsText = function(){	
	var vl = "";
	$(this).find("option").each(function(){
		vl += ","+$(this).text();
	});
	if(vl != "" && vl != null)
		vl = vl.substring(1);
	return vl;	
};

/**
 * 将值添加到<Select>中
 * * 
 * @param {}
 *            value 值
 * @param {}
 *            show 显示数据
 */
$.fn.addOptionsSingl = function(value, show){
	var isEx = false;
	$(this).find("option").each(function(){
		if($(this).attr("value") == value)
			isEx = true;
	});
	if(!isEx)
	 $("<option value='"+value+"'>"+show+"</option>").appendTo($(this));
};

/**
 * 批量把数组里的对象添加到<Select>中
 *
 * @param {}
 *            array 对象数组
 * @param {}
 *            value 用于提取的<Option>的value值的对象属性名,如果array[i]是数组的话取下标
 * @param {}
 *            show 用于提取的<Option>的显示的对象属性名,如果array[i]是数组的话取下标
 */                               
$.fn.addOptions = function(array, value, show){
	if(array.list){
		array=array.list;
	}
    if (!(array instanceof Array)) {
        return;
    }
    return this.each(function(){
        var select = this;
        $.each(array, function(i, item){   
			if(item != null)
			{
				option = new Option(item[show], item[value]);
				select.options.add(option, select.options.length);
			}
        });
    });
};

/**
 * 删除select中的选中的项
 *
 */ 
$.fn.removeSelected = function(){	
	$(this).find("option[selected=true]").remove();	
};




/**
 * 取消select中的选中的项
 *
 */                               
$.fn.selectRepeal = function(){
	var select = this;	
	
	for(var i=0;i<select[0].length;i++)
	{
		select[0].options[i].selected = false;		
	}
	if(!select[0].multiple)
		select[0].options[0].selected = true;		
};

/**
 * 表单自动(填值,清值,取值...)工具
 */

AutoUtil={
    /**
     * 利用反射,把对象属性值自动填写到页面元素中(页面元素id与对象属性相同) demo: $.autoFill(obj);
     */
    autoFill: function(obj){
        var element = $("body");
        __autoFill(element, obj);
    },
    /**
     * 利用反射,清除页面元素中的值(页面元素id与对象属性相同) demo: $.autoClear(obj);
     */
    autoClear: function(obj){
        var element = $("body");
        __autoClear(element, obj);
    },
    /**
     * 利用反射,把页面元素的值绑定到对象的相关属性中(页面元素id与对象属性相同) demo: $.autoBind(obj);
     */
    autoBind: function(obj){
        var element = $("body");
        __autoBind(element, obj);
    },
    autoEnable: function(obj){
        var element = $("body");
        __autoEnable(element, obj);
    },
    autoDisable: function(obj){
        var element = $("body");
        __autoDisable(element, obj);
    }
}

/**
 * 让Jquery扩展AutoUtil工具-----$.扩展
 */
$.extend(AutoUtil);

/**
 * 让Jquery扩展AutoUtil工具----fn扩展
 */
$.fn.extend({
    /**
     * 利用反射,把对象属性值自动填写到页面元素中(页面元素id与对象属性相同) demo: $("#addDiv").autoFill(obj);
     */
    autoFill: function(obj){
        __autoFill(this, obj);
    },
	autoFillMap: function(obj){
        __autoFillMap(this, obj);
    },
    /**
     * 利用反射,清除页面元素中的值(页面元素id与对象属性相同) demo: $("#addDiv").autoClear(obj);
     */
    autoClear: function(obj){
        __autoClear(this, obj);
    },
    /**
     * 利用反射,把页面元素的值绑定到对象的相关属性中(页面元素id与对象属性相同) demo: $("#addDiv").autoBind(obj);
     */
    autoBind: function(obj){
        __autoBind(this, obj);
    },
    autoEnable: function(obj){
        __autoEnable(this, obj);
    },
    autoDisable: function(obj){
        __autoDisable(this, obj);
    }
});


function __autoFill(element, obj){
    var arr = Reflector.getAttributes(obj);
    for (var i = 0; i < arr.length; i++) {
        var name = arr[i].name.replace(".", "\\.");
        var value = arr[i].value;
		var e_obj = element.find("#" + name);
        if (e_obj.is("input") || e_obj.is("textarea")) {
			if(e_obj.attr("type") == "radio" || e_obj.attr("type") == "checkbox")
			{
				e_obj.each(function(){
					if(this.value == value)
						this.checked = true;
					else
						this.checked = false;
				});
			}
			else
			{
				if(value != "null" && value != null)
					e_obj.val(value);
			}
        }
        else {
			if(e_obj.is("select"))
			{			
				e_obj.find("option").each(function(){
					if($(this).val() == value)
					{
						$(this).attr("selected",true);
					}
				});
			}
			else
			{
				if(value == "") value = "&#160;";
				e_obj.html(value);
			}
        }
    }
}


function __autoFillMap(element, obj){
   
	/*
    var arr = Reflector.getAttributes(obj);
    for (var i = 0; i < arr.length; i++) {
		var name = arr[i].name.replace(".", "\\.");
        var value = arr[i].value;
    */
	var key = obj.keySet();
    for (var i = 0; i < key.length; i++) {
		var name = key[i];
        var value = obj.get(name);

		var e_obj = element.find("#" + name);
		//alert(element.find("#" + name).length);
        if (e_obj.is("input") || e_obj.is("textarea")) {
			//alert(e_obj.attr("name"));
			if(e_obj.attr("type") == "radio" || e_obj.attr("type") == "checkbox")
			{
				e_obj.each(function(){
					//alert(this.value+"===="+value);
					var vs = value.split(",");
					for(var j = 0; j < vs.length; j++){
						if(vs[j]!='' && vs[j]!=null){
						   if(this.value == vs[j]){
								this.checked = true;
								break;
						   }
						   else{
								this.checked = false;
						   }
						}
					}
					
				});
			}
			else
			{
				if(value != "null" && value != null)
					e_obj.val(value);
			}
        }
        else {
			if(e_obj.is("select"))
			{			
				e_obj.find("option").each(function(){
					if($(this).val() == value)
					{
						$(this).attr("selected",true);
					}
				});
			}
			else
			{
				if(value == "") value = "&#160;";
				e_obj.html(value);
			}
        }
    }
}

function __autoClear(element, obj){
    var arr = Reflector.getAttributes(obj);
    for (var i = 0; i < arr.length; i++) {
        var name = arr[i].name.replace(".", "\\.");
        var value = arr[i].value;
		var e_obj = element.find("#" + name);
        if (e_obj.is("input") || e_obj.is("textarea")) {
            e_obj.val("");
        }
        else {
			if(e_obj.is("select"))
			{		
				$("#"+name).selectRepeal();
			}
			else
				e_obj.html("");
        }
    }
}

function __autoBind(element, obj){
    var arr = Reflector.getAttributes(obj);
    for (var i = 0; i < arr.length; i++) {
        var name = arr[i].name.replace(".", "\\.");	
        var value = arr[i].value;
		var e_obj = element.find("#" + name);
        if (e_obj.is("input") || e_obj.is("select") || e_obj.is("textarea")) {	
			if(e_obj.attr("type") == "radio" || e_obj.attr("type") == "checkbox")
			{
				e_obj.each(function(){
					if(this.checked == true)
						obj[arr[i].name] = this.value;
				});


			}else{
				if (e_obj.val() != null)
				{
					obj[arr[i].name] = e_obj.val()+"";
				}
			}
        }
        else {			
            if (e_obj.html()) {
                obj[arr[i].name] = e_obj.html();
            }
        }
    }
}

function __autoEnable(element, obj){
    var arr = Reflector.getAttributes(obj);
    for (var i = 0; i < arr.length; i++) {
        var name = arr[i].name.replace(".", "\\.");
        var value = arr[i].value;
		var e_obj = element.find("#" + name);
        if (e_obj.is("input")) {
            e_obj.removeAttr("readonly");
        }
        else 
            if (e_obj.is("select")) {
                e_obj.removeAttr("disabled");
            }
            else {
            // obj[name]=$("#" + name).html();
            }
    }
}

function __autoDisable(element, obj){
    var arr = Reflector.getAttributes(obj);
    for (var i = 0; i < arr.length; i++) {
        var name = arr[i].name.replace(".", "\\.");
        var value = arr[i].value;
		var e_obj = element.find("#" + name);
        if (e_obj.is("input")) {
            e_obj.attr("readonly", "readonly");
        }
        else 
            if (e_obj.is("select")) {
                e_obj.attr("disabled", true);
            }
            else {
            // obj[name]=$("#" + name).html();
            }
    }
}

/**
 * Javascript反射实现
 *
 * @type
 */
var Reflector = {
    getType: function(obj){
        if (obj == null) // null的类型
		{
			return null;
		}
		else {
			if (obj instanceof Object)
			{
				return obj.constructor;
			}
			else {
				if (obj.tagName != null)
				{
					return obj.tagName;
				}
				else {
					return typeof(obj);
				}
			}
		}
    },
    getAttributes: function(obj){
        var methods = new Array();
        for (idx in obj) {
            methods.push(new Type(obj[idx], Reflector.getType(obj[idx]), idx));
        }
        return methods;
    },
    getAttributeNames: function(obj){
        var methods = new Array();
        for (idx in obj) {
            methods.push(idx);
        }
        return methods;
    }
}

function Type(value, type, name){// 描述类型的类，entity是对象实体，type是对象类型，name是对象名称
    this.value = value;
    this.type = type;
    this.name = name;
}
