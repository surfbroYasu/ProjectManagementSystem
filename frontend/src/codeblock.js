import hljs from 'highlight.js/lib/core';
import sql from 'highlight.js/lib/languages/sql';
import java from 'highlight.js/lib/languages/java';
import 'highlight.js/styles/color-brewer.css';

import ClipboardJS from 'clipboard';

// 使用したい言語をここで登録
hljs.registerLanguage('sql', sql);
hljs.registerLanguage('java', java);

// 全コードブロックにハイライト適用
hljs.highlightAll();



function showCopyAlert() {
	const toast = document.getElementById('copyToast');
	toast.style.display = 'block';
	toast.classList.add('show');

	setTimeout(() => {
		toast.classList.remove('show');
		// Bootstrapの fade out アニメーション後に非表示
		setTimeout(() => {
			toast.style.display = 'none';
		}, 150);
	}, 2000);
}

document.addEventListener('DOMContentLoaded', () => {
	new ClipboardJS('.clipboard').on('success', showCopyAlert);
});