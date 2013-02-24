<theme:zone name="body">

    <ui:displayMessage/>

    <ui:h1 text="dataentry.page.body.heading"/>

    <section id="category_nav">

        <nav>

            <fc:navTree category="${com.merrycoders.furthercms.Category.findByUrlKey('')}" selectedNodeId="${categoryInstance?.id}"/>

        </nav>

    </section>

    <section id="content">

        <p>Main Content</p>

    </section>

</theme:zone>