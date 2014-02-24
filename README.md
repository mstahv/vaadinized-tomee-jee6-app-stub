# "Vaadinized" JEE6 app stub for TomEE

Apache TomEE is an increasingly popular certified JEE server that is based on Tomcat and other Apache libraries like OpenJPA and OpenEJB.

TomEE has a small and clean archetype (tomee-webapp-archetype) for JPA backed web apps. It implements a simple book inventory app, but the default UI is built with faces and is rather harsh. To help you to get started quicker with [my favourite UI technology](https://vaadin.com), I modified the generated app stub to replicate the same UI with Vaadin.

In addition to just reading and adding book entities, I also completed the exercise to be a full CRUD example with a floating editor window for book entities. The editor also contains an example how one can de-couple stuff in UI code using CDI events.

Steps to convert your faces UI into a modern RIA app:

 * Create an app stub with archetype org.apache.openejb.maven:tomee-webapp-archetype
 * Add Vaadin dependencies + some helpers [see change](https://github.com/mstahv/vaadinized-tomee-jee6-app-stub/commit/74fb12f37a8692a64edfa0e10b566ed7fdc14b67)
 * Add @CDIUI annotated Vaadin UI, now the Vaadin UI is in par in functionality with the faces generated stub [change](https://github.com/mstahv/vaadinized-tomee-jee6-app-stub/commit/267eb7a8aa7096c3672b70ace63f1022cbfa8818)
 * Clean up the project by removing "faces dirt" [change](https://github.com/mstahv/vaadinized-tomee-jee6-app-stub/commit/262ebecefc2e16f15f41e566039a17d383256924)
 * Enhance the app to a full CRUD example [change](https://github.com/mstahv/vaadinized-tomee-jee6-app-stub/commit/023ab349bb40c9019ecf5974ec83185c233c0569)

