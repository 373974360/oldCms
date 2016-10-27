/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

KindEditor.plugin('image', function(K) {
	var self = this, name = 'image', lang = self.lang(name + '.');
	
	self.clickToolbar(name, function(options) {
		var html = [
			'<div class="ke-map" style="width:558px;height:280px;"></div>',
			'</div>'].join('');
		var dialog = self.createDialog({
			name : name,
			width : 600,
			title : self.lang(name),
			body : html,
			yesBtn : {
				name : self.lang('yes'),
				click : function(e) {
					var url = win.getInputVal("url");
					var width = win.getInputVal("imgWidth");
					var height = win.getInputVal("imgHeight");
					var title = win.getInputVal("imgTitle");
					var align = win.getInputVal("align");
					var border = 0;
					self.exec('insertimage',  url, title, width, height, border, align).hideDialog().focus();
				}
			},
			beforeRemove : function() {
				
				if (doc) {
					doc.write('');
				}
				iframe.remove();
			}
		});
		var div = dialog.div,
			addressBox = K('[name="address"]', div),
			win, doc;
		var iframe = K('<iframe id="imgFrame" name="imgFrame" frameborder="0" src="' + self.pluginsPath + 'image/image.html" style="width:558px;height:260px;margin-top:12px;margin-left:12px"></iframe>');
		function ready() {
			win = iframe[0].contentWindow;
			doc = K.iframeDoc(iframe);

			var imgObj = self.plugin.getSelectedImage();
			if(imgObj != null)
			{
				var	imageUrl = imgObj.attr('data-ke-src');
				var imageWidth = imgObj.width();
				var imageHeight = imgObj.height();
				var imageTitle = imgObj.attr('title');
				var imageAlign = imgObj.attr('align');
				win.setInputVal("url",imageUrl);
				win.setInputVal("imgWidth",imageWidth);
				win.setInputVal("imgHeight",imageHeight);
				win.setInputVal("imgTitle",imageTitle);
				win.setInputVal("align",imageAlign);
			}
		}
		iframe.bind('load', function() {
			iframe.unbind('load');
			if (K.IE) {
				ready();
			} else {
				setTimeout(ready, 0);
			}
		});
		K('.ke-map', div).replaceWith(iframe);
		// search map
		
	});
});
