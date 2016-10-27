/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/


KindEditor.plugin('insertfile', function(K) {
	var self = this, name = 'insertfile', lang = self.lang(name + '.');
	
	self.clickToolbar(name, function(options) {
		var html = [
			'<div class="ke-map" style="width:480px;height:180px;"></div>',
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
					var title = win.getInputVal("title");
					if (url == 'http://' || K.invalidUrl(url)) {
						alert(self.lang('invalidUrl'));
						return;
					}
					if (K.trim(title) === '') {
						title = url;
					}

					var a = self.plugin.getSelectedLink();
					if(a)
					{
						a.html(title);
						a.attr('data-ke-src',url);
						self.hideDialog().focus();
					}else
					{
						var html = '<a href="' + url + '" data-ke-src="' + url + '" target="_blank">' + title + '</a>';	
						self.insertHtml(html).hideDialog().focus();
					}
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
		var iframe = K('<iframe id="imgFrame" name="imgFrame" frameborder="0" src="' + self.pluginsPath + 'insertfile/insertfile.html" style="width:480px;height:160px;margin-top:12px;margin-left:12px"></iframe>');
		function ready() {
			win = iframe[0].contentWindow;
			doc = K.iframeDoc(iframe);
			var a = self.plugin.getSelectedLink();
			if (a) {
				win.setInputVal("url",a.attr('data-ke-src'));
				win.setInputVal("title",a.text());
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
