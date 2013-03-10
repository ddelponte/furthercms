<r:require modules="jquery, jquery-ui"/>

<label><g:message code="plugin.furthercms.module.html.label"/></label>

%{--Takes a name and data in the model--}%
<ckeditor:config var="toolbar_Mytoolbar" enterMode="CKEDITOR.ENTER_DIV">
    [
        ['Bold', 'Italic', 'Underline', 'Link', 'Unlink', '-', 'Styles', 'Format',
            'FontSize', '-', 'TextColor','BGColor', '-', 'NumberedList', 'BulletedList']
    ]
</ckeditor:config>

<ckeditor:editor name="${name}" height="${height ?: '100%'}" width="${width ?: '100%'}" toolbar="Mytoolbar">
    ${data}
</ckeditor:editor>