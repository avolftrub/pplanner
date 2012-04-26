$(document).ready(function () {
    $.datepicker.setDefaults( $.datepicker.regional[ "ru" ] );
//    $( "#datepicker" ).datepicker( $.datepicker.regional[ "ru" ] );                               `

    var dp = $("#releaseDate").datepicker({
        dateFormat: 'dd.mm.yy',
        buttonImage: "../images/delete.png",
        changeYear: false,
        numberOfMonths: 1,
        firstDay: 1

//        showOn: 'both'
    });

//    dp.setDate($.datepicker.parseDate("#releaseDate").val())
    var setdate = $('#releaseDate').val();
//    var qqq= $.datepicker.parseDate($("#releaseDate").val());
    $("#releaseDate").datepicker('setDate', setdate);
//    $("#releaseDate").datepicker('setDate', $.datepicker.parseDate("#releaseDate"));


//    $("#releaseDate").datepicker($.datepicker.regional['ru']);

//    $("imput.calendarInput").each(function(){
//        $(this).add($(this).next()).wrapAll('<div class="calendatInputWrapper"></div>');
//    });


    $(".deleteProjectLink").live('click', function() {
        return confirm($(this).attr('helpertext'));
    })

    var options = { source: $("#city").attr("lookupUrl") };
    $("#city").autocomplete(options);
});
