jQuery(document).ready(function () {

    jQuery("section#modules-edit div a.new").click(function (event) {
        event.preventDefault()
        createAndSaveNewCategory()
    });

});

function createAndSaveNewCategory() {

    var newCategoryButton = jQuery("section#modules-edit div a.new");
    var createAndSaveNewCategoryUrl = jQuery("section#modules-edit div a.new").attr("data-url");
    var dataSuccessUrl = jQuery("section#modules-edit div a.new").attr("data-success-url");

    jQuery.ajax(createAndSaveNewCategoryUrl)
            .done(function (jsonData) {
                if (!jsonData.success) {
                    alert(jsonData.message);
                } else {
                    var newCategoryId = jsonData.message;
                    window.location = dataSuccessUrl + "/" + newCategoryId; // open url
                }
            })
            .fail(function (jsonData) {
                alert(jsonData.message)
            })

}