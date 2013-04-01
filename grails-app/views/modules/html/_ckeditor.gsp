<r:require modules="jquery, jquery-ui, htmlModuleOnSort"/>

%{--Takes a name and data in the model--}%
<ckeditor:config var="toolbar_Mytoolbar" enterMode="CKEDITOR.ENTER_DIV">
    [
        ['Bold', 'Italic', 'Underline', 'Link', 'Unlink', '-', 'Styles', 'Format',
            'FontSize', '-', 'TextColor','BGColor', '-', 'NumberedList', 'BulletedList']
    ]
</ckeditor:config>

<div class="html_ckeditor"
     data-html-module-editor-action="${g.createLink([controller: 'htmlModule', action: 'renderEditor', params: [name: name]])}">

    <ckeditor:editor
            name="${name}"
            height="${height ?: '100%'}"
            width="${width ?: '100%'}"
            toolbar="Mytoolbar">
        ${data ?: ""}
    </ckeditor:editor>

</div>