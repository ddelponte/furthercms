<ul class="nav">
    <g:each in="${primaryCategoryInstanceList}" var="category">
        <li class="${category == activePrimaryCategory ? 'active' : ''}">
            <g:link controller="requestDispatch" action="dispatch" params="[path: category.urlKey]">
                ${category}
            </g:link>
        </li>
    </g:each>
</ul>