import org.springframework.orm.hibernate3.FilterDefinitionFactoryBean

import com.nanlabs.grails.plugin.logicaldelete.DeleteHibernateFilterConfigurator
import com.nanlabs.grails.plugin.logicaldelete.DeleteHibernateFilterEnabler
import com.nanlabs.grails.plugin.logicaldelete.LogicalDeleteDomainClassEnhancer

// TODO
class LogicalDeleteGrailsPlugin {
	def version = "0.2"
	def grailsVersion = "2.0 > *"
	def title = "Logical Delete Plugin"
	def description = 'Allows you to do a logical deletion of domain classes'
	def documentation = "http://grails.org/plugin/logical-delete"
	def loadAfter = ['hibernate']

	def license = "APACHE"
	def organization = [name: "NaN Labs", url: "http://www.nan-labs.com/"]
	def developers = [
		[name: "Ezequiel Parada", email: "ezequiel@nan-labs.com"]
	]
	def issueManagement = [system: 'GITHUB', url: 'https://github.com/nanlabs/logical-delete/issues']
	def scm = [url: 'https://github.com/nanlabs/logical-delete']

	def doWithSpring = {

		logicDeleteHibernateFilter(FilterDefinitionFactoryBean) {
			//defaultFilterCondition = "deleted = :deletedValue"
			defaultFilterCondition = "deleted IS NULL"
			parameterTypes = [deletedValue: "java.util.Date"]
		}

		deleteHibernateFilterConfigurator(DeleteHibernateFilterConfigurator) {
			deleteFilterDefinition = ref("logicDeleteHibernateFilter")
		}

		deleteHibernateFilterEnabler(DeleteHibernateFilterEnabler) {
			deleteHibernateFilter = ref("logicDeleteHibernateFilter")
		}
	}

	def doWithDynamicMethods = { ctx ->
		LogicalDeleteDomainClassEnhancer.setLogicDeleteFilter(ctx.getBean("logicDeleteHibernateFilter"))
		LogicalDeleteDomainClassEnhancer.enhance(application.domainClasses)
	}
	
	def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
