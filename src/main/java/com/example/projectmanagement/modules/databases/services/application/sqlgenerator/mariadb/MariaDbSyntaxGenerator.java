package com.example.projectmanagement.modules.databases.services.application.sqlgenerator.mariadb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.projectmanagement.modules.databases.datastructure.entity.TableColumn;
import com.example.projectmanagement.modules.databases.datastructure.entity.TableInfo;
import com.example.projectmanagement.modules.databases.services.application.sqlgenerator.AbstractSqlSyntaxGenerator;

@Component("mariadbSyntax")
public class MariaDbSyntaxGenerator extends AbstractSqlSyntaxGenerator {

	/**
	 * CREATE TABLE 文を生成する。
	 *
	 * @param table テーブル情報（テーブル名など）
	 * @param columns カラム情報リスト
	 * @return SQL CREATE TABLE 文
	 */
	@Override
	public String createTable(TableInfo table, List<TableColumn> columns) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ").append(table.getTableName()).append(" (\n");

		List<String> fkConstraints = new ArrayList<>();

		for (int i = 0; i < columns.size(); i++) {
			TableColumn col = columns.get(i);
			sb.append("  ").append(col.getColumnName())
					.append(" ").append(col.getDataType());

			if (col.getDataTypeParam() != null && !col.getDataTypeParam().isBlank()) {
				sb.append("(").append(col.getDataTypeParam()).append(")");
			}

			if (Boolean.TRUE.equals(col.getIsPrimary())) {
				sb.append(" PRIMARY KEY");
			}

			if (Boolean.FALSE.equals(col.getIsNullable()))
				sb.append(" NOT NULL");
			if (Boolean.TRUE.equals(col.getIsUnique()))
				sb.append(" UNIQUE");
			if (Boolean.TRUE.equals(col.getIsAutoIncrement()))
				sb.append(" AUTO_INCREMENT");

			if (columns.size() -1 != i) {
				sb.append(",");				
			}

			// 外部キー制約を構築（別途）
			if (Boolean.TRUE.equals(col.getIsForign()) && col.getForignId() != null
					&& col.getRefTableName() != null && col.getRefColumnName() != null) {

				StringBuilder fk = new StringBuilder();
				fk.append("  CONSTRAINT fk_").append(col.getColumnName())
						.append(" FOREIGN KEY (").append(col.getColumnName()).append(")")
						.append(" REFERENCES ").append(col.getRefTableName())
						.append("(").append(col.getRefColumnName()).append(")");

				if (col.getOnDelete() != null && !col.getOnDelete().isBlank()) {
					fk.append(" ON DELETE ").append(col.getOnDelete());
				}
				if (col.getOnUpdate() != null && !col.getOnUpdate().isBlank()) {
					fk.append(" ON UPDATE ").append(col.getOnUpdate());
				}

				fkConstraints.add(fk.toString());
			}
			if (columns.size() -1 != i) {
				sb.append("\n");
			}
		}

		// 外部キー制約を追加
		for (int i = 0; i < fkConstraints.size(); i++) {
			if(i == 0) {
				sb.append(",\n");
			}
			
			sb.append(fkConstraints.get(i));
			if (i < fkConstraints.size() - 1) {
				sb.append(",");
			}
			sb.append("\n");
		}

		sb.append(") ENGINE=InnoDB;");
		return sb.toString();
	}

	/**
	 * カラム追加用のALTER TABLE文を生成する。
	 *
	 * @param tableName テーブル名
	 * @param column カラム情報
	 * @return SQL ALTER TABLE ADD COLUMN 文
	 */
	@Override
	public String addColumn(String tableName, TableColumn column) {
		StringBuilder sb = new StringBuilder();
		sb.append("ALTER TABLE ").append(tableName)
				.append(" ADD COLUMN ").append(column.getColumnName()).append(" ").append(column.getDataType());

		if (column.getDataTypeParam() != null && !column.getDataTypeParam().isBlank()) {
			sb.append("(").append(column.getDataTypeParam()).append(")");
		}

		if (Boolean.FALSE.equals(column.getIsNullable()))
			sb.append(" NOT NULL");
		if (Boolean.TRUE.equals(column.getIsUnique()))
			sb.append(" UNIQUE");
		if (Boolean.TRUE.equals(column.getIsAutoIncrement()))
			sb.append(" AUTO_INCREMENT");

		sb.append(";");
		return sb.toString();
	}

	/**
	 * DROP TABLE 文を生成する。
	 *
	 * @param tableName テーブル名
	 * @return DROP TABLE IF EXISTS 文
	 */
	@Override
	public String dropTable(String tableName) {
		return "DROP TABLE IF EXISTS " + tableName + ";";
	}

