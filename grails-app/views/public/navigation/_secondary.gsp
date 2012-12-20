<theme:zone name="secondary-navigation">
    <nav:menu custom="true" scope="plugin.furthercms.app" class="nav nav-pills">
        <g:each in="${secondaryCategoryInstanceList}" var="secondaryCategory">
            <li ${(secondaryCategory in activeSecondaryNavigationCategoryInstanceList) ? 'class=\"active\"' : ''}>
                <p:callTag tag="g:link" attrs="${[url: "/${appContext}/${secondaryCategory?.urlKey ?: ''}"]}">
                    <nav:title item="${[titleMessageCode: secondaryCategory?.code, titleDefault: secondaryCategory?.toString()]}"/>
                </p:callTag>
            </li>
        </g:each>
    </nav:menu>
</theme:zone>