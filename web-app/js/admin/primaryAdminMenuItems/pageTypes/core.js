jQuery("form a.save").click(function (event) {
    event.preventDefault();
    var form = jQuery("#pageTypeForm");
    var actionUrl = form.attr("data-update-action-url");
    form.attr("action", actionUrl);
    form.submit();
});

jQuery("form a.delete").click(function (event) {
    var form = jQuery("#pageTypeForm");
    event.preventDefault();
    var deleteActionConfirmMessage = form.attr("data-delete-action-confirm-message");
    var confirmDelete = confirm(deleteActionConfirmMessage);

    if (confirmDelete) {
        var actionUrl = form.attr("data-delete-action-url");
        form.attr("action", actionUrl);
        form.submit()
    }

});