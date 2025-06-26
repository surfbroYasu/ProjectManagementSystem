package com.example.projectmanagement.modules.databases.services.application.sqlgenerator;

import java.util.List;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;

public interface DataTypeResolver {
	List<String> getDataTypeOptions();
	List<String> getFkConstraintOptions();
	
	/**
	 * データ型に応じて、DBMSごとに適切な引数を調整または補正する。
	 * 
	 * 例えば、VARCHAR 型には未指定時にデフォルト長「255」を、DECIMAL 型には「10,2」を設定し、
	 * INT や DATE など引数が不要な型については空に補正する。
	 *
	 * @param domain データ型とその引数情報を持つ TableColumn オブジェクト
	 * @return 引数情報を調整済みの TableColumn オブジェクト
	 */
	TableColumn adjustDataTypeParam(TableColumn domain);

}