	/**
	 * INSERT文テンプレートを生成する（? プレースホルダ付き）。
	 *
	 * @param tableName テーブル名
	 * @param columnNames カラム名リスト
	 * @return INSERT INTO ... VALUES (?, ?, ...) 形式のテンプレートSQL
	 */
	@Override
	public String insertTemplate(String tableName, List<String> columnNames) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(tableName).append(" (")
				.append(String.join(", ", columnNames)).append(")\nVALUES (");

		for (int i = 0; i < columnNames.size(); i++) {
			sb.append("?");
			if (i < columnNames.size() - 1)
				sb.append(", ");
		}

		sb.append(");");
		return sb.toString();
	}

	/**
	 * 実データを含むINSERT文を生成する。
	 *
	 * @param tableName テーブル名
	 * @param columnNames カラム名リスト
	 * @param records 複数レコード（カラム名と値のマップ）
	 * @return 実値を含む INSERT INTO ... VALUES (...), (...); 形式のSQL
	 */
	@Override
	public String insertStatement(String tableName, List<String> columnNames, List<Map<String, Object>> records) {
	    if (records == null || records.isEmpty()) return "";

	    StringBuilder sb = new StringBuilder();
	    sb.append("INSERT INTO ").append(tableName).append(" (")
	      .append(String.join(", ", columnNames)).append(")\nVALUES\n");

	    for (int i = 0; i < records.size(); i++) {
	        Map<String, Object> record = records.get(i);
	        sb.append("  (");
	        for (int j = 0; j < columnNames.size(); j++) {
	            Object value = record.get(columnNames.get(j));
	            sb.append(formatValue(value));
	            if (j < columnNames.size() - 1) sb.append(", ");
	        }
	        sb.append(")");
	        if (i < records.size() - 1) sb.append(",");
	        sb.append("\n");
	    }

	    sb.append(";");
	    return sb.toString();
	}

	/**
	 * 実データを埋め込んだ UPDATE 文を生成する。
	 *
	 * ---注意:---
	 *   値には文字列や数値、Boolean、null が含まれることが想定される
	 *   値は自動的に適切なSQL形式に整形される（formatValue により）
	 *   WHERE句が指定されない場合、全行更新されるため注意
	 *   
	 *   
	 * @param tableName テーブル名
	 * @param updates カラム名と更新値のマップ
	 * @param whereClause WHERE句（例: "id = 1"）
	 * @return 完成された UPDATE SQL文
	 */
	@Override
	public String updateStatement(String tableName, Map<String, Object> updates, String whereClause) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(tableName).append(" SET\n");
		int i = 0;
		for (Map.Entry<String, Object> entry : updates.entrySet()) {
			sb.append("  ").append(entry.getKey()).append(" = ").append(formatValue(entry.getValue()));
			if (i++ < updates.size() - 1)
				sb.append(",\n");
		}
		if (whereClause != null && !whereClause.isBlank()) {
			sb.append("\nWHERE ").append(whereClause);
		}
		sb.append(";");
		return sb.toString();
	}

	/**
	 * UPDATE文テンプレートを生成する（? プレースホルダ付き）。
	 *
	 * @param tableName テーブル名
	 * @param columnNames 更新対象のカラム名リスト
	 * @param whereClause WHERE句（例："id = ?"）
	 * @return SQL UPDATE文
	 */
	@Override
	public String updateTemplate(String tableName, List<String> columnNames, String whereClause) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(tableName).append(" SET\n");
		for (int i = 0; i < columnNames.size(); i++) {
			sb.append("  ").append(columnNames.get(i)).append(" = ?");
			if (i < columnNames.size() - 1)
				sb.append(",\n");
		}
		if (whereClause != null && !whereClause.isBlank()) {
			sb.append("\nWHERE ").append(whereClause);
		}
		sb.append(";");
		return sb.toString();
	}

	@Override
	public String deleteStatement(String tableName, String whereClause) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ").append(tableName);
		if (whereClause != null && !whereClause.isBlank()) {
			sb.append(" WHERE ").append(whereClause);
		}
		sb.append(";");
		return sb.toString();
	}

	/**
	 * DELETE文を生成する。
	 *
	 * @param tableName テーブル名
	 * @param whereClause WHERE句（例："id = ?"）。空の場合は全削除になるので注意。
	 * @return SQL DELETE文
	 */
	@Override
	public String deleteTemplate(String tableName, String whereClause) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ").append(tableName);
		if (whereClause != null && !whereClause.isBlank()) {
			sb.append(" WHERE ").append(whereClause);
		}
		sb.append(";");
		return sb.toString();
	}

}
