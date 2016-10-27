/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

// Google Maps: http://code.google.com/apis/maps/index.html

var ke_multi_obj;
KindEditor.plugin('multiimage', function(K) {
	var self = this, name = 'multiimage', lang = self.lang(name + '.');
	ke_multi_obj = self;
	self.clickToolbar(name, function(options) {
		openSelectMaterialPage('setMultiImgUrl',site_id,'checkbox');
		// search map
	});
});
function setMultiImgUrl(url)
{
	if(url != "" && url != null)
	{
		var tempA = url.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			ke_multi_obj.exec('insertimage',  tempA[i]).hideDialog().focus();
		}
	}
}