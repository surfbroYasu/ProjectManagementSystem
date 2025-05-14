package com.example.projectmanagement.modules.databases.sqlgenerator;

public abstract class AbstractSqlSyntaxGenerator implements SqlSyntaxGenerator{
	/**
	 * オブジェクトの値をSQL文で使用可能な文字列形式に変換する。
	 *
	 * 数値や真偽値はそのまま文字列として出力し、null は "NULL" を返す。
	 * 文字列などはシングルクォートで囲み、内部のクォート（'）は二重にしてエスケープする。
	 */
    protected String formatValue(Object value) {
        if (value == null) return "NULL";
        if (value instanceof Number || value instanceof Boolean) return value.toString();
        return "'" + value.toString().replace("'", "''") + "'";
    }
    
}
