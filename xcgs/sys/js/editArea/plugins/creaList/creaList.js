/**
 * Plugin designed for test prupose. It add a button (that manage an alert) and a select (that allow to insert tags) in the toolbar.
 * This plugin also disable the "f" key in the editarea, and load a CSS and a JS file
 */  
var EditArea_creaList= {
	/**
	 * Get called once this file is loaded (editArea still not initialized)
	 *
	 * @return nothing	 
	 */	 	 	
	init: function(){	
		//	alert("test init: "+ this._someInternalFunction(2, 3)); 初始加载的文件
		editArea.load_css(this.baseURL+"css/creaList.css");
	}
	/**
	 * Returns the HTML code for a specific control string or false if this plugin doesn't have that control.
	 * A control can be a button, select list or any other HTML item to present in the EditArea user interface.
	 * Language variables such as {$lang_somekey} will also be replaced with contents from
	 * the language packs.
	 * 
	 * @param {string} ctrl_name: the name of the control to add	  
	 * @return HTML code for a specific control or false.
	 * @type string	or boolean
	 * 设置按钮类型,根据按钮选项显示功能区域
	 */	
	,get_control_html: function(ctrl_name){
		switch(ctrl_name){
			case "creaLink":
				// Control id, button img, command   名称,图片名,操作方法,对应editArea.execCommand
				return parent.editAreaLoader.get_button_html('creaLink', 'creaLink.png', 'creaLink', false, this.baseURL);break;
			case "creaList":
				// Control id, button img, command   名称,图片名,操作方法,对应editArea.execCommand
				return parent.editAreaLoader.get_button_html('creaList', 'creaList.png', 'creaList', false, this.baseURL);break;
			case "hotList":
				// Control id, button img, command   名称,图片名,操作方法,对应editArea.execCommand
				return parent.editAreaLoader.get_button_html('hotList', 'hotList.png', 'hotList', false, this.baseURL);break;
			case "selFile":
				// Control id, button img, command   名称,图片名,操作方法,对应editArea.execCommand
				return parent.editAreaLoader.get_button_html('selFile', 'selImg.png', 'selFile', false, this.baseURL);break;
			
		}
		return false;
	}
	/**
	 * Get called once EditArea is fully loaded and initialised
	 *	 
	 * @return nothing
	 */	 	 	
	,onload: function(){ 
		
	}
	
	/**
	 * Is called each time the user touch a keyboard key.
	 *	 
	 * @param (event) e: the keydown event
	 * @return true - pass to next handler in chain, false - stop chain execution
	 * @type boolean	 
	 */
	,onkeydown: function(e){
		var str= String.fromCharCode(e.keyCode);
		// desactivate the "f" character
		
		if(str.toLowerCase()=="f"){
			return true;
		}
		return false;
	}
	
	/**
	 * Executes a specific command, this function handles plugin commands.
	 *
	 * @param {string} cmd: the name of the command being executed
	 * @param {unknown} param: the parameter of the command	 
	 * @return true - pass to next handler in chain, false - stop chain execution
	 * @type boolean	
	 */
	,execCommand: function(cmd, param){
		// Handle commands
		switch(cmd){			
			case "creaList":						
				top.OpenModalWindow("列表生成工具","/sys/system/template/list_tools.jsp",650,420);
				return false;
			case "hotList":						
				top.OpenModalWindow("热点信息生成工具","/sys/system/template/hotlist_tools.jsp",650,440);
				return false;
			case "creaLink":						
				top.OpenModalWindow("获取链接地址","/sys/system/template/link_tools.jsp",650,495);
				return false;
			case "selFile":						
				top.OpenModalWindow("选择图片","/sys/system/template/select_file.jsp",650,495);
				return false;
		}
		// Pass to next handler in chain
		return true;
	}
	
	/**
	 * This is just an internal plugin method, prefix all internal methods with a _ character.
	 * The prefix is needed so they doesn't collide with future EditArea callback functions.
	 *
	 * @param {string} a Some arg1.
	 * @param {string} b Some arg2.
	 * @return Some return.
	 * @type unknown
	 */
	,_someInternalFunction : function(a, b) {
		return a+b;
	}
};

// Adds the plugin class to the list of available EditArea plugins
editArea.add_plugin("creaList", EditArea_creaList);
