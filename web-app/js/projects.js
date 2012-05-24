$(document).ready(function () {
    $.datepicker.setDefaults( $.datepicker.regional[ "ru" ] );
//    $( "#datepicker" ).datepicker( $.datepicker.regional[ "ru" ] );                               `

    var rd = $("#releaseDate").datepicker({
        dateFormat: 'dd.mm.yy',
        buttonImage: "../images/delete.png",
        changeYear: false,
        numberOfMonths: 1,
        firstDay: 1
    });
    $("#releaseDate").datepicker('setDate', $('#releaseDate').val());

    var cd = $("#closeDate").datepicker({
        dateFormat: 'dd.mm.yy',
        buttonImage: "../images/delete.png",
        changeYear: false,
        numberOfMonths: 1,
        firstDay: 1
    });
    $("#closeDate").datepicker('setDate', $('#closeDate').val());

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

//
//    $("#advancedSearch").live('click', function() {
//        $("#advancedSearchBlock").show("slow");
//        $("#advancedSearch").toggle();
//        $("#advancedSearchClose").toggle();
//    });
//
//    $("#advancedSearchClose").live('click', function() {
//        $("#advancedSearchBlock").hide("slow");
//        $("#advancedSearch").toggle();
//        $("#advancedSearchClose").toggle();
//    });
    $(".addCommment").click(function () {
        var template = $(".CommentTemplate");
        $(".noComments").hide();
        template.show('fast');
        return false;
    });

    $(".cancelComment").click(function () {
        var template = $(".CommentTemplate");
        $(".CommentTemplate > form > textarea").val("");
        template.hide('fast');
        $(".noComments").show();
        return false;
    });

    $(".deleteCommentLink").live('click', function() {
        return confirm($(this).attr('helpertext'));
    })

    $(".addDocumentLink").click(function () {
        $(".addDocumentLink").hide();
        $(".uploadDocumentForm").show('fast');
        return false;
    });

    $(".cancelUpload").click(function () {
        $(".uploadDocumentForm").hide();
        $(".addDocumentLink").show();
        return false;
    });
});
