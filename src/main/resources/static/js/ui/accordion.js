/**
 * ============================================================================
 * Bootstrap Accordion 状態保持スクリプト
 * ============================================================================
 *
 * ▼ 目的：
 * Bootstrapのアコーディオン（.accordion-collapse）の開閉状態を
 * sessionStorageに記録して、ページ再読み込み後も状態を復元する。
 *
 *
 * ▼ 必須要素：
 * - 対象のアコーディオンには一意のIDが必要（例: id="collapseExample1"）
 * - Bootstrap 5 の Collapse 機能が有効であること
 *
 *
 * ▼ 自動動作：
 * - ページ読み込み時：
 *   - sessionStorage から開いていたIDリストを取得して、表示状態を復元
 *
 * - アコーディオンを開いたとき：
 *   - そのIDを sessionStorage に追加
 *
 * - アコーディオンを閉じたとき：
 *   - そのIDを sessionStorage から削除
 *
 *
 * ▼ HTML構成例（Bootstrap 5 標準）：
 *
 * <div class="accordion" id="accordionExample">
 *   <div class="accordion-item">
 *     <h2 class="accordion-header" id="headingOne">
 *       <button class="accordion-button" type="button" data-bs-toggle="collapse"
 *               data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
 *         セクション1
 *       </button>
 *     </h2>
 *     <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
 *       <div class="accordion-body">
 *         コンテンツ内容
 *       </div>
 *     </div>
 *   </div>
 * </div>
 *
 * ============================================================================
 */

document.addEventListener('DOMContentLoaded', function() {
	const openAccordions = JSON.parse(sessionStorage.getItem('openAccordionIds') || '[]');

	openAccordions.forEach(id => {
		const el = document.getElementById(id);
		if (el) {
			new bootstrap.Collapse(el, { toggle: false }).show();
		}
	});

	document.querySelectorAll('.accordion-collapse').forEach(function(item) {
		item.addEventListener('show.bs.collapse', function() {
			let ids = JSON.parse(sessionStorage.getItem('openAccordionIds') || '[]');
			if (!ids.includes(item.id)) {
				ids.push(item.id);
				sessionStorage.setItem('openAccordionIds', JSON.stringify(ids));
			}
		});

		item.addEventListener('hide.bs.collapse', function() {
			let ids = JSON.parse(sessionStorage.getItem('openAccordionIds') || '[]');
			ids = ids.filter(id => id !== item.id);
			sessionStorage.setItem('openAccordionIds', JSON.stringify(ids));
		});
	});
});