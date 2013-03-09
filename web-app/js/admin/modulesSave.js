jQuery(document).ready(function () {

    $("section#main-editable-content a.btn").click(function (event) {
        event.preventDefault();
        CKupdate();
        var moduleForms = jQuery("section#main-editable-content form");

        moduleForms.each(function () {
            alert($(this).attr("action"));
            jQuery(this).ajaxSubmit({
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

//    var moduleFormInputs = jQuery("section#main-editable-content form input");
//    moduleFormInputs.change(function () {
//        alert($(this).attr("name"));
//    });

});