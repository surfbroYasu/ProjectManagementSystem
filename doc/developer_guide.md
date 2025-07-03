# 開発者向けガイド（PMシステム）

## 📦 パッケージ構成ルール

- `controller`: 画面・APIなどの入力制御（REST, Thymeleaf）  
- `service`: サービスクラスの親パッケージ（application, repositoryを内包）  
    - `service.application`: ユースケース単位の処理（アプリケーションサービス）  
    - `service.repository`: 永続化レイヤー。DTO操作やDB連携処理（旧 domain）  

- `datastructure`: アプリケーションで使用する各種データ構造を定義  
    - `datastructure.dto`: ControllerとServiceの間で使う転送用オブジェクト  
    - `datastructure.entity`: DBと対応する永続化エンティティ  
    - `datastructure.form`: 入力フォーム用のデータオブジェクト（Thymeleafバインド用など）  
    - `datastructure.model`: ドメインロジックや表示に特化した中間モデル  
    - `datastructure.enums`: 列挙型（Enum）の定義  

- `mapper`: MyBatisのMapperインターフェース定義  
- `generalutil`: 汎用ユーティリティクラス群    
- `util`: ユースケース単位のユーティリティクラス群  

## 🧾 命名規則（抜粋）

### クラス名（`UpperCamelCase`）
- `XxxContextService`: 画面描画ロジック（ViewContext関連処理）  
- `application.xxxService`: 業務ロジックを扱うサービスクラス  
- `repository.xxxService`: 永続化レイヤーよりのロジックを扱うサービスクラス  
- `XxxRepositoryService`: データ取得・永続化をRepository単位でまとめたサービス  
- `XxxFinderMapper`: MyBatisで検索特化のMapper  
- `XxxDto`: Controller ↔ Service 間などのデータ転送用オブジェクト  

### メソッド名（`lowerCamelCase`）
- `registerProject()`: 動詞＋対象 の形で統一  
- `getProjectList()`, `updateRequirementItem()` など

### 定数名（`UPPER_SNAKE_CASE`）
- `DEFAULT_TIMEOUT_SEC`  
- `MAX_LENGTH_TITLE`

### DB名／カラム名（`snake_case`）
- テーブル名：`project_info`, `requirement_item`  
- カラム名：`project_id`, `update_time`

## ✅ ファイル配置ルール

- ユースケース単位の処理 → `service.application`
- DB関連のDTO・操作 → `service.repository`
- 複数機能にまたがるユーティリティ → `util`

## 📁 フォルダ構成（抜粋）

