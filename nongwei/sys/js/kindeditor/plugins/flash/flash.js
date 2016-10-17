/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/


KindEditor.plugin('flash', function(K) {
	var self = this, name = 'flash', lang = self.lang(name + '.');
	
	self.clickToolbar(name, function(options) {
		var html = [
			'<div class="ke-map" style="width:480px;height:230px;"></div>',
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
					var width = win.getInputVal("width");
					var height = win.getInputVal("height");
					var html = K.mediaImg(self.themesPath + 'common/blank.gif', {
							src : url,
							type : K.mediaType('.swf'),
							width : width,
							height : height,
							quality : 'high'
						});
					self.insertHtml(html).hideDialog().focus();
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
		var iframe = K('<iframe id="imgFrame" name="imgFrame" frameborder="0" src="' + self.pluginsPath + 'flash/flash.html" style="width:480px;height:210px;margin-top:12px;margin-left:12px"></iframe>');
		function ready() {
			win = iframe[0].contentWindow;
			doc = K.iframeDoc(iframe);
			var img = self.plugin.getSelectedFlash();
			if(img != null)
			{
				var attrs = K.mediaAttrs(img.attr('data-ke-tag'));
				win.setInputVal("url",attrs.src);
				win.setInputVal("width",img.css('width').replace("px",""));
				win.setInputVal("height",img.css('height').replace("px",""));
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
