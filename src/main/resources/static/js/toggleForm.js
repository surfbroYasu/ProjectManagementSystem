/**
 * 
 * 以下のようなデータOkusei を持つ要素をBody内に定義すること。
 * <div id="message-container" th:data-add="#{label.column.add}" th:data-close="#{label.close}"></div>
 */
$(function () {
	const messagesDiv = $("#message-container");
	if (messagesDiv.length === 0) return;

	const messages = {
		add: messagesDiv.data("add"),
		close: messagesDiv.data("close")
	};

	window.toggleForm = function (id) {
		const form = $("#form" + id);
		const btn = $("#toggleBtn" + id);

		if (form.length && btn.length) {
			const isHidden = form.css("display") === "none";
			form.toggle();
			btn.text(isHidden ? messages.close : messages.add);
		}
	};
});
