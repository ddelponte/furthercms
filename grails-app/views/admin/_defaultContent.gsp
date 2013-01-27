<theme:zone name="body">
    <ui:displayMessage/>
    <ui:h1 text="sidebar.page.body.heading"/>
    <fc:navTree category="${com.merrycoders.furthercms.Category.findByUrlKey('')}" style="float: left;"/>
    <p>Main Content</p>
</theme:zone>

<theme:zone name="sidebar">
    <ui:block title="Your profile">
        <ui:avatar user="marc@anyware.co.uk" size="50"/>
        <p>Not everybody is this ugly</p>
    </ui:block>
</theme:zone>