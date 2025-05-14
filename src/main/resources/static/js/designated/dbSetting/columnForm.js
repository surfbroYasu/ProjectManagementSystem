/**
 * ファイルパス
 * src\main\resources\static\js\designated\dbSetting\columnForm.js
 */

$(function () {
	// すべての isForign チェックボックスを処理対象にする
	$('input[type="checkbox"][id^="isForign"]').each(function () {
		const checkbox = $(this);
		const id = checkbox.attr('id').replace('isForign', 'fkTarget');

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

window.getColumnRowData = function(id) {
	const row = $("#colRow" + id);
	return {
		id: row.data("id"),
		columnName: row.data("column-name"),
		alias: row.data("alias"),
		dataType: row.data("data-type"),
		dataTypeParam: row.data("data-type-param"),
		isPrimary: row.data("is-primary"),
		isUnique: row.data("is-unique"),
		isForign: row.data("is-forign"),
		isNullable: row.data("is-nullable"),
		isAutoIncrement: row.data("is-auto-increment"),
		defaultValue: row.data("default-value"),
		checkConstraint: row.data("check-constraint"),
		onDelete: row.data("on-delete"),
		onUpdate: row.data("on-update"),
		comment: row.data("comment"),
		forignId: row.data("forign-id"),
		refColumnName: row.data("ref-column-name")
	};
};

window.openColumnModalFromRow = function(id) {
	const data = id === 0 ? null : getColumnRowData(id);
	openColumnModal(id, data);
};

window.openColumnModal = function(id, data) {
	const modalElem = document.getElementById("columnModal");
	const modal = new bootstrap.Modal(modalElem);
	const form = $("#columnForm")[0];
	const isNew = (id === 0);

	// modalElem.querySelector(".modal-type-add").classList.toggle("d-none", !isNew);
	// modalElem.querySelector(".modal-type-edit").classList.toggle("d-none", isNew);
	modalElem.querySelectorAll(".modal-type-add").forEach(el => {
		el.classList.toggle("d-none", !isNew);
	  });
	  modalElem.querySelectorAll(".modal-type-edit").forEach(el => {
		el.classList.toggle("d-none", isNew);
	  });
	  

	if (!form) return;

	const $form = $(form);

	if (isNew) {
		form.reset();
		$form.find('input[name="id"]').val(0);
	} else if (data) {
		Object.entries(data).forEach(([key, value]) => {
			const $field = $form.find(`[name="${key}"]`);
			if ($field.attr("type") === "checkbox") {
				$field.prop("checked", String(value).toLowerCase() === "true");
			} else {
				$field.val(value ?? "");
			}
		});

		const showFK = data.isForign === true;
		$("#fkTarget").toggle(showFK);
	}

	modal.show();
};
