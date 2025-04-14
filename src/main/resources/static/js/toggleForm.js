/**
 * 
 * 以下のようなデータOkusei を持つ要素をBody内に定義すること。
 * <div id="message-container" th:data-add="#{label.column.add}" th:data-close="#{label.close}"></div>
 */
//$(function () {
//	const messagesDiv = $("#message-container");
//	if (messagesDiv.length === 0) return;
//
//	const messages = {
//		add: messagesDiv.data("add"),
//		close: messagesDiv.data("close")
//	};
//
//	window.toggleForm = function (id) {
//		const form = $("#form" + id);
//		const btn = $("#toggleBtn" + id);
//
//		if (form.length && btn.length) {
//			const isHidden = form.css("display") === "none";
//			form.toggle();
//			btn.text(isHidden ? messages.close : messages.add);
//		}
//	};
//});
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

