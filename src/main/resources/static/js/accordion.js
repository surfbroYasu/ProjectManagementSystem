/**
 * 
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
