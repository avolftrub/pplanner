$(document).ready(function () {

    $(".deleteUserLink").live('click', function() {
        return confirm($(this).attr('helpertext'));
    })

    $("#changePassword").live('click', function() {
        $(this).closest('tr').nextAll('.pwdBlock').children('td').children('input').attr("value", "");
        $(this).closest('tr').nextAll('.pwdBlock').show();
        $(this).hide();
        $("#changePwdFlag").attr("value", "true");
        return false;
    })
});
