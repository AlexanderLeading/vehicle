package org.mybatis.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * Created by andy on 2/24/15.
 */
public class PaginationPlugin extends PluginAdapter {
	public PaginationPlugin() {
		super();
	}

	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

		addLimit(topLevelClass, introspectedTable, "page");
		addLimit(topLevelClass, introspectedTable, "pageSize");
//		topLevelClass.addImportedType("com.woasis.energystation.plugin.Page");
//		addPageInfo(topLevelClass, introspectedTable, "page");
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	private void addPageInfo(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,
			String name){
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType(
				"com.woasis.energystation.plugin.Page"));
		//field.setType(PrimitiveTypeWrapper.getObjectInstance());
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String cname = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + cname);
		method.addParameter(new Parameter(new FullyQualifiedJavaType(
				"com.woasis.energystation.plugin.Page"),name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setReturnType(new FullyQualifiedJavaType(
				"com.woasis.energystation.plugin.Page")); 
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("get" + cname);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);

	}

	private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable,
			String name){
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(PrimitiveTypeWrapper.getIntegerInstance());
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String cname = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + cname);
		method.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(),name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance()); 
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("get" + cname);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);

	}
	@Override
	public boolean clientGenerated(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(
				introspectedTable.getExampleType());
		importedTypes.add(type);
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType returnType = FullyQualifiedJavaType
				.getNewListInstance();
		FullyQualifiedJavaType listType;
		if (introspectedTable.getRules().generateBaseRecordClass()) {
			listType = new FullyQualifiedJavaType(introspectedTable
					.getBaseRecordType());
		} else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			listType = new FullyQualifiedJavaType(introspectedTable
					.getPrimaryKeyType());
		} else {
			throw new RuntimeException(getString("RuntimeError.12"));
		}

		importedTypes.add(listType);
		returnType.addTypeArgument(listType);
		method.setReturnType(returnType);

		method.setName("selectByExamplePage");
		method.addParameter(new Parameter(type, "example"));

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);

		return true;
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document,
			IntrospectedTable introspectedTable) {

		XmlElement parentElement = document.getRootElement();

		//		XmlElement newResultMapElement = new XmlElement("resultMap");
		//		newResultMapElement
		//				.addAttribute(new Attribute("id", "selectExamplePageResult"));
		//		newResultMapElement.addAttribute(new Attribute("extends",
		//				"BaseResultMap"));
		//		
		//	    String returnType;
		//        if (introspectedTable.getRules().generateBaseRecordClass()) {
		//            returnType = introspectedTable.getBaseRecordType();
		//        } else {
		//            returnType = introspectedTable.getPrimaryKeyType();
		//        }
		//		
		//		newResultMapElement.addAttribute(new Attribute("type",
		//				returnType));
		//
		//		parentElement.addElement(newResultMapElement);

		//以下代码用于生成支持分页的sql片段

		String fqjt = introspectedTable.getExampleType();
		XmlElement answer = new XmlElement("select"); 
		answer.addAttribute(new Attribute("id", 
				"selectByExamplePage"));
		answer.addAttribute(new Attribute(
				"resultMap","BaseResultMap")); 
		answer.addAttribute(new Attribute("parameterType", fqjt)); 
		answer.addElement(new TextElement("select "));

		XmlElement distinctElement= new XmlElement("if");
		distinctElement.addAttribute(new Attribute("test","distinct"));
		distinctElement.addElement(new TextElement("distinct"));
		answer.addElement(distinctElement);

		XmlElement base_Column_ListElement= new XmlElement("include");
		base_Column_ListElement.addAttribute(new Attribute("refid","Base_Column_List"));
		answer.addElement(base_Column_ListElement);

		answer.addElement(new TextElement(" from "+introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));


		XmlElement oredCriteriaElement= new XmlElement("if");
		oredCriteriaElement.addAttribute(new Attribute("test","_parameter != null"));
		oredCriteriaElement.addElement(new TextElement("<include refid=\"Example_Where_Clause\"/> "));
		answer.addElement(oredCriteriaElement);

		XmlElement orderByClauseElement= new XmlElement("if");
		orderByClauseElement.addAttribute(new Attribute("test","orderByClause != null"));
		orderByClauseElement.addElement(new TextElement("order by ${orderByClause}"));
		answer.addElement(orderByClauseElement);

		XmlElement pageElement= new XmlElement("if");
		pageElement.addAttribute(new Attribute("test","page != null and page >=0"));
		pageElement.addElement(new TextElement("limit ${page} , ${pageSize}"));

		answer.addElement(pageElement);

		parentElement.addElement(answer);

		return super.sqlMapDocumentGenerated(document,introspectedTable);
	}

	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}
}
