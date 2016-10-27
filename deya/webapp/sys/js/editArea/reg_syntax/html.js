/*
* last update: 2006-08-24
*/

editAreaLoader.load_syntax["html"] = {
	'DISPLAY_NAME' : 'HTML'
	,'COMMENT_SINGLE' : {}
	,'COMMENT_MULTI' : {'<!--' : '-->'}
	,'QUOTEMARKS' : {1: "'", 2: '"'}
	,'KEYWORD_CASE_SENSITIVE' : false
	,'KEYWORDS' : {
		'statements' : [
			'as', 'break', 'case', 'catch', 'continue', 'decodeURI', 'delete', 'do',
			'else', 'encodeURI', 'eval', 'finally', 'for', 'if', 'in', 'is', 'item',
			'instanceof', 'return', 'switch', 'this', 'throw', 'try', 'typeof', 'void',
			'while', 'write', 'with','foreach','set','end'
		]
 		,'keywords' : [
			'class', 'const', 'default', 'debugger', 'export', 'extends', 'false',
			'function', 'import', 'namespace', 'new', 'null', 'package', 'private',
			'protected', 'public', 'super', 'true', 'use', 'var', 'window', 'document',		
			// the list below must be sorted and checked (if it is a keywords or a function and if it is not present twice
			'Link ', 'outerHeight ', 'Anchor', 'FileUpload', 
			'location', 'outerWidth', 'Select', 'Area', 'find', 'Location', 'Packages', 'self', 
			'arguments', 'locationbar', 'pageXoffset', 'Form', 
			'Math', 'pageYoffset', 'setTimeout', 'assign', 'Frame', 'menubar', 'parent', 'status', 
			'blur', 'frames', 'MimeType', 'parseFloat', 'statusbar', 'Boolean', 'Function', 'moveBy', 
			'parseInt', 'stop', 'Button', 'getClass', 'moveTo', 'Password', 'String', 'callee', 'Hidden', 
			'name', 'personalbar', 'Submit', 'caller', 'history', 'NaN', 'Plugin', 'sun', 'captureEvents', 
			'History', 'navigate', 'print', 'taint', 'Checkbox', 'home', 'navigator', 'prompt', 'Text', 
			'Image', 'Navigator', 'prototype', 'Textarea', 'clearTimeout', 'Infinity', 
			'netscape', 'Radio', 'toolbar', 'close', 'innerHeight', 'Number', 'ref', 'top', 'closed', 
			'innerWidth', 'Object', 'RegExp', 'toString', 'confirm', 'isFinite', 'onBlur', 'releaseEvents', 
			'unescape', 'constructor', 'isNan', 'onError', 'Reset', 'untaint', 'Date', 'java', 'onFocus', 
			'resizeBy', 'unwatch', 'defaultStatus', 'JavaArray', 'onLoad', 'resizeTo', 'valueOf', 'document', 
			'JavaClass', 'onUnload', 'routeEvent', 'watch', 'Document', 'JavaObject', 'open', 'scroll', 'window', 
			'Element', 'JavaPackage', 'opener', 'scrollbars', 'Window', 'escape', 'length', 'Option', 'scrollBy','target'
		]
    	,'functions' : [
			// common functions for Window object
			'alert', 'Array', 'back', 'blur', 'clearInterval', 'close', 'confirm', 'eval ', 'focus', 'forward', 'home',
			'name', 'navigate', 'onblur', 'onerror', 'onfocus', 'onload', 'onmove',
			'onresize', 'onunload', 'open', 'print', 'prompt', 'scroll', 'scrollTo', 'setInterval', 'status',
			'stop' 
		]
		,'attributes' : [
			'aqua', 'azimuth', 'background-attachment', 'background-color',
			'background-image', 'background-position', 'background-repeat',
			'background', 'border-bottom-color', 'border-bottom-style',
			'border-bottom-width', 'border-left-color', 'border-left-style',
			'border-left-width', 'border-right', 'border-right-color',
			'border-right-style', 'border-right-width', 'border-top-color',
			'border-top-style', 'border-top-width','border-bottom', 'border-collapse',
			'border-left', 'border-width', 'border-color', 'border-spacing',
			'border-style', 'border-top', 'border',  'caption-side',
			'clear', 'clip', 'color', 'content', 'counter-increment', 'counter-reset',
			'cue-after', 'cue-before', 'cue', 'cursor', 'direction', 'display',
			'elevation', 'empty-cells', 'float', 'font-family', 'font-size',
			'font-size-adjust', 'font-stretch', 'font-style', 'font-variant',
			'font-weight', 'font', 'height', 'letter-spacing', 'line-height',
			'list-style', 'list-style-image', 'list-style-position', 'list-style-type',
			'margin-bottom', 'margin-left', 'margin-right', 'margin-top', 'margin',
			'marker-offset', 'marks', 'max-height', 'max-width', 'min-height',
			'min-width', 'opacity', 'orphans', 'outline', 'outline-color', 'outline-style',
			'outline-width', 'overflow', 'padding-bottom', 'padding-left',
			'padding-right', 'padding-top', 'padding', 'page', 'page-break-after',
			'page-break-before', 'page-break-inside', 'pause-after', 'pause-before',
			'pause', 'pitch', 'pitch-range',  'play-during', 'position', 'quotes',
			'richness', 'right', 'size', 'speak-header', 'speak-numeral', 'speak-punctuation',
			'speak', 'speech-rate', 'stress', 'table-layout', 'text-align', 'text-decoration',
			'text-indent', 'text-shadow', 'text-transform', 'top', 'unicode-bidi',
			'vertical-align', 'visibility', 'voice-family', 'volume', 'white-space', 'widows',
			'width', 'word-spacing', 'z-index', 'bottom', 'left'
		]
		,'values' : [
			'above', 'absolute', 'always', 'armenian', 'aural', 'auto', 'avoid',
			'baseline', 'behind', 'below', 'bidi-override', 'black', 'blue', 'blink', 'block', 'bold', 'bolder', 'both',
			'capitalize', 'center-left', 'center-right', 'center', 'circle', 'cjk-ideographic', 
            'close-quote', 'collapse', 'condensed', 'continuous', 'crop', 'crosshair', 'cross', 'cursive',
			'dashed', 'decimal-leading-zero', 'decimal', 'default', 'digits', 'disc', 'dotted', 'double',
			'e-resize', 'embed', 'extra-condensed', 'extra-expanded', 'expanded',
			'fantasy', 'far-left', 'far-right', 'faster', 'fast', 'fixed', 'fuchsia',
			'georgian', 'gray', 'green', 'groove', 'hebrew', 'help', 'hidden', 'hide', 'higher',
			'high', 'hiragana-iroha', 'hiragana', 'icon', 'inherit', 'inline-table', 'inline',
			'inset', 'inside', 'invert', 'italic', 'justify', 'katakana-iroha', 'katakana',
			'landscape', 'larger', 'large', 'left-side', 'leftwards', 'level', 'lighter', 'lime', 'line-through', 'list-item', 'loud', 'lower-alpha', 'lower-greek', 'lower-roman', 'lowercase', 'ltr', 'lower', 'low',
			'maroon', 'medium', 'message-box', 'middle', 'mix', 'monospace',
			'n-resize', 'narrower', 'navy', 'ne-resize', 'no-close-quote', 'no-open-quote', 'no-repeat', 'none', 'normal', 'nowrap', 'nw-resize',
			'oblique', 'olive', 'once', 'open-quote', 'outset', 'outside', 'overline',
			'pointer', 'portrait', 'purple', 'px',
			'red', 'relative', 'repeat-x', 'repeat-y', 'repeat', 'rgb', 'ridge', 'right-side', 'rightwards',
			's-resize', 'sans-serif', 'scroll', 'se-resize', 'semi-condensed', 'semi-expanded', 'separate', 'serif', 'show', 'silent', 'silver', 'slow', 'slower', 'small-caps', 'small-caption', 'smaller', 'soft', 'solid', 'spell-out', 'square',
			'static', 'status-bar', 'super', 'sw-resize',
			'table-caption', 'table-cell', 'table-column', 'table-column-group', 'table-footer-group', 'table-header-group', 'table-row', 'table-row-group', 'teal', 'text', 'text-bottom', 'text-top', 'thick', 'thin', 'transparent',
			'ultra-condensed', 'ultra-expanded', 'underline', 'upper-alpha', 'upper-latin', 'upper-roman', 'uppercase', 'url',
			'visible',
			'w-resize', 'wait', 'white', 'wider',
			'x-fast', 'x-high', 'x-large', 'x-loud', 'x-low', 'x-small', 'x-soft', 'xx-large', 'xx-small',
			'yellow', 'yes'
		]
		,'specials' : [
			'important'
		]
	}
	,'OPERATORS' :[
		'+', '-', '/', '*', '=', '<', '>', '%', '!',':', ';', '.', '#','$'
	]
	,'DELIMITERS' :[
		'(', ')', '[', ']', '{', '}'
	]
	,'REGEXPS' : {
		'doctype' : {
			'search' : '()(<!DOCTYPE[^>]*>)()'
			,'class' : 'doctype'
			,'modifiers' : ''
			,'execute' : 'before' // before or after
		}
		,'tags' : {
			'search' : '(<)(/?[a-z][^ \r\n\t>]*)([^>]*>)'
			,'class' : 'tags'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
		,'attributes' : {
			'search' : '( |\n|\r|\t)([^ \r\n\t=]+)(=)'
			,'class' : 'attributes'
			,'modifiers' : 'g'
			,'execute' : 'before' // before or after
		},'kiss' : {
			'search' : '($)(^\s)'
			,'class' : 'kiss'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
	}
	,'STYLES' : {
		'COMMENTS': 'color: #AAAAAA;'
		,'QUOTESMARKS': 'color: #6381F8;'
		,'KEYWORDS' : {
			'statements' : 'color: #60CA00;'
			,'keywords' : 'color: #48BDDF;'
			,'functions' : 'color: #2B60FF;'
			,'attributes' : 'color: #48BDDF;'
			,'values' : 'color: #2B60FF;'
			,'specials' : 'color: #FF0000;'
		}
		,'OPERATORS' : 'color: #E775F0;'
		,'DELIMITERS' : ''
		,'REGEXPS' : {
			'attributes': 'color: #B1AC41;'
			,'tags': 'color: #E62253;'
			,'doctype': 'color: #8DCFB5;'
			,'kiss': 'color: red;'
		}	
	}
	,'AUTO_COMPLETION' :  {
		"default": {	// the name of this definition group. It's posisble to have different rules inside the same definition file
			"REGEXP": { "before_word": "[^a-zA-Z0-9_]|^"	// \\s|\\.|
						,"possible_words_letters": "[a-zA-Z0-9_]+"
						,"letter_after_word_must_match": "[^a-zA-Z0-9_]|$"
						,"prefix_separator": "\\."
					}
			,"CASE_SENSITIVE": true
			,"MAX_TEXT_LENGTH": 100		// the maximum length of the text being analyzed before the cursor position
			,"KEYWORDS": {
				'': [	// the prefix of thoses items
						/**
						 * 0 : the keyword the user is typing
						 * 1 : (optionnal) the string inserted in code ("{@}" being the new position of the cursor, "§" beeing the equivalent to the value the typed string indicated if the previous )
						 * 		If empty the keyword will be displayed
						 * 2 : (optionnal) the text that appear in the suggestion box (if empty, the string to insert will be displayed)
						 */
						 ['Array', '§()', '']
			    		,['alert', '§({@})', 'alert(String message)']
			    		,['document']
			    		,['window']
			    	]
		    	,'window' : [
			    		 ['location']
			    		,['document']
			    		,['scrollTo', 'scrollTo({@})', 'scrollTo(Int x,Int y)']
					]
		    	,'location' : [
			    		 ['href']
					]
			}
		}
	}
};
