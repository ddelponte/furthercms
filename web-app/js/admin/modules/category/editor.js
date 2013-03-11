jQuery(document).ready(function () {
    jQuery("section#modules-edit input#page\\.title").changed("changed", function (event, value) {
        var slug = convertToSlug(value);
        jQuery("section#modules-edit div.plugin\\.furthercms\\.category\\.urlkey\\.label span").text(slug);
    });
});