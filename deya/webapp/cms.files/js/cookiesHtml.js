//得到url参数
function getUrlParam(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r!=null) return unescape(r[2]); return null; //返回参数值
} 


$(function() {
	//得到浏览的信息id
    var href = window.location.href;
    var info_id = href.substring(href.lastIndexOf("/")+1,href.lastIndexOf("."));
    if(info_id.lastIndexOf("_")<0){
        //alert(info_id);
  	  //$.cookie("info_ids", "foo",{expires:30,path:"/"});//保存30天
  	  //$.cookie("example1", "foo1",{expires: 7,path:"/",domain:"localhost"});
        
		  //得到cookies中的信息id
		  var o_info_id = $.cookie("custom_info_ids");
		  if(o_info_id==null || o_info_id=="undefined"){
			 o_info_id = "";
		  }
		  //alert(o_info_id);
		  var o_info_ids = o_info_id.split(",");

		  //判断该信息id是否已经放进cookies中
		  for(var i=0;i<o_info_ids.length;i++){
              if(o_info_ids[i].indexOf(info_id)>-1){
                   return;
              }
		  }
	 	  
		  var n_info_id = "";
		  //alert(o_info_ids.length-1);
		  if((o_info_ids.length-1)<=8){//记录最近浏览的8条信息id
		      n_info_id = o_info_id + info_id + ",";
		  }else{
		      n_info_id = o_info_id.substring(o_info_id.indexOf(",")+1)+ info_id + ",";
		  }
          //alert(n_info_id);
		  $.cookie("custom_info_ids", n_info_id,{expires:30,path:"/"});
		  
    }
});

