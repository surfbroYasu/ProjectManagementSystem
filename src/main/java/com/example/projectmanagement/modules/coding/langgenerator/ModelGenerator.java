package com.example.projectmanagement.modules.coding.langgenerator;

import java.util.List;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinition;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassField;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefinitionModel;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfo;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;

public interface ModelGenerator {

	/**
	 * DBカラムに設定したDBMS指定のデータ型から、指定のプログラミング言語のデータ型に最適化します。
	 * @param dataType
	 * @param dbms
	 * @return　プログラミング言語のデータ型文字列
	 */
	public String dataTypeConverter(String dataType, String dbms);
	
	/**
	 * テーブルInfoに属するカラム全てをフィールドに変換し、ClassDefinitionModel　を返す。
	 * カラムはClassDefinitionModel内で List<FieldModel>として内包される。
	 * @param dbInfo
	 * @param tableInfo
	 * @param columnList
	 * @param dataUseType
	 * @return　 ClassDefinitionModel
	 */
	public ClassDefinitionModel createClassAndFieldsFromDBTable(DBInfo dbInfo, TableInfo tableInfo, List<TableColumn> columnList, String dataUseType);
	
	/**
	 * テーブルInfoをクラスに変換する。ClassDefinition（エンティティー）を使用する。
	 * @param projectId
	 * @param tableInfo
	 * @param dataUseType
	 * @return
	 */
	public ClassDefinition createClassFromDBTable(Integer projectId, TableInfo tableInfo, String dataUseType);
	
	
	public ClassField createFieldFromDBColumn(TableColumn column, Integer classId, String dbms);
	
	/**
	 * ClassDefinitionModelからコードブロックを生成する。
	 * @param classDefinition
	 * @return
	 */
	public String stringBuilder(ClassDefinitionModel classDefinition);
	
	/**
	 * MariaDBのデータ型から、指定のプログラミング言語のデータ型に最適化します。
	 * @param type
	 * @return プログラミング言語のデータ型文字列
	 */
	public String convertFromMariaDB(String type);
	
	/**
	 * PostgreSQLのデータ型から、指定のプログラミング言語のデータ型に最適化します。
	 * @param type
	 * @return プログラミング言語のデータ型文字列
	 */
	public String convertFromPostgreSQL(String type);
	
}
