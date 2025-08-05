package com.example.projectmanagement.modules.coding.langgenerator;

import java.util.List;

import com.example.projectmanagement.modules.coding.datastructure.entity.ClassDefinitionEntity;
import com.example.projectmanagement.modules.coding.datastructure.entity.ClassFieldEntity;
import com.example.projectmanagement.modules.coding.datastructure.models.ClassDefFieldsModel;
import com.example.projectmanagement.modules.databases.datastructure.entity.DBInfoEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumnEntity;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfoEntity;

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
	public ClassDefFieldsModel createClassAndFieldsFromDBTable(DBInfoEntity dbInfo, TableInfoEntity tableInfo, List<TableColumnEntity> columnList, String dataUseType);
	
	/**
	 * テーブルInfoをクラスに変換する。ClassDefinition（エンティティー）を使用する。
	 * @param projectId
	 * @param tableInfo
	 * @param dataUseType
	 * @return
	 */
	public ClassDefinitionEntity createClassFromDBTable(Integer projectId, TableInfoEntity tableInfo, String dataUseType);
	
	
	public ClassFieldEntity createFieldFromDBColumn(TableColumnEntity column, Integer classId, String dbms);
	
	/**
	 * ClassDefinitionModelからコードブロックを生成する。
	 * @param classDefinition
	 * @return
	 */
	public String stringBuilder(ClassDefFieldsModel classDefinition);
	
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
