function AddUrl() {
    var thisurl = '选项名称' + (document.getElementById("select_item").length + 1) + '|选项值';
    var url = prompt('请输入选项名称和值，中间用“|”隔开：', thisurl);
    if (url != null && url != '') { 
	    document.getElementById("select_item").options[document.getElementById("select_item").length] = new Option(url, url); 
	    setSelectDefault();
	}
}
function ModifyUrl() {
    if (document.getElementById("select_item").length == 0) return false;
    var thisurl = document.getElementById("select_item").value;
    if (thisurl == '') { alert('请先选择一个选项，再点修改按钮！'); return false; }
    var url = prompt('请输入选项名称和值，中间用“|”隔开：', thisurl);
    if (url != thisurl && url != null && url != '') { 
	   document.getElementById("select_item").options[document.getElementById("select_item").selectedIndex] = new Option(url, url); 
	   setSelectDefault();
	}
}
function DelUrl() {
    if (document.getElementById("select_item").length == 0) return false;
    var thisurl = document.getElementById("select_item").value;
    if (thisurl == '') { alert('请先选择一个选项，再点删除按钮！'); return false; }
    document.getElementById("select_item").options[document.getElementById("select_item").selectedIndex] = null;
	setSelectDefault();
}



function ChangeHiddenFieldValue() {
    var obj = document.getElementById("select_item_hidden");
    var choiceUrl = document.getElementById("select_item");
    var Default = document.getElementById('default_valueTextSelect').value;
    var bFoundMatchWithDefault = false;

    var value = "";
    if (choiceUrl.length < 1) { 
        parent.alertN("请添加选项！");
        return false;
    }

    for (i = 0; i < choiceUrl.length; i++) {
        if (value != "") {
            value = value + "$$$";
        } 
        value = value + choiceUrl.options[i].value;

        if (Default == choiceUrl.options[i].value.split("|")[1]) {
            bFoundMatchWithDefault = true;
        } 

    }
    obj.value = value;

    if (Default && !bFoundMatchWithDefault) {
        //parent.alertN("“选项”域的默认值必须从指定的选项中选择。请再试一次。");
        //return false;
    }
    else {
        obj.value = value;
        return true;
    }

    return true;
}


function setSelectDefault(){
	
			$("#select0").clearAll();
			$("#select0").addOption("--请选择--","");
			var choiceUrl = document.getElementById("select_item");
			for (i = 0; i < choiceUrl.length; i++) {
				//alert(choiceUrl.options[i].value);
				$("#select0").addOption(choiceUrl.options[i].value, choiceUrl.options[i].value.split("|")[1]);
			}
			
			$("#select1").clearAll();
			var choiceUrl = document.getElementById("select_item");
			for (i = 0; i < choiceUrl.length; i++) {
		        //alert(choiceUrl.options[i].value);
				$("#select1").addOption(choiceUrl.options[i].value,choiceUrl.options[i].value.split("|")[1]);
		    }
	
			$("#select2").clearAll();
			$("#select2").addOption("--请选择--","");
			var choiceUrl = document.getElementById("select_item");
			for (i = 0; i < choiceUrl.length; i++) {
				//alert(choiceUrl.options[i].value);
				$("#select2").addOption(choiceUrl.options[i].value, choiceUrl.options[i].value.split("|")[1]);
			}
			
			$("#select3").clearAll();
			var choiceUrl = document.getElementById("select_item");
			for (i = 0; i < choiceUrl.length; i++) {
		        //alert(choiceUrl.options[i].value);
				$("#select3").addOption(choiceUrl.options[i].value,choiceUrl.options[i].value.split("|")[1]);
		    }
	
}
