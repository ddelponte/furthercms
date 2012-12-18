<theme:zone name="navigation">
    <nav:primary custom="true" scope="plugin.furthercms.app">
        <g:each in="${primaryCategoryInstanceList}" var="primaryCategory">
            <li ${(primaryCategory?.category in activePrimaryNavigationCategoryInstanceList) ? 'class=\"active\"' : ''}>
                <p:callTag tag="g:link" attrs="${[url: "/${grailsApplication.config.grails.furthercms.app.context}/${primaryCategory?.category?.urlKey ?: ''}"]}">
                    <nav:title item="${[titleMessageCode: primaryCategory?.category?.code, titleDefault: primaryCategory?.category?.toString()]}"/>
                </p:callTag>
            </li>
        </g:each>
    </nav:primary>
</theme:zone>