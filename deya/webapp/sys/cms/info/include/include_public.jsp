<%@ page contentType="text/html; charset=utf-8"%>
<script type="text/javascript">

	$(document).ready(function(){
		getfontspacesize();   //获得字间距大小
	});

	/*
		标题字体斜体
		fontObliqueType noOblique(默认不加)  isOblique(添加) ;
	*/
	fontObliqueType = "noOblique"; 
    function changeFontStyle()
	{	
	    if(fontObliqueType == "noOblique") //加斜体
		{
			$("#title").css("font-style","oblique");
			fontObliqueType = "isOblique";
		}else{
			$("#title").css("font-style","normal");
			fontObliqueType = "noOblique";
		}
	    titleStyle = "";
	}
	
	/*
		标题字体加粗
		fontBoldType noBold(默认不加)  isBold(添加) ;
	*/
	fontBoldType = "noBold";  
	function changeFontBold()
	{
	    if(fontBoldType == "noBold") //加粗
		{
			$("#title").css("font-weight","bold");
			fontBoldType = "isBold";
		}else{
			$("#title").css("font-weight","normal");
			fontBoldType = "noBold";
		}
	    titleStyle = "";
	}

	/*
		字间距
		fontSpaceType noSpace(默认不加)  isSpace(添加字间距) ;
		fontspacesize 字间距值  0不加;
	*/
	fontSpaceType = "noSpace";  
	fontspacesize="";           
	function getfontspacesize()
	{
		$("#fontspace").change(function(){
			fontspacesize = $("#fontspace").val();   //获取字间距的值
			if(fontspacesize!=0)                     
			{
				$("#title").css("letter-spacing",fontspacesize+"px");
				fontSpaceType = "isSpace";
			}else{                                   
				$("#title").attr("style","word-spacing:normal;");
				fontSpaceType = "noSpace";
			}
		});
		titleStyle = "";
	}
	
	/*
		清空标题样式
	*/
	function emptyTitleStyle()
	{
		$("#title").removeClass("noOblique");
		$("#title").removeClass("isOblique");
		$("#title").removeClass("isBold");
		$("#title").removeClass("noBold");
		$("#title").attr("style","word-spacing:normal;");
		$("#title").css('font-size','');
		$("#fontspace").val("0");
		titleStyle = "emptyStyle";
	}

	/*
		标题字体大小
		fontSize 默认(12px)
		fontSizeType normal(默认) change(改变)
	*/
	fontSize=12;
	fontSizeType="normal";
	function changeFontsmall()
	{
		fontSize = parseInt(fontSize);
		fontSize -= 2;
		$("#title").css("font-size",fontSize);
		fontSizeType = "change";
		if(fontSize<10)
		{
			fontSize=10;
		}
		titleStyle = "";
	}

	function changeFontnormal()
	{
		fontSize = 12;
		fontSize = parseInt(fontSize);
		$("#title").css("font-size",fontSize);
		fontSizeType = "change";
		titleStyle = "";
	}

	function changeFontbig()
	{
		fontSize = parseInt(fontSize);
		fontSize += 2;
		$("#title").css("font-size",fontSize);
		fontSizeType = "change";
		if(fontSize>24)
		{
			fontSize=24;
		}
		titleStyle = "";
	}
