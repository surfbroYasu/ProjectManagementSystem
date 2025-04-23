/**
 * 
 */

$(function () {
	// すべての isFK チェックボックスを処理対象にする
	$('input[type="checkbox"][id^="isFK"]').each(function () {
		const checkbox = $(this);
		const id = checkbox.attr('id').replace('isFK', 'fkTarget');

		const fkTarget = $('#' + id);

		// 初期表示制御
		if (checkbox.prop('checked')) {
			fkTarget.show();
		} else {
			fkTarget.hide();
		}

		// 状態変更時の制御（動的切り替えも対応）
		checkbox.on('change', function () {
			fkTarget.toggle(this.checked);
		});
	});
});