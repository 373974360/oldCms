package com.deya.wcm.services.model.services;

import java.util.HashMap;

import org.w3c.dom.Node;

import com.deya.util.xml.XmlManager;
import com.deya.wcm.services.model.Fields;


/**
 * 内容模型表单工具类.
 * <p>Title: FieldsUtil</p>
 * <p>Description: 内容模型表单工具类。</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Cicro</p>
 * @author lisp
 * @version 1.0
 */
public class FieldsUtil {
    
	/**
     * null 转 ""
     * @param s String
     * @return String
     */
    public static String formatQuote(String s) {
        if (s == null) {
            return "";
        }
        //return s.replaceAll("'","''");
        return s;
    }
	
	/**
     * 通过字段属性拼写xml字符串
     * @param map 字段属性信息
     * @param fieldType 字段类型
     * @return String xml字符串
     */
	public static String getFieldXml(HashMap map,String fieldType){
		try{
			StringBuffer sb = new StringBuffer();
			if(fieldType.equals("0")){
				String field_maxnum = (String)map.get("field_maxnum");
				String field_maxlength = (String)map.get("field_maxlength");
				String validator = (String)map.get("validator");
				String default_value = (String)map.get("default_value");
				
				sb.append("<fieldInfo>");
				sb.append("<field_maxnum><![CDATA[");
				sb.append(field_maxnum);
				sb.append("]]></field_maxnum>");
				sb.append("<field_maxlength><![CDATA[");
				sb.append(field_maxlength);
				sb.append("]]></field_maxlength>");
				sb.append("<validator><![CDATA[");
				sb.append(validator);
				sb.append("]]></validator>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
			}else if(fieldType.equals("1")){
				
				String width = (String)map.get("width");
				String height = (String)map.get("height");
				String field_maxnum = (String)map.get("field_maxnum");
				String default_value = (String)map.get("default_value");
				sb.append("<fieldInfo>");
				sb.append("<width><![CDATA[");
				sb.append(width);
				sb.append("]]></width>");
				sb.append("<height><![CDATA[");
				sb.append(height);
				sb.append("]]></height>");
				sb.append("<field_maxnum><![CDATA[");
				sb.append(field_maxnum);
				sb.append("]]></field_maxnum>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
				
			}else if(fieldType.equals("2")){
				
				String width = (String)map.get("width");
				String height = (String)map.get("height");
				String field_maxnum = (String)map.get("field_maxnum");
				String default_value = (String)map.get("default_value");
				sb.append("<fieldInfo>");
				sb.append("<width><![CDATA[");
				sb.append(width);
				sb.append("]]></width>");
				sb.append("<height><![CDATA[");
				sb.append(height);
				sb.append("]]></height>");
				sb.append("<field_maxnum><![CDATA[");
				sb.append(field_maxnum);
				sb.append("]]></field_maxnum>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
				
			}else if(fieldType.equals("3")){
				

				String select_item = (String)map.get("select_item");
				String select_view = (String)map.get("select_view");
				String default_value = (String)map.get("default_value");
				String data_type = (String)map.get("data_type");
				String data_type_id = (String)map.get("data_type_id");
				sb.append("<fieldInfo>");
				sb.append("<data_type><![CDATA[");
				sb.append(data_type);
				sb.append("]]></data_type>");
				sb.append("<data_type_id><![CDATA["); 
				sb.append(data_type_id);
				sb.append("]]></data_type_id>");
				sb.append("<select_item><![CDATA[");
				sb.append(select_item);
				sb.append("]]></select_item>");
				sb.append("<select_view><![CDATA[");
				sb.append(select_view);
				sb.append("]]></select_view>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
				
			}else if(fieldType.equals("4")){
				

				String min_num = (String)map.get("min_num");
				String max_num = (String)map.get("max_num");
				String default_value = (String)map.get("default_value");
				sb.append("<fieldInfo>");
				sb.append("<min_num><![CDATA[");
				sb.append(min_num);
				sb.append("]]></min_num>");
				sb.append("<max_num><![CDATA[");
				sb.append(max_num);
				sb.append("]]></max_num>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
				
			}else if(fieldType.equals("5")){
				
				String default_value = (String)map.get("default_value");
				sb.append("<fieldInfo>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
				
			}else if(fieldType.equals("6")){
				
				String default_value = (String)map.get("default_value");
				sb.append("<fieldInfo>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
				
			}else if(fieldType.equals("9")){
				

				String select_item = (String)map.get("select_item");
				String select_view = (String)map.get("select_view");
				String default_value = (String)map.get("default_value");
				sb.append("<fieldInfo>");
				sb.append("<select_item><![CDATA[");
				sb.append(select_item);
				sb.append("]]></select_item>");
				sb.append("<select_view><![CDATA[");
				sb.append(select_view);
				sb.append("]]></select_view>");
				sb.append("<default_value><![CDATA[");
				sb.append(default_value);
				sb.append("]]></default_value>");
				sb.append("</fieldInfo>");
				
			} 
			
			return sb.toString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}


	
	/**
     * 通过字段类型分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @param fieldType 字段类型
     * @return field 表单bean
     */
	public static Fields getBeanByFieldXml(Fields field,String fieldXml,String fieldType){
		try {
			if(fieldType.equals("0")){
				//<fieldInfo><field_maxnum>160</field_maxnum><field_maxlength>30</field_maxlength>
				//<validator></validator><default_value></default_value></fieldInfo>
				return getBeanByText(field, fieldXml);
			}else if(fieldType.equals("1")){
				return getBeanByAreaNoHtml(field, fieldXml);
			}else if(fieldType.equals("2")){
				return getBeanByAreaHtml(field, fieldXml);
			}else if(fieldType.equals("3")){
				return getBeanBySelect(field, fieldXml);
			}else if(fieldType.equals("4")){
				return getBeanByNumber(field, fieldXml);
			}else if(fieldType.equals("5")){
				return getBeanByText(field, fieldXml);
			}else if(fieldType.equals("6")){
				return getBeanByFile(field, fieldXml);
			}else if(fieldType.equals("8")){
				return getBeanByText(field, fieldXml);
			}else if(fieldType.equals("9")){
				return getBeanBySelect(field, fieldXml);
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过字段类型单行文本分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @return field 表单bean
     */
	public static Fields getBeanByText(Fields field,String fieldXml){
		try {
			Node node = XmlManager.createNode(fieldXml);			
			String field_maxnum = XmlManager.queryNodeValue(node,"//field_maxnum");
			String field_maxlength = XmlManager.queryNodeValue(node,"//field_maxlength");
			String validator = XmlManager.queryNodeValue(node,"//validator");
			String default_value = XmlManager.queryNodeValue(node,"//default_value");
			field.setField_maxnum(field_maxnum);
			field.setField_maxlength(field_maxlength);
			field.setValidator(validator);
			field.setDefault_value(default_value);
			return field;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过字段类型不带html编辑器分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @return field 表单bean
     */
	public static Fields getBeanByAreaNoHtml(Fields field,String fieldXml){
		try {
			Node node = XmlManager.createNode(fieldXml);			
			String width = XmlManager.queryNodeValue(node,"//width");
			String height = XmlManager.queryNodeValue(node,"//height");
			String field_maxnum = XmlManager.queryNodeValue(node,"//field_maxnum");
			String default_value = XmlManager.queryNodeValue(node,"//default_value");
			field.setWidth(width);
			field.setHeight(height);
			field.setField_maxnum(field_maxnum);
			field.setDefault_value(default_value);
			return field;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过字段类型带html编辑器分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @return field 表单bean
     */
	public static Fields getBeanByAreaHtml(Fields field,String fieldXml){
		try {
			//System.out.println(fieldXml);
			Node node = XmlManager.createNode(fieldXml);			
			String width = XmlManager.queryNodeValue(node,"//width");
			String height = XmlManager.queryNodeValue(node,"//height");
			String field_maxnum = XmlManager.queryNodeValue(node,"//field_maxnum");
			String default_value = XmlManager.queryNodeValue(node,"//default_value");
			field.setWidth(width);
			field.setHeight(height);
			field.setField_maxnum(field_maxnum);
			//System.out.println(field.getIs_turnpage());
			field.setDefault_value(default_value);
			return field;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过字段类型选项分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @return field 表单bean
     */
	public static Fields getBeanBySelect(Fields field,String fieldXml){
		try {
			Node node = XmlManager.createNode(fieldXml);			
			String select_item = XmlManager.queryNodeValue(node,"//select_item");
			String select_view = XmlManager.queryNodeValue(node,"//select_view");
			String default_value = XmlManager.queryNodeValue(node,"//default_value");
			String data_type = XmlManager.queryNodeValue(node,"//data_type");
			String data_type_id = XmlManager.queryNodeValue(node,"//data_type_id");
			field.setSelect_item(select_item);
			field.setSelect_view(select_view);
			field.setDefault_value(default_value);
			field.setData_type(data_type);
			field.setData_type_id(data_type_id);
			return field;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过字段类型数字分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @return field 表单bean
     */
	public static Fields getBeanByNumber(Fields field,String fieldXml){
		try {
			Node node = XmlManager.createNode(fieldXml);			
			String max_num = XmlManager.queryNodeValue(node,"//max_num");
			String min_num = XmlManager.queryNodeValue(node,"//min_num");
			String default_value = XmlManager.queryNodeValue(node,"//default_value");
			field.setMax_num(max_num);
			field.setMin_num(min_num);
			field.setDefault_value(default_value);
			return field;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过字段类型时间分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @return field 表单bean
     */
	public static Fields getBeanByTime(Fields field,String fieldXml){
		try {
			Node node = XmlManager.createNode(fieldXml);			
			String default_value = XmlManager.queryNodeValue(node,"//default_value");
			field.setDefault_value(default_value);
			return field;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 通过字段类型单行文本分解字段信息
     * @param field 表单bean
     * @param fieldXml xml的字段信息
     * @return field 表单bean
     */
	public static Fields getBeanByFile(Fields field,String fieldXml){
		try {
			Node node = XmlManager.createNode(fieldXml);			
			String default_value = XmlManager.queryNodeValue(node,"//default_value");
			field.setDefault_value(default_value);
			return field;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
