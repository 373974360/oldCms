<%@ page contentType="text/html; charset=utf-8"%>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align:top;">内容概述：</th>
        <td>
            <textarea id="description" name="description" style="width:90%;height:70px" onblur="checkInputValue('description',true,900,'内容概述','')"></textarea>
        </td>
    </tr>
    </tbody>
</table>

<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr id="shoudong_num" class="hidden">
        <th>生成年份：</th>
        <td>
            <input type="text" id="gk_year" name="gk_year" class="width100" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true,readOnly:true})" readonly="readonly"/>&#160;
            顺序号：<input type="text" id="gk_num" name="gk_num" class="width70"  value="" maxlength="8" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/>&#160;<input type="button" value="重新生成" onclick="getNewGKIndex()"/>
        </td>
    </tr>
    <tr id="info_staus_tr">
        <th>发布状态：</th>
        <td >
            <ul class="flagClass">
                <li><input id="d" name="info_status" type="radio"  value="0" onclick="isShowAudit(false)" /><label for="d">草稿</label></li>
                <li id="li_ds"><input id="e" name="info_status" type="radio" value="2" checked="checked" onclick="isShowAudit(true)"/><label for="e">待审</label></li>
                <!--<li id="opt_303" class="hidden"><input id="f" name="info_status" type="radio" value="4" /><label for="f">待发</label></li>-->
                <li id="opt_302" class="hidden"><input id="3" name="info_status" type="radio" value="8" onclick="isShowAudit(false)"/><label for="g">发布</label></li>
            </ul>
        </td>
    </tr>
    <tr id="audit_tr" class="hidden">
        <th>选择审核人：</th>
        <td id="audit_list">

        </td>
    </tr>
    <tr id="info_level_tr">
        <th>是否IP限制：</th>
        <td >
            <ul class="flagClass">
                <li><input name="isIpLimit" type="radio"  value="1"/><label>是</label></li>
                <li><input name="isIpLimit" type="radio" value="0" checked="checked" /><label>否</label></li>
            </ul>
        </td>
    </tr>
    </tbody>
</table>
<!--
<div class="sq_box">
    <div class="sq_title_box" >
        <div class="sq_title sq_title_minus">高级属性</div>
        <div class="sq_title_right">点击闭合</div>
    </div>
    <div class="sq_box_content  ">

        <table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>信息有效性：</th>
                <td class="width150">
                    <select id="gk_validity" class="input_select" style="width:150px;">

                    </select>
                </td>
                <th style="width:40px;">生效日期：</th>
                <td class="width150">
                    <input id="effect_dtime" name="effect_dtime" type="text" class="width150" maxlength="80" value=""  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"/>
                </td>
                <th style="width:40px;">废止日期：</th>
                <td class="width150">
                    <input id="aboli_dtime" name="aboli_dtime" type="text" class="width150" maxlength="80" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <th>信息格式：</th>
                <td class="width150">
                    <select id="gk_format" class="input_select" style="width:150px;">

                    </select>
                </td>
                <th style="width:40px;">载体类型：</th>
                <td class="width150">
                    <select id="carrier_type" class="input_select" style="width:150px;">

                    </select>
                </td>
                <th  style="width:40px;">语种：</th>
                <td class="width150">
                    <select id="language" class="input_select" style="width:150px;">

                    </select>
                </td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>权重：</th>
                <td>
                    <input id="weight" name="weight" type="text" style="width:50px;" value="60"  maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
                </td>
            </tr>
            </tbody>
        </table>

        <table id="timer_publish" class="table_form hidden" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th>定时发布：</th>
                <td style="width:100px;">
                    <input id="up_dtime" name="up_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
                </td>
                <th>定时撤销：</th>
                <td style="width:100px;">
                    <input id="down_dtime" name="down_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
                </td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th valign="top">推荐：</th>
                <td>
                    <input id="btn1" name="btn1" type="button" onclick="openFocusPage(infoid,rela_site_id);" value="选择" />
                </td>
            </tr>
            <tr>
                <th valign="top"> </th>
                <td>
                    <div>
                        <table id="focusWare" class="" style="width:100%;" border="0" cellpadding="0" cellspacing="0">
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <th>相关文章：</th>
                <td>
                    <input id="btn1" name="btn1" type="button" onclick="openLinkInfoDataPage(infoid,rela_site_id)" value="选择文章" />
                    <input id="btn1" name="btn1" type="button" onclick="addRelatedHand()" value="手动添加" />
                </td>
            </tr>
            <tr>
                <th></th>
                <td align="left">
                    <table align="left" id="relateInfos" class="width:300px" border="0" cellpadding="0" cellspacing="0">
                    </table>
                </td>
            </tr>
            <tr id="info_level_tr">
                <th>稿件评级：</th>
                <td >
                    <ul class="flagClass">
                        <li><input name="info_level" type="radio"  value="A"/><label>A</label></li>
                        <li><input name="info_level" type="radio" value="B" checked="checked" /><label>B</label></li>
                        <li><input name="info_level" type="radio" value="C" /><label>C</label></li>
                        <li><input name="info_level" type="radio" value="D" /><label>D</label></li>
                    </ul>
                </td>
            </tr>


            </tbody>
        </table>
    </div>
</div>
-->
