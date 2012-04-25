$(document).ready(function () {

    $(".deleteDealerLink").live('click', function() {
        return confirm($(this).attr('helpertext'));
    })
});
