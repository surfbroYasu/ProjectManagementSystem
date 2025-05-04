/**
 * ================================================================
 * 共通UI操作スクリプト（toggleSection / openModal）
 * ================================================================
 *
 * ▼ 前提：HTML内に以下のようなdata属性を持つdivを定義してください
 *
 * <div id="message-container"
 *      data-add="追加"
 *      data-close="閉じる"
 *      data-edit="編集"
 *      data-delete="削除">
 * </div>
 *
 * ▼ 機能1：toggleSection({ id, sectionPrefix, buttonPrefix, labelType })
 * ---------------------------------------------------------------
 * セクションの表示/非表示を切り替え、ボタンの表示テキストを変更します。
 *
 * - id: 対象の識別子（例: "42"）
 * - sectionPrefix: 切り替える対象の要素IDのprefix（例: "section" → "section42"）
 * - buttonPrefix: ボタン要素IDのprefix（例: "button" → "button42"）
 * - labelType: メッセージ種類（"add" | "edit" など）デフォルト: "add"
 *
 * 【HTML例】
 * <div id="section42" style="display:none;">セクション内容</div>
 * <button id="button42" onclick="toggleSection({ id: '42', sectionPrefix: 'section', buttonPrefix: 'button', labelType: 'edit' })">編集</button>
 *
 *
 * ▼ 機能2：openModal(id, modalPrefix)
 * ---------------------------------------------------------------
 * Bootstrapモーダルを表示します。
 *
 * - id: モーダルの識別子（例: "99"）
 * - modalPrefix: モーダル要素のprefix（例: "editModal" → "editModal99"）
 *
 * 【HTML例】
 * <div class="modal fade" id="editModal99">...</div>
 * <button onclick="openModal('99', 'editModal')">編集</button>
 *
 * ---------------------------------------------------------------
 */

$(function () {
	const messagesDiv = $("#message-container");
	const messages = {
		add: messagesDiv?.data("add") || "追加",
		close: messagesDiv?.data("close") || "閉じる",
		edit: messagesDiv?.data("edit") || "編集",
		delete: messagesDiv?.data("delete") || "削除"
	};

	window.toggleSection = function ({ id, sectionPrefix, buttonPrefix, labelType = "add" }) {
		const section = $("#" + sectionPrefix + id);
		const button = $("#" + buttonPrefix + id);
		if (section.length && button.length) {
			const isHidden = section.css("display") === "none";
			section.toggle();
			button.text(isHidden ? messages.close : messages[labelType]);
		}
	};

	window.openModal = function (id, modalPrefix) {
		const modal = $("#" + modalPrefix + id);
		if (modal.length) {
			modal.modal("show");
		}
	};
});