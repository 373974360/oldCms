// String对象的几个原型方法：

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
};

String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
};

// 取字符串第一个字符的 Unicode 编码
String.prototype.ascii = function() {
	return this.charCodeAt(0);
};

String.prototype.endsWith = function(character) {
	return (character == this.charAt(this.length - 1));
};

String.prototype.startsWith = function(character) {
	return (character == this.charAt(0));
};
//返回字符串的真实长度
String.prototype.LenB = function(){
	return this.replace(/[^\x00-\xff]/g,"@@").length;
}

//返回字符串的真实长度,用于数据库　utf-8中汉字占3个字符
String.prototype.LenDB = function(){
	return this.replace(/[^\x00-\xff]/g,"@@@").length;
}

// Array对象的几个原型方法
Array.prototype.contains = function(p_var) {
	return (this.indexOf(p_var) != -1);
}

Array.prototype.max = function() {
	for (var i = 1, max = this[0]; i < this.length; i++) {
		if (max < this[i]) {
			max = this[i];
		}
	}
	return max;
};

Array.prototype.min = function() {
	for (var i = 1, min = this[0]; i < this.length; i++) {
		if (min > this[i]) {
			min = this[i];
		}
	}
	return min;
};

Array.prototype.size = function() {
	return this.length;
};
Array.prototype.add = function(o) {
	this.push(o);
}
Array.prototype.remove = function(o) {
	var i = this.indexOf(o);
	if (i > -1)
		this.splice(i, 1);
	return (i > -1)
}
Array.prototype.removeAt = function(p_iIndex) {
	return this.splice(p_iIndex, 1);
}
Array.prototype.addAll = function(otherList) {
	for (i = 0; i < otherList.length; i++) {
		this.push(otherList[i]);
	}
}
Array.prototype.subList = function(fromIndex, toIndex) {
	var subList = [];
	for (var i = fromIndex; i < toIndex; i++) {
		subList.push(this[i]);
	}
	return subList;
}
Array.prototype.isEmpty = function() {
	return this.size() == 0;
}
Array.prototype.clear = function() {
	var iLength = this.length;
	for (var i = 0; i < iLength; i++) {
		this.shift();
	}
}

/*******************************************************************************
 * String扩展函数
 ******************************************************************************/
/* 返回字符串字节数 */
String.prototype.getBytesLength = function() {
	return this.replace(/[^\x00-\xff]/g, "**").length;
}
/* 字符串首字母大写 */
String.prototype.capitalize = function() {
	return this.charAt(0).toUpperCase() + this.substr(1);
}
String.prototype.lTrim = function() {
	return this.replace(/^\s*/, "");
}
String.prototype.rTrim = function() {
	return this.replace(/\s*$/, "");
}
String.prototype.trim = function() {
	return this.rTrim().lTrim();
}
String.prototype.endsWith = function(sEnd) {
	return (this.substr(this.length - sEnd.length) == sEnd);
}
String.prototype.startsWith = function(sStart) {
	return (this.substr(0, sStart.length) == sStart);
}
String.prototype.format = function() {
	var s = this;
	for (var i = 0; i < arguments.length; i++) {
		s = s.replace("{" + (i) + "}", arguments[i]);
	}
	return (s);
}
String.prototype.text2Html = function() {
	var s = this;	
	return (s.replace(/\t|\r|\n/ig,"<br>").replace("","&nbsp;"));
}
String.prototype.removeSpaces = function() {
	return this.replace(/ /gi, '');
}
String.prototype.removeExtraSpaces = function() {
	return (this.replace(String.prototype.removeExtraSpaces.re, " "));
}
String.prototype.removeExtraSpaces.re = new RegExp("\\s+", "g");
String.prototype.removeSpaceDelimitedString = function(r) {
	var s = " " + this.trim() + " ";
	return s.replace(" " + r, "").rTrim();
}
String.prototype.isEmpty = function() {
	return this.length == 0;
};
String.prototype.validateURL = function() {
	var urlRegX = /[^a-zA-Z0-9-]/g;
	return sURL.match(urlRegX, "");
}
String.prototype.isEmail = function() {
	var emailReg = /^\w+([-.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	return emailReg.test(this);
}
String.prototype.isAlphaNumeric = function() {
	var alphaReg = /[^a-zA-Z0-9]/g;
	return !alphaReg.test(this);
}
String.prototype.encodeURI = function() {
	var returnString;
	returnString = escape(this)
	returnString = returnString.replace(/\+/g, "%2B");
	return returnString;
}
String.prototype.decodeURI = function() {
	return unescape(this);
}

// --------------------------------------Array-----------------------------------------

// 排除数组重复项
Array.prototype.unique = function() {
	var a = {};
	for (var i = 0; i < this.length; i++) {
		if (typeof a[this[i]] == "undefined")
			a[this[i]] = 1;
	}
	this.length = 0;
	for (var i in a)
		this[this.length] = i;
	return this;
};

/*******************************************************************************
 * Date
 ******************************************************************************/

/* 格式化日期yyyy-MM-dd-mm-ss-q-S */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

/* 是否周末 */
Date.prototype.isWeekend = function() {
	return (this.getDay() % 6) ? false : true;
};

/* 该月总共天数 */
Date.prototype.getMDate = function() {
	return (new Date(this.getFullYear(), this.getMonth() + 1, 0).getDate())
};
//判断时间大小
function judgeDateTime(start_date,end_date)
{
	var reg_ymd=/^([\d]{4})-([\d]{1,2})-([\d]{1,2})\s([\d]{1,2}):([\d]{1,2})$/;
	var arr_dt1,arr_dt2,dt1,dt2;

	arr_dt1=start_date.match(reg_ymd);
	arr_dt2=end_date.match(reg_ymd);

	dt1=new Date(arr_dt1[1],arr_dt1[2],arr_dt1[3],arr_dt1[4],arr_dt1[5]);
	dt2=new Date(arr_dt2[1],arr_dt2[2],arr_dt2[3],arr_dt2[4],arr_dt2[5]);

	return dt2 > dt1;
}

//判断日期大小
function judgeDate(start_date,end_date)
{
	var reg_ymd=/^([\d]{4})-([\d]{1,2})-([\d]{1,2})$/;
	var arr_dt1,arr_dt2,dt1,dt2;

	arr_dt1=start_date.match(reg_ymd);
	arr_dt2=end_date.match(reg_ymd);

	dt1=new Date(arr_dt1[1],arr_dt1[2],arr_dt1[3]);
	dt2=new Date(arr_dt2[1],arr_dt2[2],arr_dt2[3]);

	return dt2 > dt1;
}

function getCurrentDate()
{
	var date = new Date();
	var y = date.getYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m < 10) m = "0"+m;
	if(d < 10) d = "0"+d;

	return y+"-"+m+"-"+d;
}

/*******************************************************************************
 * Math
 ******************************************************************************/

/*******************************************************************************
 * Number
 ******************************************************************************/

/* 返回数值的长度 */
Number.prototype.length = function() {
	return this.toString().length;
};

/* 将整数形式RGB颜色值转换为HEX形式 */
Number.prototype.toColorPart = function() {
	var digits = this.toString(16);

	if (this < 16) {
		return '0' + digits;
	}

	return digits;
};

/* 返回000888形式 */
Number.prototype.format = function(n) {
	if (this.length() >= n) {
		return this;
	}

	return ((new Array(n).join("0") + (this | 0)).slice(-n));
};