</script>
<table id="" class="table_form table_option" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>所属栏目：</th>
			<td style=" width:100px;">
				<span class="f_red" id="showCatId">分类ID</span>
			</td>
			<th style=" width:80px;" id="t1">同时发布到：</th>
			<td style=" width:120px;"  id="t2">
				<input type="text" id="cat_tree" value="" style="width:176px; height:18px; overflow:hidden;" readonly="readonly" onclick="showCategoryTree()"/>
				<div id="cat_tree_div1" class="select_div tip hidden border_color" style="width:176px; height:300px; overflow:hidden;border:1px #7f9db9 solid;z-index:10000;" >
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="leftMenuTree1" class="easyui-tree" animate="true" style="width:176px; height:280px;">
							</ul>
						</div>
					</div>
				</div>
			</td>
			<!-- 
			<th style=" width:80px;"  id="t3">所属专题：</th>
			<td style=" width:120px;"  id="t4">
				<input type="text" id="zt_tree" value="" style="width:176px; height:18px; overflow:hidden;" readonly="readonly" onclick="showZTCategoryTree()"/>
				<div id="cat_tree_div2" class="select_div tip hidden border_color" style="width:176px; height:300px; overflow:hidden;border:1px #7f9db9 solid;" >
					<div id="leftMenuBox">
						<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
							<ul id="leftMenuTree2" class="easyui-tree" style="width:176px; height:270px;">
							</ul>
						</div>
					</div>
				</div>
			</td>
			 -->
			<td></td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	    <tr>
            <th><span class="f_red">*</span>发布渠道：</th>
            <td>
                <input  name="publish_source" type="checkbox" class="width350" value="pc" checked="checked"  />网站
                <input  name="publish_source" type="checkbox" class="width350" value="wx" checked="checked" />微信
                <input  name="publish_source" type="checkbox" class="width350" value="app" checked="checked" />APP
            </td>
        </tr>
		<tr>
			<th><span class="f_red">*</span>标题：</th>
			<td>
				<!-- 
				<input type="text" id="pre_title" value="" style="width:60px; height:18px; overflow:hidden;" />
				<div id="select4" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px;overflow-x:hidden;overflow:auto; " >
					<div id="leftMenuBox">
						<ul id="selectList_pre" class="listLi"  style="width:134px; height:30px; text-align: left;">
						</ul>
					</div>
				</div>
				 -->
				<input id="title" name="title" type="text" class="width350" value="" onkeypress="showStringLength('title','wordnum')" onkeyup="showStringLength('title','wordnum')"  onblur="checkInputValue('title',false,240,'信息标题','')"/>
				<span id="wordnum">0</span>字
				<!-- 
				<input id="title_color" name="title_color" type="text" style="width:60px;" class="iColorPicker" onchange="changeTitleColor(this)" value="" readOnly="readOnly" title="双击清空颜色值"/> 
				<SCRIPT LANGUAGE="JavaScript">
				<!--
					$("#title_color").dblclick(function(){
						$(this).val("").css("background","");
					});

				//
				</SCRIPT>
				 -->
				<input id="sttop" name="dd" type="checkbox"  onclick="showTopTitle()"/><label for="sttop">上标题</label>
				<input id="stt" name="dd" type="checkbox"  onclick="showSubTitle()"/><label for="stt">副标题</label>				
				<input id="is_pic" name="is_pic" type="checkbox"  value="1" /><label for="is_pic">图片</label>
			</td>
		</tr>
		<tr id="topTitleTr" style="display:none;">
			<th>上标题：</th>
			<td>
				<input id="top_title" name="top_title" type="text" class="width350" value="" onkeypress="showStringLength('top_title','wordnum3')" onkeyup="showStringLength('top_title','wordnum3')" onblur="checkInputValue('top_title',true,240,'上标题','')"/>
				<span id="wordnum3">0</span>字
			</td>
		</tr>
		<tr id="subTitleTr" style="display:none;">
			<th>副标题：</th>
			<td>
				<input id="subtitle" name="subtitle" type="text" class="width350" value="" onkeypress="showStringLength('subtitle','wordnum2')" onkeyup="showStringLength('subtitle','wordnum2')" onblur="checkInputValue('subtitle',true,240,'副标题','')"/>
				<span id="wordnum2">0</span>字
			</td>
		</tr>
		<!-- 
		<tr style="line-height:30px;">
		    <th></th>
			<td>
				<img src="../../../images/fontxieti.png"   onclick="changeFontStyle()" align="absmiddle" />
				<img src="../../../images/fontcuti.png"  onclick="changeFontBold()" align="absmiddle" />
				<img src="../../../images/fontsmall.png"  onclick="changeFontsmall()" align="absmiddle" />
				<img src="../../../images/fontnormal.png"  onclick="changeFontnormal()" align="absmiddle" />
				<img src="../../../images/fontbig.png"  onclick="changeFontbig()"  align="absmiddle"/>
				
				<select id="fontspace" name="fontspace" class="select_input_selected">
					<option value="0">选择字间距</option>
					<option value="1">1px</option>
					<option value="2">2px</option>
					<option value="3">3px</option>
					<option value="4">4px</option>
					<option value="5">5px</option>
				</select>
				
				<input type="button" value="取消"  onclick="emptyTitleStyle()" />
			</td>
		</tr>
		 -->
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>来源：</th>
			<td width="135">
				<div id="a11">
					<input type="text" id="source" value="" style="width:136px; height:18px; overflow:hidden;" />
					<div id="select" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; overflow:auto; " >
						<div id="leftMenuBox">
							<ul id="selectList" class="listLi"  style="width:134px; height:30px; text-align: left;">
							</ul>
						</div>
					</div>
				</div>
			</td>
			<th style="width:40px;">作者：</th>
			<td width="135">
				<div id="a12">
					<input type="text" id="author" value="" style="width:136px; height:18px; overflow:hidden;" />
					<div id="select2" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; overflow:auto; " >
						<div id="leftMenuBox">
							<ul id="selectList2" class="listLi"  style="width:134px; height:30px; text-align: left;">
							</ul>
						</div>
					</div>
				</div>
			</td>			
			<th style="width:40px;">编辑：</th>
			<td width="135">
				<div id="a12">
					<input type="text" id="editor" value="" style="width:136px; height:18px; overflow:hidden;" />
					<div id="select3" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; overflow:auto; " >
						<div id="leftMenuBox">
							<ul id="selectList3" class="listLi"  style="width:134px; height:30px; text-align: left;">
							</ul>
						</div>
					</div>
				</div>
			</td>	
			
			<td></td>
		</tr>
		<tr>
			<th>Tag：</th>
			<td colspan="3">
				<input id="tags" name="tags" type="text" class="width350" maxlength="80" value="" /><!-- <input id="i005" name="i005" type="button" onclick="javascript:void(0);" value="自动获取" /> -->
			</td>
			<th class="releaseTime" style="display:none;">显示时间：</th>
			<td class="releaseTime" style="display:none;">
				<input id="released_dtime" name="released_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
