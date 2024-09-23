document.addEventListener('DOMContentLoaded', function() {
	var title = document.querySelectorAll('td>a');
    title.forEach(function(spread) {
        spread.parentElement.addEventListener('click', function() {
            window.location.href = spread.href;
        });
    });
});