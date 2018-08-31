(function ($) {




    jQuery.fn.imageMaps = function (setting) {
        var args = Array.prototype.slice.call(arguments);
        var $container = this;
        var methods = {
            addArea:function (obj) {
                var _position_container = this.find('.position-container');
                var index = _position_container.find('.map-position').size() + 1;
                var p = $('<div ref="' + index + '" class="map-position" style="left:10px;top:10px;width:90px;height:30px;"><div class="map-position-bg"></div><a class="link-number-text" href="'+obj.href+'">'+obj.title+'</a><span class="edit">改</span><span class="delete">X</span><span class="resize"></span></div>');
                p.find('a.link-number-text').data({
                    id:obj.id
                });
                _position_container.append(p);
                bind_map_event(this);
                define_css(this);
            }
        };
        if (args.length == 2) {
            // 调方法
            var method = args[0];
            var params = args[1];
            if(methods[method]){
                methods[method].call($container,params);
            }
        } else {
            //初始化
            if ($container.length == 0) return false;
            var areas = setting.areas || [];
            var container = $container; //此处以前是循环，现在改为只处理单个图片
            var $images = container.find('img[ref=imageMaps]');
            //初始化
            var inited = 0;
            //给图片加了一个边框，并防止在
            $images.wrap('<div class="image-maps-container" style="position:relative;"></div>').css('border', '1px solid #ccc');

            //加载图片完成后设置 img的宽高为图片的真实尺寸
            $images.on('load', function () {
                var im = document.createElement('img');
                im.src = this.src;
                //设置图片的真实高度和宽度给图片
                this.width = im.width;
                this.height = im.height;
                if (inited == 0) {
                    init();
                } else {
                    //图片发生变化，重新设置 position 的宽度和高度
                    $images.parent().find('.position-container').css({
                        width: im.width,
                        height: im.height
                    });
                }
            });


            /**
             * 初始化图片转换为热点编辑区域
             */
            function init() {
                inited = 1;
                $images.each(function () {
                    var _img_container = $images.parent();
                    //图片同级添加一个链接容器和一个定位容器
                    _img_container.append('<div class="link-container"></div>').append('<div class="position-container" style="position:absolute"></div>');

                    var _img_offset = $images.offset();
                    var _img_container_offset = _img_container.offset();
                    _img_container.find('.position-container').css({
                        top: _img_offset.top - _img_container_offset.top,
                        left: _img_offset.left - _img_container_offset.left,
                        width: $images.width(),
                        height: $images.height(),
                        border: '1px solid transparent'
                    }).dblclick(function () { //双击等于是要修改标题和链接
                        var newImg = prompt("请输入新的图片地址：");
                        if (newImg != null) {
                            $images.attr('src', newImg);
                        }
                    });
                    var map_name = $images.attr('usemap').replace('#', '');
                    if (map_name != '') {
                        // var index = 1;
                        // var _link_container = _img_container.find('.link-container');
                        // _link_container.append(
                        //     '<div class="button-container">' +
                        //     '<input class="add" type="button" value="添加热点" />' +
                        //     '</div>'
                        // );
                        var _position_container = _img_container.find('.position-container');
                        //如果给了 name 属性则参数名使用数组形式的 name
                        // var image_param = $(this).attr('name') == '' ? '' : '[' + $(this).attr('name') + ']';

                        //根据现有的热点创建 input 输入框
                        for (var i = 0; i < areas.length; i++) {
                            var index = i + 1;
                            var obj = areas[i];
                            _position_container.append('<div ref="' + index + '" class="map-position" style="left:' + obj.x + 'px;top:' + obj.y + 'px;width:' + (obj.w) + 'px;height:' + (obj.h) + 'px;"><div class="map-position-bg"></div><a class="link-number-text" data-id="'+obj.id+'" href="' + obj.href + '">' + obj.t + '</a><span class="edit">改</span><span class="delete">X</span><span class="resize"></span></div>');
                        }
                    }
                });


                //绑定点击【添加热点】 事件
                // $('input.add', container).click(function () {
                //     var container = $(this).closest('.imgMap');
                //     var _position_container = container.find('.position-container');
                //     var index = _position_container.find('.map-position').size() + 1;
                //     _position_container.append('<div ref="' + index + '" class="map-position" style="left:10px;top:10px;width:90px;height:30px;"><div class="map-position-bg"></div><a class="link-number-text">热点 ' + index + '</a><span class="edit">改</span><span class="delete">X</span><span class="resize"></span></div>');
                //     bind_map_event();
                //     define_css();
                // });


                bind_map_event();
                define_css();
            }

        }

        //绑定map事件
        function bind_map_event(container) {
             container = container||$container;
            $('.position-container .map-position .map-position-bg',container).each(function () {
                var map_position_bg = $(this);
                var container = $(this).parent().parent(); //.position-container
                map_position_bg.unbind('mousedown').mousedown(function (event) {
                    map_position_bg.data('mousedown', true);
                    map_position_bg.data('pageX', event.pageX);
                    map_position_bg.data('pageY', event.pageY);
                    map_position_bg.css('cursor', 'move');
                    return false;
                }).unbind('mouseup').mouseup(function (event) {
                    map_position_bg.data('mousedown', false);
                    map_position_bg.css('cursor', 'default');
                    return false;
                }).unbind('dblclick').dblclick(function (event) {
                    $(this).siblings('.edit').click();
                    return false;
                });
                container.mousemove(function (event) {
                    if (!map_position_bg.data('mousedown')) return false;
                    var dx = event.pageX - map_position_bg.data('pageX');
                    var dy = event.pageY - map_position_bg.data('pageY');
                    // console.info("pageX:" + event.pageX + " pageY:" + event.pageY
                    //     + " px:" + map_position_bg.data('pageX')
                    //     + " py:" + map_position_bg.data('pageY')
                    //     + " dx:" + dx + " dy:" + dy);
                    if ((dx == 0) && (dy == 0)) {
                        return false;
                    }
                    var map_position = map_position_bg.parent();
                    var p = map_position.position();
                    var left = p.left + dx;
                    if (left < 0) left = 0;
                    var top = p.top + dy;
                    if (top < 0) top = 0;
                    var bottom = top + map_position.height();
                    if (bottom > container.height()) {
                        top = top - (bottom - container.height());
                    }
                    var right = left + map_position.width();
                    if (right > container.width()) {
                        left = left - (right - container.width());
                    }
                    map_position.css({
                        left: left,
                        top: top
                    });
                    map_position_bg.data('pageX', event.pageX);
                    map_position_bg.data('pageY', event.pageY);

                    bottom = top + map_position.height();
                    right = left + map_position.width();
                    return false;
                }).mouseup(function (event) {
                    map_position_bg.data('mousedown', false);
                    map_position_bg.css('cursor', 'default');
                    return false;
                });
            });
            //拖动大小按钮
            $('.position-container .map-position .resize',container).each(function () {
                var map_position_resize = $(this);
                var container = $(this).parent().parent();
                map_position_resize.unbind('mousedown').mousedown(function (event) {
                    //当鼠标按下时，标记鼠标按下，并记录鼠标所在的坐标
                    map_position_resize.data('mousedown', true);
                    map_position_resize.data('pageX', event.pageX);
                    map_position_resize.data('pageY', event.pageY);
                    return false;
                }).unbind('mouseup').mouseup(function (event) {
                    //当鼠标放开时，清除按下的标记
                    map_position_resize.data('mousedown', false);
                    return false;
                });
                container.mousemove(function (event) {
                    //当鼠标不是标记为按下时，移动不做处理
                    if (!map_position_resize.data('mousedown')) return false;


                    //x 轴移动的距离
                    var dx = event.pageX - map_position_resize.data('pageX');
                    //y 轴移动的距离
                    var dy = event.pageY - map_position_resize.data('pageY');

                    // console.info("pageX:" + event.pageX + " pageY:" + event.pageY
                    //     + " px:" + map_position_resize.data('pageX')
                    //     + " py:" + map_position_resize.data('pageY')
                    //     + " dx:" + dx + " dy:" + dy);


                    //如果鼠标没有移动
                    if ((dx == 0) && (dy == 0)) {
                        return false;
                    }


                    var map_position = map_position_resize.parent();
                    //position相对于容器的坐标
                    var p = map_position.position();
                    var left = p.left;
                    var top = p.top;

                    //新的高度等于原来的高度+增加的y轴距离
                    var height = map_position.height() + dy;
                    if ((top + height) > container.height()) { //如果 position 的高度超出了容器的边界
                        height = height - ((top + height) - container.height());
                    }
                    if (height < 20) height = 20;
                    var width = map_position.width() + dx;
                    if ((left + width) > container.width()) {
                        width = width - ((left + width) - container.width());
                    }
                    if (width < 50) width = 50;
                    map_position.css({
                        width: width,
                        height: height
                    });

                    //记录新的起点坐标
                    map_position_resize.data('pageX', event.pageX);
                    map_position_resize.data('pageY', event.pageY);

                    return false;
                }).mouseup(function (event) {
                    map_position_resize.data('mousedown', false);
                    return false;
                });
            });
            //删除按钮
            $('.position-container .map-position .delete',container).unbind('click').click(function () {
                var ref = $(this).parent().attr('ref');
                var _position_container = $(this).parent().parent().parent().find('.position-container');
                _position_container.find('.map-position[ref=' + ref + ']').remove();
            });
            //编辑按钮
            $('.position-container .map-position .edit',container).unbind('click').click(function () {
                var a = $(this).closest('.map-position').find('.link-number-text');
                // var obj = {};
                var id = a.data('id');
                var arti = InfoBaseRPC.getInfoById(id,site_id);
                top.addTab(true,"/sys/cms/info/article/"+getAddPagebyModel(arti.model_id)+"?cid="+arti.cat_id+"&info_id="+arti.id+"&site_id="+site_id+"&app_id=cms&model="+arti.model_id+"&topnum="+top.curTabIndex,"维护信息");
            });
        }

        //为各个组件设置坐标
        function define_css(container) {
            container = container||$container;

            //样式定义
            container.find('.map-position').css({
                position: 'absolute',
                border: '1px solid #000',
                'font-weight': 'bold'
            });
            container.find('.map-position .map-position-bg').css({
                position: 'absolute',
                top: 0,
                left: 0,
                right: 0,
                bottom: 0
            });
            container.find('.map-position .resize').css({
                display: 'block',
                position: 'absolute',
                right: 0,
                bottom: 0,
                width: 10,
                height: 10,
                cursor: 'nw-resize',
                background: '#000'
            });
            container.find('.map-position .delete').css({
                display: 'block',
                position: 'absolute',
                right: 0,
                top: 0,
                width: 10,
                height: 12,
                'line-height': '11px',
                'font-size': 12,
                'font-weight': 'bold',
                background: '#000',
                color: '#fff',
                'font-family': 'Arial',
                'padding-left': '2px',
                cursor: 'pointer',
                opactiey: 1
            });
            //增加编辑标题和链接的按钮
            container.find('.map-position .edit').css({
                display: 'block',
                position: 'absolute',
                left: 0,
                bottom: 0,
                width: 10,
                height: 12,
                'line-height': '11px',
                'font-size': 12,
                'font-weight': 'bold',
                background: '#000',
                color: '#fff',
                'font-family': 'Arial',
                'padding-left': '2px',
                cursor: 'pointer',
                opactiey: 1
            });
        }
    };
})(jQuery);
