<g:javascript>
    $(document).ready(function() {
        $('input[name="selectAllProjects"]').click(function() {
            $(":checkbox").attr('checked', $(this).is(':checked'));
            if ($('input:checked[name=achivedProjectChbx]').length > 0 ) {
                $('#archivePrjsBtn').show();
                $('#archivePrjsDisableBtn').hide();
            } else {
                $('#archivePrjsBtn').hide();
                $('#archivePrjsDisableBtn').show();
            }
        });

        $("#archiveForm").bind( "submit", function() {
            var form = this;
            $.each($('input:checked[name=achivedProjectChbx]'),function(i, val){

                $("<input>").attr('type','hidden').
                        attr('name','achivedProjectId').
                        attr('value',val.value).appendTo(form);
            });
//            console.log( $( this ).serialize() );
            return true;
        });

        $('#archivePrjsBtn').click(function() {
            $(this).closest("form").submit();
        });

        $('input[name=achivedProjectChbx]').change(function() {
            if ($('input:checked[name=achivedProjectChbx]').length > 0 ) {
                $('#archivePrjsBtn').show();
                $('#archivePrjsDisableBtn').hide();
            } else {
                $('#archivePrjsBtn').hide();
                $('#archivePrjsDisableBtn').show();
            }
        })
    });
</g:javascript>