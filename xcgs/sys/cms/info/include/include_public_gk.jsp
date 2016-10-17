<%@ page contentType="text/html; charset=utf-8"%>
<%
    String app_id = request.getParameter("app_id");
    String model = request.getParameter("model");
    if(app_id == null || app_id.equals("null")){
        app_id = "";
    }
    if(model == null || !model.matches("[0-9]*")){
        model = "0";
    }
%>
<script type="text/javascript" src="js/gkPublic.js"></script>
<input id="model_id" type="hidden" class="width200" value="<%=model%>" />
<input id="app_id" type="hidden" class="width200" value="<%=app_id%>" />
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
<input id="modify_user" type="hidden" class="width200" value="0" />
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
<input id="editor" type="hidden" class="width200" value="" />
<input id="released_dtime" type="hidden" class="width200" value="" />
<input id="info_level" type="hidden" class="width200" value="B" />
<table id="" class="table_form table_option" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th><span class="f_red">*</span>所属栏目：</th>
        <td style=" width:100px;">
            <span class="f_red" id="showCatId">分类ID</span>
        </td>
        <!--
        <th style=" width:80px;" id="t1">同时发布到：</th>
        <td style=" width:120px;"  id="t2">
            <input type="text" id="cat_tree" value="" style="width:176px; height:18px; overflow:hidden;" readonly="readonly" onclick="showCategoryTree()"/>
            <div id="cat_tree_div1" class="select_div tip hidden border_color" style="width:176px; height:300px; overflow:hidden;border:1px #7f9db9 solid;" >
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
        <th><span class="f_red">*</span>信息标题：</th>
        <td>
            <!--
            <input type="text" id="pre_title" value="" style="width:60px; height:18px; overflow:hidden;" />
            <div id="select4" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px;overflow-x:hidden; overflow:auto; " >
                <div id="leftMenuBox">
                    <ul id="selectList_pre" class="listLi"  style="width:134px; height:30px; text-align: left;">
                    </ul>
                </div>
            </div>
             -->
            <input id="title" name="title" type="text" class="width350" value="" onkeypress="showStringLength('title','wordnum')" onkeyup="showStringLength('title','wordnum')"  onblur="checkInputValue('title',false,240,'信息标题','')"/>
            <span id="wordnum">0</span>字
            <input id="sttop" name="dd" type="checkbox"  onclick="showTopTitle()"/><label for="sttop">上标题</label>
            <input id="stt" name="dd" type="checkbox"  onclick="showSubTitle()"/><label for="stt">副标题</label>
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
    </tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th>来源：</th>
        <td width="150">
            <div id="a11">
                <input type="text" id="source" value="" style="width:145px; height:18px; overflow:hidden;" />
                <div id="select" class="select_div tip hidden border_color selectDiv" style="width:149px; height:30px; overflow:auto; " >
                    <div id="leftMenuBox">
                        <ul id="selectList" class="listLi"  style="width:149px; height:30px; text-align: left;">
                        </ul>
                    </div>
                </div>
            </div>
        </td>
        <th style="width:40px;">作者：</th>
        <td width="142">
            <div id="a12">
                <input type="text" id="author" value="" style="width:141px; height:18px; overflow:hidden;" />
                <div id="select2" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; overflow:auto; " >
                    <div id="leftMenuBox">
                        <ul id="selectList2" class="listLi"  style="width:134px; height:30px; text-align: left;">
                        </ul>
                    </div>
                </div>
            </div>
        </td>
        <td></td>
    </tr>
    </tbody>
</table>
<!--
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="width:40px;">编辑：</th>
        <td width="178">
            <div id="a12">
                <input type="text" id="editor" value="" style="width:173px; height:18px; overflow:hidden;" />
                <div id="select3" class="select_div tip hidden border_color selectDiv" style="width:134px; height:30px; overflow:auto; " >
                    <div id="leftMenuBox">
                        <ul id="selectList3" class="listLi"  style="width:134px; height:30px; text-align: left;">
                        </ul>
                    </div>
                </div>
            </div>
        </td>
        <th style="width:85px;">显示时间：</th>
        <td>
            <input id="released_dtime" name="released_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
        </td>

        <th style="width:85px;">生成日期：</th>
        <td width="110px">
            <input id="generate_dtime" name="generate_dtime" type="text" class="width100" maxlength="80" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"/>
        </td>

    </tr>
    </tbody>
</table>
-->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="width:100px;">关键词：</th>
        <td>
            <input id="topic_key" name="topic_key" type="text" class="width350" maxlength="80" value="" onblur="checkInputValue('topic_key',true,240,'主题关键词','')"/>
        </td>
    </tr>
    </tbody>
</table>
