<r:require modules="reorderModules"/>

<ui:button class="reorder" kind="anchor" mode="secondary" text="plugin.furthercms.reorder"/>

<div id="reorder-modules-dialog-form" title="${g.message([code: 'plugin.furthercms.reorder.modules'])}" style="display: none;">

    <p class="validateTips">Unsaved data may be lost</p>

    <g:form>

        <ul class="sortable">

        </ul>

        <g:hiddenField name="sortedModules" value=""/>

    </g:form>

</div>