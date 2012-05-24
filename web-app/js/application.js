if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);

    $('input.errors').focus(function() {
        $(this).removeClass('errors');
        $(this).nextAll('.InputErrors').first().hide();
        return true;
    });

    $(".closeFlash").click(function () {
        $(".flashMessage").hide('fast');
        return false;
    });
}


