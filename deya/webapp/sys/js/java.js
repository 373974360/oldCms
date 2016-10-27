/**
 * 本文件是用于对象在Java与JavaScript透明转译的支持工具,主要是为支持JSON-RPC,
 * 以解决Java和JavaScript的阻抗不匹配
 * @author Chunson
 */

/**
 * Json中的表示java Class的属性名,
 * 在JSON-RPC中该属性名为'javaClass',在Sojo中为'class'
 * 在不同框架中使用本文件,要切换该属性名,否则在java--json的序列化和反序列化时会出错
 */
var CLASS_ATTR_NAME = "javaClass";

function __toString(){
	return toJSON(this);
}
function __alert(){
	alert(this.toString());
}

/**
 * javaBean在javascript中的实现,
 * 该对象可以直接传给JSON-RPC,JSON-RPC会将其转译为全名为fullClassName的类的对象,
 * 只有在跟java交互时候使用,否则使用{}就足够了
 * fromServer模式只有在配置了jsonrpc.jsonUtil服务端对象时才有效
 * @param {} fullClassName	javaBean的类全名
 */
function Bean(fullClassName,isFromServer) {
	var fullClassName = fullClassName || 'java.lang.Object';
	var isFromServer = isFromServer || false;
	if(isFromServer){//from server
		var obj=jsonrpc.JSONUtil.getBean(fullClassName);
		BeanUtil.copyAttrs(obj,this);
		//alert(toJSON(this));
	}
	this[CLASS_ATTR_NAME] = fullClassName;
	this.getClass=function(){
		return this[CLASS_ATTR_NAME];
	}
	this.toString=__toString;
	this.alert=__alert;
}

/**
 * java的Map集合在javascript中的实现,函数名与意义均与Java中相同,
 * 该对象可以直接传给JSON-RPC,JSON-RPC会将其转译为java的Map对象,
 * 只有在跟java交互时候使用,否则使用{}就足够了
 */
function Map() {
	this.map = {};
	this[CLASS_ATTR_NAME] = "java.util.HashMap";
	this.get = function(key) {
		return this.map[key];
	}
	this.put = function(key, value) {
		this.map[key] = value;
		return this;
	}
	this.size = function() {
		var size = 0;
		for (idx in this.map) {
			size++;
		}
		return size;
	}
	this.clear = function() {
		this.map = {};
		return this;
	}
	this.remove = function(key) {
		delete this.map[key];
		return this;
	}
	this.putAll = function(otherMap) {
		for (key in otherMap.map) {
			if (otherMap.map[key]) {
				this.map[key] = otherMap.map[key];
			}
		}
		return this;
	}
	this.keySet = function() {
		var keys=[];
		for (key in this.map) {
			keys.push(key);
		}
		return keys;
	}
	this.values = function() {
		var valueSet=[];
		for (key in this.map) {
			valueSet.push(this.map[key]);
		}
		return valueSet;
	}
	this.containsKey = function(key){
		for (key1 in this.map) {
			if(key===key1){
				return true;
			}
		}	
		return false;
	}
	this.toString=__toString;
	this.alert=__alert;
}

/**
 * 转换服务器端得到的Map对象为JS的Map对象
 * 转换后在JS端就可以调用put()，get()等方法了
 * 转了以后还可以直接交给服务端，到服务端后是java的Map
 * @param {Object} map
 */
Map.toJSMap=function(map){
	var jsMap=new Map();
	jsMap.putAll(map);
	return jsMap;
}


/**
 * java的List集合在javascript中的实现,,函数名与意义均与Java中相同,
 * 该对象可以直接传给JSON-RPC,JSON-RPC会将其转译为java的List对象,
 * 只有在跟java交互时候使用,否则使用[]就足够了
 */
function List() {
	this.list = [];
	this[CLASS_ATTR_NAME] = "java.util.ArrayList";
	this.add = function(value) {
		this.list.push(value);
		return this;
	}
	this.get = function(index) {
		return this.list[index];
	}
	this.set=function(index,obj) {
		this.list[index]=obj;
		return this;
	}
	this.size = function() {
		return this.list.length;
	}
	this.clear = function() {
		this.list = [];
		return this;
	}
	this.remove = function(value) {
		for (i=0;i<this.list.length;i++) {
			if(this.list[i]===value){
				delete this.list[i];
			}
		}
		this.list=BeanUtil.getArrayCopy(this.list);//去除null项
		return this;
	}
	this.addAll = function(otherList) {
		if(!otherList.list){
			if(otherList instanceof Array){
				for (i=0;i<otherList.length;i++) {
				this.list.push(otherList[i]);
				}				
			}
			return this;
		}
		for (i=0;i<otherList.list.length;i++) {
			this.list.push(otherList.list[i]);
		}
		return this;
	}
	this.subList=function(fromIndex, toIndex){
		var subList=new List();
		for (var i=fromIndex;i<toIndex;i++) {
			subList.list.push(this.list[i]);
		}		
		return subList;
	}
	this.isEmpty=function(){
		return this.size()==0;
	}
	this.toString=__toString;
	this.alert=__alert;
}
/**
 * 转换服务器端得到的List对象为JS的List对象
 * 转换后在JS端就可以调用get()，size()等方法了
 * 转了以后还可以直接交给服务端，到服务端后是java的List
 * @param {Object} list
 */
List.toJSList=function(list){
	var jsList=new List();
	jsList.addAll(list);
	return jsList;
}

/**
 * 用于操作Bean的一组静态函数
 */
function BeanUtil() {
}
/**
 * 拷贝一个对象的全部属性给另一个对象,用于对象复制和属性拷贝
 * @param {} obj1	源对象
 * @param {} obj2	目标对象
 */
BeanUtil.copyAttrs = function(obj1, obj2) {
	for (attr in obj1) {
		//if (obj1[attr]) {
			obj2[attr] = obj1[attr];
		//}
	}
}
/**
 * 得到对象的一个拷贝
 * @param {} obj	源对象
 * @return {}	拷贝结果对象
 */

BeanUtil.getCopy = function(obj) {
	var copy={};
	for (attr in obj) {
		//if (obj[attr]) {
			copy[attr] = obj[attr];
		//}
	}
	return copy;
}

/**
 * 拷贝列表
 * @param {} array	源列表
 * @return {}	拷贝结果,拷贝后会去除null项
 */
BeanUtil.getArrayCopy = function(array) {
	var copy=[];
	for (var i=0;array&&i<array.length;i++) {
		if (array[i]) {
			copy.push(array[i]);
		}
	}
	return copy;
}

/**
 * 把Bean对象转换成Map对象
 * @param {} obj
 * @return {}
 */
BeanUtil.beanToMap = function(obj) {
	var map=new Map();
	for (attr in obj) {
		//if (obj[attr]) {
			map.map[attr] = obj[attr];
		//}
	}
	return map;
}

/**
 * 把Map对象转换成Bean对象
 * @param {} obj
 * @return {}
 */
BeanUtil.mapToBean = function(map,fullClassName) {
	var bean=new Bean(fullClassName);
	for (attr in map.map) {
		//if (map.map[attr]) {
			bean[attr]=map.map[attr];
		//}
	}
	return bean;
}
