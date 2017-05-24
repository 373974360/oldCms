$(document).ready(function () {
    // 百度地图API功能
    function G(id) {
        return document.getElementById(id);
    }
    $("#allmap").css("height","350px");
    $("#allmap").css("width", "500px");
    var coordinate = $("#coordinate").val().split(",");
    var baiduMap = new BMap.Map("allmap");    // 创建Map实例
    if(coordinate!=null && coordinate.length>1){
        var point = new BMap.Point(coordinate[0],coordinate[1]);
        baiduMap.centerAndZoom(point, 18);
        baiduMap.addOverlay(new BMap.Marker(point));//添加标注
    }else{
        baiduMap.centerAndZoom("西安", 11);  // 初始化地图,设置中心点坐标和地图级别
    }
    baiduMap.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放


    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
        {
            "input": "suggestId"
            , "location": baiduMap
        });

    ac.addEventListener("onhighlight", function (e) {  //鼠标放在下拉列表上的事件
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province + _value.city + _value.district + _value.street + _value.business;
        }
        str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.province + _value.city + _value.district + _value.street + _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        G("searchResultPanel").innerHTML = str;
    });

    var myValue;
    ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
        G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;

        setPlace();
    });

    function setPlace() {
        baiduMap.clearOverlays();    //清除地图上所有覆盖物
        function myFun() {
            var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
            $("#coordinate").val(pp.lng+","+pp.lat);
            baiduMap.centerAndZoom(pp, 18);
            baiduMap.addOverlay(new BMap.Marker(pp));    //添加标注
        }
        var local = new BMap.LocalSearch(baiduMap, { //智能搜索
            onSearchComplete: myFun
        });
        local.search(myValue);
    }
});