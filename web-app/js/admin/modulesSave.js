jQuery(document).ready(function () {

    $("section#modules-edit a.btn").click(function (event) {
        event.preventDefault();
        CKupdate();

        var moduleForms = jQuery("section#modules-edit form");
        moduleForms.each(function () {
            event.preventDefault();

            $(this).ajaxSubmit({
                success: function () {
                    console.log('success');
                },
                error: function () {
                    console.log('error');
                }
            });

        });

    });

    function CKupdate() {
        for (instance in CKEDITOR.instances) {
            CKEDITOR.instances[instance].updateElement();
        }
    }

//    var moduleFormInputs = jQuery("section#modules-edit form input");
//    moduleFormInputs.change(function () {
//        alert($(this).attr("name"));
//    });

});