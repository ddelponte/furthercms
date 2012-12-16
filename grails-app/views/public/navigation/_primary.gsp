<theme:zone name="navigation">
    <nav:primary custom="true" scope="plugin.furthercms.app">
        <ul>
            <g:each in="${primaryCategoryInstanceList}" var="primaryCategory">
                <li ${(primaryCategory?.category in activePrimaryNavigationCategoryInstanceList) ? 'class=\"active\"' : ''}>
                    <p:callTag tag="g:link" attrs="${[url: "${primaryCategory?.category?.urlKey}"]}">
                        <nav:title item="${[titleMessageCode: "code", titleDefault: primaryCategory?.category]}"/>
                    </p:callTag>
                </li>
            </g:each>
        </ul>
    </nav:primary>
</theme:zone>