<input id="info_id" type="hidden" class="width200" value="0" />
<input id="cat_id" type="hidden" class="width200" value="0" />
<input id="from_id" type="hidden" class="width200" value="0" />
<input id="content_url" type="hidden" class="width200" value="" />
<input id="wf_id" type="hidden" class="width200" value="0" />
<input id="step_id" type="hidden" class="width200" value="0" />
<input id="final_status" type="hidden" class="width200" value="0" />
<input id="hits" type="hidden" class="width200" value="0" />
<input id="day_hits" type="hidden" class="width200" value="0" />
<input id="week_hits" type="hidden" class="width200" value="0" />
<input id="month_hits" type="hidden" class="width200" value="0" />
<input id="lasthit_dtime" type="hidden" class="width200" value="" />
<input id="comment_num" type="hidden" class="width200" value="0" />
<input id="input_user" type="hidden" class="width200" value="0" />
<input id="input_dtime" type="hidden" class="width200" value="" />
<!-- <input id="modify_user" type="hidden" class="width200" value="0" /> -->
<input id="modify_dtime" type="hidden" class="width200" value="" />
<input id="opt_dtime" type="hidden" class="width200" value="" />
<input id="is_auto_up" type="hidden" class="width200" value="0" />
<input id="is_auto_down" type="hidden" class="width200" value="0" />
<input id="is_host" type="hidden" class="width200" value="0" />
<input id="title_hashkey" type="hidden" class="width200" value="0" />
<input id="site_id" type="hidden" class="width200" value="0" />
<input id="i_ver" type="hidden" class="width200" value="0" />
<input id="page_count" type="hidden" class="width200" value="0" />
<input id="prepage_wcount" type="hidden" class="width200" value="0" />
<input id="word_count" type="hidden" class="width200" value="0" />